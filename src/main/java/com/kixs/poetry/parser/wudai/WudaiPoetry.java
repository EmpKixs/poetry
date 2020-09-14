package com.kixs.poetry.parser.wudai;

import lombok.Data;

/**
 * 五代诗词
 *
 * @author kixs
 * @version v1.0.0
 * @since 2020/9/7 23:03
 */
@Data
public class WudaiPoetry {

    /**
     * ID
     */
    private String id;

    /**
     * 诗名
     */
    private String title;

    /**
     * 词牌名
     */
    private String rhythmic;

    /**
     * 作者
     */
    private String author;

    /**
     * 诗词内容
     */
    private String paragraphs;

    /**
     * 注释
     */
    private String notes;
}
