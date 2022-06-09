package com.LibraryManagementSystem.BorrowService.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.LibraryManagementSystem.BorrowService.entity.Borrow;
import com.LibraryManagementSystem.BorrowService.service.BorrowService;


@RestController
@RequestMapping("/borrowService")
public class BorrowController {
	
	@Autowired
	private BorrowService borrowService;
	
	
	@GetMapping("/borrowBook/{id}/{currentUserSession}/{currentUserId}")
	public boolean bookBorrowed(@PathVariable("id") int id, @PathVariable("currentUserSession") String currentUserSession,
			@PathVariable("currentUserId") int currentUserId)
	{
		if(borrowService.checkIfUserBorrowedBook(currentUserSession,id))
		{
			Borrow borrow = new Borrow();
			borrow.setBookId(id);
			borrow.setBorrower(currentUserSession);
			borrow.setBorrowStatus("borrowed");
			borrow.setBorrowerId(currentUserId);
			borrowService.saveBorrowTransaction(borrow);
			return true;
		}
		return false;
	}
	
	@GetMapping("/borrowedBooksByUser/{currentUserSession}")
	public List<Borrow> bookBorrowed(@PathVariable("currentUserSession") String currentUserSession)
	{
		return borrowService.getAllBorrowedTransactions(currentUserSession);
	}
	
	@GetMapping("/allBorrowedBooks")
	public List<Borrow> allBorrowedBooks()
	{
		return borrowService.getAllBorrowedBooksByUser();
	}
	
	@GetMapping("/returnBook/{id}")
	public int getReturnedBookId(@PathVariable("id") int borrowedId)
	{
		if(borrowService.changeBorrowStatus(borrowedId))
		{
			return borrowService.getBookId(borrowedId);		
		}
		return -1;
	}
	
	@GetMapping("/checkBorrowStatus/{id}")
	public boolean checkBookBorrowStatus(@PathVariable("id") int id)
	{
		if(borrowService.checkBookBorrowedByBookId(id))
		{
			return true;
		}
		return false;
	}
	
	@GetMapping("/checkUserBookBorrowedStatus/{borrowId}")
	public boolean checkUserBookBorrowedStatus(@PathVariable("borrowId") int borrowId)
	{
		if(borrowService.checkUserBookBorrowedStatus(borrowId) && borrowService.changeBorrowStatus(borrowId))
		{
			return true;
		}
		
		return false;
	}
}
