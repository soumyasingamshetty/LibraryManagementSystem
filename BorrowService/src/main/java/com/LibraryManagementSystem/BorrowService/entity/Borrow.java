package com.LibraryManagementSystem.BorrowService.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Borrow_Table")
public class Borrow {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int BorrowId;
	private int BorrowerId;
	private String  Borrower;
	private int BookId;
	private String BorrowStatus;
	private String transactionID;
	
	public String getBorrowStatus() {
		return BorrowStatus;
	}
	public void setBorrowStatus(String borrowStatus) {
		BorrowStatus = borrowStatus;
	}
	public int getBorrowId() {
		return BorrowId;
	}
	public void setBorrowId(int borrowId) {
		BorrowId = borrowId;
	}
	public String getBorrower() {
		return Borrower;
	}
	public void setBorrower(String borrower) {
		Borrower = borrower;
	}
	public int getBookId() {
		return BookId;
	}
	public void setBookId(int bookId) {
		BookId = bookId;
	}
	public String getTransactionID() {
		return transactionID;
	}
	public void setTransactionID(String transactionID) {
		this.transactionID = transactionID;
	}
	public int getBorrowerId() {
		return BorrowerId;
	}
	public void setBorrowerId(int borrowerId) {
		BorrowerId = borrowerId;
	}
	
}
