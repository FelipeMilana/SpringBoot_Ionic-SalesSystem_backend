package com.projects.SalesSystem.services;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.projects.SalesSystem.entities.Address;
import com.projects.SalesSystem.entities.Person;
import com.projects.SalesSystem.entities.Sale;
import com.projects.SalesSystem.entities.User;
import com.projects.SalesSystem.entities.Vehicle;
import com.projects.SalesSystem.entities.dto.SaleDTO;
import com.projects.SalesSystem.repositories.SaleRepository;

@Service
public class SaleService {

	@Autowired
	private SaleRepository saleRepo;
	@Autowired
	private VehicleService vehicleService;
	@Autowired
	private PersonService personService;
	@Autowired
	private UserService userService;

	public Page<SaleDTO> mySales(Pageable page) {
		// Will be authenticated user, this user is a mockdata
		User user = new User(null, "Felipe", "fe@gmail.com", "123", 50000.00, 60000.00);

		List<Long> salesIds = user.getSales().stream().map(x -> x.getId()).collect(Collectors.toList());

		return saleRepo.findByIdIn(salesIds, page).map(x -> new SaleDTO(x));
	}

	public Page<SaleDTO> findByVehicleModelOrLicensePlate(Pageable page, String str) {
		// Will be authenticated user, this user is a mockdata
		User user = new User(null, "Felipe", "fe@gmail.com", "123", 50000.00, 60000.00);

		List<Long> salesIds = user.getSales().stream().map(x -> x.getId()).collect(Collectors.toList());

		boolean hasNumber = false;
		Page<Sale> sales;

		if (str.endsWith("0") || str.endsWith("1") || str.endsWith("2") || str.endsWith("3") || str.endsWith("4")
				|| str.endsWith("5") || str.endsWith("6") || str.endsWith("7") || str.endsWith("8")
				|| str.endsWith("9")) {
			hasNumber = true;
		}

		if (hasNumber) {
			sales = saleRepo.findByVehicleLicensePlateIgnoreCaseAndIdIn(str, salesIds, page);

		} else {
			sales = saleRepo.findByVehicleModelContainingIgnoreCaseAndIdIn(str, salesIds, page);
		}

		return sales.map(x -> new SaleDTO(x));
	}

	public void insert(Sale obj) {
		saleRepo.save(obj);
	}

	public Page<SaleDTO> monthlyReport(Pageable page, LocalDate startDate, LocalDate endDate) {
		// Will be authenticated user, this user is a mockdata
		User user = new User(null, "Felipe", "fe@gmail.com", "123", 50000.00, 60000.00);

		List<Long> salesIds = user.getSales().stream().map(x -> x.getId()).collect(Collectors.toList());
		
		return saleRepo.findByDateBetweenAndIdIn(startDate, endDate, salesIds, page).map(x -> new SaleDTO(x));
	}

	@Transactional
	public SaleDTO insertFromDTO(Long id, SaleDTO objDTO) {
		// Will be authenticated user, this user is a mockdata
		User user = new User(null, "Felipe", "fe@gmail.com", "123", 50000.00, 60000.00);

		Vehicle vehicle = vehicleService.findVehicleById(id);

		Person client = new Person(objDTO.getClient().getId(), objDTO.getClient().getName(),
				objDTO.getClient().getEmail(), objDTO.getClient().getCpf(), objDTO.getClient().getTelephone());

		Address ad = new Address(objDTO.getClient().getAddress().getId(), objDTO.getClient().getAddress().getStreet(),
				objDTO.getClient().getAddress().getNumber(), objDTO.getClient().getAddress().getDistrict(),
				objDTO.getClient().getAddress().getPostalCode(), objDTO.getClient().getAddress().getCity(),
				objDTO.getClient().getAddress().getState(), client);

		client.setAddress(ad);

		Sale sale = new Sale(objDTO.getId(), LocalDate.now(), objDTO.getFinalValue(), vehicle, client, user);

		client.getPurchases().add(sale);
		user.getStock().remove(vehicle);
		user.getSales().add(sale);

		if (vehicle.getBank().getDescription() == "Nubank") {
			user.setNubankBalance(sale.getFinalValue());
		} else {
			user.setSantanderBalance(sale.getFinalValue());
		}

		personService.insert(client);
		insert(sale);
		userService.insert(user);

		return new SaleDTO(sale);
	}
}
