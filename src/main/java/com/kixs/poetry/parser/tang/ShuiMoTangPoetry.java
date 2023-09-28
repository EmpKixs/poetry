package com.kixs.poetry.parser.tang;

import lombok.Data;

/**
 * 水墨唐诗
 *
 * @author kixs
 * @version v1.0.0
 * @since 2023/9/28 14:47
 */
@Data
public class ShuiMoTangPoetry {

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
     * 序言|解读
     */
    private String prologue;
}
