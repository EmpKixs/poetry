package com.kixs.poetry.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 诗词Dto
 *
 * @author kixs
 * @version v1.0.0
 * @since 2021/7/21 16:34
 */
@Data
public class PoetryDto implements Serializable {

    /**
     * 主键ID
     */
    private String id;
    /**
     * 诗词类型（1-诗，2-词，3-楚辞）
     */
    private Integer type;
    /**
     * 标题
     */
    private String title;
    /**
     * 词牌名
     */
    private String rhythmic;
    /**
     * 作者ID
     */
    private String authorId;
    /**
     * 作者姓名
     */
    private String authorName;
    /**
     * 作者朝代
     */
    private String authorDynasty;
    /**
     * 内容
     */
    private String content;
    /**
     * 注释
     */
    private String notes;
    /**
     * 诗词中韵律/声调/格律
     */
    private String strains;
}
