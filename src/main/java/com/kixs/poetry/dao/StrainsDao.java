package com.kixs.poetry.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kixs.poetry.entity.Strains;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 诗词中韵律/声调/格律(Strains)表数据库访问层
 *
 * @author kixs
 * @version v1.0.0
 * @since 2020/8/18 23:30
 */
@Mapper
@Repository
public interface StrainsDao extends BaseMapper<Strains> {

    /**
     * 通过实体作为筛选条件查询
     *
     * @param strains 实例对象
     * @return 对象列表
     */
    List<Strains> queryAll(Strains strains);

    /**
     * 修改数据
     *
     * @param strains 实例对象
     * @return 影响行数
     */
    int update(Strains strains);

}