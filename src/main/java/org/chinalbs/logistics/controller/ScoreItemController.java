package org.chinalbs.logistics.controller;

import java.util.Map;

import org.chinalbs.logistics.annotation.OperationDefinition;
import org.chinalbs.logistics.common.json.Response;
import org.chinalbs.logistics.common.json.ResponseHelper;
import org.chinalbs.logistics.service.ScoreItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/scoreitems")
public class ScoreItemController {

	@Autowired
	private ScoreItemService scoreItemService;
	
	@OperationDefinition(name = "获取所有积分项数据 ")
	@RequestMapping(value = "", method = RequestMethod.GET)
    public Response<Map<String, Integer>> getAllScoreItem() {
		return ResponseHelper.createSuccessResponse(scoreItemService.getAllScoreItem());
	}
	
	@OperationDefinition(name = "更新积分项数据 ")
	@RequestMapping(value = "", method = RequestMethod.PUT)
	public Response<?> updateScoreItem(@RequestBody Map<String, Integer> items) {
		scoreItemService.updateScoreItem(items);
		return ResponseHelper.createSuccessResponse();
	}
}
