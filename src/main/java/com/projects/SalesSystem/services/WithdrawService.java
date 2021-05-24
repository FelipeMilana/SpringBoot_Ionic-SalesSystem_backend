package com.projects.SalesSystem.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.projects.SalesSystem.entities.User;
import com.projects.SalesSystem.entities.Withdraw;
import com.projects.SalesSystem.entities.dto.WithdrawDTO;
import com.projects.SalesSystem.entities.enums.Bank;
import com.projects.SalesSystem.repositories.WithdrawRepository;
import com.projects.SalesSystem.services.exceptions.DataIntegrity;
import com.projects.SalesSystem.services.exceptions.ObjectNotFound;

@Service
public class WithdrawService {

	@Autowired
	private WithdrawRepository withdrawRepo;

	public Page<WithdrawDTO> myWithdraws(Pageable page) {
		// Will be authenticated user, this user is a mockdata
		User user = new User(null, "Felipe", "fe@gmail.com", "123", 50000.00, 60000.00);
		
		List<Long> withdrawIds = user.getWithdraws().stream().map(x -> x.getId()).collect(Collectors.toList());

		return withdrawRepo.findByIdIn(withdrawIds, page).map(x -> new WithdrawDTO(x));
	}

	public void insert(Withdraw obj) {
		withdrawRepo.save(obj);
	}

	@Transactional
	public void insertFromDTO(WithdrawDTO obj) {
		// Will be authenticated user, this user is a mockdata
		User user = new User(null, "Felipe", "fe@gmail.com", "123", 50000.00, 60000.00);

		Withdraw withdraw = new Withdraw(obj.getId(), Bank.toIntegerEnum(obj.getBank()), obj.getValue(),
				LocalDate.now(), user);

		user.getWithdraws().add(withdraw);

		if (withdraw.getBank().getDescription() == "Nubank") {
			user.setNubankBalance(-withdraw.getValue());
		} else {
			user.setSantanderBalance(-withdraw.getValue());
		}

		// userService.insert(user);
		insert(withdraw);
	}

	public void delete(Long id) {
		// Will be authenticated user, this user is a mockdata
		User user = new User(null, "Felipe", "fe@gmail.com", "123", 50000.00, 60000.00);
		
		Withdraw withdraw = findWithdrawById(id);
		
		Bank bank = withdraw.getBank();
		double value = withdraw.getValue();
		
		try {
			withdrawRepo.deleteById(id);
			
			if(bank.getDescription() == "Nubank") {
				user.setNubankBalance(value);
			}
			else {
				user.setSantanderBalance(value);
			}
			
			//userService.insert(user)
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrity("Não é possível deletar pois esse saque tem elementos associados");
		}
	}

	private Withdraw findWithdrawById(Long id) {
		Optional<Withdraw> obj = withdrawRepo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFound("Objeto não encontrado! Id: " + id));
	}
}
