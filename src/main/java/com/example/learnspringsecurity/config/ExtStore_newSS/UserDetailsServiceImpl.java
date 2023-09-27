package com.example.learnspringsecurity.config.ExtStore_newSS;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  private PasswordEncoder passwordEncoder;
  private Map<String, User> userDataBase;

  @Autowired
  public UserDetailsServiceImpl(PasswordEncoder passwordEncoder) {
    this.passwordEncoder = passwordEncoder;
    userDataBase = new HashMap<>();
    userDataBase.put("user1", new User("user1",passwordEncoder.encode("password1"),"admin"));
    userDataBase.put("user2", new User("user2",passwordEncoder.encode("password2"),"user"));
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userDataBase.get(username);
    if (user==null) {
      throw new UsernameNotFoundException("No user with name " + username);
    } else {
      return new UserDetailsImpl(user);
    }
  }
}
