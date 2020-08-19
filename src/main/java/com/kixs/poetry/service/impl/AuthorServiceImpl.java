package com.kixs.poetry.service.impl;

import com.kixs.poetry.dao.AuthorDao;
import com.kixs.poetry.entity.Author;
import com.kixs.poetry.service.AuthorService;
import com.kixs.poetry.utils.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 诗词经典（历史子集）作者(Author)表服务实现类
 *
 * @author kixs
 * @version v1.0.0
 * @since 2020/8/18 23:28
 */
@Service("authorService")
public class AuthorServiceImpl extends BaseServiceImpl<AuthorDao, Author> implements AuthorService {

}