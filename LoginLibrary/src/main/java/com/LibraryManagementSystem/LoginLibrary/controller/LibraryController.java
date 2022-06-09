package com.LibraryManagementSystem.LoginLibrary.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.LibraryManagementSystem.LoginLibrary.service.LibraryService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;

@Controller
@RequestMapping("/loginService")
public class LibraryController {
	
	//creating a constant for circuitbreaker
	public static final String FAIL_SERVICE = "failService";
	
	
	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	private LibraryService libraryService;
	
	private String user;
	
	@GetMapping("/")
	public String checkLoginService()
	{
		return "loginpage";
	}
	
	@CircuitBreaker(name = FAIL_SERVICE, fallbackMethod = "serviceReachFailed")
	@PostMapping("/loginpage")
	public String userlogin(@RequestParam("userName") String username,@RequestParam("password") String pswd,
			Model model, HttpSession httpSession)
	{
		if(username.equals(null) || pswd.equals(null))
		{
			model.addAttribute("loginPageError","Username and password are incorrect");
			return "loginpage";
		}
		
		else
		{
		int flagStatus = restTemplate.getForObject("http://USER-SERVICE/userService/checkUser/"+username+"/"+pswd, Integer.class);
		
		if(flagStatus == 0)
		{
			httpSession.setAttribute("username", username);
			model.addAttribute("currentuser", httpSession.getAttribute("username").toString());
			ResponseEntity<Object[]> responseList = restTemplate.getForEntity("http://BOOK-SERVICE/bookService/getAllCategories/", Object[].class);
			Object[] listOfSubjects = responseList.getBody();
			model.addAttribute("listofsubjects", listOfSubjects);
			return "userpage";
		}
		
		else if(flagStatus == 1)
		{
			httpSession.setAttribute("username", username);
			model.addAttribute("currentuser",  httpSession.getAttribute("username").toString() );
			return "adminlogin";
		}
		else
		{
			model.addAttribute("loginerror", "User Doesnot Exists");
		}
		return "loginpage";
		}
		
		
	}
	
	
	//implementing fallback method	
	
	public String serviceReachFailed(Exception serviceException)
	{
			System.out.println("-----------------"+ serviceException.getMessage());
			//return displayErrorMessage(serviceException);
			return "displayerrorpage";
	}
	public ModelAndView serviceReachFailedForReturnModel(Exception serviceException)
	{
			System.out.println("-----------------"+ serviceException.getMessage());
			ModelAndView model = new ModelAndView("displayerrorpage");
			model.addObject("exceptionmessage",serviceException.getMessage());
			model.addObject("exception",serviceException);
			
			
			//return displayErrorMessage(serviceException);
			return model;
	}
	
//	public String displayErrorMessage(Exception addBookException)
//	{
//		return displayExceptionAndErrors(addBookException);
//	}
//	
//	@RequestMapping("/errordisplaymessage/{exception}")
//	public String displayExceptionAndErrors(@PathVariable("exception") Exception exception)
//	{
//		ModelAndView model = new ModelAndView("errorpage");
//		model.addObject("exception",exception);
//		return "errorpage";
//	}
	
	
	@RequestMapping("/addbook")
	@CircuitBreaker(name = FAIL_SERVICE, fallbackMethod = "serviceReachFailed")
	public String addBookToDB(@RequestParam("bookName") String bookname,
			@RequestParam("isbn") String isbn,
			@RequestParam("author") String author,
			@RequestParam("quantity") int quantity,
			@RequestParam("subject") String subject,
			Model model)
	{
		
		if(subject.isEmpty())
		{
			subject = "Any Category";
		}
	
		if(quantity != 0)
		{
			String responseString = restTemplate.getForObject("http://BOOK-SERVICE/bookService/addingBookToDB/"+bookname+"/"+isbn+"/"+author+"/"+quantity+"/"+subject, String.class);
			
			if(responseString.equals("true"))
			{
				model.addAttribute("addedbook","Book Successfully added");
			}
			else
			{
				model.addAttribute("addbookissue","Book already exists");
			}
			
		}
		else
		{
			model.addAttribute("addbookissue","Book Quantity is Zero");
		}
		
		return "adminlogin";
		
	}
	
