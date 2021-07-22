package com.kixs.poetry.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kixs.poetry.entity.Book;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 古代典籍(Book)表数据库访问层
 *
 * @author wangbing
 * @version v1.0.0
 * @since 2021/7/22 16:02
 */
@Mapper
@Repository
public interface BookDao extends BaseMapper<Book> {

    /**
     * 通过实体作为筛选条件查询
     *
     * @param book 实例对象
     * @return 对象列表
     */
    List<Book> queryAll(Book book);

    /**
     * 修改数据
     *
     * @param book 实例对象
     * @return 影响行数
     */
    int updateEntity(Book book);

    /**
     * 批量插入
     *
     * @param books 典籍列表
     * @return 影响行数
     */
    int insertBatch(@Param("books") List<Book> books);

}