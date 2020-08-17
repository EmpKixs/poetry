package com.kixs.poetry.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * 文件操作工具类
 *
 * @author wangbing
 * @version v1.0.0
 * @since 2020/8/17 13:19
 */
public class FileUtils {

    /**
     * 获取文件夹下所有文件
     *
     * @param path 文件夹路径
     * @return 文件列表
     */
    public static File[] listDirectoryFiles(String path) {
        File directory = new File(path);
        if (!directory.isDirectory()) {
            throw new RuntimeException("文件夹不存在：" + path);
        }

        return directory.listFiles();
    }

    /**
     * 读取指定文件
     *
     * @param filePath 文件绝对路径
     * @return 文件内容
     */
    public static String read(String filePath) {
        return read(new File(filePath));
    }

    /**
     * 读取指定文件
     *
     * @param file 文件
     * @return 文件内容
     */
    public static String read(File file) {
        if (!file.exists()) {
            throw new RuntimeException("文件不存在：" + file.getAbsolutePath());
        }
        BufferedReader reader = null;
        StringBuilder builder;
        try {
            reader = new BufferedReader(new FileReader(file));
            builder = new StringBuilder();
            String temp;
            while ((temp = reader.readLine()) != null) {
                builder.append(temp);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return builder.toString();
    }
}
