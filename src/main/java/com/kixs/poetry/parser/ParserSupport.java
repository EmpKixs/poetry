package com.kixs.poetry.parser;

import com.hankcs.hanlp.HanLP;
import com.kixs.poetry.enums.ParserEnum;
import com.kixs.poetry.service.AuthorService;
import com.kixs.poetry.service.PoetryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 诗词解析支持类
 *
 * @author suyixing
 * @version v1.0.0
 * @since 2020/8/19 13:10
 */
@Slf4j
@Component
public class ParserSupport {

    @Resource
    private Map<String, PoetryParser> poetryParserMap;

    @Resource
    private PoetryService poetryService;

    @Resource
    private AuthorService authorService;

    @Transactional(rollbackFor = Exception.class)
    public ParseContext parse(String baseDir) {
        ParseContext context = new ParseContext();

        for (ParserEnum parser : ParserEnum.values()) {
            PoetryParser poetryParser = poetryParserMap.get(parser.getParser());
            if (poetryParser == null) {
                log.error("无{}诗词解析器", parser.getDesc());
                continue;
            }
            String path = baseDir + parser.getDir();
            context.add(poetryParser.parse(path));
        }
        log.debug("解析数据：作者-{}，诗词-{}", context.getAuthorMap().size(), context.getPoetries().size());
        if (!CollectionUtils.isEmpty(context.getPoetries())) {
            context.getPoetries().parallelStream().forEach(poetry -> {
                poetry.setTitle(convert(poetry.getTitle()));
                poetry.setContent(convert(poetry.getContent()));
                poetry.setRhythmic(convert(poetry.getRhythmic()));
                poetry.setNotes(convert(poetry.getNotes()));
            });
            poetryService.insertBatch(context.getPoetries(), 1000);
        }
        if (!CollectionUtils.isEmpty(context.getAuthorMap())) {
            context.getAuthorMap().values().parallelStream().forEach(author -> {
                author.setName(convert(author.getName()));
                author.setDescription(convert(author.getDescription()));
                author.setShortDescription(convert(author.getShortDescription()));
            });
            authorService.insertBatch(context.getAuthorMap().values(), 1000);
        }
        return context;
    }

    private String convert(String source) {
        if (StringUtils.isEmpty(source)) {
            return source;
        }
        return HanLP.convertToSimplifiedChinese(source);
    }
}
