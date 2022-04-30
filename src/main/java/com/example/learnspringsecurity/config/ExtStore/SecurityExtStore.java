package com.example.learnspringsecurity.config.ExtStore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityExtStore extends WebSecurityConfigurerAdapter {
  private final UserDetailsServiceImpl userDetailsService;
  private final PasswordEncoder passwordEncoder;

  @Autowired
  public SecurityExtStore(UserDetailsServiceImpl userDetailsService, PasswordEncoder passwordEncoder) {
    this.userDetailsService = userDetailsService;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
        .mvcMatchers("/").permitAll()
        .mvcMatchers("/admin.html").hasRole("admin")
        .mvcMatchers("/user.html").hasAnyRole("admin","user")
        .anyRequest().authenticated()
        .and().formLogin();
  }
}
