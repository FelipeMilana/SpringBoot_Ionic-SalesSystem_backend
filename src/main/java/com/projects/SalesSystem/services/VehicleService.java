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
	
	public VehicleDTO findById(Long id) {
		Vehicle vehicle = findVehicleById(id);
		return new VehicleDTO(vehicle);
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
		
		Person seller;
		
		if(obj.getSeller().getId() == null) {
			seller = new Person(obj.getSeller().getId(), obj.getSeller().getName(), obj.getSeller().getEmail(),
				obj.getSeller().getCpf(), obj.getSeller().getTelephone(), obj.getSeller().getTelephone2());

			Address ad = new Address(obj.getSeller().getAddress().getId(), obj.getSeller().getAddress().getStreet(),
				obj.getSeller().getAddress().getNumber(), obj.getSeller().getAddress().getDistrict(),
				obj.getSeller().getAddress().getPostalCode(), obj.getSeller().getAddress().getCity(),
				obj.getSeller().getAddress().getState(), seller);

			seller.setAddress(ad);
		}
		
		else {
			seller = personService.findPersonById(obj.getSeller().getId());
		}
		
		Vehicle vehicle = new Vehicle(obj.getId(), VehicleType.toIntegerEnum(obj.getType()), obj.getBrand(),
				obj.getModel(), obj.getVersion(), obj.getFabYear(), obj.getModYear(), obj.getColor(), obj.getMotor(),
				LocalDate.now(), obj.getLicensePlate(), obj.getChassi(), obj.getRenavam(), obj.getDescription(),
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
				expenseService.delete(exp.getId());
			}
			
			vehicleRepo.deleteById(vehicle.getId());
			userService.insert(user);
			
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrity("Não é possível deletar esse veículo! Este veículo já foi vendido.");
		}
	}

	private Vehicle updating(Vehicle obj, VehicleDTO objDTO) {
		obj.setType(VehicleType.toIntegerEnum(objDTO.getType()));
		obj.setBrand(objDTO.getBrand());
		obj.setModel(objDTO.getModel());
		obj.setVersion(objDTO.getVersion());
		obj.setFabYear(objDTO.getFabYear());
		obj.setModYear(objDTO.getModYear());
		obj.setColor(objDTO.getColor());
		obj.setMotor(objDTO.getMotor());
		obj.setLicensePlate(objDTO.getLicensePlate());
		obj.setChassi(objDTO.getChassi());
		obj.setRenavam(objDTO.getRenavam());
		obj.setDescription(objDTO.getDescription());
		obj.setPossibleSellValue(objDTO.getPossibleSellValue());

		return obj;
	}
}
