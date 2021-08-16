package com.projects.SalesSystem.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.projects.SalesSystem.entities.dto.ExpenseDTO;
import com.projects.SalesSystem.entities.dto.SaleDTO;
import com.projects.SalesSystem.entities.dto.WithdrawDTO;
import com.projects.SalesSystem.entities.enums.Bank;
import com.projects.SalesSystem.entities.enums.PaymentType;

@Service
public class ExcelService {

	public File createFile(Page<SaleDTO> sales, Page<WithdrawDTO> withdraws, LocalDate startDate, LocalDate endDate) {
		File path = new File("C:\\temp\\ws - sts\\SalesSystem\\Relatório.xlsx");

		String date1 = DateTimeFormatter.ofPattern("dd-MM-yyyy")
				.format(LocalDate.of(startDate.getYear(), startDate.getMonthValue(), startDate.getDayOfMonth()));
		String date2 = DateTimeFormatter.ofPattern("dd-MM-yyyy")
				.format(LocalDate.of(endDate.getYear(), endDate.getMonthValue(), endDate.getDayOfMonth()));

		// create workbook
		XSSFWorkbook workbook = new XSSFWorkbook();

		// create header cell style
		CellStyle headerStyle = workbook.createCellStyle();
		headerStyle.setAlignment(HorizontalAlignment.CENTER);
		headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		headerStyle.setLocked(true);
		

		// create header cell style with colors
		CellStyle headerWithYellowColorStyle = workbook.createCellStyle();
		headerWithYellowColorStyle.setAlignment(HorizontalAlignment.CENTER);
		headerWithYellowColorStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		headerWithYellowColorStyle.setLocked(true);
		headerWithYellowColorStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
		headerWithYellowColorStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		headerWithYellowColorStyle.setBorderBottom(BorderStyle.MEDIUM);
		headerWithYellowColorStyle.setBorderLeft(BorderStyle.MEDIUM);
		headerWithYellowColorStyle.setBorderRight(BorderStyle.MEDIUM);
		headerWithYellowColorStyle.setBorderTop(BorderStyle.MEDIUM);
		
		CellStyle headerWithBlueColorStyle = workbook.createCellStyle();
		headerWithBlueColorStyle.setAlignment(HorizontalAlignment.CENTER);
		headerWithBlueColorStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		headerWithBlueColorStyle.setLocked(true);
		headerWithBlueColorStyle.setFillForegroundColor(IndexedColors.SKY_BLUE.getIndex());
		headerWithBlueColorStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		headerWithBlueColorStyle.setBorderBottom(BorderStyle.MEDIUM);
		headerWithBlueColorStyle.setBorderLeft(BorderStyle.MEDIUM);
		headerWithBlueColorStyle.setBorderRight(BorderStyle.MEDIUM);
		headerWithBlueColorStyle.setBorderTop(BorderStyle.MEDIUM);
		
		CellStyle headerWithGreenColorStyle = workbook.createCellStyle();
		headerWithGreenColorStyle.setAlignment(HorizontalAlignment.CENTER);
		headerWithGreenColorStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		headerWithGreenColorStyle.setLocked(true);
		headerWithGreenColorStyle.setFillForegroundColor(IndexedColors.SEA_GREEN.getIndex());
		headerWithGreenColorStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		headerWithGreenColorStyle.setBorderBottom(BorderStyle.MEDIUM);
		headerWithGreenColorStyle.setBorderLeft(BorderStyle.MEDIUM);
		headerWithGreenColorStyle.setBorderRight(BorderStyle.MEDIUM);
		headerWithGreenColorStyle.setBorderTop(BorderStyle.MEDIUM);
		
		CellStyle headerWithOrangeColorStyle = workbook.createCellStyle();
		headerWithOrangeColorStyle.setAlignment(HorizontalAlignment.CENTER);
		headerWithOrangeColorStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		headerWithOrangeColorStyle.setLocked(true);
		headerWithOrangeColorStyle.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.getIndex());
		headerWithOrangeColorStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		headerWithOrangeColorStyle.setBorderBottom(BorderStyle.MEDIUM);
		headerWithOrangeColorStyle.setBorderLeft(BorderStyle.MEDIUM);
		headerWithOrangeColorStyle.setBorderRight(BorderStyle.MEDIUM);
		headerWithOrangeColorStyle.setBorderTop(BorderStyle.MEDIUM);
		

