package com.kixs.poetry.parser.songci;

import lombok.Data;

/**
 * 宋词
 *
 * @author kixs
 * @version v1.0.0
 * @since 2020/9/7 23:03
 */
@Data
public class SongCi {

    /**
     * ID
     */
    private String id;

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
}
