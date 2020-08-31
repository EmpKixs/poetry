package com.kixs.poetry.parser;

import com.kixs.poetry.entity.Author;
import com.kixs.poetry.entity.Poetry;
import com.kixs.poetry.entity.Strains;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 解析上下文
 *
 * @author suyixing
 * @version v1.0.0
 * @since 2020/8/19 13:10
 */
@Getter
public class ParseContext {

    /**
     * 作者集合
     */
    private final Map<String, Author> authorMap = new ConcurrentHashMap<>();

    /**
     * 诗词集合
     */
    private final List<Poetry> poetries = Collections.synchronizedList(new ArrayList<>());

    /**
     * 韵律集合
     */
    private final List<Strains> strainsList = Collections.synchronizedList(new ArrayList<>());

    /**
     * 添加作者
     *
     * @param author 作者信息
     */
    public void putAuthor(Author author) {
        authorMap.put(author.getName(), author);
    }

    /**
     * 根据名称获取作责信息
     *
     * @param name 作者名称
     * @return 作责信息
     */
    public Author getAuthor(String name) {
        return authorMap.get(name);
    }

    /**
     * 新增诗词
     *
     * @param poetry 诗词信息
     */
    public void addPoetry(Poetry poetry) {
        poetries.add(poetry);
    }

    /**
     * 新增韵律
     *
     * @param strains 韵律信息
     */
    public void addStrains(Strains strains) {
        strainsList.add(strains);
    }
}
