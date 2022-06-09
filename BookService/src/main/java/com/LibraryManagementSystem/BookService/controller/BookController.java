package com.LibraryManagementSystem.BookService.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.LibraryManagementSystem.BookService.entity.Book;
import com.LibraryManagementSystem.BookService.exceptions.BookDeletionException;
import com.LibraryManagementSystem.BookService.repository.BookRepository;
import com.LibraryManagementSystem.BookService.service.BookService;

@RestController
@RequestMapping("/bookService")
public class BookController {
	
	@Autowired
	RestTemplate restTemplate;
	
	//RestTemplate restTemplate = new RestTemplate();
	
	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	private BookService bookService;
	
	
	@GetMapping("/addingBookToDB/{bookname}/{isbn}/{author}/{quantity}/{subject}")
	public String savetoBook(@PathVariable("bookname") String bookname,
			@PathVariable("isbn") String isbn, @PathVariable("author") String author,
			@PathVariable("quantity") int quantity, @PathVariable("subject") String subject)
	{
		
		Book book = new Book();
		if(!findBookByISBN(isbn).isEmpty())
		{
			return "false";
		}
		else {
			
			book.setAuthor(author);
			book.setBookName(bookname);
			book.setISBN(isbn);
			book.setQuantity(quantity);
			book.setSubject(subject);
			
			bookRepository.save(book);
			return "true";
		}		
	}
	
	@GetMapping("/booklist")
	public List<Book> findAllBooks() {		
		return bookRepository.findAll();		
	}
	
	
    public List<Book> findBookByISBN(String isbn) {
		
	
		List<Book> bookswithisbn = new ArrayList<Book>();
		List<Book> allrecords = bookRepository.findAll();
		for(Book item : allrecords)
		{ 
			if(item.getISBN().equalsIgnoreCase(isbn))
			{
				bookswithisbn.add(item);
			}
		}
		return bookswithisbn;
	}
    
    @GetMapping("/deletefromdb/{id}")
    public String deleteBookFromDB(@PathVariable("id") int id)
    {
    	try
    	{
    		Book book = new Book();
            book = bookService.findBookById(id);
        	bookRepository.delete(book);
        	return "Book Successfully deleted!!";
    	}
    	catch(BookDeletionException deletionexception)
    	{
    		return deletionexception.getMessage();
    	}
    	catch(Exception e)
    	{
    		return "Exception in Book Controller";
    	}	
    }
    
    @GetMapping("/availableBookList/{popularity}/{interest}")
    public List<Book> getAvailableBooks(@PathVariable("popularity") String category,
    		@PathVariable("interest") String interest)
    {
    	List<Book> popularBookList = bookService.getPopularBooks(category);
    	List<Book> interestBookList = bookService.getInterestedBookList(popularBookList,interest);
    	return interestBookList;
    }
    
    @GetMapping("/borrowBook/{id}/{currentUserSession}/{currentUserId}")
    public String borrowBook(@PathVariable("id") int id, @PathVariable("currentUserSession") String currentUserSession,
    		@PathVariable("currentUserId") String currentUserId)
    {
    	if(bookService.checkQuantity(id))
    	{
    		boolean response = restTemplate.getForObject("http://BORROW-SERVICE/borrowService/borrowBook/"+id+"/"+currentUserSession+"/"+currentUserId, Boolean.class);
    		if(response)
    		{
    			bookService.reduceBookQuantity(id);
    			return "Book borrowed Successfully";
    		}  		
    	}
    	return "Book Already Borrowed";
    }
    
    @GetMapping("/incrementBookQuantity/{bookId}")
    public boolean addBookQuantityByOne(@PathVariable("bookId") int bookId)
    {
    	if(bookService.addBookByOne(bookId))
    	{
    		return true;
    	}
    	return false;
    }
    
    @GetMapping("/getAllCategories")
    public List<String> getAllBookCategories()
    {
    	return bookService.getAllBookCategories();
    }
    
    @GetMapping("/setBookPopularity/{id}")
    public boolean setBookPopularity(@PathVariable("id") int id)
    {
    	return bookService.setBookPopularity(id);
    }
    
    @GetMapping("/checkBook/{ISBN}/{quantity}")
    public String checkBookExists(@PathVariable("ISBN") String ISBN,@PathVariable("quantity") int quantity )
    {
    	try {
    	
    	if(bookService.findBookByISBN(ISBN))
    	{
    		bookService.updateBook(quantity, ISBN);
    	}
    	}catch(Exception e)
    	{
    		return "Book does not exists";
    	}
    	return "Book Updated!!!";
    	
    }
}
