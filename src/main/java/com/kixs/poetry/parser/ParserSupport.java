package com.kixs.poetry.parser;

import com.kixs.poetry.enums.ParserEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

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
        return context;
    }
}
