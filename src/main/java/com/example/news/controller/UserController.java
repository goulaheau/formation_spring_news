package com.example.news.controller;

import com.example.news.model.User;
import com.example.news.service.AccountService;
import com.example.news.service.UserService;
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
@RequestMapping("/users")
public class UserController {

  @Autowired
  private UserService userService;

  @Autowired
  private AccountService accountService;

  @GetMapping
  public String getUserListPage(
    Model model,
    @RequestParam(name = "idToDelete", required = false) Optional<Integer> idToDelete
  ) {
    if (idToDelete.isPresent()) {
      try {
        userService.delete(idToDelete.get());
      } catch (Exception ignored) {}
    }

    List<User> users = userService.getAll();

    model.addAttribute("users", users);
    model.addAttribute("userToAdd", new User());

    return "users";
  }

  @PostMapping
  public String saveOrDelete(Model model, @ModelAttribute("userToAdd") User userToAdd) {
    accountService.saveUser(userToAdd);

    return getUserListPage(model, Optional.empty());
  }
}
