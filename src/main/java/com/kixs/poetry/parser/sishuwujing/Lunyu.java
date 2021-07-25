package com.kixs.poetry.parser.sishuwujing;

import lombok.Data;

/**
 * 论语
 *
 * @author kixs
 * @version v1.0.0
 * @since 2021/7/24 23:07
 */
@Data
public class Lunyu {
    /**
     * 章节/卷
     */
    private String chapter;
    /**
     * 内容
     */
    private String paragraphs;
}
