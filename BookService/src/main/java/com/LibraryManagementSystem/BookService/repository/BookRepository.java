package com.LibraryManagementSystem.BookService.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.LibraryManagementSystem.BookService.entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

	@Query("SELECT DISTINCT b.subject FROM Book b")
	List<String> getDistinctSubjects();

}
