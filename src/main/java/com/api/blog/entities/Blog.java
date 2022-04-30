package com.api.blog.entities;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name="blogs")
public class Blog {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	private String title;
	
	@Column(name = "body", columnDefinition = "TEXT", nullable = false)
    private String body;
	
	private String image;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creation_date", nullable = false, updatable = false)
    private Date creationDate;
	
	@ManyToOne
	@JsonManagedReference
	@JsonIgnore
    @JoinColumn(name = "author_id", referencedColumnName = "authorId")
	private Author author;
	
	public Blog() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Blog(int id, String title, String body, String image, Date creationDate, Author author) {
		super();
		this.id = id;
		this.title = title;
		this.body = body;
		this.image = image;
		this.creationDate = creationDate;
		this.author = author;
	}


	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Author getAuthor() {
		return author;
	}
	public void setAuthor(Author author) {
		this.author = author;
	}

	@Override
	public String toString() {
		return "Blog [id=" + id + ", title=" + title + ", body=" + body + ", image=" + image + ", creationDate="
				+ creationDate + ", author=" + author + "]";
	}
	
	

}
