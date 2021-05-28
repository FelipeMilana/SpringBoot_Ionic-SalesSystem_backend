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

import com.projects.SalesSystem.entities.Address;
import com.projects.SalesSystem.entities.Expense;
import com.projects.SalesSystem.entities.Person;
import com.projects.SalesSystem.entities.User;
import com.projects.SalesSystem.entities.Vehicle;
import com.projects.SalesSystem.entities.dto.PersonDTO;
import com.projects.SalesSystem.entities.dto.VehicleDTO;
import com.projects.SalesSystem.entities.enums.Bank;
import com.projects.SalesSystem.entities.enums.Status;
import com.projects.SalesSystem.entities.enums.VehicleType;
import com.projects.SalesSystem.repositories.VehicleRepository;
import com.projects.SalesSystem.security.UserSS;
import com.projects.SalesSystem.services.exceptions.AuthorizationException;
import com.projects.SalesSystem.services.exceptions.DataIntegrity;
import com.projects.SalesSystem.services.exceptions.ObjectNotFound;

@Service
public class VehicleService {

	@Autowired
	private VehicleRepository vehicleRepo;
	@Autowired
	private PersonService personService;
	@Autowired
	private ExpenseService expenseService;
	@Autowired
	private UserService userService;

	public Page<VehicleDTO> myStock(Pageable page) {
		UserSS authUser = userService.getAuthenticatedUser();

		if (authUser == null) {
			throw new AuthorizationException("Acesso Negado");
		}

		User user = userService.findUserByEmail(authUser.getUsername());
		
		List<Long> idsInStock  = user.getVehicles().stream()
									.filter(x -> x.getStatus().getDescription().equals("Estoque"))
									.map(x -> x.getId())
									.collect(Collectors.toList());
	
		return vehicleRepo.findByIdIn(idsInStock, page).map(x -> new VehicleDTO(x));
	}

	public Page<VehicleDTO> findByModelOrLicensePlate(Pageable page, String str) {
		UserSS authUser = userService.getAuthenticatedUser();

		if (authUser == null) {
			throw new AuthorizationException("Acesso Negado");
		}

		User user = userService.findUserByEmail(authUser.getUsername());
		List<Long> idsInStock  = user.getVehicles().stream()
				.filter(x -> x.getStatus().getDescription().equals("Estoque"))
				.map(x -> x.getId())
				.collect(Collectors.toList());

		boolean hasNumber = false;
		Page<Vehicle> vehicles;

		if (str.endsWith("0") || str.endsWith("1") || str.endsWith("2") || str.endsWith("3") || str.endsWith("4")
				|| str.endsWith("5") || str.endsWith("6") || str.endsWith("7") || str.endsWith("8")
				|| str.endsWith("9")) {
			hasNumber = true;
		}

		if (hasNumber) {
			vehicles = vehicleRepo.findByLicensePlateIgnoreCaseAndIdIn(str, idsInStock, page);

		} else {
			vehicles = vehicleRepo.findByModelContainingIgnoreCaseAndIdIn(str, idsInStock, page);
		}

		return vehicles.map(x -> new VehicleDTO(x));
	}

