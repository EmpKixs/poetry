package com.kixs.poetry.parser.tang;

import lombok.Data;

import java.util.List;

/**
 * 御定全唐诗
 *
 * @author kixs
 * @version v1.0.0
 * @since 2023/9/28 14:47
 */
@Data
public class YuDingTangPoetry {

    /**
     * 作者
     */
    private String author;

    /**
     * 诗名
     */
    private String title;

    /**
     * 诗词内容
     */
    private String paragraphs;

    /**
     * 人物传记
     */
    private String biography;

    /**
     * 解析
     */
    private List<String> notes;

    /**
     * 分卷
     */
    private String volume;

    /**
     * 编号
     */
    private Integer no;
}
