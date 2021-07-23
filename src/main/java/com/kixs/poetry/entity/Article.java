package com.kixs.poetry.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 古代典籍内容(Article)实体类
 *
 * @author wangbing
 * @version v1.0.0
 * @since 2021/7/22 16:02
 */
@Data
@TableName("article")
public class Article implements Serializable {
    private static final long serialVersionUID = 768041706739807474L;
    /**
     * 主键ID
     */
    @TableId(value = "id")
    private String id;
    /**
     * 典籍ID
     */
    private String bookId;
    /**
     * 卷名
     */
    private String volume;
    /**
     * 卷排序
     */
    private Integer volumeSort;
    /**
     * 章节/篇章
     */
    private String section;
    /**
     * 章节/篇章排序
     */
    private Integer sectionSort;
    /**
     * 标题
     */
    private String title;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 内容作者ID
     */
    private String authorId;
    /**
     * 内容
     */
    private String content;
    /**
     * 注释
     */
    private String notes;


}