package com.kixs.poetry.utils;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 实体赋值工具类
 *
 * @author wangbing
 * @version v1.0.0
 * @since 2020/8/19 13:06
 */
@Slf4j
public class EntityUtils {

    /**
     * 设置删除信息
     *
     * @param ids    ids
     * @param entity 实体
     */
    public static <T> List<T> deletedBy(String[] ids, Class<T> entity) {
        List<T> entityList = new ArrayList<>(ids.length);

        for (String id : ids) {
            T entityObject = deletedBy(id, entity);
            entityList.add(entityObject);
        }

        return entityList;
    }

    /**
     * 设置删除信息
     *
     * @param id     id
     * @param entity 实体
     */
    public static <T> T deletedBy(String id, Class<T> entity) {
        Map<String, Object> map = new HashMap<>(4);
        map.put("id", id);
        map.put("updateTime", LocalDateTime.now());
        map.put("isDel", 1);

        T entityObject;
        try {
            entityObject = entity.newInstance();
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                setValue(entityObject, entry.getKey(), entry.getValue());
            }
        } catch (Exception e) {
            log.error("设置删除信息异常", e);
            throw new RuntimeException("设置删除信息异常");
        }

        return entityObject;
    }

    private static <T> void setValue(T entity, String key, Object value) {
        Class<?> clazz = entity.getClass();
        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);

                if (field.getName().equalsIgnoreCase(key)) {
                    try {
                        field.set(entity, value);
                    } catch (IllegalAccessException e) {
                        continue;
                    }
                    return;
                }
            }
        }
    }


    /**
     * 得到对象属性值
     *
     * @param obj
     */
    public static Object readAttributeValue(Object obj, String fieldName) {
        Object nameValue = null;
        //得到class
        Class cls = obj.getClass();
        //得到所有属性
        Field[] fields = cls.getDeclaredFields();
        //遍历
        for (int i = 0; i < fields.length; i++) {
            try {
                //得到属性
                Field field = fields[i];
                //打开私有访问
                field.setAccessible(true);
                //获取属性
                String name = field.getName();
                if (fieldName.equals(name)) {
                    nameValue = field.get(obj);
                    break;
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return nameValue;
    }
}
