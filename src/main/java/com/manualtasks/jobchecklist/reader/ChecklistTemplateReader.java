package com.manualtasks.jobchecklist.reader;

import static com.manualtasks.jobchecklist.util.ClassDataUtils.*;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.manualtasks.jobchecklist.model.ChecklistTemplateData;

@Component
public class ChecklistTemplateReader {

	private static Logger logger = LoggerFactory.getLogger(ChecklistTemplateReader.class);

	public List<ChecklistTemplateData> readSheet(XSSFSheet sheet) {

		logger.info("Job Checklist template reader - START");

		// Create a new List
		List<ChecklistTemplateData> templateData = new ArrayList<>();

		// Transform the row data to the defined data type
		for (Row row : sheet)
			templateData.add(createModelFromRow(row));

		logger.info("Job Checklist template reader - END");
		return templateData;

	}

	private static ChecklistTemplateData createModelFromRow(Row row) {

		DataFormatter formatter = new DataFormatter();

		ChecklistTemplateData checklistTemplateData = new ChecklistTemplateData();

		for (int i = 0; i < TOTAL_COL_IN_CHECKLIST_SHEET; i++) {
			switch (i) {
			case 0:
				checklistTemplateData.setSerialNo(formatter.formatCellValue(row.getCell(i)));
				break;
			case 1:
				checklistTemplateData.setType(formatter.formatCellValue(row.getCell(i)));
				break;
			case 2:
				checklistTemplateData.setJobName(formatter.formatCellValue(row.getCell(i)));
				break;
			case 3:
				checklistTemplateData.setFolderName(formatter.formatCellValue(row.getCell(i)));
				break;
			case 4:
				checklistTemplateData.setBusinessReportName(formatter.formatCellValue(row.getCell(i)));
				break;
			case 5:
				checklistTemplateData.setIsReportGenerated(formatter.formatCellValue(row.getCell(i)));
				break;
			case 6:
				checklistTemplateData.setCtmServer(formatter.formatCellValue(row.getCell(i)));
				break;
			case 7:
				checklistTemplateData.setSchedule(formatter.formatCellValue(row.getCell(i)));
				break;
			case 8:
				checklistTemplateData.setFrequency(formatter.formatCellValue(row.getCell(i)));
				break;
			case 9:
				checklistTemplateData.setShift(formatter.formatCellValue(row.getCell(i)));
				break;
			case 10:
				checklistTemplateData.setJobDependency(formatter.formatCellValue(row.getCell(i)));
				break;
			case 11:
				checklistTemplateData.setStartTime(formatter.formatCellValue(row.getCell(i)));
				break;
			case 12:
				checklistTemplateData.setEndTime(formatter.formatCellValue(row.getCell(i)));
				break;
			case 13:
				checklistTemplateData.setJobStatus(formatter.formatCellValue(row.getCell(i)));
				break;
			case 14:
				String isLogsValidatedValue = formatter.formatCellValue(row.getCell(i));
				if (!(isLogsValidatedValue.equalsIgnoreCase("y") || isLogsValidatedValue.equalsIgnoreCase("n")
						|| isLogsValidatedValue.equalsIgnoreCase("yes") || isLogsValidatedValue.equalsIgnoreCase("no")
						|| isLogsValidatedValue.equalsIgnoreCase("job status"))
						&& !isLogsValidatedValue.contains("Y/N")) {
					checklistTemplateData.setIsLogsValidated(null);
					break;
				}
				checklistTemplateData.setIsLogsValidated(isLogsValidatedValue);
				break;
			case 15:
				checklistTemplateData.setErrorDetails(formatter.formatCellValue(row.getCell(i)));
				break;
			case 16:
				checklistTemplateData.setResolution(formatter.formatCellValue(row.getCell(i)));
				break;
			case 17:
				checklistTemplateData.setApplication(formatter.formatCellValue(row.getCell(i)));
				break;
			case 18:
				checklistTemplateData.setRemarks(formatter.formatCellValue(row.getCell(i)));
				break;
			case 19:
				checklistTemplateData.setProcessingDate(formatter.formatCellValue(row.getCell(i)));
				break;
			default:
				break;
			}
			checklistTemplateData.setJobDetailsFilled(false);
		}
		return checklistTemplateData;
	}

}
