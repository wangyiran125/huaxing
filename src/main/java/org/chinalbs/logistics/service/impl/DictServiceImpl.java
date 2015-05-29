package org.chinalbs.logistics.service.impl;

import java.util.HashMap;
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
import org.chinalbs.logistics.repository.DictDistrictRepository;
import org.chinalbs.logistics.repository.DictGoodsNameRepository;
import org.chinalbs.logistics.repository.DictGoodsTypeRepository;
import org.chinalbs.logistics.repository.DictGoodsVolumeRepository;
import org.chinalbs.logistics.repository.DictGoodsWeightRepository;
import org.chinalbs.logistics.repository.DictPhraseRepository;
import org.chinalbs.logistics.repository.DictQuantityRepository;
import org.chinalbs.logistics.repository.DictShippingTypeRepository;
import org.chinalbs.logistics.repository.DictTruckConditionRepository;
import org.chinalbs.logistics.repository.DictTruckLengthRepository;
import org.chinalbs.logistics.repository.DictTruckLoadRepository;
import org.chinalbs.logistics.repository.DictTruckTypeRepository;
import org.chinalbs.logistics.repository.DictWarehouseAreaRepository;
import org.chinalbs.logistics.repository.DictWarehouseTypeRepository;
import org.chinalbs.logistics.repository.DictWarehouseVolumeRepository;
import org.chinalbs.logistics.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DictServiceImpl implements DictService {

	
	@Autowired
	private DictGoodsNameRepository dictGoodsNameRepository;
	
	@Autowired
	private DictGoodsTypeRepository dictGoodsTypeRepository;
	
	@Autowired
	private DictGoodsWeightRepository dictGoodsWeightRepository;
	
	@Autowired
	private DictGoodsVolumeRepository dictGoodsVolumeRepository;
	
	@Autowired
	private DictPhraseRepository dictPhraseRepository;
	
	@Autowired
	private DictQuantityRepository dictQuantityRepository;
	
	@Autowired
	private DictShippingTypeRepository dictShippingTypeRepository;
	
	@Autowired
	private DictTruckConditionRepository dictTruckConditionRepository;
	
	@Autowired
	private DictTruckLengthRepository dictTruckLengthRepository;
	
	@Autowired
	private DictTruckLoadRepository dictTruckLoadRepository;
	
	@Autowired
	private DictTruckTypeRepository dictTruckTypeRepository;
	
	@Autowired
	private DictWarehouseAreaRepository dictWarehouseAreaRepository;
	
	@Autowired
	private DictWarehouseVolumeRepository DictWarehouseVolumeRepository;
	
	@Autowired
	private DictWarehouseTypeRepository dictWarehouseTypeRepository;
	
	@Autowired
	private DictDistrictRepository dictDistrictRepository;
	
	@Override
	public Map<String, List<?>> findAllDict() {
		Map<String, List<?>> map = new HashMap<String, List<?>>();
		map.put("DictGoodsName", dictGoodsNameRepository.findAll());
		map.put("DictGoodsType", dictGoodsTypeRepository.findAll());
		map.put("DictGoodsWeight", dictGoodsWeightRepository.findAll());
		map.put("DictGoodsVolume", dictGoodsVolumeRepository.findAll());
		map.put("DictPhrase", dictPhraseRepository.findAll());
		map.put("DictQuantity", dictQuantityRepository.findAll());
		map.put("DictShippingType", dictShippingTypeRepository.findAll());
		map.put("DictTruckCondition", dictTruckConditionRepository.findAll());
		map.put("DictTruckLength", dictTruckLengthRepository.findAll());
		map.put("DictTruckLoad", dictTruckLoadRepository.findAll());
		map.put("DictTruckType", dictTruckTypeRepository.findAll());
		map.put("DictWarehouseArea", dictWarehouseAreaRepository.findAll());
		map.put("DictWarehouseType", dictWarehouseTypeRepository.findAll());
		map.put("DictWarehouseVolume", DictWarehouseVolumeRepository.findAll());
		map.put("DictDistrict", dictDistrictRepository.findAll());
		return map;
	}
	
	@Override
	public List<DictGoodsName> findGoodsNames() {

		return dictGoodsNameRepository.findAll();
	}

	@Override
	public List<DictGoodsType> findGoodsTypes() {

		return dictGoodsTypeRepository.findAll();
	}

	@Override
	public List<DictPhrase> findPhrases() {

		return dictPhraseRepository.findAll();
	}

	@Override
	public List<DictQuantity> findQuantities() {

		return dictQuantityRepository.findAll();
	}

	@Override
	public List<DictShippingType> findShippingTypes() {

		return dictShippingTypeRepository.findAll();
	}

	@Override
	public List<DictTruckCondition> findTruckConditions() {

		return dictTruckConditionRepository.findAll();
	}

	@Override
	public List<DictTruckLength> findTruckLengths() {

		return dictTruckLengthRepository.findAll();
	}

	@Override
	public List<DictTruckLoad> findTruckLoads() {

		return dictTruckLoadRepository.findAll();
	}

	@Override
	public List<DictTruckType> findTruckTypes() {

		return dictTruckTypeRepository.findAll();
	}

	@Override
	public List<DictWarehouseArea> findWarehouseAreas() {

		return dictWarehouseAreaRepository.findAll();
	}

	@Override
	public List<DictWarehouseType> findWarehouseTypes() {

		return dictWarehouseTypeRepository.findAll();
	}

	@Override
	public List<DictWarehouseVolume> findWarehouseVolumes() {

		return DictWarehouseVolumeRepository.findAll();
	}

	@Override
	public List<DictDistrict> findDistricts() {
		return dictDistrictRepository.findAll();
	}
	
	@Override
	public List<DictDistrict> findByParentCode(Long code) {

		return dictDistrictRepository.findByParentCode(code);
	}

	@Override
	public List<DictGoodsWeight> findGoodsWeight() {
		return dictGoodsWeightRepository.findAll();
	}

	@Override
	public List<DictGoodsVolume> findGoodsVolume() {
		return dictGoodsVolumeRepository.findAll();
	}

}
