package com.JSlog.JSblog.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Lob
    private String content;

    @ManyToOne
    @JoinColumn
    private User user;

    @Builder
    public Post(String title, String content, User user) {
        this.title = title;
        this.content = content;
        this.user = user;
    }

    public void change(String title, String content){
        this.title = title;
        this.content = content;
    }

    public PostUpdateDto.PostUpdateDtoBuilder toEditor(){
        return PostUpdateDto.builder()
                .title(this.title)
                .content(this.content);
    }

    public void edit(PostUpdateDto postUpdateDto) {
        this.title = postUpdateDto.getTitle();
        this.content = postUpdateDto.getContent();
    }

    public Long getUserId() {
        return this.user.getId();
    }
}
