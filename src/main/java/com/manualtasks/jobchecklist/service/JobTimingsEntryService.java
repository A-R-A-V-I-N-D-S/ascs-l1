package com.manualtasks.jobchecklist.service;

import static com.manualtasks.jobchecklist.util.ClassDataUtils.*;

import java.text.ParseException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.manualtasks.jobchecklist.model.ChecklistTemplateData;
import com.manualtasks.jobchecklist.model.JobDetailsData;

@Component
public class JobTimingsEntryService {

	private static Logger logger = LoggerFactory.getLogger(JobTimingsEntryService.class);

	public void fillJobTimingsAndStatus(List<ChecklistTemplateData> jobTemplateDataList,
			List<JobDetailsData> jobDetailsDataList, String gvnOrdrDate) throws ParseException {

		try {
			fillTimingsForJobsInScan(jobTemplateDataList, jobDetailsDataList, gvnOrdrDate);
			fillNAForJobsNotInScan(jobTemplateDataList);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}

	}

	private void fillNAForJobsNotInScan(List<ChecklistTemplateData> jobTemplateDataList) {
		for (ChecklistTemplateData jobTemplateData : jobTemplateDataList) {
			if (jobTemplateData.getJobStatus().equals(""))
				jobTemplateData.setJobStatus(STATUS_NA);
		}
	}

	private void fillTimingsForJobsInScan(List<ChecklistTemplateData> jobTemplateDataList,
			List<JobDetailsData> jobDetailsDataList, String gvnOrdrDate) throws ParseException {

		for (JobDetailsData jobDetailsData : jobDetailsDataList) {
			if (!jobDetailsData.getOrderDate().equalsIgnoreCase("Order Date")
					&& DATE_FORMAT.parse(gvnOrdrDate).equals(DATE_FORMAT.parse(jobDetailsData.getOrderDate()))) {
				for (ChecklistTemplateData jobTemplateData : jobTemplateDataList) {
					if (jobDetailsData.getJobName().equals(jobTemplateData.getJobName())
							&& jobDetailsData.getFolderName().equals(jobTemplateData.getFolderName())) {
						if (!jobTemplateData.isJobDetailsFilled() && !jobDetailsData.isJobDetailsFilled()) {

							// Filling job start and end time
							jobTemplateData.setStartTime(jobDetailsData.getStartTime());
							jobTemplateData.setEndTime(jobDetailsData.getEndTime());

							// Condition for filling job status
							if (jobDetailsData.getJobStatus().equals("Ended OK")) {
								jobTemplateData.setJobStatus(STATUS_ENDED_OK);
							} else if (jobDetailsData.getJobStatus().equals(STATUS_EXECUTING)) {
								jobTemplateData.setJobStatus(STATUS_EXECUTING);
							} else if (jobDetailsData.getJobStatus().equals("Wait Condition")
									&& jobDetailsData.getStartTime().equals("")
									&& jobDetailsData.getStartTime().equals("")) {
								jobTemplateData.setJobStatus(STATUS_YET_TO_START);
							} else if (jobDetailsData.getJobStatus().equals("Wait Condition")
									&& !jobDetailsData.getStartTime().equals("")) {
								jobTemplateData.setJobStatus(STATUS_EXECUTING);
							} else if (jobDetailsData.getStartTime().equals("")
									&& jobDetailsData.getStartTime().equals("")
									&& jobDetailsData.getIsDeleted().equals("Checked")) {
								jobTemplateData.setJobStatus(STATUS_CANCELLED);
							} else if (jobDetailsData.getStartTime().equals("")
									&& jobDetailsData.getStartTime().equals("")
									&& jobDetailsData.getIsJobHeld().equals("Checked")) {
								jobTemplateData.setJobStatus(STATUS_HELD);
							}

							// To avoid duplicate timings entry
							jobTemplateData.setJobDetailsFilled(true);
							jobDetailsData.setJobDetailsFilled(true);
							break;
						}
					}
				}
			}
		}
	}

}
