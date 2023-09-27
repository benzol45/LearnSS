package com.example.learnspringsecurity.config.ExtStore_newSS;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class UserDetailsImpl implements UserDetails {
  private final String name;
  private final String hashPassword;
  private final List<GrantedAuthority> authorities;

  public UserDetailsImpl(User user) {
    this.name = user.getName();
    this.hashPassword = user.getPassword();
    this.authorities = List.of(new SimpleGrantedAuthority("ROLE_"+user.getRole()));
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.authorities;
  }

  @Override
  public String getPassword() {
    return this.hashPassword;
  }

  @Override
  public String getUsername() {
    return this.name;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
