package com.JSlog.JSblog.service;

import com.JSlog.JSblog.domain.Post;
import com.JSlog.JSblog.repository.PostRepository;
import com.JSlog.JSblog.request.PostCreate;
import com.JSlog.JSblog.response.PostResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;


    @BeforeEach
    void clean(){
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("글 작성")
    void test1(){
        PostCreate postCreate = PostCreate.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();

        postService.write(postCreate);

        assertEquals(1L, postRepository.count());
        Post post = postRepository.findAll().get(0);
        assertEquals("제목입니다.", post.getTitle());
        assertEquals("내용입니다.", post.getContent());
    }

    @Test
    @DisplayName("글 1개 조회")
    void test2(){
        Post requestPost = Post.builder()
                .title("foo")
                .content("bar")
                .build();
        postRepository.save(requestPost);

        Long postId = 1L;
        PostResponse post = postService.get(requestPost.getId());
        assertNotNull(post);
        assertEquals(1L, postRepository.count());
        assertEquals("foo", post.getTitle());
        assertEquals("bar", post.getContent());
    }

    @Test
    @DisplayName("글 여러개 조회")
    void test3(){
        postRepository.saveAll(List.of(
                Post.builder()
                        .title("foo1")
                        .content("bar1")
                        .build(),
                Post.builder()
                        .title("foo2")
                        .content("bar2")
                        .build()
        ));

        Long postId = 1L;
        List<PostResponse> posts = postService.getList();

        assertEquals(2L, posts.size());
    }

}