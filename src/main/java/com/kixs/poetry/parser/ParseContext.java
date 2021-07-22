package com.kixs.poetry.parser;

import com.kixs.poetry.entity.Article;
import com.kixs.poetry.entity.Author;
import com.kixs.poetry.entity.Book;
import com.kixs.poetry.entity.Poetry;
import lombok.Getter;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

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
    private final List<Poetry> poetryList = Collections.synchronizedList(new ArrayList<>());

    /**
     * 典籍集合
     */
    private final List<Book> books = Collections.synchronizedList(new ArrayList<>());

    /**
     * 文章集合
     */
    private final List<Article> articles = Collections.synchronizedList(new ArrayList<>());

    /**
     * 添加作者
     *
     * @param author 作者信息
     */
    public void putAuthor(Author author) {
        authorMap.put(author.generateDynastyAuthorKey(), author);
    }

    /**
     * 根据名称获取作责信息
     *
     * @param name 作者名称
     * @return 作责信息
     */
    public Author getAuthor(Function<String, String> generator, String name) {
        return authorMap.get(generator.apply(name));
    }

    /**
     * 新增诗词
     *
     * @param poetry 诗词信息
     */
    public void addPoetry(Poetry poetry) {
        poetryList.add(poetry);
    }

    /**
     * 新增典籍
     *
     * @param book 典籍信息
     */
    public void addBook(Book book) {
        books.add(book);
    }

    /**
     * 新增文章
     *
     * @param article 文章信息
     */
    public void addArticle(Article article) {
        articles.add(article);
    }

    /**
     * 其它上下文加入当前上下文
     *
     * @param other 其它上下文
     */
    public void add(ParseContext other) {
        if (!CollectionUtils.isEmpty(other.getAuthorMap())) {
            this.authorMap.putAll(other.getAuthorMap());
        }
        if (!CollectionUtils.isEmpty(other.poetryList)) {
            this.poetryList.addAll(other.poetryList);
        }
        if (!CollectionUtils.isEmpty(other.books)) {
            this.books.addAll(other.books);
        }
        if (!CollectionUtils.isEmpty(other.articles)) {
            this.articles.addAll(other.articles);
        }

    }
}
