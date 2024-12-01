package com.manualtasks.jobchecklist.models;

public class UserData {

	private String domainId;

	private String password;

	public String getDomainId() {
		return domainId;
	}

	public void setDomainId(String username) {
		this.domainId = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "UserData [domainId=" + domainId + ", password=" + password + "]";
	}

}