	public Vehicle findVehicleById(Long id) {
		Optional<Vehicle> obj = vehicleRepo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFound("Objeto não encontrado! Id: " + id));
	}

	public void insert(Vehicle obj) {
		vehicleRepo.save(obj);
	}

	@Transactional
	public VehicleDTO insertFromDTO(VehicleDTO obj) {
		UserSS authUser = userService.getAuthenticatedUser();

		if (authUser == null) {
			throw new AuthorizationException("Acesso Negado");
		}

		User user = userService.findUserByEmail(authUser.getUsername());
		
		Person seller = new Person(obj.getSeller().getId(), obj.getSeller().getName(), obj.getSeller().getEmail(),
				obj.getSeller().getCpf(), obj.getSeller().getTelephone());

		Address ad = new Address(obj.getSeller().getAddress().getId(), obj.getSeller().getAddress().getStreet(),
				obj.getSeller().getAddress().getNumber(), obj.getSeller().getAddress().getDistrict(),
				obj.getSeller().getAddress().getPostalCode(), obj.getSeller().getAddress().getCity(),
				obj.getSeller().getAddress().getState(), seller);

		seller.setAddress(ad);

		Vehicle vehicle = new Vehicle(obj.getId(), VehicleType.toIntegerEnum(obj.getType()), obj.getBrand(),
				obj.getModel(), obj.getYear(), LocalDate.now(), obj.getLicensePlate(), obj.getDescription(),
				obj.getPaidValue(), Bank.toIntegerEnum(obj.getBank()), obj.getPossibleSellValue(), Status.STOCK, user, seller);

		seller.getSales().add(vehicle);

		user.getVehicles().add(vehicle);

		if (vehicle.getBank().getDescription() == "Nubank") {
			user.setNubankBalance(-vehicle.getPaidValue());
		} else {
			user.setSantanderBalance(-vehicle.getPaidValue());
		}

		personService.insert(seller);
		insert(vehicle);
		userService.insert(user);

		return new VehicleDTO(vehicle);
	}

	public void update(VehicleDTO objDTO, Long id) {
		Vehicle dbVehicle = findVehicleById(id);
		dbVehicle = updating(dbVehicle, objDTO);
		vehicleRepo.save(dbVehicle);
	}

	public void updateSeller(PersonDTO objDTO, Long id) {
		Person dbPerson = personService.findPersonById(id);
		dbPerson = updatingSeller(dbPerson, objDTO);
		personService.insert(dbPerson);
	}

	@Transactional
	public void delete(Long id) {
		UserSS authUser = userService.getAuthenticatedUser();

		if (authUser == null) {
			throw new AuthorizationException("Acesso Negado");
		}

		User user = userService.findUserByEmail(authUser.getUsername());

		try {
			Vehicle vehicle = findVehicleById(id);
			
			if(vehicle.getBank().getDescription() == "Nubank") {
				user.setNubankBalance(vehicle.getPaidValue());
			}
			else {
				user.setSantanderBalance(vehicle.getPaidValue());
			}
			
			for(Expense exp: vehicle.getExpenses()) {
				if(exp.getBank().getDescription() == "Nubank") {
					user.setNubankBalance(exp.getValue());
				}
				else {
					user.setSantanderBalance(exp.getValue());
				}
				expenseService.delete(exp.getId());
			}
			
			userService.insert(user);
			
			Long sellerId = vehicle.getSeller().getId();
			
			
			vehicleRepo.deleteById(id);
			personService.delete(sellerId);
		
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrity("Não é possível deletar esse veículo! Este veículo já foi vendido.");
		}
	}

	private Vehicle updating(Vehicle obj, VehicleDTO objDTO) {
		obj.setType(VehicleType.toIntegerEnum(objDTO.getType()));
		obj.setBrand(objDTO.getBrand());
		obj.setModel(objDTO.getModel());
		obj.setYear(objDTO.getYear());
		obj.setLicensePlate(objDTO.getLicensePlate());
		obj.setDescription(objDTO.getDescription());
		obj.setPossibleSellValue(objDTO.getPossibleSellValue());

		return obj;
	}

	private Person updatingSeller(Person obj, PersonDTO objDTO) {
		obj.setName(objDTO.getName());
		obj.setEmail(objDTO.getEmail());
		obj.setTelephone(objDTO.getTelephone());
		obj.getAddress().setStreet(objDTO.getAddress().getStreet());
		obj.getAddress().setNumber(objDTO.getAddress().getNumber());
		obj.getAddress().setDistrict(objDTO.getAddress().getDistrict());
		obj.getAddress().setPostalCode(objDTO.getAddress().getPostalCode());
		obj.getAddress().setCity(objDTO.getAddress().getCity());
		obj.getAddress().setState(objDTO.getAddress().getState());

		return obj;
	}
}
