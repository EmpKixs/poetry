package com.kixs.poetry.parser.mengxue;

import lombok.Data;

import java.util.List;

/**
 * 幼学琼林
 *
 * @author wangbing
 * @version v1.0.0
 * @since 2021/7/23 16:39
 */
@Data
public class Youxueqionglin {

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
