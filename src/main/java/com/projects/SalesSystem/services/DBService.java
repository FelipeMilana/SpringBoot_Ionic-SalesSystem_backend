package com.projects.SalesSystem.services;

import java.time.LocalDate;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projects.SalesSystem.entities.Address;
import com.projects.SalesSystem.entities.Expense;
import com.projects.SalesSystem.entities.Person;
import com.projects.SalesSystem.entities.Sale;
import com.projects.SalesSystem.entities.User;
import com.projects.SalesSystem.entities.Vehicle;
import com.projects.SalesSystem.entities.Withdraw;
import com.projects.SalesSystem.entities.enums.Bank;
import com.projects.SalesSystem.entities.enums.VehicleType;
import com.projects.SalesSystem.repositories.ExpenseRepository;
import com.projects.SalesSystem.repositories.PersonRepository;
import com.projects.SalesSystem.repositories.SaleRepository;
import com.projects.SalesSystem.repositories.UserRepository;
import com.projects.SalesSystem.repositories.VehicleRepository;
import com.projects.SalesSystem.repositories.WithdrawRepository;

@Service
public class DBService {

	@Autowired
	private PersonRepository personRepo;
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private ExpenseRepository expenseRepo;
	@Autowired
	private VehicleRepository vehicleRepo;
	@Autowired
	private SaleRepository saleRepo;
	@Autowired
	private WithdrawRepository withdrawRepo;

	public void instantiateDatabase() {
		
		Person felipe = new Person(null, "Felipe", "Felipe@gmail.com", "456123698", "33126456");

		Address ad1 = new Address(null, "Av. São Carlos", "789", "Centro", "13565789", "São Carlos", "São Paulo", felipe);
		felipe.setAddress(ad1);
		
		personRepo.save(felipe);
		
		User neto = new User(null, "Neto", "neto@gmail.com", "123", 200000.00, 500000.00);
		userRepo.save(neto);
		
		
		Vehicle v1 = new Vehicle(null, VehicleType.CAR, "VW", "Gol", "2008", LocalDate.now(), "UVY8998", "IPVA pago", 
				45000.00, Bank.NUBANK, 56000.00, neto, felipe);
		
		neto.setNubankBalance(-v1.getPaidValue());
		
		felipe.getSales().add(v1);
		neto.getStock().add(v1);
				
		vehicleRepo.save(v1);
		personRepo.save(felipe);
		userRepo.save(neto);
		
		Expense exp = new Expense(null, "Gasolina", 50.00, LocalDate.now(), v1);
		Expense exp2 = new Expense(null, "Manutenção", 950.00, LocalDate.now(), v1);
		v1.getExpenses().addAll(Arrays.asList(exp, exp2));
		
		neto.setNubankBalance(-exp.getValue());
		neto.setNubankBalance(-exp2.getValue());
		
		expenseRepo.saveAll(Arrays.asList(exp, exp2));
		vehicleRepo.save(v1);
		userRepo.save(neto);
		
		Person fernanda = new Person(null, "Fernanda", "fernanda@gmail.com", "45723698", "33896456");

		Address ad2 = new Address(null, "Av. Brasil", "124", "Centro", "1389789", "São Carlos", "São Paulo", fernanda);
		fernanda.setAddress(ad2);
		
		personRepo.save(fernanda);
		
		Sale sale = new Sale(null, LocalDate.now(), 53000.00, v1, fernanda, neto);
		neto.getSales().add(sale);
		neto.setNubankBalance(sale.getFinalValue());
		fernanda.getPurchases().add(sale);
		
		
		saleRepo.save(sale);
		userRepo.save(neto);
		personRepo.save(fernanda);
		
		Withdraw maio = new Withdraw(null, Bank.NUBANK, 7000.00, LocalDate.now(), neto);
		neto.setNubankBalance(-maio.getValue());
		
		withdrawRepo.save(maio);
		userRepo.save(neto);
	}
}
