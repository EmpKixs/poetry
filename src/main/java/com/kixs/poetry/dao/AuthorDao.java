package com.kixs.poetry.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kixs.poetry.entity.Author;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 诗词经典（历史子集）作者(Author)表数据库访问层
 *
 * @author kixs
 * @version v1.0.0
 * @since 2020/8/18 23:28
 */
@Mapper
@Repository
public interface AuthorDao extends BaseMapper<Author> {

    /**
     * 通过实体作为筛选条件查询
     *
     * @param author 实例对象
     * @return 对象列表
     */
    List<Author> queryAll(Author author);

    /**
     * 修改数据
     *
     * @param author 实例对象
     * @return 影响行数
     */
    int update(Author author);

    /**
     * 批量插入
     *
     * @param authors 作者列表
     * @return 影响行数
     */
    int insertBatch(@Param("authors") List<Author> authors);

    List<Author> queryAuthor();
}