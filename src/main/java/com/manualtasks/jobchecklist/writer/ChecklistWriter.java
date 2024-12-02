package com.manualtasks.jobchecklist.writer;

import static com.manualtasks.jobchecklist.util.ClassDataUtils.*;

import java.util.List;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.manualtasks.jobchecklist.model.ChecklistTemplateData;

@Component
public class ChecklistWriter {

	private static Logger logger = LoggerFactory.getLogger(ChecklistWriter.class);

	public void writeChecklist(XSSFSheet sheet, List<ChecklistTemplateData> templateDataList) {

		logger.info("Job checklist writer - START");

		// Writing rows data
		for (int i = 0; i < templateDataList.size(); i++) {
			XSSFRow row = sheet.createRow(i);
			for (int j = 0; j < TOTAL_COL_IN_CHECKLIST_SHEET; j++) {
				switch (j) {
				case 0:
					XSSFCell serialNoCell = row.createCell(j);
					// Only for the first column in Header row
					if (i == 0) {
						serialNoCell.setCellValue(templateDataList.get(i).getSerialNo());
					} else {
						serialNoCell.setCellType(CellType.NUMERIC);
						serialNoCell.setCellValue(Integer.parseInt(templateDataList.get(i).getSerialNo()));
					}
					break;
				case 1:
					row.createCell(j).setCellValue(templateDataList.get(i).getType());
					break;
				case 2:
					row.createCell(j).setCellValue(templateDataList.get(i).getJobName());
					break;
				case 3:
					row.createCell(j).setCellValue(templateDataList.get(i).getFolderName());
					break;
				case 4:
					row.createCell(j).setCellValue(templateDataList.get(i).getBusinessReportName());
					break;
				case 5:
					row.createCell(j).setCellValue(templateDataList.get(i).getIsReportGenerated());
					break;
				case 6:
					row.createCell(j).setCellValue(templateDataList.get(i).getCtmServer());
					break;
				case 7:
					row.createCell(j).setCellValue(templateDataList.get(i).getSchedule());
					break;
				case 8:
					row.createCell(j).setCellValue(templateDataList.get(i).getFrequency());
					break;
				case 9:
					row.createCell(j).setCellValue(templateDataList.get(i).getShift());
					break;
				case 10:
					row.createCell(j).setCellValue(templateDataList.get(i).getJobDependency());
					break;
				case 11:
					row.createCell(j).setCellValue(templateDataList.get(i).getStartTime());
					break;
				case 12:
					row.createCell(j).setCellValue(templateDataList.get(i).getEndTime());
					break;
				case 13:
					row.createCell(j).setCellValue(templateDataList.get(i).getJobStatus());
					break;
				case 14:
					row.createCell(j).setCellValue(templateDataList.get(i).getIsLogsValidated());
					break;
				case 15:
					row.createCell(j).setCellValue(templateDataList.get(i).getErrorDetails());
					break;
				case 16:
					row.createCell(j).setCellValue(templateDataList.get(i).getResolution());
					break;
				case 17:
					row.createCell(j).setCellValue(templateDataList.get(i).getApplication());
					break;
				case 18:
					row.createCell(j).setCellValue(templateDataList.get(i).getRemarks());
					break;
				case 19:
					row.createCell(j).setCellValue(templateDataList.get(i).getProcessingDate());
					break;
				default:
					break;
				}
			}
//			System.out.println(templateDataList.get(i).toString());
		}

		logger.info("Job checklist writer - END");

	}

	public void setChecklistExcelDesign(XSSFWorkbook outputWorkbook) {
		XSSFSheet sheet = outputWorkbook.getSheet(CHECKLIST_SHEET_NAME);

		adjustColumnWidth(sheet);

		// All rows Style
		XSSFCellStyle rowCellStyle = outputWorkbook.createCellStyle();
		rowCellStyle.setBorderLeft(BorderStyle.THIN);
		rowCellStyle.setBorderRight(BorderStyle.THIN);
		rowCellStyle.setBorderTop(BorderStyle.THIN);
		rowCellStyle.setBorderBottom(BorderStyle.THIN);

		for (Row row : sheet)
			setRowsStyle(row, rowCellStyle);

		// Setting wrap text for some cells
		XSSFCellStyle wrapCellStyle = outputWorkbook.createCellStyle();
		wrapCellStyle.setBorderLeft(BorderStyle.THIN);
		wrapCellStyle.setBorderRight(BorderStyle.THIN);
		wrapCellStyle.setBorderTop(BorderStyle.THIN);
		wrapCellStyle.setBorderBottom(BorderStyle.THIN);
		wrapCellStyle.setWrapText(true);

		for (Row row : sheet) {
			row.getCell(10).setCellStyle(wrapCellStyle);
			row.getCell(15).setCellStyle(wrapCellStyle);
		}

		// Header row color and font
		XSSFFont font = outputWorkbook.createFont();
		font.setBold(true);

		XSSFCellStyle headerCellStyle = outputWorkbook.createCellStyle();
		headerCellStyle.setFillForegroundColor(new XSSFColor(new java.awt.Color(180, 198, 231)));
		headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		headerCellStyle.setBorderLeft(BorderStyle.THIN);
		headerCellStyle.setBorderRight(BorderStyle.THIN);
		headerCellStyle.setBorderTop(BorderStyle.THIN);
		headerCellStyle.setBorderBottom(BorderStyle.THIN);
		headerCellStyle.setFont(font);
		headerCellStyle.setWrapText(true);

		setRowsStyle(sheet.getRow(0), headerCellStyle);

		sheet.createFreezePane(0, 1);
		sheet.setAutoFilter(new CellRangeAddress(0, sheet.getLastRowNum(), 0, TOTAL_COL_IN_CHECKLIST_SHEET - 1));

	}

	private static void setRowsStyle(Row row, XSSFCellStyle rowCellStyle) {
		for (Cell cell : row) {
			cell.setCellStyle(rowCellStyle);
		}
	}

	private static void adjustColumnWidth(XSSFSheet sheet) {
		sheet.setColumnWidth(2, (70 * 256) + 200);
		sheet.setColumnWidth(3, (70 * 256) + 200);
		sheet.setColumnWidth(4, (50 * 256) + 200);
		sheet.setColumnWidth(5, (26 * 256) + 200);
		sheet.setColumnWidth(6, (14 * 256) + 200);
		sheet.setColumnWidth(7, (24 * 256) + 200);
		sheet.setColumnWidth(8, (35 * 256) + 200);
		sheet.setColumnWidth(9, (8 * 256) + 200);
		sheet.setColumnWidth(10, (70 * 256) + 200);
		sheet.setColumnWidth(11, (19 * 256) + 200);
		sheet.setColumnWidth(12, (19 * 256) + 200);
		sheet.setColumnWidth(13, (9 * 256) + 200);
		sheet.setColumnWidth(14, (15 * 256) + 200);
		sheet.setColumnWidth(15, (150 * 256) + 200);
		sheet.setColumnWidth(16, (40 * 256) + 200);
		sheet.setColumnWidth(17, (40 * 256) + 200);
		sheet.setColumnWidth(18, (40 * 256) + 200);
		sheet.setColumnWidth(19, (19 * 256) + 200);
	}

}
