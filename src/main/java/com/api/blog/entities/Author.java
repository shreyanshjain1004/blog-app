package com.api.blog.entities;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Author {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int authorId;
	
	@NotBlank(message = "Please enter a valid first name!!")
	@Size(min=2, max=20, message="Enter a name between 2 to 20 characters!!")
	private String firstName;
	
	@NotBlank(message = "Please enter a valid last name!!")
	private String lastName;
	
	@Column(unique = true)
	@NotBlank(message="Email field is Required!!")
    @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?",message="Invalid Email!!")
    private String email;
	
	@NotBlank(message="Password field is Required!!")
	private String password;
	
	private String profilePic;
	
	@OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
	@JsonBackReference
	private Collection<Blog> blogs;
	
	
	public Author() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Author(int authorId, String firstName, String lastName, String email, String password, String profilePic,
			Collection<Blog> blogs) {
		super();
		this.authorId = authorId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.profilePic = profilePic;
		this.blogs = blogs;
	}


	
	public Collection<Blog> getBlogs() {
		return blogs;
	}

	public void setBlogs(Collection<Blog> blogs) {
		this.blogs = blogs;
	}

	public int getAuthorId() {
		return authorId;
	}
	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getProfilePic() {
		return profilePic;
	}

	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}

	@Override
	public String toString() {
		return "Author [authorId=" + authorId + ", firstName=" + firstName + ", lastName=" + lastName + ", email="
				+ email + ", password=" + password + ", profilePic=" + profilePic + "]";
	}
	
	
}