		Font font = workbook.createFont();
		font.setBold(true);
		headerStyle.setFont(font);
		headerWithYellowColorStyle.setFont(font);
		headerWithBlueColorStyle.setFont(font);
		headerWithGreenColorStyle.setFont(font);
		headerWithOrangeColorStyle.setFont(font);

		// create cell style
		CellStyle style = workbook.createCellStyle();
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		style.setLocked(true);
		style.setBorderBottom(BorderStyle.MEDIUM);
		style.setBorderLeft(BorderStyle.MEDIUM);
		style.setBorderRight(BorderStyle.MEDIUM);
		style.setBorderTop(BorderStyle.MEDIUM);
		
		
		CellStyle redStyle = workbook.createCellStyle();
		redStyle.setAlignment(HorizontalAlignment.CENTER);
		redStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		redStyle.setLocked(true);
		redStyle.setBorderBottom(BorderStyle.MEDIUM);
		redStyle.setBorderLeft(BorderStyle.MEDIUM);
		redStyle.setBorderRight(BorderStyle.MEDIUM);
		redStyle.setBorderTop(BorderStyle.MEDIUM);
		
		Font font2 = workbook.createFont();
		font2.setColor(IndexedColors.RED.getIndex());
		redStyle.setFont(font2);
		
		CellStyle greenStyle = workbook.createCellStyle();
		greenStyle.setAlignment(HorizontalAlignment.CENTER);
		greenStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		greenStyle.setLocked(true);
		greenStyle.setBorderBottom(BorderStyle.MEDIUM);
		greenStyle.setBorderLeft(BorderStyle.MEDIUM);
		greenStyle.setBorderRight(BorderStyle.MEDIUM);
		greenStyle.setBorderTop(BorderStyle.MEDIUM);
		
		Font font3 = workbook.createFont();
		font3.setColor(IndexedColors.GREEN.getIndex());
		greenStyle.setFont(font3);
		
		CellStyle redGroundStyle = workbook.createCellStyle();
		redGroundStyle.setAlignment(HorizontalAlignment.CENTER);
		redGroundStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		redGroundStyle.setLocked(true);
		redGroundStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
		redGroundStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		redGroundStyle.setBorderBottom(BorderStyle.MEDIUM);
		redGroundStyle.setBorderLeft(BorderStyle.MEDIUM);
		redGroundStyle.setBorderRight(BorderStyle.MEDIUM);
		redGroundStyle.setBorderTop(BorderStyle.MEDIUM);
		
		CellStyle purpleGroundStyle = workbook.createCellStyle();
		purpleGroundStyle.setAlignment(HorizontalAlignment.CENTER);
		purpleGroundStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		purpleGroundStyle.setLocked(true);
		purpleGroundStyle.setFillForegroundColor(IndexedColors.MAROON.getIndex());
		purpleGroundStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		purpleGroundStyle.setBorderBottom(BorderStyle.MEDIUM);
		purpleGroundStyle.setBorderLeft(BorderStyle.MEDIUM);
		purpleGroundStyle.setBorderRight(BorderStyle.MEDIUM);
		purpleGroundStyle.setBorderTop(BorderStyle.MEDIUM);

		// create spreadsheet
		XSSFSheet sheet = workbook.createSheet(date1 + " a " + date2);
		sheet.enableLocking();

		// create row
		XSSFRow row;

		// create headers
		row = sheet.createRow(0);
		Cell headerCell = row.createCell(5);
		headerCell.setCellValue("RELATORIO");
		headerCell.setCellStyle(headerStyle);

		row = sheet.createRow(2);
		Cell headerSaleCell = row.createCell(5);
		headerSaleCell.setCellValue("VENDAS");
		headerSaleCell.setCellStyle(headerStyle);
		
		row = sheet.createRow(3);
		Cell cell0 = row.createCell(0);
		cell0.setCellValue("Data de Compra");
		cell0.setCellStyle(headerWithYellowColorStyle);

		Cell cell1 = row.createCell(1);
		cell1.setCellValue("Data de Venda");
		cell1.setCellStyle(headerWithYellowColorStyle);
		
		Cell cell2 = row.createCell(2);
		cell2.setCellValue("Dias de Estoque");
		cell2.setCellStyle(headerWithYellowColorStyle);
		
