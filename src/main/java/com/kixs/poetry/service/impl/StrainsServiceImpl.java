package com.kixs.poetry.service.impl;

import com.kixs.poetry.dao.StrainsDao;
import com.kixs.poetry.entity.Strains;
import com.kixs.poetry.service.StrainsService;
import com.kixs.poetry.utils.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 诗词中韵律/声调/格律(Strains)表服务实现类
 *
 * @author kixs
 * @version v1.0.0
 * @since 2020/8/18 23:30
 */
@Service("strainsService")
public class StrainsServiceImpl extends BaseServiceImpl<StrainsDao, Strains> implements StrainsService {

}