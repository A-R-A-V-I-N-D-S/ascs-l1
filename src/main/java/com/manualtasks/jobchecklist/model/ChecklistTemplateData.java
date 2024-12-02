package com.manualtasks.jobchecklist.model;

public class ChecklistTemplateData {

	private String serialNo;

	private String type;

	private String jobName;

	private String folderName;

	private String businessReportName;

	private String isReportGenerated;

	private String ctmServer;

	private String schedule;

	private String frequency;

	private String shift;

	private String jobDependency;

	private String startTime;

	private String endTime;

	private String jobStatus;

	private String isLogsValidated;

	private String errorDetails;

	private String resolution;

	private String application;

	private String remarks;

	private String processingDate;

	private boolean isJobDetailsFilled;

	public ChecklistTemplateData() {

	}

	public ChecklistTemplateData(String serialNo, String type, String jobName, String folderName,
			String businessReportName, String isReportGenerated, String ctmServer, String schedule, String frequency,
			String shift, String jobDependency, String startTime, String endTime, String jobStatus,
			String isLogsValidated, String errorDetails, String resolution, String application, String remarks,
			String processingDate, boolean isJobDetailsFilled) {
		super();
		this.serialNo = serialNo;
		this.type = type;
		this.jobName = jobName;
		this.folderName = folderName;
		this.businessReportName = businessReportName;
		this.isReportGenerated = isReportGenerated;
		this.ctmServer = ctmServer;
		this.schedule = schedule;
		this.frequency = frequency;
		this.shift = shift;
		this.jobDependency = jobDependency;
		this.startTime = startTime;
		this.endTime = endTime;
		this.jobStatus = jobStatus;
		this.isLogsValidated = isLogsValidated;
		this.errorDetails = errorDetails;
		this.resolution = resolution;
		this.application = application;
		this.remarks = remarks;
		this.isJobDetailsFilled = isJobDetailsFilled;
		this.processingDate = processingDate;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getFolderName() {
		return folderName;
	}

	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}

	public String getBusinessReportName() {
		return businessReportName;
	}

	public void setBusinessReportName(String businessReportName) {
		this.businessReportName = businessReportName;
	}

	public String getIsReportGenerated() {
		return isReportGenerated;
	}

	public void setIsReportGenerated(String isReportGenerated) {
		this.isReportGenerated = isReportGenerated;
	}

	public String getCtmServer() {
		return ctmServer;
	}

	public void setCtmServer(String ctmServer) {
		this.ctmServer = ctmServer;
	}

	public String getSchedule() {
		return schedule;
	}

	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public String getShift() {
		return shift;
	}

	public void setShift(String shift) {
		this.shift = shift;
	}

	public String getJobDependency() {
		return jobDependency;
	}

	public void setJobDependency(String jobDependency) {
		this.jobDependency = jobDependency;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getJobStatus() {
		return jobStatus;
	}

	public void setJobStatus(String jobStatus) {
		this.jobStatus = jobStatus;
	}

	public String getIsLogsValidated() {
		return isLogsValidated;
	}

	public void setIsLogsValidated(String isLogsValidated) {
		this.isLogsValidated = isLogsValidated;
	}

	public String getErrorDetails() {
		return errorDetails;
	}

	public void setErrorDetails(String errorDetails) {
		this.errorDetails = errorDetails;
	}

	public String getResolution() {
		return resolution;
	}

	public void setResolution(String resolution) {
		this.resolution = resolution;
	}

	public String getApplication() {
		return application;
	}

	public void setApplication(String application) {
		this.application = application;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public boolean isJobDetailsFilled() {
		return isJobDetailsFilled;
	}

	public void setJobDetailsFilled(boolean isJobDetailsFilled) {
		this.isJobDetailsFilled = isJobDetailsFilled;
	}

	public String getProcessingDate() {
		return processingDate;
	}

	public void setProcessingDate(String processingDate) {
		this.processingDate = processingDate;
	}

	@Override
	public String toString() {
		return "ChecklistTemplateData [serialNo=" + serialNo + ", type=" + type + ", jobName=" + jobName
				+ ", folderName=" + folderName + ", businessReportName=" + businessReportName + ", isReportGenerated="
				+ isReportGenerated + ", ctmServer=" + ctmServer + ", schedule=" + schedule + ", frequency=" + frequency
				+ ", shift=" + shift + ", jobDependency=" + jobDependency + ", startTime=" + startTime + ", endTime="
				+ endTime + ", jobStatus=" + jobStatus + ", isLogsValidated=" + isLogsValidated + ", errorDetails="
				+ errorDetails + ", resolution=" + resolution + ", application=" + application + ", remarks=" + remarks
				+ ", isJobDetailsFilled=" + isJobDetailsFilled + "processingDate" + processingDate + "]";
	}

}
