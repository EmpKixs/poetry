package com.kixs.poetry.parser.wudai;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.kixs.poetry.constant.ParserConstant;
import com.kixs.poetry.entity.Author;
import com.kixs.poetry.entity.Poetry;
import com.kixs.poetry.enums.PoetryType;
import com.kixs.poetry.parser.ParseContext;
import com.kixs.poetry.parser.PoetryParser;
import com.kixs.poetry.parser.songci.SongCi;
import com.kixs.poetry.parser.songci.SongCiAuthor;
import com.kixs.poetry.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * 五代诗词-南唐二主词解析
 *
 * @author suyixing
 * @version v1.0.0
 * @since 2020/8/19 13:10
 */
@Slf4j
@Service(ParserConstant.WUDAI_NANTANG_POETRY)
public class NanTangParser implements PoetryParser {

    @Override
    public String dynasty() {
        return "五代十国";
    }

    @Override
    public ParseContext parse(String filePath) {
        // 作者解析
        String authorFile = filePath + "\\authors.json";
        String authorData = FileUtils.read(authorFile);
        List<WudaiAuthor> nanTangAuthors = JSON.parseArray(authorData, WudaiAuthor.class);
        ParseContext context = new ParseContext();
        nanTangAuthors.stream().parallel().forEach(nantang -> {
            Author author = new Author();
            author.setId(IdWorker.getIdStr());
            author.setName(nantang.getName());
            author.setDynasty(dynasty());
            author.setDescription(nantang.getDesc());
            context.putAuthor(author);
        });
        // 诗词解析
        String poetryFile = filePath + "\\poetrys.json";
        String poetryData = FileUtils.read(poetryFile);
        List<WudaiPoetry> poetries = JSON.parseArray(poetryData, WudaiPoetry.class);
        poetries.stream().parallel().forEach(wudai -> {
            Poetry poetry = new Poetry();
            poetry.setId(IdWorker.getIdStr());
            poetry.setTitle(wudai.getTitle());
            poetry.setType(PoetryType.CI.getCode());
            poetry.setRhythmic(wudai.getRhythmic());
            if (StringUtils.isNotBlank(wudai.getAuthor())) {
                Author author = context.getAuthor(this::generateDynastyAuthorKey, wudai.getAuthor());
                if (Objects.nonNull(author)) {
                    poetry.setAuthorId(author.getId());
                } else {
                    poetry.setNotes("##" + wudai.getAuthor() + "，此作者未在作者列表中查询到##");
                }
            }
            poetry.setContent(wudai.getParagraphs());
            poetry.setNotes(wudai.getNotes());
            context.addPoetry(poetry);
        });
        log.debug("解析五代诗词-南唐二主词数据：作者-{}，诗词-{}", context.getAuthorMap().size(), context.getPoetries().size());
        return context;
    }

    public static void main(String[] args) {
        String filePath = "D:\\Github\\chinese-poetry\\wudai\\nantang";
        NanTangParser parser = new NanTangParser();
        parser.parse(filePath);
    }
}
