
package org.chinalbs.logistics.service;

import java.util.List;
import java.util.Map;

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

public interface DictService {

	public Map<String, List<?>> findAllDict();
	public List<DictGoodsName> findGoodsNames();
	
	public List<DictGoodsType> findGoodsTypes();
	
	public List<DictGoodsWeight> findGoodsWeight();
	
	public List<DictGoodsVolume> findGoodsVolume();
	
	public List<DictPhrase> findPhrases();
	
	public List<DictQuantity> findQuantities();
	
	public List<DictShippingType> findShippingTypes();
	
	public List<DictTruckCondition> findTruckConditions();
	
	public List<DictTruckLength> findTruckLengths();
	
	public List<DictTruckLoad> findTruckLoads();
	
	public List<DictTruckType> findTruckTypes();
	
	public List<DictWarehouseArea> findWarehouseAreas();
	
	public List<DictWarehouseType> findWarehouseTypes();
	
	public List<DictWarehouseVolume> findWarehouseVolumes();
	
	public List<DictDistrict> findDistricts();
	
	public List<DictDistrict> findByParentCode(Long code);
}
