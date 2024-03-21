package com.xh.security.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResult<T> {

    /**
     * 数据列表
     */
    private List<T> data;

    /**
     * 数据总数
     */
    private long total;

}
