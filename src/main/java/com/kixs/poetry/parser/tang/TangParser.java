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

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
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

    private Map<String, Poetry> strainsPoetryMap = new ConcurrentHashMap<>();

    @Override
    public String dynasty() {
        return "唐";
    }

    @Override
    public ParseContext parse(String filePath) {
        // 作者解析
        String authorFile = filePath + "\\json\\authors.tang.json";
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
        File[] files = FileUtils.listDirectoryFiles(filePath + "\\json", (dir, filename) -> Pattern.matches(pattern, filename));
        if (files != null && files.length > 0) {
            Stream.of(files).parallel().forEach(file -> {
                String data = FileUtils.read(file);
                List<TangPoetry> poetries = JSON.parseArray(data, TangPoetry.class);
                poetries.stream().parallel().forEach(tang -> {
                    Poetry poetry = new Poetry();
                    poetry.setId(IdWorker.getIdStr());
                    poetry.setType(PoetryType.SHI.getCode());
                    poetry.setTitle(tang.getTitle());
                    if (StringUtils.isNotBlank(tang.getAuthor())) {
                        Author author = context.getAuthor(this::generateDynastyAuthorKey, tang.getAuthor());
                        if (Objects.nonNull(author)) {
                            poetry.setAuthorId(author.getId());
                        } else {
                            poetry.setNotes("##" + tang.getAuthor() + "，此作者未在作者列表中查询到##");
                        }
                    }
                    poetry.setContent(tang.getParagraphs());
                    strainsPoetryMap.put(tang.getId(), poetry);
                    context.addPoetry(poetry);
                });
            });
        }
        // 韵律/声调/格律解析
        String strainsPattern = "^poet.tang.([0-9])*.json$";
        File[] strainsFiles = FileUtils.listDirectoryFiles(filePath + "\\strains\\json", (dir, filename) -> Pattern.matches(strainsPattern, filename));
        if (strainsFiles != null && strainsFiles.length > 0) {
            Stream.of(strainsFiles).parallel().forEach(file -> {
                String data = FileUtils.read(file);
                List<TangStrains> strains = JSON.parseArray(data, TangStrains.class);
                strains.stream().parallel().forEach(s -> {
                    Poetry poetry = strainsPoetryMap.get(s.getId());
                    if (Objects.isNull(poetry)) {
                        log.error(file.getName() + "中[" + s.getId() + "]韵律未找到对应诗词");
                    } else {
                        poetry.setStrains(s.getStrains());
                    }
                });
            });
        }
        log.debug("解析唐诗词数据：作者-{}，诗词-{}", context.getAuthorMap().size(), context.getPoetryList().size());
        return context;
    }

    public static void main(String[] args) {
        String filePath = "D:\\Github\\chinese-poetry\\json";
        TangParser parser = new TangParser();
        parser.parse(filePath);
    }
}
