package com.kixs.poetry.parser.wudai;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.kixs.poetry.entity.Author;
import com.kixs.poetry.entity.Poetry;
import com.kixs.poetry.parser.ParseContext;
import com.kixs.poetry.parser.PoetryParser;
import com.kixs.poetry.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * 五代诗词-花间集解析
 *
 * @author suyixing
 * @version v1.0.0
 * @since 2020/8/19 13:10
 */
@Slf4j
public class HuajianjiParser implements PoetryParser {

    @Override
    public ParseContext parse(String filePath) {
        ParseContext context = new ParseContext();
        // 诗词解析
        String pattern = "^huajianji-\\w{1}-\\w*.json$";
        File[] files = FileUtils.listDirectoryFiles(filePath, (dir, filename) -> Pattern.matches(pattern, filename));
        if (files != null && files.length > 0) {
            Stream.of(files).parallel().forEach(file -> {
                String data = FileUtils.read(file);
                List<WudaiPoetry> poetries = JSON.parseArray(data, WudaiPoetry.class);
                poetries.stream().parallel().forEach(wudai -> {
                    Poetry poetry = new Poetry();
                    poetry.setId(IdWorker.getIdStr());
                    poetry.setTitle(wudai.getTitle());
                    poetry.setRhythmic(wudai.getRhythmic());
                    Author author = context.getAuthor(wudai.getAuthor());
                    if (Objects.isNull(author)) {
                        synchronized (HuajianjiParser.class) {
                            author = context.getAuthor(wudai.getAuthor());
                            if (Objects.isNull(author)) {
                                author = new Author();
                                author.setId(IdWorker.getIdStr());
                                author.setName(wudai.getAuthor());
                                context.putAuthor(author);
                            }
                        }
                    }
                    poetry.setAuthorId(author.getId());
                    poetry.setContent(wudai.getParagraphs());
                    poetry.setNotes(wudai.getNotes());
                    context.addPoetry(poetry);
                });
            });
        }
        log.debug("解析数据：作者-{}，诗词-{}", context.getAuthorMap().size(), context.getPoetries().size());
        return context;
    }

    public static void main(String[] args) {
        String filePath = "D:\\Github\\chinese-poetry\\wudai\\huajianji";
        HuajianjiParser parser = new HuajianjiParser();
        parser.parse(filePath);
    }
}
