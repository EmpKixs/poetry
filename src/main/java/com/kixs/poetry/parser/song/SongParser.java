package com.kixs.poetry.parser.song;

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
 * 宋诗词解析
 *
 * @author suyixing
 * @version v1.0.0
 * @since 2020/8/19 13:10
 */
@Slf4j
public class SongParser implements PoetryParser {

    @Override
    public String dynasty() {
        return "宋朝";
    }

    @Override
    public ParseContext parse(String filePath) {
        // 作者解析
        String authorFile = filePath + "\\authors.song.json";
        String authorData = FileUtils.read(authorFile);
        List<SongAuthor> songAuthors = JSON.parseArray(authorData, SongAuthor.class);
        ParseContext context = new ParseContext();
        songAuthors.stream().parallel().forEach(song -> {
            Author author = new Author();
            author.setId(IdWorker.getIdStr());
            author.setName(song.getName());
            author.setDynasty(dynasty());
            author.setDescription(song.getDesc());
            context.putAuthor(author);
        });
        // 诗词解析
        String pattern = "^poet.song.([0-9])*.json$";
        File[] files = FileUtils.listDirectoryFiles(filePath, (dir, filename) -> Pattern.matches(pattern, filename));
        if (files != null && files.length > 0) {
            Stream.of(files).parallel().forEach(file -> {
                String data = FileUtils.read(file);
                List<SongPoetry> poetries = JSON.parseArray(data, SongPoetry.class);
                poetries.stream().parallel().forEach(song -> {
                    Poetry poetry = new Poetry();
                    poetry.setId(IdWorker.getIdStr());
                    poetry.setTitle(song.getTitle());
                    Author author = context.getAuthor(this::generateDynastyAuthorKey, song.getAuthor());
                    if (Objects.nonNull(author)) {
                        poetry.setAuthorId(author.getId());
                    }
                    poetry.setContent(song.getParagraphs());
                    context.addPoetry(poetry);
                });
            });
        }
        log.debug("解析数据：作者-{}，诗词-{}", context.getAuthorMap().size(), context.getPoetries().size());
        return context;
    }

    public static void main(String[] args) {
        String filePath = "D:\\Github\\chinese-poetry\\json";
        SongParser parser = new SongParser();
        parser.parse(filePath);
    }
}