	@RequestMapping("/listbooks")
	@CircuitBreaker(name = FAIL_SERVICE, fallbackMethod = "serviceReachFailedForReturnModel")
	public ModelAndView listOfBooks()
	{
		ModelAndView model = new ModelAndView("viewbooks");
		ResponseEntity<Object[]> response = restTemplate.getForEntity("http://BOOK-SERVICE/bookService/booklist",Object[].class);
		Object[] listOfBooks = response.getBody();
		if(listOfBooks.length <= 0)
		{
			model.addObject("bookerror","Currently there are no books");
		}
		else
		{
			model.addObject("books", listOfBooks);
		}
		
		return model;
	}
	
	@GetMapping(value="/deletebook/{id}")
	@CircuitBreaker(name = FAIL_SERVICE, fallbackMethod = "serviceReachFailedForReturnModel")
	public ModelAndView deleteBookById(@PathVariable("id") int id)
	{
		ModelAndView modelAndView = new ModelAndView("viewbooks");
		boolean statusFlag = restTemplate.getForObject("http://BORROW-SERVICE/borrowService/checkBorrowStatus/"+id, Boolean.class);
		if(!statusFlag)
		{
			System.out.println("------------------"+statusFlag);
		String responseString = restTemplate.getForObject("http://BOOK-SERVICE/bookService/deletefromdb/"+id, String.class);
		modelAndView = listOfBooks();
		modelAndView.addObject("deleted", responseString);
		}
		else
		{
			System.out.println("------------------"+statusFlag);
			modelAndView = listOfBooks();
		    modelAndView.addObject("errormessage", "Cannot Delete. There is a user who currently borrowed this book");
		}		
		return modelAndView;
	}
	
	@RequestMapping("/displayregistrationform")
	@CircuitBreaker(name = FAIL_SERVICE, fallbackMethod = "serviceReachFailed")
	public String checkRegisterService()
	{
		return "registeruser";
	}
	
	@RequestMapping("/registeruser")
	@CircuitBreaker(name = FAIL_SERVICE, fallbackMethod = "serviceReachFailed")
	public String registerUser(@RequestParam("firstname") String firstname,
			@RequestParam("lastname") String lastname,
			@RequestParam("email") String email,
			@RequestParam("gender") String gender,
			@RequestParam("password") String passwd,
			@RequestParam("confirmpassword") String confpsswd,
			@RequestParam("usertype") String usertype,
			@RequestParam("adminid") String adminid, Model model)
	{
		int userTypeFlag = 0;
		String username = firstname+lastname;
		String flag = "false";
		if(passwd.equals(confpsswd))
		{
			if(usertype.equals("admin"))
			{
				if(adminid.isEmpty())
				{ 
					model.addAttribute("adminError", "Admin ID required");	
					return "registeruser";
				}
				else if(!adminid.equals("admin123"))
				{
					model.addAttribute("adminError", "Admin ID incorrect");	
					return "registeruser";
				}
				else
				{
					userTypeFlag = 1;
					flag = restTemplate.getForObject("http://USER-SERVICE/userService/addToUser/"+firstname+
							"/"+lastname+"/"+email+"/"+gender+"/"+passwd+"/"+usertype+"/"+userTypeFlag,String.class);	
				}				
			}
			else
			{
				if(usertype.equals(""))
				{
					model.addAttribute("userTypeError", "Please choose your user type");
					return "registeruser";
				}
				else
				{
					userTypeFlag = 0;
					flag = restTemplate.getForObject("http://USER-SERVICE/userService/addToUser/"+firstname+
							"/"+lastname+"/"+email+"/"+gender+"/"+passwd+"/"+usertype+"/"+userTypeFlag,String.class);	
				}							
			}								
		}
		else
		{
			model.addAttribute("responseString", "Password doesnot match");
			return "registeruser";
		}
		 
		 if(flag.equals("true"))
		  {
			 model.addAttribute("successMessage", username +" Registration Successful. Please login");			 
		  }
		  else
		  {
			  model.addAttribute("statusFlagError", "User Already Exists");
		  }
				
		return "loginpage";		
	}
	

