package com.kixs.poetry.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 古代典籍(Book)实体类
 *
 * @author wangbing
 * @version v1.0.0
 * @since 2021/7/22 16:02
 */
@Data
@TableName("book")
public class Book implements Serializable {
    private static final long serialVersionUID = -79473495904825256L;
    /**
     * 主键ID
     */
    private String id;
    /**
     * 典籍名称
     */
    private String name;
    /**
     * 作者ID
     */
    private String authorId;
    /**
     * 典籍介绍
     */
    private String introduction;
    /**
     * 典籍标签
     */
    private String tag;


}