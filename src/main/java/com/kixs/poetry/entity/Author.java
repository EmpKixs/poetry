package com.kixs.poetry.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 诗词经典（历史子集）作者(Author)实体类
 *
 * @author kixs
 * @version v1.0.0
 * @since 2020/8/18 23:28
 */
@Data
@TableName("author")
public class Author implements Serializable {
    private static final long serialVersionUID = -31700693051048888L;
    /**
     * 主键ID
     */
    private String id;
    /**
     * 姓名
     */
    private String name;
    /**
     * 朝代
     */
    private String dynasty;
    /**
     * 生平简介
     */
    private String description;
    /**
     * 生平简介（短）
     */
    private String shortDescription;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDynasty() {
        return dynasty;
    }

    public void setDynasty(String dynasty) {
        this.dynasty = dynasty;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    /**
     * 生成朝代作者关键字
     *
     * @return Key
     */
    public String generateDynastyAuthorKey() {
        return dynasty + "·" + name;
    }
}