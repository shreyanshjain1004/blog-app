package com.api.blog.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.api.blog.entities.Author;

public interface AuthorRepository extends JpaRepository<Author, Integer>{
	@Query("select u from Author u where u.email = :email")
	 Author getAuthorByName(@Param("email") String email);
}
