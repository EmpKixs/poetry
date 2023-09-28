package com.kixs.poetry.parser.tang;

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
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 御定全唐诗解析
 *
 * @author kixs
 * @version v1.0.0
 * @since 2023/9/28 14:47
 */
@Slf4j
@Service(ParserConstant.YUDING_TANG_POETRY)
public class YuDingTangParser implements PoetryParser {

    @Override
    public String dynasty() {
        return "唐";
    }

    @Override
    public ParseContext parse(String filePath) {
        ParseContext context = new ParseContext();
        // 诗词解析
        File[] files = FileUtils.listDirectoryFiles(filePath);
        if (files != null && files.length > 0) {
            Stream.of(files).parallel().forEach(file -> {
                String data = FileUtils.read(file);
                List<YuDingTangPoetry> poetries = JSON.parseArray(data, YuDingTangPoetry.class);
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
                                author.setShortDescription(tang.getBiography());
                                context.putAuthor(author);
                            }
                        }
                    }
                    poetry.setAuthorId(author.getId());
                    poetry.setContent(tang.getParagraphs());
                    if (!CollectionUtils.isEmpty(tang.getNotes())) {
                        poetry.setNotes(tang.getVolume() + "；" + String.join("；", tang.getNotes()));
                    } else {
                        poetry.setNotes(tang.getVolume());
                    }
                    context.addPoetry(poetry);
                });
            });
        }
        log.debug("解析御定全唐诗数据：作者-{}，诗词-{}", context.getAuthorMap().size(), context.getPoetryList().size());
        return context;
    }

    public static void main(String[] args) {
        String filePath = "D:\\Github\\chinese-poetry\\御定全唐诗";
        YuDingTangParser parser = new YuDingTangParser();
        parser.parse(filePath);
    }
}
