package org.chinalbs.logistics.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.chinalbs.logistics.annotation.OperationDefinition;
import org.chinalbs.logistics.common.json.Response;
import org.chinalbs.logistics.common.json.ResponseHelper;
import org.chinalbs.logistics.service.ExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/export")
public class ExportController {

	@Autowired
	private ExportService exportService;
	
	@OperationDefinition(name = "数据导出")
	@RequestMapping(value = "", params = {"status","type"}, method = RequestMethod.GET)
	public Response<?> exportData(
			@RequestParam int status,
			@RequestParam String type,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		exportService.exportExcel(status, type, request, response);
		return ResponseHelper.createSuccessResponse();
	}
	
	@OperationDefinition(name = "数据导出(某用户)")
	@RequestMapping(value = "/{userid}", params = {"status"}, method = RequestMethod.GET)
	public Response<?> exportData(
			@PathVariable Long userid,
			String startTime,
			String endTime,
			@RequestParam int status,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		exportService.exportExcelByUserId(userid, startTime, endTime, status, null, request, response);
		return null;
	}
}
