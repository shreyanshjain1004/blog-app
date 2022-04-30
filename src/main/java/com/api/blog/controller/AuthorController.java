package com.api.blog.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.api.blog.dao.AuthorRepository;
import com.api.blog.dao.BlogRepository;
import com.api.blog.entities.Author;
import com.api.blog.entities.Blog;
import com.api.blog.helper.Message;

@Controller
@RequestMapping("/author")
public class AuthorController {
	
	@Autowired
	private AuthorRepository authRepo;
	
	@Autowired
	private BlogRepository blogRepo;
	
	@ModelAttribute
	public void addCommonData(Model m, Principal principal)
	{
		String username = principal.getName();
		Author author = authRepo.getAuthorByName(username);
		m.addAttribute("author", author);		
	}
	
	@RequestMapping("/index")
	public String dashboard(Model m, Principal principal)
	{
		return "author/author_dash";
	}
	
	@RequestMapping("/add-blog")
	public String addBlog(Model m)
	{
		m.addAttribute("blog", new Blog());
		return "author/add_blog";
	}
	
	@RequestMapping(value="/process-blog", method=RequestMethod.POST)
	public String processBlog(@ModelAttribute Blog blog, @RequestParam("blogImage") MultipartFile file, Principal principal){
		try {
			String username = principal.getName();
			Author author = authRepo.getAuthorByName(username);
			if(file.isEmpty())
			{
				System.out.println("file is empty");
			}
			else
			{
				blog.setImage(file.getOriginalFilename());
				File saveFile = new ClassPathResource("static/img").getFile();
				Path path = Paths.get(saveFile.getAbsolutePath()+File.separator+file.getOriginalFilename());
				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
			}
			blog.setAuthor(author);
			this.blogRepo.save(blog);
			System.out.println("Added");
			System.out.println(blog);
			return "redirect:/author/index";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return "author/add_blog";
		}
	}
	
	@RequestMapping("/view-blogs/{page}")
	public String getBlogs(@PathVariable("page") Integer page, Model m, Principal principal)
	{
		String username = principal.getName();
		Author author = authRepo.getAuthorByName(username);
		Pageable pg = PageRequest.of(page, 4);
		Page<Blog> list = this.blogRepo.findAllByAuthorId(author.getAuthorId(),pg);
		m.addAttribute("blogs",list);
		m.addAttribute("currentPage", page);
		m.addAttribute("totalPages", list.getTotalPages());
		return "author/view_blogs";
	}
	
	@RequestMapping("/view-blogs")
	public String homeMapping()
	{
		return "redirect:view-blogs/0";
	}
	
	@RequestMapping("/delete/{id}")
	public String deleteContact(@PathVariable("id") Integer id, HttpSession session)
	{
		Optional<Blog> opBlog = this.blogRepo.findById(id);
		Blog blog = opBlog.get();
		this.blogRepo.delete(blog);
		System.out.println("Deleted");
		session.setAttribute("message", new Message("Contact deleted succesfully...", "success"));
		return "redirect:/author/view-blogs/0";
	}
	
	@RequestMapping(value="/edit-blog/{id}", method=RequestMethod.POST)
	public String editBlog(@PathVariable("id") Integer id, Model m)
	{
		Optional<Blog> opBlog = this.blogRepo.findById(id);
		Blog blog = opBlog.get();
		m.addAttribute("blog",blog);
		return "author/edit_blog";
	}
	
	@RequestMapping(value="/process-edit", method=RequestMethod.POST)
	public String editHandler(@ModelAttribute Blog blog, @RequestParam("blogImage") MultipartFile file, Principal principal)
	{
		try {
			String username = principal.getName();
			Author author = authRepo.getAuthorByName(username);	
			System.out.println(blog);
			Blog oldBlog = this.blogRepo.findById(blog.getId());
			
			if(file.isEmpty())
			{
				System.out.println("file is empty");
				blog.setImage(oldBlog.getImage());
			}
			else
			{	
				File deleteFile = new ClassPathResource("static/img").getFile();
				File file1 = new File(deleteFile, oldBlog.getImage());
				file1.delete();
				
				
				blog.setImage(file.getOriginalFilename());
				File saveFile = new ClassPathResource("static/img").getFile();
				Path path = Paths.get(saveFile.getAbsolutePath()+File.separator+file.getOriginalFilename());
				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
			}
			blog.setAuthor(author);
			this.blogRepo.save(blog);
			System.out.println("Added");
			System.out.println(blog);
			return "redirect:/post/"+blog.getId();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return "author/edit_blog";
		}
	}
	
}
