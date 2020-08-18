package com.kixs.poetry.service.impl;

import com.kixs.poetry.dao.PoetryDao;
import com.kixs.poetry.entity.Poetry;
import com.kixs.poetry.service.PoetryService;
import org.springframework.stereotype.Service;

/**
 * 诗歌(Poetry)表服务实现类
 *
 * @author kixs
 * @version v1.0.0
 * @since 2020/8/18 23:29
 */
@Service("poetryService")
public class PoetryServiceImpl extends BaseServiceImpl<PoetryDao, Poetry> implements PoetryService {

}