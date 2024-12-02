package com.manualtasks.jobchecklist.service;

import static com.manualtasks.jobchecklist.util.ClassDataUtils.ASCSSBO_LOGS_LOCATIONS_002;
import static com.manualtasks.jobchecklist.util.ClassDataUtils.ASCSSBO_LOGS_LOCATIONS_300_301;
import static com.manualtasks.jobchecklist.util.ClassDataUtils.LOG_NAME_START_INDEX1;
import static com.manualtasks.jobchecklist.util.ClassDataUtils.LOG_NAME_START_INDEX2;
import static com.manualtasks.jobchecklist.util.ClassDataUtils.EASTERN_TIME_ZONE;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.jcraft.jsch.ChannelSftp;
import com.manualtasks.jobchecklist.config.ApplicationConfig;
import com.manualtasks.jobchecklist.model.UserInputData;

@Service
public class JschDataEntryService {

	@Value("${batch.server.username}")
	private String username;

	@Value("${batch.server.password}")
	private String password;

	@Autowired
	private ApplicationConfig applicationConfig;

	@Autowired
	private LogsReaderService logsReaderService;

	private static Logger logger = LoggerFactory.getLogger(JschDataEntryService.class);

	public String putData(UserInputData inputData) {
		logger.info(inputData.toString());
		return "Success";
	}

	@Async("asyncTaskExecutor")
	public CompletableFuture<Map<String, ArrayList<String>>> validateLogsInServer(String shift, String batchServer,
			boolean daylightSavings) {
		Map<String, ArrayList<String>> listOfErrorsOfLogs = new HashMap<>();
		int logNameStartIndexNum;
		try {

			logger.info("Validating the Spring Batch logs in {} for errors - START", batchServer);

//			FileWriter errorLogFile = new FileWriter(errorValidationLogPath + batchServer.toUpperCase() + ".txt");
//			ChannelSftp sftpChannel = applicationContext.getBean(ChannelSftp.class, batchServer, username, password);
			ChannelSftp sftpChannel = applicationConfig.connectSftp(batchServer, username, password);
			ArrayList<String> listOfDatesForLogsCheck = logsReaderService.findLogOccuringDatesByShift(shift);

			String shiftStartTime = getShiftTimings(shift.toUpperCase(), daylightSavings).get(0);
			String shiftEndTime = getShiftTimings(shift.toUpperCase(), daylightSavings).get(1);

			logger.debug("Checking the logs from \"" + shiftStartTime + "\" to \"" + shiftEndTime + "\"");

			if (batchServer.contains("dc04")) {
				logNameStartIndexNum = LOG_NAME_START_INDEX1;
				for (String logPath : ASCSSBO_LOGS_LOCATIONS_300_301) {
					try {
						logsReaderService.readLogFilesForErrors(sftpChannel, logPath, listOfDatesForLogsCheck,
								listOfErrorsOfLogs, logNameStartIndexNum, shiftStartTime, shiftEndTime);
						sftpChannel.cd(logPath);
					} catch (Exception exc) {
						if (exc.toString().contains("2: No such file")) {
							logger.warn("{} --> {} is not applicable for logs", sftpChannel.getSession().getHost(),
									logPath);
							continue;
						} else {
							logger.error(exc.getMessage());
							exc.printStackTrace();
						}
					}
				}
			} else {
				logNameStartIndexNum = LOG_NAME_START_INDEX2;
				for (String logPath : ASCSSBO_LOGS_LOCATIONS_002) {
					try {
						logsReaderService.readLogFilesForErrors(sftpChannel, logPath, listOfDatesForLogsCheck,
								listOfErrorsOfLogs, logNameStartIndexNum, shiftStartTime, shiftEndTime);
						sftpChannel.cd(logPath);
					} catch (Exception exc) {
						if (exc.toString().contains("2: No such file")) {
							logger.warn("{} --> {} is not applicable for logs", sftpChannel.getSession().getHost(),
									logPath);
							continue;
						} else {
							logger.error(exc.getMessage());
							exc.printStackTrace();
						}
					}
				}
			}

			applicationConfig.disconnectSftp(sftpChannel);
			logger.info("Validating the Spring Batch logs in {} for errors - END", batchServer);

		} catch (Exception e) {
			if (e.toString().contains("Auth fail for methods 'publickey,password,keyboard-interactive'")) {
				logger.error(e.getMessage());
				System.err
						.println("Change the password in the application.properties file to avoid the domain ID lock");
				return null;
			} else {
				logger.error(e.getMessage());
				e.printStackTrace();
			}
		}
		return CompletableFuture.completedFuture(listOfErrorsOfLogs);
	}

