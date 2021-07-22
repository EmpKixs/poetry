package com.kixs.poetry.service.impl;

import com.kixs.poetry.dao.ArticleDao;
import com.kixs.poetry.entity.Article;
import com.kixs.poetry.service.ArticleService;
import com.kixs.poetry.utils.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 古代典籍内容(Article)表服务实现类
 *
 * @author wangbing
 * @version v1.0.0
 * @since 2021/7/22 16:02
 */
@Service("articleService")
public class ArticleServiceImpl extends BaseServiceImpl<ArticleDao, Article> implements ArticleService {

}