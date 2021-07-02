package com.example.news.service;

import com.example.news.model.User;
import java.util.List;

public interface UserService {
  List<User> getAll();
  User get(long id);
  void delete(long id);
}
