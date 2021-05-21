package com.projects.SalesSystem.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.projects.SalesSystem.entities.Expense;
import com.projects.SalesSystem.repositories.ExpenseRepository;
import com.projects.SalesSystem.services.exceptions.DataIntegrity;
import com.projects.SalesSystem.services.exceptions.ObjectNotFound;

@Service
public class ExpenseService {

	@Autowired
	private ExpenseRepository expenseRepo;
	
	public void insert(Expense obj) {
		expenseRepo.save(obj);
	}
	
	public void delete(Long id) {
		findExpenseById(id);
		try {
			expenseRepo.deleteById(id);
		}
		catch(DataIntegrityViolationException e) {
			throw new DataIntegrity("Não é possível deletar pois essa despesa tem elementos associados");
		}
	}
	
	private Expense findExpenseById(Long id) {
		Optional<Expense> obj = expenseRepo.findById(id);
		return obj.orElseThrow( () -> 
					new ObjectNotFound("Objeto não encontrado! Id: " + id));
	}
}
