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
import com.projects.SalesSystem.entities.dto.ExpenseDTO;
import com.projects.SalesSystem.entities.dto.PersonDTO;
import com.projects.SalesSystem.entities.dto.VehicleDTO;
import com.projects.SalesSystem.entities.enums.Bank;
import com.projects.SalesSystem.entities.enums.VehicleType;
import com.projects.SalesSystem.repositories.VehicleRepository;
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
		// Will be authenticated user, this user is a mockdata
		User user = new User(null, "Felipe", "fe@gmail.com", "123", 50000.00, 60000.00);

		List<Long> vehiclesIds = user.getStock().stream().map(x -> x.getId()).collect(Collectors.toList());

		return vehicleRepo.findByIdIn(vehiclesIds, page).map(x -> new VehicleDTO(x));
	}

	public Page<VehicleDTO> findByModelOrLicensePlate(Pageable page, String str) {
		// Will be authenticated user, this user is a mockdata
		User user = new User(null, "Felipe", "fe@gmail.com", "123", 50000.00, 60000.00);

		List<Long> vehiclesIds = user.getStock().stream().map(x -> x.getId()).collect(Collectors.toList());
		
		boolean hasNumber = false;
		Page<Vehicle> vehicles;
		
		if (str.endsWith("0") || str.endsWith("1") || str.endsWith("2") || str.endsWith("3") || str.endsWith("4") || str.endsWith("5") || str.endsWith("6")
				|| str.endsWith("7") || str.endsWith("8") || str.endsWith("9")) {
			hasNumber = true;
		}

		if (hasNumber) {
			vehicles = vehicleRepo.findByLicensePlateIgnoreCaseAndIdIn(str, vehiclesIds, page);
		
		} else {
			vehicles = vehicleRepo.findByModelContainingIgnoreCaseAndIdIn(str, vehiclesIds, page);
		}
		
		return vehicles.map(x -> new VehicleDTO(x));
	}

	public void insert(Vehicle obj) {
		vehicleRepo.save(obj);
	}

	@Transactional
	public VehicleDTO insertFromDTO(VehicleDTO obj) {
		Person seller = new Person(obj.getSeller().getId(), obj.getSeller().getName(), obj.getSeller().getEmail(),
				obj.getSeller().getCpf(), obj.getSeller().getTelephone());

		Address ad = new Address(obj.getSeller().getAddress().getId(), obj.getSeller().getAddress().getStreet(),
				obj.getSeller().getAddress().getNumber(), obj.getSeller().getAddress().getDistrict(),
				obj.getSeller().getAddress().getPostalCode(), obj.getSeller().getAddress().getCity(),
				obj.getSeller().getAddress().getState(), seller);

		seller.setAddress(ad);

		// Will be authenticated user, this user is a mockdata
		User buyer = new User(null, "Felipe", "fe@gmail.com", "123", 50000.00, 60000.00);

		Vehicle vehicle = new Vehicle(obj.getId(), VehicleType.toIntegerEnum(obj.getType()), obj.getBrand(),
				obj.getModel(), obj.getYear(), LocalDate.now(), obj.getLicensePlate(), obj.getDescription(),
				obj.getPaidValue(), Bank.toIntegerEnum(obj.getBank()), obj.getPossibleSellValue(), buyer, seller);

		seller.getSales().add(vehicle);

		buyer.getStock().add(vehicle);

		if (vehicle.getBank().getDescription() == "Nubank") {
			buyer.setNubankBalance(-vehicle.getPaidValue());
		} else {
			buyer.setSantanderBalance(-vehicle.getPaidValue());
		}

		personService.insert(seller);
		userService.insert(buyer);
		insert(vehicle);

		return new VehicleDTO(vehicle);
	}

	@Transactional
	public void insertExpenses(Long id, ExpenseDTO obj) {
		Vehicle vehicle = findVehicleById(id);

		Expense exp = new Expense(obj.getId(), obj.getName(), Bank.toIntegerEnum(obj.getBank()), obj.getValue(),
				LocalDate.now(), vehicle);

		vehicle.getExpenses().add(exp);

		// wil be authenticated user, this user is a mockdata
		User buyer = new User(null, "Felipe", "fe@gmail.com", "123", 50000.00, 60000.00);

		if (exp.getBank().getDescription() == "Nubank") {
			buyer.setNubankBalance(-exp.getValue());
		} else {
			buyer.setSantanderBalance(-exp.getValue());
		}

		expenseService.insert(exp);
		insert(vehicle);
		userService.insert(buyer);
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

	public void delete(Long id) {
		// wil be authenticated user, this user is a mockdata
		User buyer = new User(null, "Felipe", "fe@gmail.com", "123", 50000.00, 60000.00);

		Vehicle obj = findVehicleById(id);
		Bank bank = obj.getBank();
		Double value = obj.getPaidValue();
		List<Expense> expenses = obj.getExpenses();

		try {
			for (Expense exp : expenses) {
				expenseService.delete(exp.getId());

				if (exp.getBank().getDescription() == "Nubank") {

					buyer.setNubankBalance(exp.getValue());
				} else {
					buyer.setSantanderBalance(exp.getValue());
				}
			}

			vehicleRepo.deleteById(id);

			if (bank.getDescription() == "Nubank") {
				buyer.setNubankBalance(value);
			} else {
				buyer.setSantanderBalance(value);
			}

			personService.delete(obj.getSeller().getId());
			userService.insert(buyer);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrity("Não é possível deletar pois esse veículo tem elementos associados");
		}
	}

	private Vehicle findVehicleById(Long id) {
		Optional<Vehicle> obj = vehicleRepo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFound("Objeto não encontrado! Id: " + id));
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
