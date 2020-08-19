package com.kixs.poetry.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 类型转换工具
 *
 * @author wangbing
 * @version v1.0.0
 * @since 2020/8/19 13:14
 */
@Slf4j
public class ConvertUtils {

    /**
     * 转化出新对象
     *
     * @param source 源对象
     * @param target 目标对象
     * @param <T>
     * @return
     */
    public static <T> T sourceToTarget(Object source, Class<T> target) {
        if (source == null) {
            return null;
        }
        T targetObject = null;
        try {
            targetObject = target.newInstance();
            BeanUtils.copyProperties(source, targetObject);
        } catch (Exception e) {
            log.error("convert error ", e);
        }
        return targetObject;
    }

    /**
     * 转化出新对象集合
     *
     * @param sourceList 源对象集合
     * @param target     目标对象
     * @param <T>        新对象集合
     * @return
     */
    public static <T> List<T> sourceToTarget(Collection<?> sourceList, Class<T> target) {
        if (sourceList == null) {
            return null;
        }
        List targetList = new ArrayList<>(sourceList.size());
        try {
            for (Object source : sourceList) {
                T targetObject = target.newInstance();
                BeanUtils.copyProperties(source, targetObject);
                targetList.add(targetObject);
            }
        } catch (Exception e) {
            log.error("convert error ", e);
        }

        return targetList;
    }
}
