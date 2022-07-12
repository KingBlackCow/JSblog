package com.JSlog.JSblog.service;

import com.JSlog.JSblog.domain.Post;
import com.JSlog.JSblog.repository.PostRepository;
import com.JSlog.JSblog.request.PostCreate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    public void write(PostCreate postCreate){
        // postCreaete -> entity
        Post post = new Post(postCreate.getTitle(), postCreate.getContent());
        postRepository.save(post);
    }
}
