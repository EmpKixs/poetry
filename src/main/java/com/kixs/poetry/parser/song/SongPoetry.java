package com.kixs.poetry.parser.song;

import lombok.Data;

/**
 * 宋诗词
 *
 * @author kixs
 * @version v1.0.0
 * @since 2020/9/7 23:03
 */
@Data
public class SongPoetry {

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
