package com.kixs.poetry.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 诗词中韵律/声调/格律(Strains)实体类
 *
 * @author kixs
 * @version v1.0.0
 * @since 2020/8/18 23:30
 */
@Data
@TableName("strains")
public class Strains implements Serializable {
    private static final long serialVersionUID = -69372797907111405L;
    /**
     * 主键ID
     */
    private String id;
    /**
     * 诗词中韵律/声调/格律
     */
    private String strains;

}