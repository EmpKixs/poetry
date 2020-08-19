package com.kixs.poetry.utils;

import java.io.Serializable;
import java.util.List;

/**
 * 分页结果集工具
 *
 * @author wangbing
 * @version v1.0.0
 * @since 2020/8/19 13:12
 */
public class PageData<T> implements Serializable {

    private static final long serialVersionUID = 1835804307633930093L;

    /**
     * 总记录数
     */
    private int total;

    /**
     * 列表数据
     */
    private List<T> list;

    /**
     * 分页
     *
     * @param list  列表数据
     * @param total 总记录数
     */
    public PageData(List<T> list, long total) {
        this.list = list;
        this.total = (int) total;
    }

    public PageData() {
    }
}
