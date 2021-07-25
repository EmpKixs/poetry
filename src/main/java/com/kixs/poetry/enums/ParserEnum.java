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

    /**
     * 解析器枚举
     */
    CAOCAO_POETRY(ParserConstant.CAOCAO_POETRY, "曹操诗集", "caocaoshiji"),
    SONG_POETRY(ParserConstant.SONG_POETRY, "宋代诗词", ""),
    SONG_CI(ParserConstant.SONG_CI, "宋词", "ci"),
    TANG_POETRY(ParserConstant.TANG_POETRY, "唐诗", ""),
    WUDAI_HUAJIANJI_POETRY(ParserConstant.WUDAI_HUAJIANJI_POETRY, "五代·花间集", "wudai\\huajianji"),
    WUDAI_NANTANG_POETRY(ParserConstant.WUDAI_NANTANG_POETRY, "五代·南唐二主词", "wudai\\nantang"),
    YUAN_QU(ParserConstant.YUAN_QU, "元曲", "yuanqu\\"),
    CHU_CI(ParserConstant.CHU_CI, "楚辞", "chuci\\"),
    MENGXUE(ParserConstant.MENGXUE, "蒙学", "mengxue\\"),
    SHIJING(ParserConstant.SHIJING, "诗经", "shijing\\"),
    LUNYU(ParserConstant.LUNYU, "论语", "lunyu\\"),
    DAXUE(ParserConstant.DAXUE, "大学", "sishuwujing\\"),
    MENGZI(ParserConstant.MENGZI, "孟子", "sishuwujing\\"),
    ZHONGYONG(ParserConstant.ZHONGYONG, "中庸", "sishuwujing\\");

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
