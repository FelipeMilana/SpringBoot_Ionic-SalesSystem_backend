package com.projects.SalesSystem.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.projects.SalesSystem.entities.Address;
import com.projects.SalesSystem.entities.Person;
import com.projects.SalesSystem.entities.User;
import com.projects.SalesSystem.entities.Vehicle;
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

	public VehicleDTO findById(Long id) {
		Vehicle obj = findVehicleById(id);
		return new VehicleDTO(obj);
	}

	public VehicleDTO findByName(String name) {
		Vehicle obj = vehicleRepo.findByName(name);

		if (obj != null) {
			return new VehicleDTO(obj);
		} else {
			throw new ObjectNotFound("Objeto não encontrado! Name: " + name);
		}
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
				obj.getModel(), obj.getYear(), obj.getDate(), obj.getLicensePlate(), obj.getDescription(),
				obj.getPaidValue(), Bank.toIntegerEnum(obj.getBank()), obj.getPossibleSellValue(), buyer, seller);
		
		seller.getSales().add(vehicle);
		
		buyer.getStock().add(vehicle);
		
		if(vehicle.getBank().getDescription() == "Nubank") {
			buyer.setNubankBalance(-vehicle.getPaidValue());
		}
		else {
			buyer.setSantanderBalance(-vehicle.getPaidValue());
		}

		personService.insert(seller);
		//userService.insert(buyer);
		vehicleRepo.save(vehicle);

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

	public void delete(Long id) {
		Vehicle obj = findVehicleById(id);
		try {
			vehicleRepo.deleteById(id);
			personService.delete(obj.getSeller().getId());
		}
		catch (DataIntegrityViolationException e) {
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
