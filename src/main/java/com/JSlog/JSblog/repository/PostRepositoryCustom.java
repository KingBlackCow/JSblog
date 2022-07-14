package com.JSlog.JSblog.repository;

import com.JSlog.JSblog.domain.Post;
import com.JSlog.JSblog.request.PostSearch;

import java.util.List;

public interface PostRepositoryCustom {

    List<Post> getList(PostSearch postSearch);
}
