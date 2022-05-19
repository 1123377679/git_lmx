package org.lanqiao.utils;

import lombok.*;

import java.util.*;

/**
 * 分页工具类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageHelper<T> {
    private Long pageIndex;//当前页
    private Long pageSize;//一页显示多少条
    private Long pageCount;//总页数
    private Long totalCount;//总条数
    private List<T> records;

}
