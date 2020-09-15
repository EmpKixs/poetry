package com.kixs.poetry.parser.yuanqu;

import lombok.Data;

/**
 * 元曲
 *
 * @author kixs
 * @version v1.0.0
 * @since 2020/9/7 23:03
 */
@Data
public class YuanquPoetry {

    /**
     * ID
     */
    private String id;

    /**
     * 诗名
     */
    private String title;

    /**
     * 朝代
     */
    private String dynasty;

    /**
     * 作者
     */
    private String author;

    /**
     * 诗词内容
     */
    private String paragraphs;
}
