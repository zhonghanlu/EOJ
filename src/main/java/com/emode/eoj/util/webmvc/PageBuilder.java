package com.emode.eoj.util.webmvc;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author zhl
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageBuilder<T> {

    private long current;

    private long size;

    private long total;

    private List<T> records;

    public static <T> PageBuilder<T> pB(IPage<T> page, List<T> records) {
        return PageBuilder.<T>builder()
                .current(page.getCurrent())
                .size(page.getSize())
                .total(page.getTotal())
                .records(records).build();
    }
}
