package com.kixs.poetry.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 常量定义
 *
 * @author suyixing
 * @version v1.0.0
 * @since 2020/8/19 13:10
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "poetry")
public class PoetryProperties {

    /**
     * 基础目录
     */
    private String baseDir;
}
