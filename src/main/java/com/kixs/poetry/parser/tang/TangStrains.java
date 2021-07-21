package com.kixs.poetry.parser.tang;

import lombok.Data;

/**
 * 唐·韵律/声调/格律
 *
 * @author wangbing
 * @version v1.0.0
 * @since 2021/7/21 15:53
 */
@Data
public class TangStrains {

    /**
     * ID
     */
    private String id;
    /**
     * 韵律/声调/格律
     */
    private String strains;
}
