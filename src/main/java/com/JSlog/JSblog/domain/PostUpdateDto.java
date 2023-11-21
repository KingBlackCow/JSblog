package com.JSlog.JSblog.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PostUpdateDto {
    private final String title;
    private final String content;

    @Builder
    public PostUpdateDto(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
