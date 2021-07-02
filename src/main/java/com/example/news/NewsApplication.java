package com.example.news;

import com.example.news.model.Role;
import com.example.news.model.User;
import com.example.news.repository.RoleRepository;
import com.example.news.repository.UserRepository;
import com.example.news.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class NewsApplication implements CommandLineRunner {

  @Autowired
  private AccountService accountService;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private RoleRepository roleRepository;

  public static void main(String[] args) {
    SpringApplication.run(NewsApplication.class, args);
  }

  @Bean
  public BCryptPasswordEncoder getBCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  public void run(String... args) {
    boolean shouldCreateRoleAdmin = roleRepository.findByName("ADMIN") == null;
    boolean shouldCreateRoleEditor = roleRepository.findByName("EDITOR") == null;

    boolean shouldCreateUserAdmin = userRepository.findByUsername("admin") == null;
    boolean shouldCreateUserEditor = userRepository.findByUsername("editor") == null;

    if (shouldCreateRoleAdmin) {
      accountService.saveRole(new Role("ADMIN"));
    }

    if (shouldCreateRoleEditor) {
      accountService.saveRole(new Role("EDITOR"));
    }

    if (shouldCreateUserAdmin) {
      accountService.saveUser(new User("admin", "1234"));
      accountService.addRoleToUser("admin", "ADMIN");
    }

    if (shouldCreateUserEditor) {
      accountService.saveUser(new User("editor", "1234"));
      accountService.addRoleToUser("editor", "EDITOR");
    }
  }
}
