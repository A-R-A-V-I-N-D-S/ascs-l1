package com.manualtasks.jobchecklist.model;

public class JobDetailsData {

	private String jobName;

	private String folderName;

	private String jobStatus;

	private String isJobHeld;

	private String startTime;

	private String endTime;

	private String orderDate;

	private String isDeleted;

	private boolean isJobDetailsFilled;

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

	public String getJobStatus() {
		return jobStatus;
	}

	public void setJobStatus(String jobStatus) {
		this.jobStatus = jobStatus;
	}

	public String getIsJobHeld() {
		return isJobHeld;
	}

	public void setIsJobHeld(String isJobHeld) {
		this.isJobHeld = isJobHeld;
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

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public String getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}

	public boolean isJobDetailsFilled() {
		return isJobDetailsFilled;
	}

	public void setJobDetailsFilled(boolean isJobDetailsFilled) {
		this.isJobDetailsFilled = isJobDetailsFilled;
	}

	public JobDetailsData() {

	}

	public JobDetailsData(String jobName, String jobStatus, String startTime, String endTime, String orderDate,
			String isDeleted, String folderName) {
		super();
		this.jobName = jobName;
		this.jobStatus = jobStatus;
		this.startTime = startTime;
		this.endTime = endTime;
		this.orderDate = orderDate;
		this.isDeleted = isDeleted;
		this.folderName = folderName;
	}

	@Override
	public String toString() {
		return "JobDetailsData [jobName=" + jobName + ", folderName=" + folderName + ", jobStatus=" + jobStatus
				+ ", isJobHeld=" + isJobHeld + ", startTime=" + startTime + ", endTime=" + endTime + ", orderDate="
				+ orderDate + ", isDeleted=" + isDeleted + ", isJobDetailsFilled=" + isJobDetailsFilled + "]";
	}

}
