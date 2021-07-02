package com.example.news.controller;

import com.example.news.model.Content;
import com.example.news.model.Post;
import com.example.news.model.User;
import com.example.news.service.ContentService;
import com.example.news.service.PostService;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/contents")
public class ContentController {

  @Autowired
  private ContentService contentService;

  @Autowired
  private PostService postService;

  @GetMapping
  public String getContentListPage(
    Model model,
    @RequestParam(name = "idToDelete", required = false) Optional<Integer> idToDelete
  ) {
    if (idToDelete.isPresent()) {
      try {
        contentService.delete(idToDelete.get());
      } catch (Exception ignored) {}
    }

    List<Content> contents = contentService.getAll();
    List<Post> posts = postService.getAll();

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    boolean isAdmin = authentication
      .getAuthorities()
      .stream()
      .anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"));

    model.addAttribute("contents", contents);
    model.addAttribute("posts", posts);
    model.addAttribute("isAdmin", isAdmin);
    model.addAttribute("contentToAdd", new Content());

    return "contents";
  }

  @PostMapping
  public String saveOrDelete(Model model, @ModelAttribute("contentToAdd") Content contentToAdd) {
    contentService.save(contentToAdd);

    return getContentListPage(model, Optional.empty());
  }
}