		Cell cell3 = row.createCell(3);
		cell3.setCellValue("   Nome do Cliente   ");
		cell3.setCellStyle(headerWithYellowColorStyle);
		
		Cell cell4 = row.createCell(4);
		cell4.setCellValue("     CPF/CNPJ     ");
		cell4.setCellStyle(headerWithYellowColorStyle);

		Cell cell5 = row.createCell(5);
		cell5.setCellValue("Modelo do Veiculo");
		cell5.setCellStyle(headerWithYellowColorStyle);

		Cell cell6 = row.createCell(6);
		cell6.setCellValue("Placa do Veiculo");
		cell6.setCellStyle(headerWithYellowColorStyle);

		Cell cell7 = row.createCell(7);
		cell7.setCellValue("   Valor Pago   ");
		cell7.setCellStyle(headerWithYellowColorStyle);

		Cell cell8 = row.createCell(8);
		cell8.setCellValue("Despesas Totais");
		cell8.setCellStyle(headerWithYellowColorStyle);

		Cell cell9 = row.createCell(9);
		cell9.setCellValue("   Valor Venda   ");
		cell9.setCellStyle(headerWithYellowColorStyle);

		Cell cell10 = row.createCell(10);
		cell10.setCellValue("Forma de pagamento");
		cell10.setCellStyle(headerWithYellowColorStyle);

		Cell cell11 = row.createCell(11);
		cell11.setCellValue("       Lucro       ");
		cell11.setCellStyle(headerWithYellowColorStyle);
		

		// create cells
		double totalProfit = 0.0;

		int j = 4;

		for (SaleDTO s : sales) {

			double totalExpense = 0.0;
			for (ExpenseDTO exp : s.getVehicle().getExpenses()) {
				totalExpense += exp.getValue();
			}

			row = sheet.createRow(j);

			for (int i = 0; i < 12; i++) {
				Cell cell = row.createCell(i);
				cell.setCellStyle(style);

				if (cell.getColumnIndex() == 0) {
					String date = DateTimeFormatter.ofPattern("dd/MM/yyyy").format(LocalDate.of(s.getVehicle().getDate().getYear(),
							s.getVehicle().getDate().getMonthValue(), s.getVehicle().getDate().getDayOfMonth()));

					cell.setCellValue(date);
				} 
				else if (cell.getColumnIndex() == 1) {
					String date = DateTimeFormatter.ofPattern("dd/MM/yyyy").format(LocalDate.of(s.getDate().getYear(),
							s.getDate().getMonthValue(), s.getDate().getDayOfMonth()));

					cell.setCellValue(date);
				} 
				else if (cell.getColumnIndex() == 2) {
					Long daysOfStock = s.getDate().toEpochDay() - s.getVehicle().getDate().toEpochDay();
					cell.setCellValue(String.valueOf(daysOfStock));
				} 
				else if (cell.getColumnIndex() == 3) {
					cell.setCellValue(s.getClient().getName());
				} else if (cell.getColumnIndex() == 4) {
					cell.setCellValue(s.getClient().getCpfCnpj());
				}else if (cell.getColumnIndex() == 5) {
					cell.setCellValue(s.getVehicle().getModel());
				} else if (cell.getColumnIndex() == 6) {
					cell.setCellValue(s.getVehicle().getLicensePlate());
				} else if (cell.getColumnIndex() == 7) {
					cell.setCellValue("R$ " + String.format("%.2f", s.getVehicle().getPaidValue()));
				} else if (cell.getColumnIndex() == 8) {
					cell.setCellValue("R$ " + String.format("%.2f", totalExpense));
				} else if (cell.getColumnIndex() == 9) {
					cell.setCellValue("R$ " + String.format("%.2f", s.getFinalValue()));
				} else if (cell.getColumnIndex() == 10) {
					cell.setCellValue(PaymentType.toIntegerEnum(s.getPayment().getPaymentType()).getDescription());
				} else {
					cell.setCellValue("R$ " + String.format("%.2f", s.getProfit()));
				}
			}

			totalProfit += s.getProfit();
			j++;
		}

		row = sheet.createRow(6 + sales.getNumberOfElements());
		Cell c0 = row.createCell(0);
		c0.setCellValue("   Lucro Total   ");
		c0.setCellStyle(headerWithBlueColorStyle);

		Cell c1 = row.createCell(1);
		c1.setCellValue("N° de Veiculos");
		c1.setCellStyle(headerWithYellowColorStyle);

