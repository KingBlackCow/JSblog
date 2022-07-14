package com.JSlog.JSblog.controller;

import com.JSlog.JSblog.domain.Post;
import com.JSlog.JSblog.repository.PostRepository;
import com.JSlog.JSblog.request.PostCreate;
import com.JSlog.JSblog.response.PostResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@WebMvcTest // @SpringBootTest와 같이 쓰면₩ 안됨
@AutoConfigureMockMvc // mockMvc주입일 경우
@SpringBootTest
class PostControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;//mockMvc 주입은 @WebMvcTest 어노테이션을 통해 주입한다.

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    void clean(){
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("/posts 요청 시 Hello World를 출력한다.")
    void test() throws Exception{
        PostCreate request = PostCreate.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();
        String json = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/posts")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                ) //application/json or x-www-form-urlencodeed
                .andExpect(status().isOk())
                .andExpect(content().string(""))
                .andDo(print());
    }

    @Test
    @DisplayName("/posts 요청 시 title값은 필수다.")
    void test2() throws Exception{
        PostCreate request = PostCreate.builder()
                .content("내용입니다.")
                .build();
        String json = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/posts")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                ) //application/json or x-www-form-urlencodeed
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
                .andExpect(jsonPath("$.validation.title").value("타이틀을 입력해주세요."))
                .andDo(print());
    }

    @Test
    @DisplayName("/posts 요청 시 db에 값이 저장된다.")
    void test3() throws Exception{
        PostCreate request = PostCreate.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();
        String json = objectMapper.writeValueAsString(request);
        mockMvc.perform(post("/posts")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                ) //application/json or x-www-form-urlencodeed
                .andExpect(status().isOk())
                .andDo(print());

        assertEquals(1L, postRepository.count());
        Post post = postRepository.findAll().get(0);
        assertEquals("제목입니다.", post.getTitle());
        assertEquals("내용입니다.", post.getContent());
    }

    @Test
    @DisplayName("글 1개 조회")
    void test4() throws Exception{
        Post post = Post.builder()
                .title("123456789012345")
                .content("bar")
                .build();

        postRepository.save(post);

        mockMvc.perform(get("/posts/{postId}", post.getId())
                        .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(post.getId()))
                .andExpect(jsonPath("$.title").value("1234567890"))
                .andExpect(jsonPath("$.content").value("bar"))
                .andDo(print());
    }

//    @Test
//    @DisplayName("글 여러개 조회")
//    void test5() throws Exception {
//        List<Post> requestPosts = IntStream.range(1,31)
//                .mapToObj(i ->{
//                    return Post.builder()
//                            .title("호돌맨 제목 " + i)
//                            .content("반포자이 " + i)
//                            .build();
//                })
//                .collect(Collectors.toList());
//        postRepository.saveAll(requestPosts);
//
//        mockMvc.perform(get("/posts?page=1&sort=id,desc")
//                        .contentType(APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.length()", Matchers.is(5)))
//                .andExpect(jsonPath("$[0].id").value(30))
//                .andExpect(jsonPath("$[0].title").value("호돌맨 제목 30"))
//                .andExpect(jsonPath("$[0].content").value("반포자이 30"))
//                .andDo(print());
//    }

    @Test
    @DisplayName("글 여러개 조회")
    void test5() throws Exception {
        List<Post> requestPosts = IntStream.range(0,10)
                .mapToObj(i ->{
                    return Post.builder()
                            .title("foo " + i)
                            .content("bar " + i)
                            .build();
                })
                .collect(Collectors.toList());
        postRepository.saveAll(requestPosts);

        mockMvc.perform(get("/posts?page=1&size=10")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", Matchers.is(10)))
                .andExpect(jsonPath("$[0].title").value("foo 9"))
                .andExpect(jsonPath("$[0].content").value("bar 9"))
                .andDo(print());
    }

    @Test
    @DisplayName("페이지를 0으로 요청하면 첫 페이지를 가져온다.")
    void test6() throws Exception {
        List<Post> requestPosts = IntStream.range(0,10)
                .mapToObj(i ->{
                    return Post.builder()
                            .title("foo " + i)
                            .content("bar " + i)
                            .build();
                })
                .collect(Collectors.toList());
        postRepository.saveAll(requestPosts);

        mockMvc.perform(get("/posts?page=0&size=10")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", Matchers.is(10)))
                .andExpect(jsonPath("$[0].title").value("foo 9"))
                .andExpect(jsonPath("$[0].content").value("bar 9"))
                .andDo(print());
    }


}