package com.manualtasks.jobchecklist.controller;

import static com.manualtasks.jobchecklist.util.ClassDataUtils.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.manualtasks.jobchecklist.service.JobTimingsEntryService;
import com.manualtasks.jobchecklist.model.ChecklistTemplateData;
import com.manualtasks.jobchecklist.model.JobDetailsData;
import com.manualtasks.jobchecklist.model.UserInputData;
import com.manualtasks.jobchecklist.reader.ChecklistTemplateReader;
import com.manualtasks.jobchecklist.reader.JobDetailsReader;
import com.manualtasks.jobchecklist.service.FileValidatorService;
import com.manualtasks.jobchecklist.service.JobLogsEntryService;
import com.manualtasks.jobchecklist.service.JschDataEntryService;
import com.manualtasks.jobchecklist.writer.ChecklistWriter;

@Controller
public class ApplicationController {

	private static Logger logger = LoggerFactory.getLogger(ApplicationController.class);

	@Autowired
	private JschDataEntryService dataEntryService;

	@Autowired
	private FileValidatorService validatorService;

	@Autowired
	private JobTimingsEntryService jobTimingsEntryService;

	@Autowired
	private JobLogsEntryService logsEntryService;

	@Autowired
	private ChecklistTemplateReader reader1;

	@Autowired
	private JobDetailsReader reader2;

	@Autowired
	private ChecklistWriter writer;

	@GetMapping("/login")
	public String loginPage() {
		return "LoginPage";
	}

	@GetMapping("/")
	public String homePage() {
		return "HomePage";
	}

