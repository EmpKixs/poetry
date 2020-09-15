package com.kixs.poetry.parser;

/**
 * 朝代解析器
 *
 * @author suyixing
 * @version v1.0.0
 * @since 2020/8/19 13:10
 */
public interface DynastyParser {

    /**
     * 当前朝代
     *
     * @return 朝代名称
     */
    String dynasty();

    /**
     * 生成朝代作者关键字
     *
     * @param authorName 作者名称
     * @return Key
     */
    default String generateDynastyAuthorKey(String authorName) {
        return dynasty() + "·" + authorName;
    }
}
