package com.kixs.poetry.parser.tang;

import lombok.Data;

/**
 * 唐诗词
 *
 * @author kixs
 * @version v1.0.0
 * @since 2020/9/7 23:03
 */
@Data
public class TangPoetry {

    /**
     * ID
     */
    private String id;

    /**
     * 诗名
     */
    private String title;

    /**
     * 作者
     */
    private String author;

    /**
     * 诗词内容
     */
    private String paragraphs;
}
