package org.chinalbs.logistics.controller;

import org.chinalbs.logistics.annotation.OperationDefinition;
import org.chinalbs.logistics.common.json.Response;
import org.chinalbs.logistics.common.json.ResponseHelper;
import org.chinalbs.logistics.common.utils.ListSlice;
import org.chinalbs.logistics.domain.Score;
import org.chinalbs.logistics.service.ScoreService;
import org.chinalbs.logistics.session.SessionInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/scores")
public class ScoreController {

	@Autowired
	private ScoreService scoreService;
	
	@OperationDefinition(name = "获取积分列表")
	@RequestMapping(value = "",params = {"from","max"}, method = RequestMethod.GET)
	private Response<ListSlice<Score>> listSlice(@RequestParam int from, @RequestParam int max) {
		Long userId = SessionInfo.getCurrent().getUserInfo().getUserId();
		return ResponseHelper.createSuccessResponse(scoreService.findListSlice(userId, from, max));
	}
	
	@OperationDefinition(name = "获取当前用户总积分")
	@RequestMapping(value = "", method = RequestMethod.GET)
	private Response<Integer> viewTotalPoints(){
		long userId = SessionInfo.getCurrent().getUserInfo().getUserId();
		return ResponseHelper.createSuccessResponse(scoreService.findTotalPoints(userId));
	}
}
