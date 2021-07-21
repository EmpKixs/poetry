package com.kixs.poetry.parser.song;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.kixs.poetry.constant.ParserConstant;
import com.kixs.poetry.entity.Author;
import com.kixs.poetry.entity.Poetry;
import com.kixs.poetry.enums.PoetryType;
import com.kixs.poetry.parser.ParseContext;
import com.kixs.poetry.parser.PoetryParser;
import com.kixs.poetry.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
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
@Service(ParserConstant.SONG_POETRY)
public class SongParser implements PoetryParser {

    private Map<String, Poetry> strainsPoetryMap = new ConcurrentHashMap<>();

    @Override
    public String dynasty() {
        return "宋";
    }

    @Override
    public ParseContext parse(String filePath) {
        // 作者解析
        String authorFile = filePath + "\\json\\authors.song.json";
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
        File[] files = FileUtils.listDirectoryFiles(filePath + "\\json", (dir, filename) -> Pattern.matches(pattern, filename));
        if (files != null && files.length > 0) {
            Stream.of(files).parallel().forEach(file -> {
                String data = FileUtils.read(file);
                List<SongPoetry> poetries = JSON.parseArray(data, SongPoetry.class);
                poetries.stream().parallel().forEach(song -> {
                    Poetry poetry = new Poetry();
                    poetry.setId(IdWorker.getIdStr());
                    poetry.setType(PoetryType.SHI.getCode());
                    poetry.setTitle(song.getTitle());
                    if (StringUtils.isNotBlank(song.getAuthor())) {
                        Author author = context.getAuthor(this::generateDynastyAuthorKey, song.getAuthor());
                        if (Objects.nonNull(author)) {
                            poetry.setAuthorId(author.getId());
                        } else {
                            poetry.setNotes("##" + song.getAuthor() + "，此作者未在作者列表中查询到##");
                        }
                    }
                    poetry.setContent(song.getParagraphs());
                    strainsPoetryMap.put(song.getId(), poetry);
                    context.addPoetry(poetry);
                });
            });
        }
        // 韵律/声调/格律解析
        String strainsPattern = "^poet.song.([0-9])*.json$";
        File[] strainsFiles = FileUtils.listDirectoryFiles(filePath + "\\strains\\json", (dir, filename) -> Pattern.matches(strainsPattern, filename));
        if (strainsFiles != null && strainsFiles.length > 0) {
            Stream.of(strainsFiles).parallel().forEach(file -> {
                String data = FileUtils.read(file);
                List<SongStrains> strains = JSON.parseArray(data, SongStrains.class);
                strains.stream().parallel().forEach(s -> {
                    Poetry poetry = strainsPoetryMap.get(s.getId());
                    if (Objects.isNull(poetry)) {
                        log.error(file.getName() + "中[" + s.getId() + "]未找到对应诗词");
                    } else {
                        poetry.setStrains(s.getStrains());
                    }
                });
            });
        }
        log.debug("解析宋诗数据：作者-{}，诗词-{}", context.getAuthorMap().size(), context.getPoetries().size());
        return context;
    }

    public static void main(String[] args) {
        String filePath = "D:\\Github\\chinese-poetry";
        SongParser parser = new SongParser();
        parser.parse(filePath);
    }
}
