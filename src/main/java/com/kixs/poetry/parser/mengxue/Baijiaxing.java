package com.kixs.poetry.parser.mengxue;

import joptsimple.internal.Strings;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 百家姓
 *
 * @author wangbing
 * @version v1.0.0
 * @since 2021/7/22 17:20
 */
@Data
public class Baijiaxing {

    /**
     * 标题
     */
    private String title;
    /**
     * 作者
     */
    private String author;
    /**
     * 标签
     */
    private String tags;
    /**
     * 内容
     */
    private String paragraphs;
    /**
     * 姓氏地域映射
     */
    private List<Origin> origin;

    /**
     * 姓氏地域映射
     */
    @Data
    static class Origin {

        private String surname;

        private String place;
    }

    /**
     * 姓氏地域映射转换输出
     *
     * @return 姓氏地域映射
     */
    public String originConvert() {
        if (CollectionUtils.isEmpty(origin)) {
            return Strings.EMPTY;
        }
        return origin.stream()
                .map(e -> e.getSurname() + " ---> " + e.getPlace())
                .collect(Collectors.joining("\n"));
    }

}
