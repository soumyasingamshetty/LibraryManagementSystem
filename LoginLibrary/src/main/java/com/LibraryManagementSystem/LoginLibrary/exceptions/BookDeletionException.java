package com.LibraryManagementSystem.LoginLibrary.exceptions;

public class BookDeletionException extends Exception {
	
	public String getMessage()
	{
		return "Exception while deleting book";
	}

}
