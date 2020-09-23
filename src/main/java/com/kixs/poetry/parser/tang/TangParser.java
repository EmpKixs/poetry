package com.kixs.poetry.parser.tang;

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
 * 唐诗词解析
 *
 * @author suyixing
 * @version v1.0.0
 * @since 2020/8/19 13:10
 */
@Slf4j
@Service(ParserConstant.TANG_POETRY)
public class TangParser implements PoetryParser {

    @Override
    public String dynasty() {
        return "唐朝";
    }

    @Override
    public ParseContext parse(String filePath) {
        // 作者解析
        String authorFile = filePath + "\\authors.tang.json";
        String authorData = FileUtils.read(authorFile);
        List<TangAuthor> tangAuthors = JSON.parseArray(authorData, TangAuthor.class);
        ParseContext context = new ParseContext();
        tangAuthors.stream().parallel().forEach(tang -> {
            Author author = new Author();
            author.setId(IdWorker.getIdStr());
            author.setName(tang.getName());
            author.setDynasty(dynasty());
            author.setDescription(tang.getDesc());
            context.putAuthor(author);
        });
        // 诗词解析
        String pattern = "^poet.tang.([0-9])*.json$";
        File[] files = FileUtils.listDirectoryFiles(filePath, (dir, filename) -> Pattern.matches(pattern, filename));
        if (files != null && files.length > 0) {
            Stream.of(files).parallel().forEach(file -> {
                String data = FileUtils.read(file);
                List<TangPoetry> poetries = JSON.parseArray(data, TangPoetry.class);
                poetries.stream().parallel().forEach(tang -> {
                    Poetry poetry = new Poetry();
                    poetry.setId(IdWorker.getIdStr());
                    poetry.setTitle(tang.getTitle());
                    Author author = context.getAuthor(this::generateDynastyAuthorKey, tang.getAuthor());
                    if (Objects.nonNull(author)) {
                        poetry.setAuthorId(author.getId());
                    }
                    poetry.setContent(tang.getParagraphs());
                    context.addPoetry(poetry);
                });
            });
        }
        log.debug("解析数据：作者-{}，诗词-{}", context.getAuthorMap().size(), context.getPoetries().size());
        return context;
    }

    public static void main(String[] args) {
        String filePath = "D:\\Github\\chinese-poetry\\json";
        TangParser parser = new TangParser();
        parser.parse(filePath);
    }
}