	@RequestMapping("/allBooks")
	@CircuitBreaker(name = FAIL_SERVICE, fallbackMethod = "serviceReachFailedForReturnModel")
	public ModelAndView allBookList(@RequestParam("popularity") String category, @RequestParam("interest") String interest, HttpSession httpSession)
	{
		ModelAndView model = new ModelAndView("booklist");
		httpSession.setAttribute("interest", interest);
		httpSession.setAttribute("popularity", category);
		ResponseEntity<Object[]> response = restTemplate.getForEntity("http://BOOK-SERVICE/bookService/availableBookList/"+category+"/"+interest,Object[].class);
		Object[] listOfBooks = response.getBody();
		if(listOfBooks.length <= 0)
		{
			model.addObject("bookerror","Currently there are no books");
		}
		else
		{
			model.addObject("userRequestedBookList", listOfBooks);
		}
		
		return model;
	}
	
	@GetMapping("/borrow/{id}")
	@CircuitBreaker(name = FAIL_SERVICE, fallbackMethod = "serviceReachFailedForReturnModel")
	public ModelAndView borrowBookService(@PathVariable("id") int id, HttpSession httpSession, Model model)
	{
		//System.out.println("-------------------------------------------------"+httpSession.getAttribute("username"));
		ModelAndView modelAndView = new ModelAndView("borrowedbooks");
		String currentUserSession = httpSession.getAttribute("username").toString();
		int currentUserId = restTemplate.getForObject("http://USER-SERVICE/userService/getUserId/"+currentUserSession, Integer.class);
		//System.out.println("------------------------"+currentUserId);
		httpSession.setAttribute("currentUserId", currentUserId);
		String response = restTemplate.getForObject("http://BOOK-SERVICE/bookService/borrowBook/"+id+"/"+currentUserSession+"/"+currentUserId, String.class);
		modelAndView = borrowedBooks(httpSession, response);
		modelAndView.addObject("borrowstatus", "Book Borrowed Successfully");
		return modelAndView;
	}
	
	@GetMapping("/borrowedBooks")
	@CircuitBreaker(name = FAIL_SERVICE, fallbackMethod = "serviceReachFailedForReturnModel")
	public ModelAndView borrowedBooks(HttpSession httpSession,String message)
	{
		ModelAndView modelAndView = new ModelAndView("borrowedbooks");
		String currentUserSession = httpSession.getAttribute("username").toString();
		
		ResponseEntity<Object[]> borrowedBooksByUser = restTemplate.getForEntity("http://BORROW-SERVICE/borrowService/borrowedBooksByUser/"+currentUserSession, Object[].class);
		Object[] listOfBooks = borrowedBooksByUser.getBody();
		modelAndView.addObject("borrowedbooks", listOfBooks);
	    return modelAndView;
	}

	@RequestMapping("/userBorrowedBooks")
	@CircuitBreaker(name = FAIL_SERVICE, fallbackMethod = "serviceReachFailedForReturnModel")
	public ModelAndView allUserBorrowedBooks()
	{
		ModelAndView modelAndView = new ModelAndView("allusersborrowedbooks");
		ResponseEntity<Object[]> borrowedBooksByAllUser = restTemplate.getForEntity("http://BORROW-SERVICE/borrowService/allBorrowedBooks", Object[].class);
		Object[] listOfBooks = borrowedBooksByAllUser.getBody();
		if(listOfBooks.length != 0)
		{
			modelAndView.addObject("borrowedbooks", listOfBooks);
		}
		else
		{
			modelAndView.addObject("noBorrowedBooksError", "Currently there are no borrowed books");
		}
		
		return modelAndView;
	}	
	
	@CircuitBreaker(name = FAIL_SERVICE, fallbackMethod = "serviceReachFailedForReturnModel")
	@GetMapping("/returnBook/{id}")
	public ModelAndView returnBorrowedBook(@PathVariable("id") int id, HttpSession httpSession)
	{
		ModelAndView modelAndView = new ModelAndView("allusersborrowedbooks");
		int bookId = restTemplate.getForObject("http://BORROW-SERVICE/borrowService/returnBook/"+id, Integer.class);
		boolean bookIncrementStatus = restTemplate.getForObject("http://BOOK-SERVICE/bookService/incrementBookQuantity/"+bookId, Boolean.class);
		modelAndView = allUserBorrowedBooks();
		
	    return modelAndView;
	}
	
