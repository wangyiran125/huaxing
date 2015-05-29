package org.chinalbs.logistics.domain;

import java.util.List;

public class Action {
	private String action;
	private List<String> operations;

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public List<String> getOperations() {
		return operations;
	}

	public void setOperations(List<String> operations) {
		this.operations = operations;
	}

}
