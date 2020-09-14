package com.kixs.poetry.parser.wudai;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.kixs.poetry.entity.Author;
import com.kixs.poetry.entity.Poetry;
import com.kixs.poetry.parser.ParseContext;
import com.kixs.poetry.parser.PoetryParser;
import com.kixs.poetry.parser.songci.SongCi;
import com.kixs.poetry.parser.songci.SongCiAuthor;
import com.kixs.poetry.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;

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
public class NanTangParser implements PoetryParser {

    @Override
    public ParseContext parse(String filePath) {
        // 作者解析
        String authorFile = filePath + "\\authors.json";
        String authorData = FileUtils.read(authorFile);
        List<WudaiAuthor> songCiAuthors = JSON.parseArray(authorData, WudaiAuthor.class);
        ParseContext context = new ParseContext();
        songCiAuthors.stream().parallel().forEach(nantang -> {
            Author author = new Author();
            author.setId(IdWorker.getIdStr());
            author.setName(nantang.getName());
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
            poetry.setTitle(wudai.getRhythmic());
            Author author = context.getAuthor(wudai.getAuthor());
            if (Objects.nonNull(author)) {
                poetry.setAuthorId(author.getId());
            }
            poetry.setContent(wudai.getParagraphs());
            context.addPoetry(poetry);
        });
        log.debug("解析数据：作者-{}，诗词-{}", context.getAuthorMap().size(), context.getPoetries().size());
        return context;
    }

    public static void main(String[] args) {
        String filePath = "D:\\Github\\chinese-poetry\\wudai\\nantang";
        NanTangParser parser = new NanTangParser();
        parser.parse(filePath);
    }
}
