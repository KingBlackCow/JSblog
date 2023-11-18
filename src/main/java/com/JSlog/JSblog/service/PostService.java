package com.JSlog.JSblog.service;

import com.JSlog.JSblog.domain.Post;
import com.JSlog.JSblog.domain.PostEditor;
import com.JSlog.JSblog.exception.PostNotFound;
import com.JSlog.JSblog.repository.PostRepository;
import com.JSlog.JSblog.request.PostCreate;
import com.JSlog.JSblog.request.PostEdit;
import com.JSlog.JSblog.request.PostSearch;
import com.JSlog.JSblog.response.PostResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public void write(PostCreate postCreate) {
        // postCreaete -> entity
        Post post = Post.builder()
                .title(postCreate.getTitle())
                .content(postCreate.getContent())
                .build();
        postRepository.save(post);
    }

    public PostResponse get(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(PostNotFound::new);

        PostResponse response = PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .build();
        return response;
    }

    public List<PostResponse> getList(PostSearch postSearch) {
//        Pageable pageable = PageRequest.of(page, 5, Sort.by(Sort.Direction.DESC,"id"));
//        return postRepository.findAll(pageable).stream()
//                .map(PostResponse::new)
//                .collect(Collectors.toList());
        return postRepository.getList(postSearch).stream()
                .map(PostResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void edit(Long id, PostEdit postEdit) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFound());
        //post.change(postEdit.getTitle(), postEdit.getContent());
        //postRepository.save(post);
        PostEditor.PostEditorBuilder editorBuilder = post.toEditor();

        if(postEdit.getTitle() != null){
            editorBuilder.title(postEdit.getTitle());
        }

        if(postEdit.getContent() != null){
            editorBuilder.content(postEdit.getContent());
        }

//        PostEditor postEditor = editorBuilder
//                .title(postEdit.getTitle())
//                .content(postEdit.getContent())
//                .build();

        //post.edit(postEditor);  //1번방법
        post.edit(editorBuilder.build()); //2번방법
    }

    public void delete(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글입니다."));
        postRepository.delete(post);
    }
}
