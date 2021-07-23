package com.kixs.poetry.parser.mengxue;

import lombok.Data;

import java.util.List;

/**
 * 文字蒙求
 *
 * @author wangbing
 * @version v1.0.0
 * @since 2021/7/23 16:33
 */
@Data
public class Wenzimengqiu {

    /**
     * 标题
     */
    private String title;
    /**
     * 作者
     */
    private String author;
    /**
     * 前言/序言
     */
    private String preface;
    /**
     * 内容
     */
    private List<Content> content;

    @Data
    static class Content {

        private String title;

        private String paragraphs;
    }

}
