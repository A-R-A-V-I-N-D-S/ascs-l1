package com.manualtasks.jobchecklist.controller;

import static com.manualtasks.jobchecklist.util.ClassDataUtils.BATCH_SERVERS_LIST;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.manualtasks.jobchecklist.models.UserData;
import com.manualtasks.jobchecklist.models.UserInputData;
import com.manualtasks.jobchecklist.service.JschDataEntryService;

@Controller
public class ApplicationController {

	private static Logger logger = LoggerFactory.getLogger(ApplicationController.class);

	@Autowired
	private JschDataEntryService dataEntryService;

	@GetMapping("/login")
	public String loginPage(Model model) {
		return "LoginPage";
	}

	@GetMapping("/")
	public String homePage() {
		return "HomePage";
	}

	@PostMapping("/process-data")
	public ResponseEntity<?> processData(@ModelAttribute UserInputData inputData)
			throws InvalidFormatException, IOException {
		logger.info(inputData.toString());
		Workbook workbook = WorkbookFactory.create(inputData.getInputFile().getInputStream());
		Sheet jobDetailsSheet = workbook.getSheet("CTM Details");
		logger.info(jobDetailsSheet.getSheetName());
		Map<String, ArrayList<String>> listOfErrorsOfLogs = new HashMap<>();
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		if ((inputData.getShiftStartTime() == null && inputData.getShiftEndTime() == null)
				|| (inputData.getShiftStartTime().equals("") && inputData.getShiftEndTime().equals(""))) {
			CompletableFuture<Map<String, ArrayList<String>>> completableFutureList1 = dataEntryService
					.validateLogsInServer(inputData.getShift(), BATCH_SERVERS_LIST[0], inputData.isDayLightSavings());
			CompletableFuture<Map<String, ArrayList<String>>> completableFutureList2 = dataEntryService
					.validateLogsInServer(inputData.getShift(), BATCH_SERVERS_LIST[1], inputData.isDayLightSavings());
			CompletableFuture<Map<String, ArrayList<String>>> completableFutureList3 = dataEntryService
					.validateLogsInServer(inputData.getShift(), BATCH_SERVERS_LIST[2], inputData.isDayLightSavings());
			CompletableFuture.allOf(completableFutureList1, completableFutureList2, completableFutureList3).join();
			Map<String, ArrayList<String>> listOfErrorsInLogs1;
			Map<String, ArrayList<String>> listOfErrorsInLogs2;
			Map<String, ArrayList<String>> listOfErrorsInLogs3;
			try {
				listOfErrorsInLogs1 = completableFutureList1.get();
				listOfErrorsInLogs2 = completableFutureList2.get();
				listOfErrorsInLogs3 = completableFutureList3.get();
				listOfErrorsOfLogs.putAll(listOfErrorsInLogs1);
				listOfErrorsOfLogs.putAll(listOfErrorsInLogs2);
				listOfErrorsOfLogs.putAll(listOfErrorsInLogs3);
			} catch (InterruptedException | ExecutionException e) {
				logger.error("Error occured while capturing the logs {}", e.getMessage());
			}
		} else {
			logger.info("Entering into custom timings function.");
			CompletableFuture<Map<String, ArrayList<String>>> completableFutureList1 = dataEntryService
					.validateLogsInServer(inputData.getShift(), inputData.getShiftStartTime(),
							inputData.getShiftEndTime(), BATCH_SERVERS_LIST[0]);
			CompletableFuture<Map<String, ArrayList<String>>> completableFutureList2 = dataEntryService
					.validateLogsInServer(inputData.getShift(), inputData.getShiftStartTime(),
							inputData.getShiftEndTime(), BATCH_SERVERS_LIST[1]);
			CompletableFuture<Map<String, ArrayList<String>>> completableFutureList3 = dataEntryService
					.validateLogsInServer(inputData.getShift(), inputData.getShiftStartTime(),
							inputData.getShiftEndTime(), BATCH_SERVERS_LIST[2]);
			CompletableFuture.allOf(completableFutureList1, completableFutureList2, completableFutureList3).join();
			Map<String, ArrayList<String>> listOfErrorsInLogs1;
			Map<String, ArrayList<String>> listOfErrorsInLogs2;
			Map<String, ArrayList<String>> listOfErrorsInLogs3;
			try {
				listOfErrorsInLogs1 = completableFutureList1.get();
				listOfErrorsInLogs2 = completableFutureList2.get();
				listOfErrorsInLogs3 = completableFutureList3.get();
				listOfErrorsOfLogs.putAll(listOfErrorsInLogs1);
				listOfErrorsOfLogs.putAll(listOfErrorsInLogs2);
				listOfErrorsOfLogs.putAll(listOfErrorsInLogs3);
			} catch (InterruptedException | ExecutionException e) {
				logger.error("Error occured while capturing the logs {}", e.getMessage());
			}
		}
		stopWatch.stop();
		logger.info("Caputred logs in the specified timeframe in {} minute(s).", stopWatch.getTotalTimeSeconds() / 60);
		return new ResponseEntity<>(HttpStatus.OK);
	}
//	public ResponseEntity<?> processData(@RequestParam("orderDate") String orderDate,
//			@RequestParam("shift") String shift, @RequestParam("daylightSavings") boolean daylightSavings,
//			@RequestParam("shiftStartTime") String shiftStartTime, @RequestParam("shiftEndTime") String shiftEndTime,
//			@RequestParam("inputFile") MultipartFile inputFile) throws InvalidFormatException, IOException {
//		Map<String, ArrayList<String>> listOfErrorsOfLogs = new HashMap<>();
//		logger.info(shift);
//		Workbook workbook = WorkbookFactory.create(inputFile.getInputStream());
//		String sheetName = workbook.getSheetName(0);
//		logger.info(sheetName);
//		StopWatch stopWatch = new StopWatch();
//		stopWatch.start();
//		if (shiftStartTime == null && shiftEndTime == null) {
//			CompletableFuture<Map<String, ArrayList<String>>> completableFutureList1 = dataEntryService
//					.validateLogsInServer(shift, BATCH_SERVERS_LIST[0],daylightSavings);
//			CompletableFuture<Map<String, ArrayList<String>>> completableFutureList2 = dataEntryService
//					.validateLogsInServer(shift, BATCH_SERVERS_LIST[1],daylightSavings);
//			CompletableFuture<Map<String, ArrayList<String>>> completableFutureList3 = dataEntryService
//					.validateLogsInServer(shift, BATCH_SERVERS_LIST[2],daylightSavings);
//			CompletableFuture.allOf(completableFutureList1, completableFutureList2, completableFutureList3).join();
//			Map<String, ArrayList<String>> listOfErrorsInLogs1;
//			Map<String, ArrayList<String>> listOfErrorsInLogs2;
//			Map<String, ArrayList<String>> listOfErrorsInLogs3;
//			try {
//				listOfErrorsInLogs1 = completableFutureList1.get();
//				listOfErrorsInLogs2 = completableFutureList2.get();
//				listOfErrorsInLogs3 = completableFutureList3.get();
//				listOfErrorsOfLogs.putAll(listOfErrorsInLogs1);
//				listOfErrorsOfLogs.putAll(listOfErrorsInLogs2);
//				listOfErrorsOfLogs.putAll(listOfErrorsInLogs3);
//			} catch (InterruptedException | ExecutionException e) {
//				logger.error("Error occured while capturing the logs {}", e.getMessage());
//			}
//		} else {
//			CompletableFuture<Map<String, ArrayList<String>>> completableFutureList1 = dataEntryService
//					.validateLogsInServer(shift, shiftStartTime, shiftEndTime, BATCH_SERVERS_LIST[0]);
//			CompletableFuture<Map<String, ArrayList<String>>> completableFutureList2 = dataEntryService
//					.validateLogsInServer(shift, shiftStartTime, shiftEndTime, BATCH_SERVERS_LIST[1]);
//			CompletableFuture<Map<String, ArrayList<String>>> completableFutureList3 = dataEntryService
//					.validateLogsInServer(shift, shiftStartTime, shiftEndTime, BATCH_SERVERS_LIST[2]);
//			CompletableFuture.allOf(completableFutureList1, completableFutureList2, completableFutureList3).join();
//			Map<String, ArrayList<String>> listOfErrorsInLogs1;
//			Map<String, ArrayList<String>> listOfErrorsInLogs2;
//			Map<String, ArrayList<String>> listOfErrorsInLogs3;
//			try {
//				listOfErrorsInLogs1 = completableFutureList1.get();
//				listOfErrorsInLogs2 = completableFutureList2.get();
//				listOfErrorsInLogs3 = completableFutureList3.get();
//				listOfErrorsOfLogs.putAll(listOfErrorsInLogs1);
//				listOfErrorsOfLogs.putAll(listOfErrorsInLogs2);
//				listOfErrorsOfLogs.putAll(listOfErrorsInLogs3);
//			} catch (InterruptedException | ExecutionException e) {
//				logger.error("Error occured while capturing the logs {}", e.getMessage());
//			}
//		}
//		stopWatch.stop();
//		logger.info("Caputred logs for the specified timeframe in {} minute(s).", stopWatch.getTotalTimeSeconds() / 60);
//		return new ResponseEntity<>(HttpStatus.OK);
//	}

	/*
	 * @GetMapping("/page-not-found") public String pageNotFound() { return
	 * "NotFoundPage"; }
	 * 
	 * @RequestMapping(value = "/**") public String redirect() { return
	 * "redirect:/page-not-found"; }
	 */

}
