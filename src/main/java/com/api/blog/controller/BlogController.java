package com.api.blog.controller;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.api.blog.entities.Blog;
import com.api.blog.services.BlogService;


@RestController
public class BlogController {	

	@Autowired
	private BlogService blogService;


	private static Logger logger = LogManager.getLogger(BlogController.class);

	@GetMapping("/blog/{page}")
	public ResponseEntity<Page<Blog>> getBlogs(@PathVariable("page") Integer page)
	{
		Page<Blog> list = this.blogService.getAllBlogs(page);
		if(list.getSize()<=0) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		logger.info("Method Called");
		return ResponseEntity.status(HttpStatus.CREATED).body(list);
	}
	
	@GetMapping("/blog/{id}")
	public ResponseEntity<Blog> getBlog(@PathVariable("id") int id)
	{
		Blog blog = this.blogService.getBlogById(id);
		if(blog == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return ResponseEntity.of(Optional.of(blog));
	}
	
	@PostMapping(path="/blog", consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Blog> addBlog(@RequestBody Blog blog)
	{
		Blog b = null;
		try{
			b = this.blogService.addBlog(blog);
			return ResponseEntity.of(Optional.of(b));
		}
		catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@DeleteMapping("/blog/{id}")
	public ResponseEntity<Void> deleteBlog(@PathVariable("id") int id)
	{
		try {
			this.blogService.deleteBlog(id);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@PutMapping("/blog/{id}")
	public ResponseEntity<Blog> updateBlog(@RequestBody Blog blog, @PathVariable("id") int id) 
	{
		try {
			this.blogService.updateBlog(blog, id);
			return ResponseEntity.ok().body(blog);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
}
