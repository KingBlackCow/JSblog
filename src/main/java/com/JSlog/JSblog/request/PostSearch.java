package com.JSlog.JSblog.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import static java.lang.Math.*;

@Getter
@Setter
@Builder
public class PostSearch {
    @Builder.Default
    private Integer page = 1;
    @Builder.Default
    private Integer size = 10;

    private static final int MAX_VALUE = 2000;

    public long getOffset() {
        return (long) (max(1, page) - 1) * min(size, MAX_VALUE);
    }
}
