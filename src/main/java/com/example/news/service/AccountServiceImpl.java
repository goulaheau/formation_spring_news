package com.example.news.service;

import com.example.news.model.Role;
import com.example.news.model.User;
import com.example.news.repository.RoleRepository;
import com.example.news.repository.UserRepository;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private RoleRepository roleRepository;

  @Override
  public User saveUser(User user) {
    String hashedPassword = bCryptPasswordEncoder.encode(user.getPassword());

    user.setPassword(hashedPassword);

    return userRepository.save(user);
  }

  @Override
  public Role saveRole(Role role) {
    return roleRepository.save(role);
  }

  @Override
  public void addRoleToUser(String username, String roleName) {
    Role role = roleRepository.findByName(roleName);
    User user = userRepository.findByUsername(username);

    user.getRoles().add(role);
  }
}