	@PostMapping("/process-data")
	public void processData(@ModelAttribute UserInputData inputData, HttpServletResponse response)
			throws InvalidFormatException, IOException {

		logger.info("Input Data received from user is - {}", inputData.toString());

		XSSFWorkbook workbook = new XSSFWorkbook(inputData.getInputFile().getInputStream());

		XSSFSheet jobTemplateSheet = workbook.getSheet(CHECKLIST_SHEET_NAME);
		XSSFSheet jobDetailsSheet = workbook.getSheet(CTM_DETAILS_SHEET_NAME);

		List<ChecklistTemplateData> jobChecklistDataList = reader1.readSheet(jobTemplateSheet);
		List<JobDetailsData> jobDetailsData = reader2.readSheet(jobDetailsSheet);

//		try {
//			jobTimingsEntryService.fillJobTimingsAndStatus(jobChecklistDataList, jobDetailsData,
//					inputData.getOrderDate());
//		} catch (ParseException exc) {
//			logger.error("Parsing error occured for order date - {}", exc.getMessage());
//		}
//
//		if (validatorService.isSheetValid(jobTemplateSheet) && validatorService.isSheetValid(jobDetailsSheet)) {
//			Map<String, ArrayList<String>> listOfErrorsOfLogs = new HashMap<>();
//			StopWatch stopWatch = new StopWatch();
//			stopWatch.start();
//			if ((inputData.getShiftStartTime() == null && inputData.getShiftEndTime() == null)
//					|| (inputData.getShiftStartTime().equals("") && inputData.getShiftEndTime().equals(""))) {
//				CompletableFuture<Map<String, ArrayList<String>>> completableFutureList1 = dataEntryService
//						.validateLogsInServer(inputData.getShift(), BATCH_SERVERS_LIST[0],
//								inputData.isDayLightSavings());
//				CompletableFuture<Map<String, ArrayList<String>>> completableFutureList2 = dataEntryService
//						.validateLogsInServer(inputData.getShift(), BATCH_SERVERS_LIST[1],
//								inputData.isDayLightSavings());
//				CompletableFuture<Map<String, ArrayList<String>>> completableFutureList3 = dataEntryService
//						.validateLogsInServer(inputData.getShift(), BATCH_SERVERS_LIST[2],
//								inputData.isDayLightSavings());
//				CompletableFuture.allOf(completableFutureList1, completableFutureList2, completableFutureList3).join();
//				Map<String, ArrayList<String>> listOfErrorsInLogs1;
//				Map<String, ArrayList<String>> listOfErrorsInLogs2;
//				Map<String, ArrayList<String>> listOfErrorsInLogs3;
//				try {
//					listOfErrorsInLogs1 = completableFutureList1.get();
//					listOfErrorsInLogs2 = completableFutureList2.get();
//					listOfErrorsInLogs3 = completableFutureList3.get();
//					listOfErrorsOfLogs.putAll(listOfErrorsInLogs1);
//					listOfErrorsOfLogs.putAll(listOfErrorsInLogs2);
//					listOfErrorsOfLogs.putAll(listOfErrorsInLogs3);
//				} catch (InterruptedException | ExecutionException e) {
//					logger.error("Error occured while capturing the logs {}", e.getMessage());
//				}
//			} else {
//				logger.info("Entering into custom timings function.");
//				CompletableFuture<Map<String, ArrayList<String>>> completableFutureList1 = dataEntryService
//						.validateLogsInServer(inputData.getShift(), inputData.getShiftStartTime(),
//								inputData.getShiftEndTime(), BATCH_SERVERS_LIST[0]);
//				CompletableFuture<Map<String, ArrayList<String>>> completableFutureList2 = dataEntryService
//						.validateLogsInServer(inputData.getShift(), inputData.getShiftStartTime(),
//								inputData.getShiftEndTime(), BATCH_SERVERS_LIST[1]);
//				CompletableFuture<Map<String, ArrayList<String>>> completableFutureList3 = dataEntryService
//						.validateLogsInServer(inputData.getShift(), inputData.getShiftStartTime(),
//								inputData.getShiftEndTime(), BATCH_SERVERS_LIST[2]);
//				CompletableFuture.allOf(completableFutureList1, completableFutureList2, completableFutureList3).join();
//				Map<String, ArrayList<String>> listOfErrorsInLogs1;
//				Map<String, ArrayList<String>> listOfErrorsInLogs2;
//				Map<String, ArrayList<String>> listOfErrorsInLogs3;
//				try {
//					listOfErrorsInLogs1 = completableFutureList1.get();
//					listOfErrorsInLogs2 = completableFutureList2.get();
//					listOfErrorsInLogs3 = completableFutureList3.get();
//					listOfErrorsOfLogs.putAll(listOfErrorsInLogs1);
//					listOfErrorsOfLogs.putAll(listOfErrorsInLogs2);
//					listOfErrorsOfLogs.putAll(listOfErrorsInLogs3);
//				} catch (InterruptedException | ExecutionException e) {
//					logger.error("Error occured while capturing the logs {}", e.getMessage());
//				}
//			}
//
//			if (listOfErrorsOfLogs != null)
//				logsEntryService.fillJobErrorsAndValidationStatus(listOfErrorsOfLogs, jobChecklistDataList,
//						inputData.getShift());
//
//			stopWatch.stop();
//			logger.info("Caputred logs in the specified timeframe in {} minute(s).",
//					stopWatch.getTotalTimeSeconds() / 60);
//		} else {
//
//		}
		
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment;filename=sample.xlsx");

		XSSFWorkbook outputWorkbook = new XSSFWorkbook();
		XSSFSheet checklistSheet = outputWorkbook.createSheet(CHECKLIST_SHEET_NAME);
		XSSFRow row = checklistSheet.createRow(0);
		XSSFCell cell = row.createCell(0);
		cell.setCellValue(999999);
		
//		writer.writeChecklist(checklistSheet, jobChecklistDataList);
//		writer.setChecklistExcelDesign(outputWorkbook);

		ServletOutputStream outputStream = response.getOutputStream();
		outputWorkbook.write(outputStream);
		outputWorkbook.close();

//		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Excel.xlsx")
//				.contentType(MediaType.APPLICATION_OCTET_STREAM).body(outputStream.toByteArray());
	}

	/*
	 * @GetMapping("/page-not-found") public String pageNotFound() { return
	 * "NotFoundPage"; }
	 * 
	 * @RequestMapping(value = "/**") public String redirect() { return
	 * "redirect:/page-not-found"; }
	 */

}
