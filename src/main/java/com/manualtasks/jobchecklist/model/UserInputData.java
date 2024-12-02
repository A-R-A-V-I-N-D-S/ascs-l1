package com.manualtasks.jobchecklist.model;

import org.springframework.web.multipart.MultipartFile;

public class UserInputData {

	private String orderDate;

	private String shift;
	
	private boolean dayLightSavings;

	private String shiftStartTime;
	
	private String shiftEndTime;
	
	private MultipartFile inputFile;

	public UserInputData(String orderDate, String shift, String shiftStartTime, String shiftEndTime,
			MultipartFile inputFile) {
		super();
		this.orderDate = orderDate;
		this.shift = shift;
		this.shiftStartTime = shiftStartTime;
		this.shiftEndTime = shiftEndTime;
		this.inputFile = inputFile;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	
	public String getShiftStartTime() {
		return shiftStartTime;
	}
	
	public String getShift() {
		return shift;
	}

	public void setShift(String shift) {
		this.shift = shift;
	}
	
	public boolean isDayLightSavings() {
		return dayLightSavings;
	}

	public void setDayLightSavings(boolean dayLightSavings) {
		this.dayLightSavings = dayLightSavings;
	}
	
	public String getOrderDate() {
		return orderDate;
	}

	public void setShiftStartTime(String shiftStartTime) {
		this.shiftStartTime = shiftStartTime;
	}

	public String getShiftEndTime() {
		return shiftEndTime;
	}

	public void setShiftEndTime(String shiftEndTime) {
		this.shiftEndTime = shiftEndTime;
	}
	
	public MultipartFile getInputFile() {
		return inputFile;
	}

	public void setInputFile(MultipartFile inputFile) {
		this.inputFile = inputFile;
	}

	@Override
	public String toString() {
		return "UserInputData [orderDate=" + orderDate + ", shift=" + shift + ", dayLightSavings=" + dayLightSavings
				+ ", shiftStartTime=" + shiftStartTime + ", shiftEndTime=" + shiftEndTime + ", inputFile=" + inputFile
				+ "]";
	}
	
}
