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
     * 词牌名
     */
    private String rhythmic;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRhythmic() {
        return rhythmic;
    }

    public void setRhythmic(String rhythmic) {
        this.rhythmic = rhythmic;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}