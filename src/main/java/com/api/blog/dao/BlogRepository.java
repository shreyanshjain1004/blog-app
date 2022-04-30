package com.api.blog.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.api.blog.entities.Blog;

public interface BlogRepository extends CrudRepository<Blog, Integer>{
	public Blog findById(int id);
	@Query(value="select * from blogs order by creation_date desc", nativeQuery=true)
	public Page<Blog> findAllOrderByCreationDateDesc(Pageable pePagable);
	
	@Query(value="select * from blogs b where b.author_id = :author_id order by creation_date desc", nativeQuery=true)
	public Page<Blog> findAllByAuthorId(@Param("author_id") int author_id, Pageable pg);
}
