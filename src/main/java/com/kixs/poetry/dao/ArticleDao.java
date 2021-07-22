package com.kixs.poetry.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kixs.poetry.entity.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 古代典籍内容(Article)表数据库访问层
 *
 * @author wangbing
 * @version v1.0.0
 * @since 2021/7/22 16:02
 */
@Mapper
@Repository
public interface ArticleDao extends BaseMapper<Article> {

    /**
     * 通过实体作为筛选条件查询
     *
     * @param article 实例对象
     * @return 对象列表
     */
    List<Article> queryAll(Article article);

    /**
     * 修改数据
     *
     * @param article 实例对象
     * @return 影响行数
     */
    int updateEntity(Article article);

    /**
     * 批量插入
     *
     * @param articles 文章列表
     * @return 影响行数
     */
    int insertBatch(@Param("articles") List<Article> articles);

}