	public CompletableFuture<Map<String, ArrayList<String>>> validateLogsInServer(String shift, String shiftStartTime,
			String shiftEndTime, String batchServer) {
		Map<String, ArrayList<String>> listOfErrorsOfLogs = new HashMap<>();

		SimpleDateFormat dateFormatter2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		SimpleDateFormat dateFormatter4 = new SimpleDateFormat("MM/dd/yy HH:mm");
		int logNameStartIndexNum;
		try {

			logger.info("Validating the Spring Batch logs in {} for errors - START", batchServer);

//			FileWriter errorLogFile = new FileWriter(errorValidationLogPath + batchServer.toUpperCase() + ".txt");
//			ChannelSftp sftpChannel = applicationContext.getBean(ChannelSftp.class, batchServer, username, password);
			ChannelSftp sftpChannel = applicationConfig.connectSftp(batchServer, username, password);

			dateFormatter2.setTimeZone(EASTERN_TIME_ZONE);

			dateFormatter4.setTimeZone(EASTERN_TIME_ZONE);

			final String formattedShiftStartTime = dateFormatter2.format(dateFormatter4.parse(shiftStartTime));
			final String formattedShiftEndTime = dateFormatter2.format(dateFormatter4.parse(shiftEndTime));

			ArrayList<String> listOfDatesForLogsCheck = logsReaderService.findLogOccuringDatesByShift(shift,
					formattedShiftStartTime, formattedShiftEndTime);

			logger.debug(
					"Checking the logs from \"" + formattedShiftStartTime + "\" to \"" + formattedShiftEndTime + "\"");

			if (batchServer.contains("dc04")) {
				logNameStartIndexNum = LOG_NAME_START_INDEX1;

				for (String logPath : ASCSSBO_LOGS_LOCATIONS_300_301) {
					try {
						logsReaderService.readLogFilesForErrors(sftpChannel, logPath, listOfDatesForLogsCheck,
								listOfErrorsOfLogs, logNameStartIndexNum, formattedShiftStartTime,
								formattedShiftEndTime);
						sftpChannel.cd(logPath);
					} catch (Exception exc) {
						if (exc.toString().contains("2: No such file")) {
							logger.warn("{} --> {} is not applicable for logs", sftpChannel.getSession().getHost(),
									logPath);
							continue;
						} else {
							logger.error(exc.getMessage());
							exc.printStackTrace();
						}
					}
				}
			} else {
				logNameStartIndexNum = LOG_NAME_START_INDEX2;
				for (String logPath : ASCSSBO_LOGS_LOCATIONS_002) {
					try {
						logsReaderService.readLogFilesForErrors(sftpChannel, logPath, listOfDatesForLogsCheck,
								listOfErrorsOfLogs, logNameStartIndexNum, formattedShiftStartTime,
								formattedShiftEndTime);
						sftpChannel.cd(logPath);
					} catch (Exception exc) {
						if (exc.toString().contains("2: No such file")) {
							logger.warn("{} --> {} is not applicable for logs", sftpChannel.getSession().getHost(),
									logPath);
							continue;
						} else {
							logger.error(exc.getMessage());
							exc.printStackTrace();
						}
					}
				}
			}

			applicationConfig.disconnectSftp(sftpChannel);
			logger.info("Validating the Spring Batch logs in {} for errors - END", batchServer);

		} catch (Exception e) {
			if (e.toString().contains("Auth fail for methods 'publickey,password,keyboard-interactive'")) {
				System.err
						.println("Change the password in the application.properties file to avoid the domain ID lock");
				return null;
			} else {
				logger.error(e.getMessage());
				e.printStackTrace();
			}
		}
		return CompletableFuture.completedFuture(listOfErrorsOfLogs);
	}

	private List<String> getShiftTimings(String shift, boolean daylightSavings) {
		List<String> shiftTimings = new ArrayList<>();
		String shiftStartTime = "", shiftEndTime = "";

		SimpleDateFormat dateFormatter5 = new SimpleDateFormat("yyyy-MM-dd");

		dateFormatter5.setTimeZone(EASTERN_TIME_ZONE);

		Calendar calendar = new GregorianCalendar();
		calendar.setTimeZone(EASTERN_TIME_ZONE);

		if (daylightSavings) {
			switch (shift) {
			case "S1":
				shiftEndTime = dateFormatter5.format(calendar.getTime()) + " 04:40";
				calendar.add(Calendar.DATE, -1);
				shiftStartTime = dateFormatter5.format(calendar.getTime()) + " 19:20";
				break;
			case "S2":
				shiftStartTime = dateFormatter5.format(calendar.getTime()) + " 03:20";
				shiftEndTime = dateFormatter5.format(calendar.getTime()) + " 12:40";
				break;
			case "S3":
				shiftStartTime = dateFormatter5.format(calendar.getTime()) + " 10:50";
				shiftEndTime = dateFormatter5.format(calendar.getTime()) + " 20:10";
				break;
			default:
				break;
			}
		} else {
			switch (shift) {
			case "S1":
				shiftEndTime = dateFormatter5.format(calendar.getTime()) + " 05:40";
				calendar.add(Calendar.DATE, -1);
				shiftStartTime = dateFormatter5.format(calendar.getTime()) + " 20:20";
				break;
			case "S2":
				shiftStartTime = dateFormatter5.format(calendar.getTime()) + " 04:20";
				shiftEndTime = dateFormatter5.format(calendar.getTime()) + " 13:40";
				break;
			case "S3":
				shiftStartTime = dateFormatter5.format(calendar.getTime()) + " 11:50";
				shiftEndTime = dateFormatter5.format(calendar.getTime()) + " 21:10";
				break;
			default:
				break;
			}
		}
		shiftTimings.add(shiftStartTime);
		shiftTimings.add(shiftEndTime);
		return shiftTimings;
	}

}
