package com.example.news.security;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private DataSource dataSource;

  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    auth
      .jdbcAuthentication()
      .dataSource(dataSource)
      .passwordEncoder(bCryptPasswordEncoder)
      .usersByUsernameQuery("SELECT username, password, enabled FROM \"user\" WHERE username = ?")
      .authoritiesByUsernameQuery(
        "SELECT u.username, r.name " +
        "FROM \"user\" u " +
        "LEFT JOIN user_roles ur ON u.id = ur.user_id " +
        "LEFT JOIN role r ON r.id = ur.roles_id " +
        "WHERE u.username = ?"
      )
      .rolePrefix("ROLE_");
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.formLogin();
    http.csrf().disable();
    http.authorizeRequests().antMatchers("/login/**").permitAll();

    http.authorizeRequests().antMatchers(HttpMethod.GET, "/users/**").hasRole("ADMIN");
    http.authorizeRequests().antMatchers(HttpMethod.POST, "/users/**").hasRole("ADMIN");
    http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/users/**").hasRole("ADMIN");

    http.authorizeRequests().antMatchers(HttpMethod.POST, "/posts/**").hasRole("ADMIN");
    http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/posts/**").hasRole("ADMIN");

    http
      .authorizeRequests()
      .antMatchers(HttpMethod.POST, "/contents/**")
      .hasAnyRole("EDITOR", "ADMIN");
    http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/contents/**").hasRole("ADMIN");

    http.authorizeRequests().anyRequest().authenticated();
  }
}
