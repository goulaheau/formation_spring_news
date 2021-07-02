package com.example.news.service;

import com.example.news.model.Role;
import com.example.news.model.User;

public interface AccountService {
  User saveUser(User user);
  Role saveRole(Role role);
  void addRoleToUser(String username, String roleName);
}
