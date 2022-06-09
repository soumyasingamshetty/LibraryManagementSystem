package com.LibraryManagementSystem.LoginLibrary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.LibraryManagementSystem.LoginLibrary.entity.Library;

@Repository
public interface LibraryRepository extends JpaRepository<Library, Integer> {
	

}
