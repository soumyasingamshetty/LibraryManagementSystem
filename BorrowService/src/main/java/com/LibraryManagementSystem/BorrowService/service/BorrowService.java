package com.LibraryManagementSystem.BorrowService.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.LibraryManagementSystem.BorrowService.entity.Borrow;
import com.LibraryManagementSystem.BorrowService.repository.BorrowRepository;

@Service
public class BorrowService {
	
	@Autowired
	private BorrowRepository borrowRepository;
	
	public Borrow saveBorrowTransaction(Borrow borrow)
	{
		borrow.setTransactionID(UUID.randomUUID().toString());
		return borrowRepository.save(borrow);
	}
	
	public List<Borrow> getAllBorrowedTransactions(String user)
	{
		List<Borrow> borrowedBooks = borrowRepository.findAll();
		List<Borrow> userBorrowedBooks = new ArrayList<>();
		for(Borrow borrowedBook : borrowedBooks)
		{
			if(borrowedBook.getBorrower().equals(user))
			{
				userBorrowedBooks.add(borrowedBook);
			}
		}
		return userBorrowedBooks;
	}

	public boolean checkIfUserBorrowedBook(String user, int id) {
		List<Borrow> borrowedBooks = borrowRepository.findAll();
		//List<Borrow> userBorrowedBooks = new ArrayList<>();
		for(Borrow borrowedBook : borrowedBooks)
		{
			if(borrowedBook.getBorrower().equals(user) && borrowedBook.getBookId() == id && borrowedBook.getBorrowStatus().equalsIgnoreCase("borrowed"))
			{
				return false;
			}
		}			
		return true;
	}

	public List<Borrow> getAllBorrowedBooksByUser() {
		List<Borrow> allBorrowedBooks = borrowRepository.findAll();
		List<Borrow> currentBorrowedBooks = new ArrayList<>();
		for(Borrow borrow : allBorrowedBooks)
		{
			if(borrow.getBorrowStatus().equals("borrowed"))
			{
				currentBorrowedBooks.add(borrow);
			}		
		}
		return currentBorrowedBooks;
		//return allBorrowedBooks;
	}
	
	public int getBookId(int borrowedId)
	{
		List<Borrow> allBorrowedBooks = borrowRepository.findAll();
		for(Borrow borrow : allBorrowedBooks)
		{
			if(borrow.getBorrowId() == borrowedId)
			{
				return borrow.getBookId();
			}
		}
		return -1;
	}

	public boolean changeBorrowStatus(int borrowedId) {
		List<Borrow> allBorrowedBooks = borrowRepository.findAll();
		for(Borrow borrow : allBorrowedBooks)
		{
			if(borrow.getBorrowId() == borrowedId)
			{
				borrow.setBorrowStatus("returned");
				borrowRepository.save(borrow);
				return true;
			}
		}
		return false;
	}

	public boolean checkBookBorrowedByBookId(int id) {
		List<Borrow> allBorrowedBooks = borrowRepository.findAll();
		for(Borrow borrow : allBorrowedBooks)
		{
			if(borrow.getBookId() == id && borrow.getBorrowStatus().equals("borrowed"))
			{
				return true;
			}
		}
		return false;
	}

	public boolean checkUserBookBorrowedStatus(int borrowId) {
		List<Borrow> allBorrowedBooks = borrowRepository.findAll();
		for(Borrow borrow : allBorrowedBooks)
		{
			if(borrow.getBorrowId() == borrowId && borrow.getBorrowStatus().equals("borrowed"))
			{
				return true;
			}
		}
		return false;
	}

}
