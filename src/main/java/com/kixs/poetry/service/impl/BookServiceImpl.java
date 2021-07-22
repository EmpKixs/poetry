package com.kixs.poetry.service.impl;

import com.kixs.poetry.dao.BookDao;
import com.kixs.poetry.entity.Book;
import com.kixs.poetry.service.BookService;
import com.kixs.poetry.utils.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 古代典籍(Book)表服务实现类
 *
 * @author wangbing
 * @version v1.0.0
 * @since 2021/7/22 16:02
 */
@Service("bookService")
public class BookServiceImpl extends BaseServiceImpl<BookDao, Book> implements BookService {

}