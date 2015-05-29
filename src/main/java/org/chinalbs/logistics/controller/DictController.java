package org.chinalbs.logistics.controller;

import java.util.List;
import java.util.Map;

import org.chinalbs.logistics.annotation.OperationDefinition;
import org.chinalbs.logistics.cache.DictMapper;
import org.chinalbs.logistics.common.json.Response;
import org.chinalbs.logistics.common.json.ResponseHelper;
import org.chinalbs.logistics.common.json.ReturnCode;
import org.chinalbs.logistics.domain.DictDistrict;
import org.chinalbs.logistics.domain.DictGoodsName;
import org.chinalbs.logistics.domain.DictGoodsType;
import org.chinalbs.logistics.domain.DictGoodsVolume;
import org.chinalbs.logistics.domain.DictGoodsWeight;
import org.chinalbs.logistics.domain.DictPhrase;
import org.chinalbs.logistics.domain.DictQuantity;
import org.chinalbs.logistics.domain.DictShippingType;
import org.chinalbs.logistics.domain.DictTruckCondition;
import org.chinalbs.logistics.domain.DictTruckLength;
import org.chinalbs.logistics.domain.DictTruckLoad;
import org.chinalbs.logistics.domain.DictTruckType;
import org.chinalbs.logistics.domain.DictWarehouseArea;
import org.chinalbs.logistics.domain.DictWarehouseType;
import org.chinalbs.logistics.domain.DictWarehouseVolume;
import org.chinalbs.logistics.service.DictService;
import org.chinalbs.logistics.session.SessionInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/dicts")
public class DictController {

	@Autowired
	private DictService dictService;
	
	@OperationDefinition(name = "获取所有字典表")
	@RequestMapping(value = "", method = RequestMethod.GET)
	public Response<Map<String, List<?>>> getMapDict(){
		DictMapper dictMapper = DictMapper.getInstance();
		long lastUpdateTime = SessionInfo.getCurrent().getLastUpdateTime();
		if (lastUpdateTime >= dictMapper.getLastUpdateTime()) {
			return ResponseHelper.createResponse(ReturnCode.CACHE_AVAILABLE, dictMapper.getLastUpdateTime());
		}
		return ResponseHelper.createSuccessResponse(dictMapper.getDictContent());
		
	}
	
	@OperationDefinition(name = "获取货物名称")
	@RequestMapping(value = "/DictGoodsName", method = RequestMethod.GET)
	public Response<List<DictGoodsName>> viewGoodsNames(){
		return ResponseHelper.createSuccessResponse(dictService.findGoodsNames());
	}
	
	@OperationDefinition(name = "获取货物类型")
	@RequestMapping(value = "/DictGoodsType", method = RequestMethod.GET)
	public Response<List<DictGoodsType>> viewGoodsTypes(){
		return ResponseHelper.createSuccessResponse(dictService.findGoodsTypes());
	}
	
	
	@OperationDefinition(name = "获取货物重量")
	@RequestMapping(value = "/DictGoodsWeight", method = RequestMethod.GET)
	public Response<List<DictGoodsWeight>> viewGoodsWeight(){
		return ResponseHelper.createSuccessResponse(dictService.findGoodsWeight());
	}
	
	@OperationDefinition(name = "获取货物体积")
	@RequestMapping(value = "/DictGoodsVolume", method = RequestMethod.GET)
	public Response<List<DictGoodsVolume>> viewGoodsVolume(){
		return ResponseHelper.createSuccessResponse(dictService.findGoodsVolume());
	}
	
	@OperationDefinition(name = "获取常用语")
	@RequestMapping(value = "/DictPhrase", method = RequestMethod.GET)
	public Response<List<DictPhrase>> viewPhrases(){
		return ResponseHelper.createSuccessResponse(dictService.findPhrases());
	}
	
	@OperationDefinition(name = "获取数量")
	@RequestMapping(value = "/DictQuantity", method = RequestMethod.GET)
	public Response<List<DictQuantity>> viewQuantities(){
		return ResponseHelper.createSuccessResponse(dictService.findQuantities());
	}
	
	@OperationDefinition(name = "获取运输类型")
	@RequestMapping(value = "/DictShippingType", method = RequestMethod.GET)
	public Response<List<DictShippingType>> viewShippingTypes(){
		return ResponseHelper.createSuccessResponse(dictService.findShippingTypes());
	}
	
	@OperationDefinition(name = "获取车辆状况")
	@RequestMapping(value = "/DictTruckCondition", method = RequestMethod.GET)
	public Response<List<DictTruckCondition>> viewTruckConditions(){
		return ResponseHelper.createSuccessResponse(dictService.findTruckConditions());
	}
	
	@OperationDefinition(name = "获取车辆长度")
	@RequestMapping(value = "/DictTruckLength", method = RequestMethod.GET)
	public Response<List<DictTruckLength>> viewTruckLengths(){
		return ResponseHelper.createSuccessResponse(dictService.findTruckLengths());
	}
	
	@OperationDefinition(name = "获取车辆载重")
	@RequestMapping(value = "/DictTruckLoad", method = RequestMethod.GET)
	public Response<List<DictTruckLoad>> viewTruckLoads(){
		return ResponseHelper.createSuccessResponse(dictService.findTruckLoads());
	}
	
	@OperationDefinition(name = "获取车辆类型")
	@RequestMapping(value = "/DictTruckType", method = RequestMethod.GET)
	public Response<List<DictTruckType>> viewTruckTypes(){
		return ResponseHelper.createSuccessResponse(dictService.findTruckTypes());
	}
	
	@OperationDefinition(name = "获取仓库面积")
	@RequestMapping(value = "/DictWarehouseArea", method = RequestMethod.GET)
	public Response<List<DictWarehouseArea>> viewWarehouseAreas(){
		return ResponseHelper.createSuccessResponse(dictService.findWarehouseAreas());
	}
	
	@OperationDefinition(name = "获取仓库体积")
	@RequestMapping(value = "/DictWarehouseVolume", method = RequestMethod.GET)
	public Response<List<DictWarehouseVolume>> viewWarehouseVolumes(){
		return ResponseHelper.createSuccessResponse(dictService.findWarehouseVolumes());
	}
	
	@OperationDefinition(name = "获取仓库类型")
	@RequestMapping(value = "/DictWarehouseType", method = RequestMethod.GET)
	public Response<List<DictWarehouseType>> viewWarehouseTypes(){
		return ResponseHelper.createSuccessResponse(dictService.findWarehouseTypes());
	}
	
	@OperationDefinition(name = "获取行政地区")
	@RequestMapping(value = "/DictDistrict/{code}", method = RequestMethod.GET)
	public Response<List<DictDistrict>> viewDictDistricts(@PathVariable Long code){
		return ResponseHelper.createSuccessResponse(dictService.findByParentCode(code));
	}
}
