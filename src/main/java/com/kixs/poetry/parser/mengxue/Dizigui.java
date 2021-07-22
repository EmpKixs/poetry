package com.kixs.poetry.parser.mengxue;

import lombok.Data;

import java.util.List;

/**
 * 弟子规
 *
 * @author wangbing
 * @version v1.0.0
 * @since 2021/7/22 17:43
 */
@Data
public class Dizigui {

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

        private String chapter;

        private String paragraphs;
    }
}
