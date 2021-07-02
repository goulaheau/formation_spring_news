package com.example.news.controller;

import com.example.news.model.Post;
import com.example.news.service.PostService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/posts")
public class PostController {

  @Autowired
  private PostService postService;

  @GetMapping
  public String getPostListPage(
    Model model,
    @RequestParam(name = "idToDelete", required = false) Optional<Integer> idToDelete
  ) {
    if (idToDelete.isPresent()) {
      try {
        postService.delete(idToDelete.get());
      } catch (Exception ignored) {}
    }

    List<Post> posts = postService.getAll();

    model.addAttribute("posts", posts);
    model.addAttribute("postToAdd", new Post());

    return "posts";
  }

  @PostMapping
  public String saveOrDelete(Model model, @ModelAttribute("postToAdd") Post postToAdd) {
    postService.save(postToAdd);

    return getPostListPage(model, Optional.empty());
  }
}
