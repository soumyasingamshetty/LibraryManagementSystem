package com.LibraryManagementSystem.BookService.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.LibraryManagementSystem.BookService.entity.Book;
import com.LibraryManagementSystem.BookService.exceptions.BookDeletionException;
import com.LibraryManagementSystem.BookService.repository.BookRepository;

@Service
public class BookService {
	
	
	
	@Autowired
	private BookRepository bookRepository;
	
	public Book saveBook(Book book)
	{
		return bookRepository.save(book);
	}
	

	public Book findBookById(int id) throws  BookDeletionException{
		try
		{
			return bookRepository.findById(id).get();
		}
		catch(Exception exception)
		{
			throw new BookDeletionException();
		}
		
	}


	public List<Book> getPopularBooks(String category) {
		
		List<Book> allBooks = bookRepository.findAll();
		List<Book> matchedBooks = new ArrayList<>();		
	    if(category.equals("popular"))
	    {
	    	for(Book book: allBooks)
			{
			    if(book.getPopularity()>3 && book.getQuantity()>0)
			    {
			    	matchedBooks.add(book);
			    }
			}
	    }
	    if(category.equals("all"))
	    {
	    	for(Book book: allBooks)
			{
			    if(book.getQuantity()>0)
			    {
			    	matchedBooks.add(book);
			    }
			}
	    }
		return matchedBooks;
	}


	public List<Book> getInterestedBookList(List<Book> popularBookList, String interest) {
		List<Book> matchedBooks = new ArrayList<>();
		if(interest.equals("all"))
		{
			for(Book book: popularBookList)
			{
			    if(book.getQuantity()>0)
			    {
			    	matchedBooks.add(book);
			    }
			}
		}
		else
		{
			for(Book book : popularBookList)
			{
				if(book.getSubject().equals(interest))
				{
					matchedBooks.add(book);
				}	
			}
		}		
		return matchedBooks;
	}

	public boolean checkQuantity(int id)
	{
		List<Book> allBooks = bookRepository.findAll();
		for(Book book : allBooks)
		{
			if(book.getBookId() == id && book.getQuantity() > 0)
			{
				return true;
			}
		}
		
		return false;
	}


	public void reduceBookQuantity(int id) {
		List<Book> allBooks = bookRepository.findAll();
		for(Book book : allBooks)
		{
			if(book.getBookId() == id)
			{
				int quantity = book.getQuantity();
				book.setQuantity(quantity - 1);
				bookRepository.save(book);
			}
		}
	}


	public boolean addBookByOne(int bookId) {
		List<Book> allBooks = bookRepository.findAll();
		for(Book book : allBooks)
		{
			if(book.getBookId() == bookId)
			{
				book.setQuantity(book.getQuantity() + 1);
				bookRepository.save(book);
				return true;
			}
		}
		return false;
	}


	public List<String> getAllBookCategories() {
	//	List<String> category = new ArrayList<>();
		return bookRepository.getDistinctSubjects();
	// category;
	}


	public boolean setBookPopularity(int id) {
		List<Book> allBooks = bookRepository.findAll();
		for(Book book : allBooks)
		{
			if(book.getBookId() == id)
			{
				book.setPopularity(book.getPopularity() + 1);
				bookRepository.save(book);
				return true;
			}			
		}
		return false;
	}
	
public boolean findBookByISBN(String iSBN) {
		
		List<Book> allBooks = bookRepository.findAll();
		for(Book book : allBooks)
		{
			if(book.getISBN().equals(iSBN) )
			{
				return true;
			}
		}
		
		return false;
		
	}
	
	public void updateBook(int quantity, String iSBN)
	{
		List<Book> allBooks = bookRepository.findAll();
		for(Book book : allBooks)
		{
			if(book.getISBN().equals(iSBN) )
			{
				book.setQuantity(quantity);
				bookRepository.save(book);
			}
		}
	
	}
	
	

}
