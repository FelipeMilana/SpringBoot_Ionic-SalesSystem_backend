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
import com.projects.SalesSystem.security.UserSS;
import com.projects.SalesSystem.services.exceptions.AuthorizationException;
import com.projects.SalesSystem.services.exceptions.DataIntegrity;
import com.projects.SalesSystem.services.exceptions.ObjectNotFound;

@Service
public class WithdrawService {

	@Autowired
	private WithdrawRepository withdrawRepo;
	@Autowired
	private UserService userService;

	public Page<WithdrawDTO> myWithdraws(Pageable page) {

		UserSS authUser = userService.getAuthenticatedUser();

		if (authUser == null) {
			throw new AuthorizationException("Acesso Negado");
		}

		User user = userService.findUserByEmail(authUser.getUsername());

		List<Long> withdrawIds = user.getWithdraws().stream().map(x -> x.getId()).collect(Collectors.toList());
		
		return withdrawRepo.findByIdIn(withdrawIds, page).map(x -> new WithdrawDTO(x));
		
	}

	public Page<WithdrawDTO> findByNameOrBank(Pageable page, String str) {
		
		UserSS authUser = userService.getAuthenticatedUser();
		
		if (authUser == null) {
			throw new AuthorizationException("Acesso Negado");
		}

		User user = userService.findUserByEmail(authUser.getUsername());

		List<Long> withdrawIds = user.getWithdraws().stream().map(x -> x.getId()).collect(Collectors.toList());
		
		Page<Withdraw> withdraws;
		
		if(str.equalsIgnoreCase("Santander") || str.equalsIgnoreCase("Nubank")) {
			
			Integer number = null;
			
			if(str.equalsIgnoreCase("Santander")) {
				number = 1;
			}
			else {
				number = 2;
			}
			
			withdraws = withdrawRepo.findByBankAndIdIn(number, withdrawIds, page);
		}
		else {
			withdraws = withdrawRepo.findByNameContainingIgnoreCaseAndIdIn(str, withdrawIds, page);
		}
		
		return withdraws.map(x -> new WithdrawDTO(x));
	}
	
	public void insert(Withdraw obj) {
		withdrawRepo.save(obj);
	}

	@Transactional
	public WithdrawDTO insertFromDTO(WithdrawDTO obj) {

		UserSS authUser = userService.getAuthenticatedUser();

		if (authUser == null) {
			throw new AuthorizationException("Acesso Negado");
		}

		User user = userService.findUserByEmail(authUser.getUsername());

		Withdraw withdraw = new Withdraw(obj.getId(), obj.getName(), Bank.toIntegerEnum(obj.getBank()), obj.getValue(),
				LocalDate.now(), user);

		user.getWithdraws().add(withdraw);

		if (withdraw.getBank().getDescription() == "Nubank") {
			user.setNubankBalance(-withdraw.getValue());
		} else {
			user.setSantanderBalance(-withdraw.getValue());
		}

		insert(withdraw);
		userService.insert(user);
		
		return new WithdrawDTO(withdraw);
	}

	@Transactional
	public void delete(Long id) {
		UserSS authUser = userService.getAuthenticatedUser();

		if (authUser == null) {
			throw new AuthorizationException("Acesso Negado");
		}

		User user = userService.findUserByEmail(authUser.getUsername());

		try {
			Withdraw withdraw = findWithdrawById(id);

			if (withdraw.getBank().getDescription() == "Nubank") {
				user.setNubankBalance(withdraw.getValue());
			} else {
				user.setSantanderBalance(withdraw.getValue());
			}
			
			userService.insert(user);
			withdrawRepo.deleteById(id);
		} 
		catch (DataIntegrityViolationException e) {
			throw new DataIntegrity("Não foi possível deletar");
		}
	}

	private Withdraw findWithdrawById(Long id) {
		Optional<Withdraw> obj = withdrawRepo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFound("Objeto não encontrado! Id: " + id));
	}
}
