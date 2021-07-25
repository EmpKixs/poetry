package com.kixs.poetry.parser.sishuwujing;

import lombok.Data;

/**
 * 诗经
 *
 * @author kixs
 * @version v1.0.0
 * @since 2021/7/24 23:07
 */
@Data
public class Shijing {
    /**
     * 标题
     */
    private String title;
    /**
     * 章节/卷
     */
    private String chapter;
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
