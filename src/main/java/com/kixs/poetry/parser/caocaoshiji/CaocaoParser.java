package com.kixs.poetry.parser.caocaoshiji;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.kixs.poetry.entity.Author;
import com.kixs.poetry.entity.Poetry;
import com.kixs.poetry.parser.ParseContext;
import com.kixs.poetry.parser.PoetryParser;
import com.kixs.poetry.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
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
@Service
public class CaocaoParser implements PoetryParser {
    @Override
    public String dynasty() {
        return "唐朝";
    }

    @Override
    public ParseContext parse(String filePath) {
        ParseContext context = new ParseContext();
        // 作者解析
        Author author = new Author();
        author.setId(IdWorker.getIdStr());
        author.setName("曹操");
        author.setDynasty(dynasty());
        context.putAuthor(author);
        // 诗词解析
        String pattern = "^caocao.json$";
        File[] files = FileUtils.listDirectoryFiles(filePath, (dir, filename) -> Pattern.matches(pattern, filename));
        if (files != null && files.length > 0) {
            Stream.of(files).parallel().forEach(file -> {
                String data = FileUtils.read(file);
                List<CaocaoPoetry> poetries = JSON.parseArray(data, CaocaoPoetry.class);
                poetries.stream().parallel().forEach(poet -> {
                    Poetry poetry = new Poetry();
                    poetry.setId(IdWorker.getIdStr());
                    poetry.setTitle(poet.getTitle());
                    poetry.setAuthorId(author.getId());
                    poetry.setContent(poet.getParagraphs());
                    context.addPoetry(poetry);
                });
            });
        }
        log.debug("解析数据：作者-{}，诗词-{}", context.getAuthorMap().size(), context.getPoetries().size());
        return context;
    }

    public static void main(String[] args) {
        String filePath = "D:\\Github\\chinese-poetry\\caocaoshiji";
        CaocaoParser parser = new CaocaoParser();
        parser.parse(filePath);
    }
}
