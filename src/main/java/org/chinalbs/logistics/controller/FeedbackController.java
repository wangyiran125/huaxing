package org.chinalbs.logistics.controller;

import java.util.List;

import org.chinalbs.logistics.annotation.OperationDefinition;
import org.chinalbs.logistics.common.json.Response;
import org.chinalbs.logistics.common.json.ResponseHelper;
import org.chinalbs.logistics.common.utils.ListSlice;
import org.chinalbs.logistics.domain.Feedback;
import org.chinalbs.logistics.service.FeedbackService;
import org.chinalbs.logistics.vo.FeedBackInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/feedbacks")
public class FeedbackController {

	@Autowired
	private FeedbackService feedbackService;
		
	@OperationDefinition(name = "查看反馈信息列表")
	@RequestMapping(value="", params={"from", "max"} , method=RequestMethod.GET)
	public Response<ListSlice<Feedback>> list(@RequestParam int from, @RequestParam int max)
	{		
		ListSlice<Feedback> slice =feedbackService.findSlice(from, max);
		Response<ListSlice<Feedback>> rsp = ResponseHelper.createSuccessResponse(slice);		
		return rsp;
	}
	
	@OperationDefinition(name = "查看反馈信息列表")
	@RequestMapping(value = "", method = RequestMethod.GET)
	public Response<List<Feedback>> listAll() {
		List<Feedback> feedbacks = feedbackService.findAll();
		Response<List<Feedback>> rsp = ResponseHelper.createSuccessResponse(feedbacks);
		return rsp;
	}
	
	@OperationDefinition(name = "创建反馈信息")
	@RequestMapping(value="", method=RequestMethod.POST)
	public Response<Feedback> create(@RequestBody FeedBackInfo info) throws Exception
	{
		Feedback fbk = feedbackService.create(info);
		Response<Feedback> rsp =  ResponseHelper.createSuccessResponse(fbk);
		return rsp;
	}

	@OperationDefinition(name = "创建反馈信息（兼容APP端接口定义）")//为了兼容APP端接口定义
	@RequestMapping(value="", method=RequestMethod.PUT)
	public Response<Feedback> createPut(@RequestBody FeedBackInfo info) throws Exception
	{
		return create(info);
	}
}
