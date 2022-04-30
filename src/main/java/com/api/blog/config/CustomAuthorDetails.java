package com.api.blog.config;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.api.blog.entities.Author;

public class CustomAuthorDetails implements UserDetails{

	private Author author;
	
	public CustomAuthorDetails(Author author) {
		super();
		this.author = author;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	@Override
	public String getPassword() {		
		return author.getPassword();
	}

	@Override
	public String getUsername() {		
		return author.getEmail();
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
