package com.kixs.poetry.parser.caocaoshiji;

import lombok.Data;

/**
 * 曹操诗集
 *
 * @author kixs
 * @version v1.0.0
 * @since 2020/9/7 23:03
 */
@Data
public class CaocaoPoetry {

    /**
     * ID
     */
    private String id;

    /**
     * 诗名
     */
    private String title;

    /**
     * 诗词内容
     */
    private String paragraphs;
}
