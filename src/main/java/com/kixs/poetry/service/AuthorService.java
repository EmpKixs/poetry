package com.kixs.poetry.service;

import com.kixs.poetry.entity.Author;
import com.kixs.poetry.utils.service.BaseService;

import java.util.List;

/**
 * 诗词经典（历史子集）作者(Author)表服务接口
 *
 * @author kixs
 * @version v1.0.0
 * @since 2020/8/18 23:28
 */
public interface AuthorService extends BaseService<Author> {

    List<Author> queryAuthor();
}