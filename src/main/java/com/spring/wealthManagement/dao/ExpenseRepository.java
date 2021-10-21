package com.spring.wealthManagement.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.wealthManagement.models.Expenses;

@Repository
public interface ExpenseRepository extends JpaRepository<Expenses, Long> {
	
	public Optional<Expenses> findById(Long id);

}






