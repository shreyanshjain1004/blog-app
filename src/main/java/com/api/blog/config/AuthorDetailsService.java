package com.api.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.api.blog.dao.AuthorRepository;
import com.api.blog.entities.Author;

@Service
public class AuthorDetailsService implements UserDetailsService{

	@Autowired
	private AuthorRepository authorRepo;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Author author = authorRepo.getAuthorByName(username);
		if(author == null)
		{
			throw new UsernameNotFoundException("Could not found user");
		}
		
		CustomAuthorDetails customAuthorDetails = new CustomAuthorDetails(author);
		return customAuthorDetails;
	}

}
