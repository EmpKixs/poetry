package com.kixs.poetry.parser.tang;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.kixs.poetry.constant.ParserConstant;
import com.kixs.poetry.entity.Author;
import com.kixs.poetry.entity.Poetry;
import com.kixs.poetry.enums.PoetryType;
import com.kixs.poetry.parser.ParseContext;
import com.kixs.poetry.parser.PoetryParser;
import com.kixs.poetry.parser.yuanqu.YuanquPoetry;
import com.kixs.poetry.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * 水墨唐诗解析
 *
 * @author kixs
 * @version v1.0.0
 * @since 2023/9/28 14:47
 */
@Slf4j
@Service(ParserConstant.SHUIMO_TANG_POETRY)
public class ShuiMoTangParser implements PoetryParser {

    @Override
    public String dynasty() {
        return "唐";
    }

    @Override
    public ParseContext parse(String filePath) {
        ParseContext context = new ParseContext();
        // 诗词解析
        String file = filePath + "shuimotangshi.json";
        String data = FileUtils.read(file);
        List<ShuiMoTangPoetry> poetries = JSON.parseArray(data, ShuiMoTangPoetry.class);
        poetries.stream().parallel().forEach(tang -> {
            Poetry poetry = new Poetry();
            poetry.setId(IdWorker.getIdStr());
            poetry.setType(PoetryType.SHI.getCode());
            poetry.setTitle(tang.getTitle());
            Author author = context.getAuthor(this::generateDynastyAuthorKey, tang.getAuthor());
            if (Objects.isNull(author)) {
                synchronized (ShuiMoTangParser.class) {
                    author = context.getAuthor(this::generateDynastyAuthorKey, tang.getAuthor());
                    if (Objects.isNull(author)) {
                        author = new Author();
                        author.setId(IdWorker.getIdStr());
                        author.setName(tang.getAuthor());
                        author.setDynasty(dynasty());
                        context.putAuthor(author);
                    }
                }
            }
            poetry.setAuthorId(author.getId());
            poetry.setContent(tang.getParagraphs());
            if ("田家行".equals(tang.getTitle())) {
                if (StringUtils.isEmpty(tang.getParagraphs())) {
                    // 源数据文件中内容为空，加一个判断，填充相应内容
                    poetry.setContent("男声欣欣女颜悦，人家不怨言语别。\n" +
                            "五月虽热麦风清，檐头索索缲车鸣。\n" +
                            "野蚕作茧人不取，叶间扑扑秋蛾生。\n" +
                            "麦收上场绢在轴，的知输得官家足。\n" +
                            "不望入口复上身，且免向城卖黄犊。\n" +
                            "回家衣食无厚薄，不见县门身即乐。");
                }
            }
            poetry.setNotes(tang.getPrologue());
            context.addPoetry(poetry);
        });
        log.debug("解析水墨唐诗数据：作者-{}，诗词-{}", context.getAuthorMap().size(), context.getPoetryList().size());
        return context;
    }

    public static void main(String[] args) {
        String filePath = "D:\\Github\\chinese-poetry\\水墨唐诗\\";
        ShuiMoTangParser parser = new ShuiMoTangParser();
        parser.parse(filePath);
    }
}
