package com.example.learnspringsecurity.config.HardcodeUsers;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;


//@EnableWebSecurity
public class SecurityFixUsersInMemory extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.inMemoryAuthentication()
        .withUser("user1").password("password1").roles("admin")
        .and().withUser("user2").password("password2").roles("user")
        .and().passwordEncoder(NoOpPasswordEncoder.getInstance());
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
        .mvcMatchers("/").permitAll()
        .mvcMatchers("/admin.html").hasRole("admin")
        .mvcMatchers("/user.html").hasAnyRole("admin","user")
        .anyRequest().authenticated()
        .and().formLogin();
        //.and().httpBasic();

  }
}
