package com.kixs.poetry.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * Emoji符号过滤检测
 *
 * @author kixs
 * @version v1.0.0
 * @since 2020/10/5 21:33
 */
public class EmojiUtils {

    private final static String DEFAULT_REPLACE_STR = "#*#";

    /**
     * 字符串中是否包含Emoji符号
     *
     * @param source 源字符串
     * @return true：包含；false：不包含
     */
    public static boolean containsEmoji(String source) {
        if (StringUtils.isNotBlank(source)) {
            int len = source.length();
            for (int i = 0; i < len; i++) {
                if (isEmojiCharacter(Character.codePointAt(source, i))) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 检测当前是否为Emoji符号
     *
     * @param codePoint 被检测数据
     * @return true：是；false：不是
     */
    private static boolean isEmojiCharacter(int codePoint) {
        return (0x0080 <= codePoint && codePoint <= 0x02AF)
                || (0x0300 <= codePoint && codePoint <= 0x03FF)
                || (0x0600 <= codePoint && codePoint <= 0x06FF)
                || (0x0C00 <= codePoint && codePoint <= 0x0C7F)
                || (0x1DC0 <= codePoint && codePoint <= 0x1DFF)
                || (0x1E00 <= codePoint && codePoint <= 0x1EFF)
                || (0x2000 <= codePoint && codePoint <= 0x209F)
                || (0x20D0 <= codePoint && codePoint <= 0x214F)
                || (0x2190 <= codePoint && codePoint <= 0x23FF)
                || (0x2460 <= codePoint && codePoint <= 0x25FF)
                || (0x2600 <= codePoint && codePoint <= 0x27EF)
                || (0x2900 <= codePoint && codePoint <= 0x29FF)
                || (0x2B00 <= codePoint && codePoint <= 0x2BFF)
                || (0x2C60 <= codePoint && codePoint <= 0x2C7F)
                || (0x2E00 <= codePoint && codePoint <= 0x2E7F)
                || (0xA490 <= codePoint && codePoint <= 0xA4CF)
                || (0xE000 <= codePoint && codePoint <= 0xF8FF)
                || (0xFE00 <= codePoint && codePoint <= 0xFE0F)
                || (0xFE30 <= codePoint && codePoint <= 0xFE4F)
                || (0x1F000 <= codePoint && codePoint <= 0x1F02F)
                || (0x1F0A0 <= codePoint && codePoint <= 0x1F0FF)
                || (0x1F100 <= codePoint && codePoint <= 0x1F64F)
                || (0x1F680 <= codePoint && codePoint <= 0x1F6FF)
                || (0x1F910 <= codePoint && codePoint <= 0x1F96B)
                || (0x1F980 <= codePoint && codePoint <= 0x1F9E0);
    }

    /**
     * 过滤返回字符串中包含的Emoji符号
     *
     * @param source 源字符串
     * @return 字符串包含的Emoji符号
     */
    public static String filterEmoji(String source) {
        if (StringUtils.isBlank(source)) {
            return source;
        }
        StringBuilder buf = new StringBuilder();
        int len = source.length();
        for (int i = 0; i < len; i++) {
            int codePoint = Character.codePointAt(source, i);
            if (isEmojiCharacter(codePoint)) {
                buf.append(Character.toChars(codePoint));
            }
        }
        return buf.toString();
    }

    /**
     * 替换符串中的Emoji符号
     *
     * @param source 源字符串
     * @return 替换后的字符串
     */
    public static String replaceEmoji(String source) {
        return replaceEmoji(source, DEFAULT_REPLACE_STR);
    }

    /**
     * 替换符串中的Emoji符号
     *
     * @param source     源字符串
     * @param replaceStr Emoji替代内容
     * @return 替换后的字符串
     */
    public static String replaceEmoji(String source, String replaceStr) {
        if (StringUtils.isBlank(source)) {
            return source;
        }
        StringBuilder buf = new StringBuilder();
        int len = source.length();
        for (int i = 0; i < len; i++) {
            int codePoint = Character.codePointAt(source, i);
            if (isEmojiCharacter(codePoint)) {
                buf.append(replaceStr);
            } else {
                buf.append(Character.toChars(codePoint));
            }
        }
        return buf.toString();
    }
}
