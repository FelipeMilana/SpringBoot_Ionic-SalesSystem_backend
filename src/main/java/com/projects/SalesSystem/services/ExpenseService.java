package com.projects.SalesSystem.services;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.projects.SalesSystem.entities.Expense;
import com.projects.SalesSystem.entities.User;
import com.projects.SalesSystem.entities.Vehicle;
import com.projects.SalesSystem.entities.dto.ExpenseDTO;
import com.projects.SalesSystem.entities.enums.Bank;
import com.projects.SalesSystem.repositories.ExpenseRepository;
import com.projects.SalesSystem.security.UserSS;
import com.projects.SalesSystem.services.exceptions.AuthorizationException;
import com.projects.SalesSystem.services.exceptions.DataIntegrity;
import com.projects.SalesSystem.services.exceptions.ObjectNotFound;

@Service
public class ExpenseService {

	@Autowired
	private ExpenseRepository expenseRepo;
	@Autowired
	private VehicleService vehicleService;
	@Autowired
	private UserService userService;
	
	public void insert(Expense obj) {
		expenseRepo.save(obj);
	}
	
	@Transactional
	public ExpenseDTO insertExpenses(Long id, ExpenseDTO obj) {
		UserSS authUser = userService.getAuthenticatedUser();

		if (authUser == null) {
			throw new AuthorizationException("Acesso Negado");
		}

		User user = userService.findUserByEmail(authUser.getUsername());
		
		Vehicle vehicle = vehicleService.findVehicleById(id);

		Expense exp = new Expense(obj.getId(), obj.getName(), Bank.toIntegerEnum(obj.getBank()), obj.getValue(),
				LocalDate.now(), vehicle);

		vehicle.getExpenses().add(exp);

		
		if (exp.getBank().getDescription() == "Nubank") {
			user.setNubankBalance(-exp.getValue());
		} else {
			user.setSantanderBalance(-exp.getValue());
		}

		insert(exp);
		vehicleService.insert(vehicle);
		userService.insert(user);
		
		return new ExpenseDTO(exp);
	}
	
	@Transactional
	public void delete(Long id) {
		UserSS authUser = userService.getAuthenticatedUser();

		if (authUser == null) {
			throw new AuthorizationException("Acesso Negado");
		}

		User user = userService.findUserByEmail(authUser.getUsername());
		
		try {
			Expense exp = findExpenseById(id);
			
			if(exp.getBank().getDescription() == "Nubank") {
				user.setNubankBalance(exp.getValue());
			}
			else {
				user.setSantanderBalance(exp.getValue());
			}
			
			userService.insert(user);
			expenseRepo.deleteById(id);
		}
		catch(DataIntegrityViolationException e) {
			throw new DataIntegrity("Não é possível deletar essa despesa");
		}
	}
	
	private Expense findExpenseById(Long id) {
		Optional<Expense> obj = expenseRepo.findById(id);
		return obj.orElseThrow( () -> 
					new ObjectNotFound("Objeto não encontrado! Id: " + id));
	}
}
