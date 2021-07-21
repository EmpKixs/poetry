package com.kixs.poetry.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kixs.poetry.dto.PoetryDto;
import com.kixs.poetry.entity.Poetry;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 诗歌(Poetry)表数据库访问层
 *
 * @author kixs
 * @version v1.0.0
 * @since 2020/8/18 23:29
 */
@Mapper
@Repository
public interface PoetryDao extends BaseMapper<Poetry> {

    /**
     * 通过实体作为筛选条件查询
     *
     * @param poetry 实例对象
     * @return 对象列表
     */
    List<Poetry> queryAll(Poetry poetry);

    /**
     * 修改数据
     *
     * @param poetry 实例对象
     * @return 影响行数
     */
    int updateEntity(Poetry poetry);

    /**
     * 批量插入
     *
     * @param poetryList 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("poetryList") List<Poetry> poetryList);

    /**
     * 分页查询诗词数据
     *
     * @param offset   偏移量
     * @param pageSize 每页大小
     * @return 查询结果
     */
    List<PoetryDto> pagePoetryDto(@Param("offset") int offset, @Param("pageSize") int pageSize);

}