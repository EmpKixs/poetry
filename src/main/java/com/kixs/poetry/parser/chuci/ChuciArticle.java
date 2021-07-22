package com.kixs.poetry.parser.chuci;

import lombok.Data;

/**
 * 楚辞
 *
 * @author wangbing
 * @version v1.0.0
 * @since 2021/7/22 16:12
 */
@Data
public class ChuciArticle {
    /**
     * 标题
     */
    private String title;
    /**
     * 篇章
     */
    private String section;
    /**
     * 作者
     */
    private String author;
    /**
     * 内容
     */
    private String content;
}
