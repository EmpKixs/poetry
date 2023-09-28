package com.kixs.poetry.parser.tang;

import lombok.Data;

import java.util.List;

/**
 * 唐诗词（唐诗三百首）
 *
 * @author kixs
 * @version v1.0.0
 * @since 2023/9/28 16:37
 */
@Data
public class TangSanBaiPoetry extends TangPoetry {

    /**
     * 标签
     */
    private List<String> tags;
}
