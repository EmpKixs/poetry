package com.kixs.poetry.parser.song;

import lombok.Data;

/**
 * 宋朝作者信息
 *
 * @author suyixing
 * @version v1.0.0
 * @since 2020/8/19 13:10
 */
@Data
public class SongAuthor {

    /**
     * ID
     */
    private String id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 生平简介
     */
    private String desc;
}
