package com.kixs.poetry.service.impl;

import com.google.common.collect.Lists;
import com.kixs.poetry.dao.PoetryDao;
import com.kixs.poetry.entity.Poetry;
import com.kixs.poetry.service.PoetryService;
import com.kixs.poetry.utils.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * 诗歌(Poetry)表服务实现类
 *
 * @author kixs
 * @version v1.0.0
 * @since 2020/8/18 23:29
 */
@Service
public class PoetryServiceImpl extends BaseServiceImpl<PoetryDao, Poetry> implements PoetryService {

    @Override
    public boolean insertBatch(Collection<Poetry> entityList, int batchSize) {
        Lists.partition(Lists.newArrayList(entityList), batchSize)
                .forEach(values -> this.baseDao.insertBatch(values));
        return true;
    }


}