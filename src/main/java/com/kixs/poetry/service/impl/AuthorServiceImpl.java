package com.kixs.poetry.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.kixs.poetry.dao.AuthorDao;
import com.kixs.poetry.entity.Author;
import com.kixs.poetry.service.AuthorService;
import com.kixs.poetry.utils.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * 诗词经典（历史子集）作者(Author)表服务实现类
 *
 * @author kixs
 * @version v1.0.0
 * @since 2020/8/18 23:28
 */
@Service
public class AuthorServiceImpl extends BaseServiceImpl<AuthorDao, Author> implements AuthorService {

    @Override
    public boolean insertBatch(Collection<Author> entityList, int batchSize) {
        Lists.partition(Lists.newArrayList(entityList), batchSize)
                .forEach(values -> this.baseDao.insertBatch(values));
        return true;
    }

    @Override
    public List<Author> queryAuthor() {
        return this.baseDao.queryAuthor();
    }
}