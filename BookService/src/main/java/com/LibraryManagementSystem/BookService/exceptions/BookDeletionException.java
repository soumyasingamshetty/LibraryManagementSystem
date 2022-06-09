package com.LibraryManagementSystem.BookService.exceptions;

public class BookDeletionException extends Exception {
	
	public String getMessage()
	{
		return "Exception while deleting book";
	}

}