package com.LibraryManagementSystem.LoginLibrary.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "BookVote_table")
public class Library {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int Vote_id;
	private int Book_id;
	private int User_id;
	public int getBook_id() {
		return Book_id;
	}
	public void setBook_id(int book_id) {
		Book_id = book_id;
	}
	public int getUser_id() {
		return User_id;
	}
	public void setUser_id(int user_id) {
		User_id = user_id;
	}

}
