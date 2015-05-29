package org.chinalbs.logistics.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.chinalbs.logistics.domain.DictScoreItem;
import org.chinalbs.logistics.repository.ScoreItemRepository;
import org.chinalbs.logistics.service.ScoreItemService;
import org.chinalbs.logistics.utils.Consts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ScoreItemServiceImpl implements ScoreItemService{

	@Autowired
	private ScoreItemRepository scoreItemRepository;
	private static final Map<Long, String> SCOREITEM = new HashMap<Long, String>();
	private static final Map<Long, String> SCOREITEMDesc = new HashMap<Long, String>();
	static{
		SCOREITEM.put(Consts.ScoreItem.GOODOWNER_PUBLISHED, "goodsPublish");
		SCOREITEM.put(Consts.ScoreItem.GOODOWNER_FINISH_ORDER, "orderFinish");
		SCOREITEM.put(Consts.ScoreItem.GOODOWNER_GOT_GOOD_REVIEW, "goodsGoodReview");
		SCOREITEM.put(Consts.ScoreItem.GOODOWNER_GOT_MEDIUM_REVIEW, "goodsMeduimReview");
		SCOREITEM.put(Consts.ScoreItem.GOODOWNER_GOT_BAD_REVIEW, "goodsBadReview");
		SCOREITEM.put(Consts.ScoreItem.TRUCKOWNER_GOT_GOOD_REVIEW, "TruckGoodReview");
		SCOREITEM.put(Consts.ScoreItem.TRUCKOWNER_GOT_MEDIUM_REVIEW, "TruckMeduimReview");
		SCOREITEM.put(Consts.ScoreItem.TRUCKOWNER_GOT_BAD_REVIEW, "TruckBadReview");
		
		SCOREITEMDesc.put(Consts.ScoreItem.GOODOWNER_PUBLISHED, "货主发布货源得分");
		SCOREITEMDesc.put(Consts.ScoreItem.GOODOWNER_FINISH_ORDER, "货主完成订单积分");
		SCOREITEMDesc.put(Consts.ScoreItem.GOODOWNER_GOT_GOOD_REVIEW, "货主获得好评积分");
		SCOREITEMDesc.put(Consts.ScoreItem.GOODOWNER_GOT_MEDIUM_REVIEW, "货主获得中评积分");
		SCOREITEMDesc.put(Consts.ScoreItem.GOODOWNER_GOT_BAD_REVIEW, "货主获得差评积分");
		SCOREITEMDesc.put(Consts.ScoreItem.TRUCKOWNER_GOT_GOOD_REVIEW, "车主获得好评积分");
		SCOREITEMDesc.put(Consts.ScoreItem.TRUCKOWNER_GOT_MEDIUM_REVIEW, "车主获得中评积分");
		SCOREITEMDesc.put(Consts.ScoreItem.TRUCKOWNER_GOT_BAD_REVIEW, "车主获得差评积分");
	}

	@Override
	public Map<String, Integer> getAllScoreItem() {
		List<DictScoreItem> items = scoreItemRepository.findAll();
		Map<String, Integer> itemsMap = new HashMap<String, Integer>();
		for(DictScoreItem item : items){
			itemsMap.put(SCOREITEM.get(item.getCode()), item.getScore());
		}
		return itemsMap;
	}

	@Override
	public void updateScoreItem(Map<String, Integer> scoreItems) {
		List<DictScoreItem> items = scoreItemRepository.findAll();
		for(DictScoreItem item : items){
			String key = SCOREITEM.get(item.getCode());
			Integer score = scoreItems.get(key) == null ? item.getScore() : scoreItems.get(key);
			item.setScore(score);
		}
		scoreItemRepository.save(items);
	}
}
