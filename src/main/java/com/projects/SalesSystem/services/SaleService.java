package com.projects.SalesSystem.services;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.projects.SalesSystem.entities.Address;
import com.projects.SalesSystem.entities.CashPayment;
import com.projects.SalesSystem.entities.CashWithExchangePayment;
import com.projects.SalesSystem.entities.ConsortiumPayment;
import com.projects.SalesSystem.entities.ConsortiumWithExchangePayment;
import com.projects.SalesSystem.entities.FundedPayment;
import com.projects.SalesSystem.entities.FundedWithExchangePayment;
import com.projects.SalesSystem.entities.Payment;
import com.projects.SalesSystem.entities.Person;
import com.projects.SalesSystem.entities.Sale;
import com.projects.SalesSystem.entities.User;
import com.projects.SalesSystem.entities.Vehicle;
import com.projects.SalesSystem.entities.dto.CashPaymentDTO;
import com.projects.SalesSystem.entities.dto.CashWithExchangePaymentDTO;
import com.projects.SalesSystem.entities.dto.ConsortiumPaymentDTO;
import com.projects.SalesSystem.entities.dto.ConsortiumWithExchangePaymentDTO;
import com.projects.SalesSystem.entities.dto.ExchangeVehicleDTO;
import com.projects.SalesSystem.entities.dto.ExpenseDTO;
import com.projects.SalesSystem.entities.dto.FundedPaymentDTO;
import com.projects.SalesSystem.entities.dto.FundedWithExchangePaymentDTO;
import com.projects.SalesSystem.entities.dto.SaleDTO;
import com.projects.SalesSystem.entities.enums.Bank;
import com.projects.SalesSystem.entities.enums.PaymentType;
import com.projects.SalesSystem.entities.enums.Status;
import com.projects.SalesSystem.entities.enums.VehicleType;
import com.projects.SalesSystem.repositories.SaleRepository;
import com.projects.SalesSystem.security.UserSS;
import com.projects.SalesSystem.services.exceptions.AuthorizationException;

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
	@Autowired
	private EmailService emailService;

	public Page<SaleDTO> mySales(Pageable page) {
		UserSS authUser = userService.getAuthenticatedUser();

		if (authUser == null) {
			throw new AuthorizationException("Acesso Negado");
		}

		User user = userService.findUserByEmail(authUser.getUsername());
		List<Long> salesIds = user.getSales().stream().map(x -> x.getId()).collect(Collectors.toList());

		return saleRepo.findByIdIn(salesIds, page).map(x -> new SaleDTO(x));
	}

	public Page<SaleDTO> findByVehicleModelOrLicensePlate(Pageable page, String str) {
		UserSS authUser = userService.getAuthenticatedUser();

		if (authUser == null) {
			throw new AuthorizationException("Acesso Negado");
		}

		User user = userService.findUserByEmail(authUser.getUsername());
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
		UserSS authUser = userService.getAuthenticatedUser();

		if (authUser == null) {
			throw new AuthorizationException("Acesso Negado");
		}

		User user = userService.findUserByEmail(authUser.getUsername());
		List<Long> salesIds = user.getSales().stream().map(x -> x.getId()).collect(Collectors.toList());

		Page<SaleDTO> sales = saleRepo.findByDateBetweenAndIdIn(startDate, endDate, salesIds, page)
				.map(x -> new SaleDTO(x));

		File path = createCSVFIle(sales, startDate, endDate);
		emailService.sendMonthlyReportToEmail(path);

		return sales;
	}

	@Transactional
	public SaleDTO insertFromDTO(Long id, SaleDTO objDTO) {
		UserSS authUser = userService.getAuthenticatedUser();

		if (authUser == null) {
			throw new AuthorizationException("Acesso Negado");
		}

		User user = userService.findUserByEmail(authUser.getUsername());

		Vehicle vehicle = vehicleService.findVehicleById(id);

		Person client;
		
		if(objDTO.getClient().getId() == null) {
		
			client = new Person(objDTO.getClient().getId(), objDTO.getClient().getName(),
				objDTO.getClient().getEmail(), objDTO.getClient().getCpf(), objDTO.getClient().getTelephone(),
				objDTO.getClient().getTelephone2());

			Address ad = new Address(objDTO.getClient().getAddress().getId(), objDTO.getClient().getAddress().getStreet(),
				objDTO.getClient().getAddress().getNumber(), objDTO.getClient().getAddress().getDistrict(),
				objDTO.getClient().getAddress().getPostalCode(), objDTO.getClient().getAddress().getCity(),
				objDTO.getClient().getAddress().getState(), client);

			client.setAddress(ad);
		}
		else {
			client = personService.findPersonById(objDTO.getClient().getId());
		}

		Sale sale = new Sale(null, LocalDate.now(), objDTO.getFinalValue(), vehicle, client, user);

		vehicle.setStatus(Status.SOLD);
		client.getPurchases().add(sale);
		user.getSales().add(sale);

		personService.insert(client);
		Vehicle exchange = null;

		if (objDTO.getPayment() instanceof CashPaymentDTO) {
			CashPaymentDTO payDTO = (CashPaymentDTO) objDTO.getPayment();
			Payment pay = new CashPayment(payDTO.getId(), sale, Bank.toIntegerEnum(payDTO.getBank()));
			pay.setPaymentType(PaymentType.CASH);
			sale.setPayment(pay);

			if (payDTO.getBank().equals(2)) {
				user.setNubankBalance(objDTO.getFinalValue());
			} else {
				user.setSantanderBalance(objDTO.getFinalValue());
			}
		}

		else if (objDTO.getPayment() instanceof CashWithExchangePaymentDTO) {
			CashWithExchangePaymentDTO payDTO = (CashWithExchangePaymentDTO) objDTO.getPayment();

			exchange = addVehicleToStock(objDTO.getClient().getCpf(), payDTO.getExchangeVehicle(), user);

			Payment pay = new CashWithExchangePayment(payDTO.getId(), sale, Bank.toIntegerEnum(payDTO.getBank()),
					payDTO.getCashValue(), exchange);
			pay.setPaymentType(PaymentType.CASHWITHEXCHANGE);
			sale.setPayment(pay);

			if (payDTO.getBank().equals(2)) {
				user.setNubankBalance(payDTO.getCashValue());
			} else {
				user.setSantanderBalance(payDTO.getCashValue());
			}
		}

		else if (objDTO.getPayment() instanceof FundedPaymentDTO) {
			FundedPaymentDTO payDTO = (FundedPaymentDTO) objDTO.getPayment();

			Payment pay = new FundedPayment(payDTO.getId(), sale, payDTO.getName(), payDTO.getCnpj(),
					payDTO.getTelephone(), payDTO.getInputValue(), Bank.toIntegerEnum(payDTO.getInputBank()),
					payDTO.getFundedValue(), Bank.toIntegerEnum(payDTO.getFundedBank()));

			pay.setPaymentType(PaymentType.FUNDED);
			sale.setPayment(pay);

			if (payDTO.getInputBank().equals(2)) {
				user.setNubankBalance(payDTO.getInputValue());
			} else {
				user.setSantanderBalance(payDTO.getInputValue());
			}

			if (payDTO.getFundedBank().equals(2)) {
				user.setNubankBalance(payDTO.getFundedValue());
			} else {
				user.setSantanderBalance(payDTO.getFundedValue());
			}
		}

		else if (objDTO.getPayment() instanceof FundedWithExchangePaymentDTO) {
			FundedWithExchangePaymentDTO payDTO = (FundedWithExchangePaymentDTO) objDTO.getPayment();

			exchange = addVehicleToStock(objDTO.getClient().getCpf(), payDTO.getExchangeVehicle(), user);

			Payment pay = new FundedWithExchangePayment(payDTO.getId(), sale, payDTO.getName(), payDTO.getCnpj(), 
					payDTO.getTelephone(), payDTO.getInputValue(), Bank.toIntegerEnum(payDTO.getInputBank()),
					payDTO.getFundedValue(), Bank.toIntegerEnum(payDTO.getFundedBank()), exchange);
			
			pay.setPaymentType(PaymentType.FUNDEDWITHEXCHANGE);
			sale.setPayment(pay);

			if (payDTO.getInputBank().equals(2)) {
				user.setNubankBalance(payDTO.getInputValue());
			} else {
				user.setSantanderBalance(payDTO.getInputValue());
			}
			
			if(payDTO.getFundedBank().equals(2)) {
				user.setNubankBalance(payDTO.getFundedValue());;
			}
			else {
				user.setSantanderBalance(payDTO.getFundedValue());
			}
		}
		
		else if (objDTO.getPayment() instanceof ConsortiumPaymentDTO) {
			ConsortiumPaymentDTO payDTO = (ConsortiumPaymentDTO) objDTO.getPayment();

			Payment pay = new ConsortiumPayment(payDTO.getId(), sale, payDTO.getName(), payDTO.getCnpj(), 
					payDTO.getTelephone(), payDTO.getQuota(), payDTO.getGroup(), payDTO.getInputValue(), 
					Bank.toIntegerEnum(payDTO.getInputBank()), payDTO.getConsortiumValue(), 
					Bank.toIntegerEnum(payDTO.getConsortiumBank()));

			pay.setPaymentType(PaymentType.CONSORTIUM);
			sale.setPayment(pay);

			if (payDTO.getInputBank().equals(2)) {
				user.setNubankBalance(payDTO.getInputValue());
			} else {
				user.setSantanderBalance(payDTO.getInputValue());
			}

			if (payDTO.getConsortiumBank().equals(2)) {
				user.setNubankBalance(payDTO.getConsortiumValue());
			} else {
				user.setSantanderBalance(payDTO.getConsortiumValue());
			}
		}
		
		else {
			ConsortiumWithExchangePaymentDTO payDTO = (ConsortiumWithExchangePaymentDTO) objDTO.getPayment();

			exchange = addVehicleToStock(objDTO.getClient().getCpf(), payDTO.getExchangeVehicle(), user);

			Payment pay = new ConsortiumWithExchangePayment(payDTO.getId(), sale, payDTO.getName(), payDTO.getCnpj(), 
					payDTO.getTelephone(), payDTO.getQuota(), payDTO.getGroup(), payDTO.getInputValue(), 
					Bank.toIntegerEnum(payDTO.getInputBank()), payDTO.getConsortiumValue(), 
					Bank.toIntegerEnum(payDTO.getConsortiumBank()), exchange);
			
			pay.setPaymentType(PaymentType.CONSORTIUMWITHEXCHANGE);
			sale.setPayment(pay);

			if (payDTO.getInputBank().equals(2)) {
				user.setNubankBalance(payDTO.getInputValue());
			} else {
				user.setSantanderBalance(payDTO.getInputValue());
			}
			
			if(payDTO.getConsortiumBank().equals(2)) {
				user.setNubankBalance(payDTO.getConsortiumValue());;
			}
			else {
				user.setSantanderBalance(payDTO.getConsortiumValue());
			}
		}

		personService.insert(client);
		insert(sale);
		vehicleService.insert(vehicle);

		if (exchange != null) {
			vehicleService.insert(exchange);
		}

		userService.insert(user);

		return new SaleDTO(sale);
	}

	private File createCSVFIle(Page<SaleDTO> sales, LocalDate startDate, LocalDate endDate) {

		File path = new File("C:\\temp\\ws - sts\\SalesSystem\\RelatórioMensal.csv");
		double totalProfit = 0.0;

		String date1 = DateTimeFormatter.ofPattern("dd/MM/yyyy")
				.format(LocalDate.of(startDate.getYear(), startDate.getMonthValue(), startDate.getDayOfMonth()));
		String date2 = DateTimeFormatter.ofPattern("dd/MM/yyyy")
				.format(LocalDate.of(endDate.getYear(), endDate.getMonthValue(), endDate.getDayOfMonth()));

		try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {

			bw.write("RELATORIO DE VENDAS: de " + date1 + " ate " + date2);
			bw.newLine();
			bw.newLine();

			bw.write("Data" + ";" + "Cliente" + ";" + "Veiculo" + ";" + "Placa" + ";" + "Valor Pago (R$)" + ";"
					+ "Despesas (R$)" + ";" + "Valor Vendido (R$)" + ";" + "Metodo de Pagamento" + ";" + "Lucro (R$)");
			bw.newLine();

			for (SaleDTO s : sales) {

				double totalExpense = 0.0;
				for (ExpenseDTO exp : s.getVehicle().getExpenses()) {
					totalExpense += exp.getValue();
				}

				bw.write(s.getDate() + ";" + s.getClient().getName() + ";" + s.getVehicle().getModel() + ";"
						+ s.getVehicle().getLicensePlate() + ";" + s.getVehicle().getPaidValue() + ";" + totalExpense
						+ ";" + s.getFinalValue() + ";"
						+ PaymentType.toIntegerEnum(s.getPayment().getPaymentType()).getDescription() + ";"
						+ s.getProfit());

				totalProfit += s.getProfit();
				bw.newLine();
			}

			bw.newLine();
			bw.write("Lucro total (R$)" + ";" + totalProfit);
			bw.close();
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage());
		}
		return path;
	}

	private Vehicle addVehicleToStock(String cpf, ExchangeVehicleDTO objDTO, User user) {

		Person client = personService.findPersonByCpf(cpf);

		Vehicle exchange = new Vehicle(null, VehicleType.toIntegerEnum(objDTO.getType()), objDTO.getBrand(),
				objDTO.getModel(), objDTO.getYear(), LocalDate.now(), objDTO.getLicensePlate(), objDTO.getDescription(),
				objDTO.getPaidValue(), null, objDTO.getPossibleSellValue(), Status.STOCK, user, client);

		client.getSales().add(exchange);
		user.getVehicles().add(exchange);

		return exchange;
	}
}