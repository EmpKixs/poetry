package com.kixs.poetry.parser.mengxue;

import lombok.Data;

import java.util.List;

/**
 * 声律启蒙
 *
 * @author wangbing
 * @version v1.0.0
 * @since 2021/7/23 9:10
 */
@Data
public class Shenglvqimeng {

    /**
     * 标题
     */
    private String title;
    /**
     * 作者
     */
    private String author;
    /**
     * 内容
     */
    private List<Content> content;

    @Data
    static class Content {

        private String title;

        private List<ContentItem> content;
    }

    @Data
    static class ContentItem {

        private String chapter;

        private String paragraphs;
    }
}
