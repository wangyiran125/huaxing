package org.chinalbs.logistics.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ExportService {

	public void exportExcel(int status, String type, HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	public void exportExcelByUserId(Long userId, String startTime, String endTime, int status, String type, HttpServletRequest request, HttpServletResponse response) throws Exception;
}
