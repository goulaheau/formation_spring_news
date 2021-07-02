package com.example.news.service;

import com.example.news.model.Post;
import com.example.news.repository.PostRepository;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class PostServiceImpl implements PostService {

  @Autowired
  private PostRepository postRepository;

  @Override
  public List<Post> getAll() {
    return postRepository.findAll();
  }

  @Override
  public Post get(long id) {
    return postRepository.getById(id);
  }

  @Override
  public Post save(Post post) {
    return postRepository.save(post);
  }

  @Override
  public void delete(long id) {
    postRepository.deleteById(id);
  }
}
