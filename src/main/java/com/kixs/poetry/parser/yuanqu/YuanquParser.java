package com.kixs.poetry.parser.yuanqu;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.kixs.poetry.entity.Author;
import com.kixs.poetry.entity.Poetry;
import com.kixs.poetry.parser.ParseContext;
import com.kixs.poetry.parser.PoetryParser;
import com.kixs.poetry.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Objects;

/**
 * 元曲解析
 *
 * @author suyixing
 * @version v1.0.0
 * @since 2020/8/19 13:10
 */
@Slf4j
public class YuanquParser implements PoetryParser {

    @Override
    public String dynasty() {
        return "元朝";
    }

    @Override
    public ParseContext parse(String filePath) {
        ParseContext context = new ParseContext();
        // 诗词解析
        String file = filePath + "yuanqu.json";
        String data = FileUtils.read(file);
        List<YuanquPoetry> poetries = JSON.parseArray(data, YuanquPoetry.class);
        poetries.stream().parallel().forEach(qu -> {
            Poetry poetry = new Poetry();
            poetry.setId(IdWorker.getIdStr());
            poetry.setTitle(qu.getTitle());
            Author author = context.getAuthor(this::generateDynastyAuthorKey, qu.getAuthor());
            if (Objects.isNull(author)) {
                synchronized (YuanquParser.class) {
                    author = context.getAuthor(this::generateDynastyAuthorKey, qu.getAuthor());
                    if (Objects.isNull(author)) {
                        author = new Author();
                        author.setId(IdWorker.getIdStr());
                        author.setName(qu.getAuthor());
                        author.setDynasty(dynasty());
                        context.putAuthor(author);
                    }
                }
            }
            poetry.setAuthorId(author.getId());
            poetry.setContent(qu.getParagraphs());
            context.addPoetry(poetry);
        });
        log.debug("解析数据：作者-{}，诗词-{}", context.getAuthorMap().size(), context.getPoetries().size());
        return context;
    }

    public static void main(String[] args) {
        String filePath = "D:\\Github\\chinese-poetry\\yuanqu\\";
        YuanquParser parser = new YuanquParser();
        parser.parse(filePath);
    }
}
