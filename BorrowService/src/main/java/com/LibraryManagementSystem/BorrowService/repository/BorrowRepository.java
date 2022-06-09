package com.LibraryManagementSystem.BorrowService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.LibraryManagementSystem.BorrowService.entity.Borrow;

@Repository
public interface BorrowRepository extends JpaRepository<Borrow, Integer> {

	
}
