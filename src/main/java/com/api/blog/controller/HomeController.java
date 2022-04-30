package com.api.blog.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.RedirectView;

import com.api.blog.dao.AuthorRepository;
import com.api.blog.entities.Author;
import com.api.blog.entities.Blog;
import com.api.blog.helper.Message;
import com.api.blog.services.BlogService;

@Controller
public class HomeController implements ErrorController {

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private BlogService blogService;
	@Autowired
	private AuthorRepository authorRepo;
	private static Logger logger = LogManager.getLogger(BlogController.class);
	@RequestMapping("/{page}")
	public String getBlogs(@PathVariable("page") Integer page, Model m)
	{
		Page<Blog> list = this.blogService.getAllBlogs(page);
		m.addAttribute("blogs",list);
		m.addAttribute("currentPage", page);
		m.addAttribute("totalPages", list.getTotalPages());
		logger.info("blog paGE");
		return "index";
	}
	
	@RequestMapping("/")
	public String homeMapping()
	{
		return "redirect:/0";
	}
	
	@RequestMapping("/post/{id}")
	public String getBlog(@PathVariable("id") int id, Model m)
	{
		Blog blog = this.blogService.getBlogById(id);
		m.addAttribute("blog", blog);
		return "post";
	}
	
	@RequestMapping("/about")
	public String showAbout()
	{
		return "about";
	}
	
	@RequestMapping("/contact")
	public String showContact()
	{
		return "contact";
	}
	
	@RequestMapping("/signup")
	public String signUp(Model m)
	{
		m.addAttribute("author", new Author());
		return "signup";
	}
	
	@RequestMapping(value="/do_register", method=RequestMethod.POST)
	public String registerUser(@Valid @ModelAttribute("author") Author author, BindingResult res, HttpSession session, Model m)
	{
		try {
			if(res.hasErrors())
			{
				System.out.println(res);
				m.addAttribute("author", author);
				return "signup";
			}
			author.setPassword(passwordEncoder.encode(author.getPassword()));
			System.out.println(author);
			Author result = this.authorRepo.save(author);
			m.addAttribute("author", new Author());
			session.setAttribute("message", new Message("SuccessFully Registered" ,"alert-success"));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			m.addAttribute("author", author);
			System.out.println("Hello");
			session.setAttribute("message", new Message("Something Went Wrong!!" + e.getMessage(),"alert-danger"));
		}
		return "redirect:/signin";
	}
	
	@RequestMapping("/signin")
	public String customLogin(Model m)
	{
		return "login";
	}
	
	@RequestMapping("/error")
	public String handleError(HttpServletRequest request, Model m)
	{
		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
	    Integer statusCode = Integer.valueOf(status.toString());
	    m.addAttribute("error", RequestDispatcher.ERROR_EXCEPTION);
	    m.addAttribute("status", statusCode);
	    return "error";
	}
}
