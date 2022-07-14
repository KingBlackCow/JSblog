package com.JSlog.JSblog.service;

import com.JSlog.JSblog.domain.Post;
import com.JSlog.JSblog.repository.PostRepository;
import com.JSlog.JSblog.request.PostCreate;
import com.JSlog.JSblog.request.PostEdit;
import com.JSlog.JSblog.request.PostSearch;
import com.JSlog.JSblog.response.PostResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.data.domain.Sort.Direction.*;

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
    @DisplayName("글 1페이지 조회")
    void test3(){

        List<Post> requestPosts = IntStream.range(0, 20)
                        .mapToObj(i ->{
                            return Post.builder()
                                    .title("foo" + i)
                                    .content("bar1 " + i)
                                    .build();
                        })
                        .collect(Collectors.toList());
        postRepository.saveAll(requestPosts);

        //Pageable pageable = PageRequest.of(0, 5, Sort.by(DESC,"id"));
        PostSearch postSearch = PostSearch.builder()
                .page(1)
                .build();

        List<PostResponse> posts = postService.getList(postSearch);

        assertEquals(10L, posts.size());
        assertEquals("foo19", posts.get(0).getTitle());
//        assertEquals("호돌맨 제목 26", posts.get(4).getTitle());
    }

    @Test
    @DisplayName("글 제목 수정")
    void test4(){

        Post post = Post.builder()
                .title("이조순제목")
                .content("이조순내용")
                .build();

        postRepository.save(post);

        PostEdit postEdit =PostEdit.builder()
                .title("제목")
                .build();

        postService.edit(post.getId(), postEdit);

        Post changedPost = postRepository.findById(post.getId())
                .orElseThrow(() -> new RuntimeException("글이 존재하지 않습니다. id=" + post.getId()));

        assertEquals("제목", changedPost.getTitle());
    }

    @Test
    @DisplayName("글 제목 수정")
    void test5(){

        Post post = Post.builder()
                .title("이조순제목")
                .content("이조순내용")
                .build();

        postRepository.save(post);

        PostEdit postEdit =PostEdit.builder()
                .title("제목")
                .content("내용")
                .build();

        postService.edit(post.getId(), postEdit);

        Post changedPost = postRepository.findById(post.getId())
                .orElseThrow(() -> new RuntimeException("글이 존재하지 않습니다. id=" + post.getId()));

        assertEquals("제목", changedPost.getTitle());
        assertEquals("내용", changedPost.getContent());
    }

}