package com.projects.SalesSystem.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.projects.SalesSystem.entities.User;
import com.projects.SalesSystem.entities.dto.BalanceDTO;
import com.projects.SalesSystem.entities.dto.UserDTO;
import com.projects.SalesSystem.repositories.UserRepository;
import com.projects.SalesSystem.security.UserSS;
import com.projects.SalesSystem.services.exceptions.ObjectNotFound;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepo;
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	public User findUserById(Long id) {
		Optional<User> obj = userRepo.findById(id);
		return obj.orElseThrow( () ->
				new ObjectNotFound("Objeto não encontrado! Id: " + id));
	}
	
	public User findUserByEmail(String email) {
		User obj = userRepo.findByEmail(email);
		
		if(obj == null) {
			throw new ObjectNotFound("Objeto não encontrado! Email: " + email);
		}
		
		return obj;
	}
	
	public UserSS getAuthenticatedUser() {
		try {
			return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}
		catch(Exception e) {
			return null;
		}
	}
	
	public UserDTO findByEmail(String email) {
		User user = findUserByEmail(email);
		return new UserDTO(user);
	}
	
	public void insert(User obj) {
		userRepo.save(obj);
	}
	
	public UserDTO insertFromDTO(UserDTO objDTO) {
		User user = new User(objDTO.getId(), objDTO.getName(), objDTO.getEmail(), 
				encoder.encode(objDTO.getPassword()), objDTO.getSantanderBalance(), objDTO.getNubankBalance());
		
		insert(user);
		return new UserDTO(user);
	}
	
	public void update(Long id, UserDTO objDTO) {
		User dbUser = findUserById(id);
		dbUser = updating(dbUser, objDTO);
		insert(dbUser);
	}
	
	public void updateBalance(BalanceDTO objDTO, Long id, String bank) {
		User dbUser = findUserById(id);
		dbUser = updatingBalance(dbUser, objDTO, bank);
		insert(dbUser);
	}
	
	private User updating(User obj, UserDTO objDTO) {
		obj.setName(objDTO.getName());
		
		if(!obj.getPassword().equals(objDTO.getPassword())) {
			obj.setPassword(encoder.encode(objDTO.getPassword()));
		}
		
		return obj;
	}
	
	private User updatingBalance(User obj, BalanceDTO objDTO, String bank)  {
		
		if(bank.equalsIgnoreCase("Nubank")) {
			obj.setNubankBalance(objDTO.getValue());
		}
		else {
			obj.setSantanderBalance(objDTO.getValue());
		}
		
		return obj;
	}
}
