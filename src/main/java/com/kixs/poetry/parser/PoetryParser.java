package com.kixs.poetry.parser;

/**
 * 诗词解析器接口定义
 *
 * @author suyixing
 * @version v1.0.0
 * @since 2020/8/19 13:10
 */
public interface PoetryParser {

    /**
     * 文件解析
     *
     * @param filePath 文件路径
     * @return 解析上下文
     */
    ParseContext parse(String filePath);
}
