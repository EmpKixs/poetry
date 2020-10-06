package com.kixs.poetry.parser;

import com.hankcs.hanlp.HanLP;
import com.kixs.poetry.entity.Author;
import com.kixs.poetry.entity.Poetry;
import com.kixs.poetry.enums.ParserEnum;
import com.kixs.poetry.service.AuthorService;
import com.kixs.poetry.service.PoetryService;
import com.kixs.poetry.service.StrainsService;
import com.kixs.poetry.utils.EmojiUtils;
import com.spreada.utils.chinese.ZHConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
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

    @Resource
    private StrainsService strainsService;

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
                poetry.setTitle(convert(EmojiUtils.replaceEmoji(poetry.getTitle())));
                poetry.setContent(convert(EmojiUtils.replaceEmoji(poetry.getContent())));
                poetry.setRhythmic(convert(EmojiUtils.replaceEmoji(poetry.getRhythmic())));
                // poetryService.insert(poetry);
            });
            List<List<Poetry>> data = new ArrayList<>();
            int index = 0;
            int step = 1000;
            int length = context.getPoetries().size();
            while (index <= length) {
                int toIndex = index + step;
                if (toIndex >= length) {
                    data.add(context.getPoetries().subList(index, length));
                    // poetryService.insertBatch(context.getPoetries().subList(index, length));
                } else {
                    data.add(context.getPoetries().subList(index, toIndex));
                    // poetryService.insertBatch(context.getPoetries().subList(index, toIndex));
                }
                index = toIndex;
            }
            data.parallelStream().forEach(poetries -> poetryService.insertBatch(poetries));
        }
        if (!CollectionUtils.isEmpty(context.getAuthorMap())) {
            context.getAuthorMap().values().parallelStream().forEach(author -> {
                author.setName(convert(EmojiUtils.replaceEmoji(author.getName())));
                author.setDescription(convert(EmojiUtils.replaceEmoji(author.getDescription())));
                author.setShortDescription(convert(EmojiUtils.replaceEmoji(author.getShortDescription())));
                authorService.insert(author);
            });
            // authorService.insertBatch(context.getAuthorMap().values());
            List<List<Author>> data = new ArrayList<>();
            int index = 0;
            int step = 1000;
            int length = context.getPoetries().size();
            while (index <= length) {
                int toIndex = index + step;
                if (toIndex >= length) {
                    data.add(context.getAuthorMap().values().subList(index, length));
                    // poetryService.insertBatch(context.getPoetries().subList(index, length));
                } else {
                    data.add(context.getPoetries().subList(index, toIndex));
                    // poetryService.insertBatch(context.getPoetries().subList(index, toIndex));
                }
                index = toIndex;
            }
            data.parallelStream().forEach(poetries -> poetryService.insertBatch(poetries));
        }
        return context;
    }

    private String convert(String source) {
        if (StringUtils.isEmpty(source)) {
            return source;
        }
        return HanLP.convertToSimplifiedChinese(source);
    }

    public static void main(String[] args) {
        String data = "靈山話月";
        ZHConverter converter = ZHConverter.getInstance(ZHConverter.SIMPLIFIED);
        System.out.println(converter.convert(data));
        System.out.println(HanLP.convertToSimplifiedChinese(data));
    }
}
