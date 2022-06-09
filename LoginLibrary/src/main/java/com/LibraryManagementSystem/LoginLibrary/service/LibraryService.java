package com.LibraryManagementSystem.LoginLibrary.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.LibraryManagementSystem.LoginLibrary.entity.Library;
import com.LibraryManagementSystem.LoginLibrary.repository.LibraryRepository;

@Service
public class LibraryService {
	
	@Autowired
	LibraryRepository libraryRepository;

	public boolean findVoteStatus(int userId, int id) {
		List<Library> allLibraryEntityData = libraryRepository.findAll();
		for(Library library : allLibraryEntityData)
		{
			if(library.getBook_id() == id && library.getUser_id() == userId)
			{
				return true;
			}
		}		
		return false;
	}

	public void setVoteStatus(int userId, int id) {
		Library library = new Library();
		library.setBook_id(id);
		library.setUser_id(userId);
		libraryRepository.save(library);
	}
	
	

}
