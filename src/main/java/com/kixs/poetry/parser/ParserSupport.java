package com.kixs.poetry.parser;

import com.kixs.poetry.enums.ParserEnum;
import com.kixs.poetry.service.AuthorService;
import com.kixs.poetry.service.PoetryService;
import com.kixs.poetry.service.StrainsService;
import com.kixs.poetry.utils.EmojiFilter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

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
            poetryService.insertBatch(context.getPoetries());
            context.getPoetries().forEach(poetry -> {
                boolean containsEmoji = EmojiFilter.containsEmoji(poetry.getTitle())
                        || EmojiFilter.containsEmoji(poetry.getContent())
                        || (StringUtils.isNotBlank(poetry.getNotes()) && EmojiFilter.containsEmoji(poetry.getNotes()));
                if (containsEmoji) {
                    log.debug("包含表情符的诗词：{}", poetry);
                }
            });
        }
        if (!CollectionUtils.isEmpty(context.getAuthorMap())) {
            authorService.insertBatch(context.getAuthorMap().values());
            context.getAuthorMap().values().forEach(author -> {
                boolean containsEmoji = EmojiFilter.containsEmoji(author.getName())
                        || (StringUtils.isNotBlank(author.getDescription()) && EmojiFilter.containsEmoji(author.getDescription()))
                        || (StringUtils.isNotBlank(author.getShortDescription()) && EmojiFilter.containsEmoji(author.getShortDescription()));
                if (containsEmoji) {
                    log.debug("包含表情符的作者信息：{}", author);
                }
            });
        }
        return context;
    }
}
