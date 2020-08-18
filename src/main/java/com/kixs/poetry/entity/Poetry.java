package com.kixs.poetry.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 诗歌(Poetry)实体类
 *
 * @author kixs
 * @version v1.0.0
 * @since 2020/8/18 23:29
 */
@Data
@TableName("poetry")
public class Poetry implements Serializable {
    private static final long serialVersionUID = -98212766921806597L;
    /**
     * 主键ID
     */
    private String id;
    /**
     * 诗词类型
     */
    private Integer type;
    /**
     * 标题
     */
    private String title;
    /**
     * 作者
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