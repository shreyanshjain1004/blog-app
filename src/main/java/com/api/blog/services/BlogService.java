package com.api.blog.services;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.api.blog.dao.BlogRepository;
import com.api.blog.entities.Blog; 

@Component
public class BlogService {
	
	@Autowired
	private BlogRepository blogRepository;
	
//	private static List<Blog> list=new ArrayList<>(); 
//	
//	static {
//		list.add(new Blog(12,"Java Blog","kd sharma"));
//		list.add(new Blog(13,"Python Blog","dolby meter"));
//		list.add(new Blog(42,"Maths Blog","rd sharma"));
//	}
	
	public Page<Blog> getAllBlogs(int page)
	{
		Pageable pg = PageRequest.of(page, 4);
		Page<Blog> list = (Page<Blog>)this.blogRepository.findAllOrderByCreationDateDesc(pg);
		return list;
	}
	
	public Blog getBlogById(int id)
	{
		Blog blog=null;
		try {
			blog = this.blogRepository.findById(id);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return blog;
	}
	
	public Blog addBlog(Blog b)
	{
		Blog result=null;
		try {
			result = blogRepository.save(b);          
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
		return result;
	}
	
	public void deleteBlog(int id)
	{
		blogRepository.deleteById(id);
	}
	
	public void updateBlog(Blog blog, int id)
	{
		blog.setId(id);
		blogRepository.save(blog);
	}
}
