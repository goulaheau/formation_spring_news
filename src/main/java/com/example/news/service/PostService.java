package com.example.news.service;

import com.example.news.model.Post;
import java.util.List;

public interface PostService {
  List<Post> getAll();
  Post get(long id);
  Post save(Post post);
  void delete(long id);
}
