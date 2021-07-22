package com.kixs.poetry.parser.mengxue;

import lombok.Data;

/**
 * 千字文
 *
 * @author wangbing
 * @version v1.0.0
 * @since 2021/7/22 17:20
 */
@Data
public class Qianziwen {

    /**
     * 标题
     */
    private String title;
    /**
     * 作者
     */
    private String author;
    /**
     * 标签
     */
    private String tags;
    /**
     * 内容
     */
    private String paragraphs;
    /**
     * 拼写
     */
    private String spells;


}
