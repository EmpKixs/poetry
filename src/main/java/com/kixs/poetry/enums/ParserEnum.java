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
    CAOCAO_POETRY(ParserConstant.CAOCAO_POETRY, "曹操诗集", "曹操诗集\\"),
    SONG_POETRY(ParserConstant.SONG_POETRY, "宋诗", "全唐诗\\"),
    SONG_CI(ParserConstant.SONG_CI, "宋词", "宋词\\"),
    TANG_POETRY(ParserConstant.TANG_POETRY, "唐诗", "全唐诗\\"),
    SHUIMO_TANG_POETRY(ParserConstant.SHUIMO_TANG_POETRY, "水墨唐诗", "水墨唐诗\\"),
    YUDING_TANG_POETRY(ParserConstant.YUDING_TANG_POETRY, "御定全唐诗", "御定全唐詩\\json"),
    WUDAI_HUAJIANJI_POETRY(ParserConstant.WUDAI_HUAJIANJI_POETRY, "五代诗词·花间集", "五代诗词\\huajianji\\"),
    WUDAI_NANTANG_POETRY(ParserConstant.WUDAI_NANTANG_POETRY, "五代诗词·南唐二主词", "五代诗词\\nantang\\"),
    YUAN_QU(ParserConstant.YUAN_QU, "元曲", "元曲\\"),
    CHU_CI(ParserConstant.CHU_CI, "楚辞", "楚辞\\"),
    MENGXUE(ParserConstant.MENGXUE, "蒙学", "蒙学\\"),
    SHIJING(ParserConstant.SHIJING, "诗经", "诗经\\"),
    LUNYU(ParserConstant.LUNYU, "论语", "论语\\"),
    // SISHUWUJING(ParserConstant.SISHUWUJING, "四书五经", "四书五经\\"),
    DAXUE(ParserConstant.DAXUE, "大学", "四书五经\\"),
    MENGZI(ParserConstant.MENGZI, "孟子", "四书五经\\"),
    ZHONGYONG(ParserConstant.ZHONGYONG, "中庸", "四书五经\\");

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