	@CircuitBreaker(name = FAIL_SERVICE, fallbackMethod = "serviceReachFailedForReturnModel")
	@GetMapping("/returnUserBorrowedBook/{borrowId}/{bookId}")
	public ModelAndView returnUserBorrowedBook(@PathVariable("borrowId") int borrowId,  @PathVariable("bookId") int bookId, HttpSession httpSession)
	{
		ModelAndView modelAndView = new ModelAndView("borrowedbooks");
		boolean borrowStatus = restTemplate.getForObject("http://BORROW-SERVICE/borrowService/checkUserBookBorrowedStatus/"+borrowId, Boolean.class);
		if(borrowStatus)
		{
			    if(restTemplate.getForObject("http://BOOK-SERVICE/bookService/incrementBookQuantity/"+bookId, Boolean.class))
			    {	
			    	modelAndView = borrowedBooks(httpSession,"response");
					modelAndView.addObject("borrowstatus","Book Returned Successfully");
			    }				
		}
		else
		{
			modelAndView = borrowedBooks(httpSession,"response");
			modelAndView.addObject("borrowstatus","Book is already returned. Check if transaction id is different");
		}
		
		return modelAndView;
	}	
	
	@CircuitBreaker(name = FAIL_SERVICE, fallbackMethod = "serviceReachFailed")
	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		if(session.getAttribute("user") != null){
            session.removeAttribute("user");
		}
	    return "loginpage";
	} 
	
	@CircuitBreaker(name = FAIL_SERVICE, fallbackMethod = "serviceReachFailedForReturnModel")
	@GetMapping("/vote/{id}")
	public ModelAndView voteForBook(@PathVariable("id") int id, HttpSession httpSession)
	{
		ModelAndView modelAndView = new ModelAndView("booklist");
		String currentUser = (String) httpSession.getAttribute("username");
		String interest = (String) httpSession.getAttribute("interest");
		String category = (String) httpSession.getAttribute("popularity");
		int userId = restTemplate.getForObject("http://USER-SERVICE/userService/getUserId/"+currentUser, Integer.class);
		if(!libraryService.findVoteStatus(userId,id))
		{	
			libraryService.setVoteStatus(userId,id);
			if(restTemplate.getForObject("http://BOOK-SERVICE/bookService/setBookPopularity/"+id, Boolean.class))
			{
				modelAndView = allBookList(interest,category,httpSession);
				modelAndView.addObject("votestatus", "You have voted Successfully");
			}
		}
		else
		{
			modelAndView = allBookList(interest,category,httpSession);
			modelAndView.addObject("votestatus", "Voting allowed only once.You have already voted.");
		}
		
		return modelAndView;
	}
	
	@CircuitBreaker(name = FAIL_SERVICE, fallbackMethod = "serviceReachFailed")
	@GetMapping("/adminEditBook")
	public String editBookService()
	{
		return "editbook";
	}

	@CircuitBreaker(name = FAIL_SERVICE, fallbackMethod = "serviceReachFailed")
	@GetMapping("/editBook/{id}/{ISBN}/{quantity}")
	public String ediSpecificBookService()
	{
		return "editbook";
	}
	
	@CircuitBreaker(name = FAIL_SERVICE, fallbackMethod = "serviceReachFailedForReturnModel")
	@PostMapping("/updateBook")
	public ModelAndView updateBookService(@RequestParam("ISBN") String ISBN,@RequestParam("quantity") int quantity)
	{

			ModelAndView modelAndView = new ModelAndView("editbook");
			String responseString = restTemplate.getForObject("http://BOOK-SERVICE/bookService/checkBook/"+ISBN+"/"+quantity, String.class);
			modelAndView.addObject("Updated", responseString);
			return modelAndView;
	}
	
	@CircuitBreaker(name = FAIL_SERVICE, fallbackMethod = "serviceReachFailedForReturnModel")
	@GetMapping("/editBookbyisbn/{id}/{ISBN}/{quantity}")
	public ModelAndView editBookByISBN(@PathVariable("ISBN") String ISBN,@PathVariable("quantity") int quantity)
	{
		ModelAndView modelAndView = new ModelAndView("editbookbyisbn");
		modelAndView.addObject("bookisbn",ISBN);
		return modelAndView;
	}

}
