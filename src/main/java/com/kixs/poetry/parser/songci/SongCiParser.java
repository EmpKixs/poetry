package com.kixs.poetry.parser.songci;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.kixs.poetry.constant.ParserConstant;
import com.kixs.poetry.entity.Author;
import com.kixs.poetry.entity.Poetry;
import com.kixs.poetry.parser.ParseContext;
import com.kixs.poetry.parser.PoetryParser;
import com.kixs.poetry.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
@Service(ParserConstant.SONG_CI)
public class SongCiParser implements PoetryParser {
    @Override
    public String dynasty() {
        return "宋朝";
    }

    @Override
    public ParseContext parse(String filePath) {
        // 作者解析
        String authorFile = filePath + "\\author.song.json";
        String authorData = FileUtils.read(authorFile);
        List<SongCiAuthor> songCiAuthors = JSON.parseArray(authorData, SongCiAuthor.class);
        ParseContext context = new ParseContext();
        songCiAuthors.stream().parallel().forEach(song -> {
            Author author = new Author();
            author.setId(IdWorker.getIdStr());
            author.setName(song.getName());
            author.setDynasty(dynasty());
            author.setDescription(song.getDescription());
            author.setShortDescription(song.getShortDescription());
            context.putAuthor(author);
        });
        // 诗词解析
        String pattern = "^ci.song.([0-9])*.json$";
        File[] files = FileUtils.listDirectoryFiles(filePath, (dir, filename) -> Pattern.matches(pattern, filename));
        if (files != null && files.length > 0) {
            Stream.of(files).parallel().forEach(file -> {
                String data = FileUtils.read(file);
                List<SongCi> poetries = JSON.parseArray(data, SongCi.class);
                poetries.stream().parallel().forEach(song -> {
                    Poetry poetry = new Poetry();
                    poetry.setId(IdWorker.getIdStr());
                    poetry.setTitle(song.getRhythmic());
                    poetry.setRhythmic(song.getRhythmic());
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
        String filePath = "D:\\Github\\chinese-poetry\\ci";
        SongCiParser parser = new SongCiParser();
        parser.parse(filePath);
    }
}
