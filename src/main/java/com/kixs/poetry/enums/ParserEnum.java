package com.kixs.poetry.enums;

import com.kixs.poetry.constant.ParserConstant;
import lombok.Getter;

/**
 * 解析器枚举
 *
 * @author suyixing
 * @version v1.0.0
 * @since 2020/8/19 13:10
 */
@Getter
public enum ParserEnum {

    CAOCAO_POETRY(ParserConstant.CAOCAO_POETRY, "曹操诗集", "caocaoshiji");

    /**
     * 解析器标识
     */
    private final String parser;

    /**
     * 解析器描述
     */
    private final String desc;

    /**
     * 文件目录
     */
    private final String dir;

    ParserEnum(String parser, String desc, String dir) {
        this.parser = parser;
        this.desc = desc;
        this.dir = dir;
    }
}
