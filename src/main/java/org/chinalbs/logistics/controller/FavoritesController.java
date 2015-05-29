package org.chinalbs.logistics.controller;

import org.chinalbs.logistics.annotation.OperationDefinition;
import org.chinalbs.logistics.common.json.Response;
import org.chinalbs.logistics.common.json.ResponseHelper;
import org.chinalbs.logistics.common.utils.ListSlice;
import org.chinalbs.logistics.domain.Favorites;
import org.chinalbs.logistics.service.FavoritesService;
import org.chinalbs.logistics.session.SessionInfo;
import org.chinalbs.logistics.vo.AddFavoritesInfo;
import org.chinalbs.logistics.vo.FavoritesInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/favorites")
public class FavoritesController {

	@Autowired
	private FavoritesService favoritesService;

	@OperationDefinition(name = "获取收藏列表")
	@RequestMapping(value = "", params = { "from",
			"max" }, method = RequestMethod.GET)
	public Response<ListSlice<FavoritesInfo>> listSlice(@RequestParam int from, @RequestParam int max) {
		long userId = SessionInfo.getCurrent().getUserInfo().getUserId();
		return ResponseHelper.createSuccessResponse(favoritesService.findListSlice(userId, from, max));
	}

	@OperationDefinition(name = "添加收藏")
	@RequestMapping(value = "", method = RequestMethod.POST)
	public Response<Favorites> create(@RequestBody AddFavoritesInfo info) {
		return ResponseHelper.createSuccessResponse(favoritesService.create(info));
	}

	@OperationDefinition(name = "获取收藏的详细信息")
	@RequestMapping(value = "/{favoritesId}", method = RequestMethod.GET)
	public Response<FavoritesInfo> viewFavoritesInfo(
			@PathVariable Long favoritesId) {
		return ResponseHelper.createSuccessResponse(favoritesService
				.findOne(favoritesId));
	}

	@OperationDefinition(name = "删除收藏")
	@RequestMapping(value = "/{favoritesId}", method = RequestMethod.DELETE)
	public Response<?> delete(@PathVariable Long favoritesId) {
		favoritesService.delete(favoritesId);
		return ResponseHelper.createSuccessResponse();
	}

}