		Cell c2 = row.createCell(2);
		c2.setCellValue("   Ticket Medio   ");
		c2.setCellStyle(headerWithYellowColorStyle);

		row = sheet.createRow(7 + sales.getNumberOfElements());
		c0 = row.createCell(0);
		c0.setCellValue("R$ " + String.format("%.2f", totalProfit));
		c0.setCellStyle(style);

		c1 = row.createCell(1);
		c1.setCellValue(sales.getNumberOfElements());
		c1.setCellStyle(style);

		c2 = row.createCell(2);
		c2.setCellValue("R$ " + String.format("%.2f", totalProfit / sales.getNumberOfElements()));
		c2.setCellStyle(style);
		
		
		row = sheet.createRow(10 + sales.getNumberOfElements());
		Cell headerWithdrawCell = row.createCell(1);
		headerWithdrawCell.setCellValue("DESPESAS");
		headerWithdrawCell.setCellStyle(headerStyle);
		
		row = sheet.createRow(11 + sales.getNumberOfElements());
		Cell ce0 = row.createCell(0);
		ce0.setCellValue("Data");
		ce0.setCellStyle(headerWithYellowColorStyle);

		Cell ce1 = row.createCell(1);
		ce1.setCellValue("   Descrição   ");
		ce1.setCellStyle(headerWithYellowColorStyle);

		Cell ce2 = row.createCell(2);
		ce2.setCellValue("   Valor   ");
		ce2.setCellStyle(headerWithYellowColorStyle);
		
		Cell ce3 = row.createCell(3);
		ce3.setCellValue("   Banco   ");
		ce3.setCellStyle(headerWithYellowColorStyle);

		
		double totalWithdraws = 0.0; 
		int z = 12 + sales.getNumberOfElements();
		
		for(WithdrawDTO w: withdraws) {
			
			row = sheet.createRow(z);
			
			for(int i = 0; i<4; i++) {
				Cell cell = row.createCell(i);
				cell.setCellStyle(style);
				
				if(cell.getColumnIndex() == 0) {
					String date = DateTimeFormatter.ofPattern("dd/MM/yyyy").format(LocalDate.of(w.getDate().getYear(),
							w.getDate().getMonthValue(), w.getDate().getDayOfMonth()));

					cell.setCellValue(date);
				}
				else if(cell.getColumnIndex() == 1) {
					cell.setCellValue(w.getName());
				}
				else if (cell.getColumnIndex() == 2){
					cell.setCellValue("R$ " + String.format("%.2f", w.getValue()));
				}
				else {
					cell.setCellValue(Bank.toIntegerEnum(w.getBank()).getDescription());
					
					if(w.getBank() == 1) {
						cell.setCellStyle(redGroundStyle);
					}
					else {
						cell.setCellStyle(purpleGroundStyle);
					}
				}
			}
			
			totalWithdraws += w.getValue();
			z++;
		}
		
		row = sheet.createRow(14 + sales.getNumberOfElements() + withdraws.getNumberOfElements());
		Cell cel0 = row.createCell(0);
		cel0.setCellValue("   Despesas Totais   ");
		cel0.setCellStyle(headerWithGreenColorStyle);

		Cell cel1 = row.createCell(1);
		cel1.setCellValue("Lucro Total - Despesas	 Totais");
		cel1.setCellStyle(headerWithOrangeColorStyle);


		row = sheet.createRow(15 + sales.getNumberOfElements() + withdraws.getNumberOfElements());
		cel0 = row.createCell(0);
		cel0.setCellValue("R$ " + String.format("%.2f", totalWithdraws));
		cel0.setCellStyle(style);

		cel1 = row.createCell(1);
		cel1.setCellValue("R$ " + String.format("%.2f", totalProfit - totalWithdraws));
		
		if(totalProfit - totalWithdraws < 0) {
			cel1.setCellStyle(redStyle);
		}
		else if(totalProfit - totalWithdraws > 0) {
			cel1.setCellStyle(greenStyle);
		}
		else {
			cel1.setCellStyle(style);
		}
			
		//auto size columns
		for(int i=0; i<12; i++) {
			sheet.autoSizeColumn(i);
		}

		// writing in excel file
		try {
			FileOutputStream out = new FileOutputStream(path);
			workbook.write(out);
			out.close();
			workbook.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return path;
	}
}
