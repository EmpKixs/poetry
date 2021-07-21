package com.kixs.poetry.enums;

/**
 * 诗词歌赋类型枚举
 *
 * @author wangbing
 * @version v1.0.0
 * @since 2021/7/21 15:25
 */
public enum PoetryType {

    /**
     * 诗词歌赋类型枚举
     */
    SHI(1, "诗"),

    CI(2, "词"),

    CHU_CI(3, "楚辞");

    private final int code;

    private final String desc;

    PoetryType(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
