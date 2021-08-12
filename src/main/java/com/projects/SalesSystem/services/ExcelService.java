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
import com.projects.SalesSystem.entities.enums.PaymentType;

@Service
public class ExcelService {

	public File createFile(Page<SaleDTO> sales, LocalDate startDate, LocalDate endDate) {
		File path = new File("C:\\temp\\ws - sts\\SalesSystem\\RelatórioDeVendas.xlsx");

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

		Font font = workbook.createFont();
		font.setBold(true);
		headerStyle.setFont(font);
		headerWithYellowColorStyle.setFont(font);
		headerWithBlueColorStyle.setFont(font);

		// create cell style
		CellStyle style = workbook.createCellStyle();
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		style.setLocked(true);
		style.setBorderBottom(BorderStyle.MEDIUM);
		style.setBorderLeft(BorderStyle.MEDIUM);
		style.setBorderRight(BorderStyle.MEDIUM);
		style.setBorderTop(BorderStyle.MEDIUM);

		// create spreadsheet
		XSSFSheet sheet = workbook.createSheet(date1 + " a " + date2);
		sheet.enableLocking();

		// create row
		XSSFRow row;

		// create headers
		row = sheet.createRow(0);
		Cell headerCell = row.createCell(4);
		headerCell.setCellValue("RELATORIO DE VENDAS");
		headerCell.setCellStyle(headerStyle);
		sheet.autoSizeColumn(4);

		row = sheet.createRow(2);

		Cell cell0 = row.createCell(0);
		cell0.setCellValue("Data");
		cell0.setCellStyle(headerWithYellowColorStyle);

		Cell cell1 = row.createCell(1);
		cell1.setCellValue("   Nome do Cliente   ");
		cell1.setCellStyle(headerWithYellowColorStyle);

		Cell cell2 = row.createCell(2);
		cell2.setCellValue("Modelo do Veiculo");
		cell2.setCellStyle(headerWithYellowColorStyle);

		Cell cell3 = row.createCell(3);
		cell3.setCellValue("Placa do Veiculo");
		cell3.setCellStyle(headerWithYellowColorStyle);
		sheet.autoSizeColumn(3);

		Cell cell4 = row.createCell(4);
		cell4.setCellValue("   Valor Pago   ");
		cell4.setCellStyle(headerWithYellowColorStyle);

		Cell cell5 = row.createCell(5);
		cell5.setCellValue("Despesas Totais");
		cell5.setCellStyle(headerWithYellowColorStyle);
		sheet.autoSizeColumn(5);


		Cell cell6 = row.createCell(6);
		cell6.setCellValue("   Valor Venda   ");
		cell6.setCellStyle(headerWithYellowColorStyle);
		sheet.autoSizeColumn(6);

		Cell cell7 = row.createCell(7);
		cell7.setCellValue("Forma de pagamento");
		cell7.setCellStyle(headerWithYellowColorStyle);
		sheet.autoSizeColumn(7);

		Cell cell8 = row.createCell(8);
		cell8.setCellValue("       Lucro       ");
		cell8.setCellStyle(headerWithYellowColorStyle);
		sheet.autoSizeColumn(8);
		;

		// create cells
		double totalProfit = 0.0;

		int j = 3;

		for (SaleDTO s : sales) {

			double totalExpense = 0.0;
			for (ExpenseDTO exp : s.getVehicle().getExpenses()) {
				totalExpense += exp.getValue();
			}

			row = sheet.createRow(j);

			for (int i = 0; i < 9; i++) {
				Cell cell = row.createCell(i);
				cell.setCellStyle(style);

				if (cell.getColumnIndex() == 0) {

					String date = DateTimeFormatter.ofPattern("dd/MM/yyyy").format(LocalDate.of(s.getDate().getYear(),
							s.getDate().getMonthValue(), s.getDate().getDayOfMonth()));

					cell.setCellValue(date);
				} else if (cell.getColumnIndex() == 1) {
					cell.setCellValue(s.getClient().getName());
				} else if (cell.getColumnIndex() == 2) {
					cell.setCellValue(s.getVehicle().getModel());
				} else if (cell.getColumnIndex() == 3) {
					cell.setCellValue(s.getVehicle().getLicensePlate());
				} else if (cell.getColumnIndex() == 4) {
					cell.setCellValue("R$ " + String.format("%.2f", s.getVehicle().getPaidValue()));
				} else if (cell.getColumnIndex() == 5) {
					cell.setCellValue("R$ " + String.format("%.2f", totalExpense));
				} else if (cell.getColumnIndex() == 6) {
					cell.setCellValue("R$ " + String.format("%.2f", s.getFinalValue()));
				} else if (cell.getColumnIndex() == 7) {
					cell.setCellValue(PaymentType.toIntegerEnum(s.getPayment().getPaymentType()).getDescription());
				} else {
					cell.setCellValue("R$ " + String.format("%.2f", s.getProfit()));
				}
			}

			totalProfit += s.getProfit();
			j++;
		}

		row = sheet.createRow(5 + sales.getNumberOfElements());
		Cell c0 = row.createCell(0);
		c0.setCellValue("   Lucro Total   ");
		c0.setCellStyle(headerWithBlueColorStyle);
		sheet.autoSizeColumn(0);

		Cell c1 = row.createCell(1);
		c1.setCellValue("N° de Veiculos");
		c1.setCellStyle(headerWithYellowColorStyle);
		sheet.autoSizeColumn(1);

		Cell c2 = row.createCell(2);
		c2.setCellValue("   Ticket Medio   ");
		c2.setCellStyle(headerWithYellowColorStyle);
		sheet.autoSizeColumn(2);

		row = sheet.createRow(6 + sales.getNumberOfElements());
		Cell ce0 = row.createCell(0);
		ce0.setCellValue("R$ " + String.format("%.2f", totalProfit));
		ce0.setCellStyle(style);

		Cell ce1 = row.createCell(1);
		ce1.setCellValue(sales.getNumberOfElements());
		ce1.setCellStyle(style);

		Cell ce2 = row.createCell(2);
		ce2.setCellValue("R$ " + String.format("%.2f", totalProfit / sales.getNumberOfElements()));
		ce2.setCellStyle(style);

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
