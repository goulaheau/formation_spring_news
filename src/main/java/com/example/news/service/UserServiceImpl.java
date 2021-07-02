package com.example.news.service;

import com.example.news.model.User;
import com.example.news.repository.UserRepository;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserServiceImpl implements UserService {

  @Autowired
  private UserRepository userRepository;

  @Override
  public List<User> getAll() {
    return userRepository.findAll();
  }

  @Override
  public User get(long id) {
    return userRepository.getById(id);
  }

  @Override
  public void delete(long id) {
    userRepository.deleteById(id);
  }
}
