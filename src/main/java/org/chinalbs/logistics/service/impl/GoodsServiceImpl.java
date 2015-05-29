package org.chinalbs.logistics.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.chinalbs.logistics.common.json.ReturnCode;
import org.chinalbs.logistics.common.utils.ListSlice;
import org.chinalbs.logistics.domain.DictScoreItem;
import org.chinalbs.logistics.domain.Driver;
import org.chinalbs.logistics.domain.Goods;
import org.chinalbs.logistics.domain.GoodsOwner;
import org.chinalbs.logistics.domain.LogisticsOrder;
import org.chinalbs.logistics.domain.OrderIntent;
import org.chinalbs.logistics.domain.Score;
import org.chinalbs.logistics.domain.TruckOwner;
import org.chinalbs.logistics.error.CodeException;
import org.chinalbs.logistics.repository.DriverRepository;
import org.chinalbs.logistics.repository.GoodsOwnerRepository;
import org.chinalbs.logistics.repository.GoodsRepository;
import org.chinalbs.logistics.repository.LogisticsOrderRepository;
import org.chinalbs.logistics.repository.OrderIntentRepository;
import org.chinalbs.logistics.repository.ScoreItemRepository;
import org.chinalbs.logistics.repository.ScoreRepository;
import org.chinalbs.logistics.repository.TruckOwnerRepository;
import org.chinalbs.logistics.repository.VipLevelRepository;
import org.chinalbs.logistics.repository.criteria.GoodsDAO;
import org.chinalbs.logistics.service.GoodsService;
import org.chinalbs.logistics.service.OrderIntentService;
import org.chinalbs.logistics.session.SessionInfo;
import org.chinalbs.logistics.utils.Consts;
import org.chinalbs.logistics.utils.MessageDes;
import org.chinalbs.logistics.utils.SimplePageable;
import org.chinalbs.logistics.vo.GoodsInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class GoodsServiceImpl implements GoodsService {

	@Autowired
	private GoodsRepository goodsRepository;
	
	@Autowired
	private GoodsOwnerRepository goodsOwnerRepository;
	
	@Autowired
	private LogisticsOrderRepository logisticsOrderRepository;
	
	@Autowired
	private OrderIntentRepository orderIntentRepository;
	
	@Autowired
	private VipLevelRepository vipLevelRepository;
	
	@Autowired
	private TruckOwnerRepository truckOwnerRepository;
	
	@Autowired
	private GoodsDAO goodsDAO;
	
	@Autowired
	private ScoreRepository  scorRepository;
	
	@Autowired
	private ScoreItemRepository  scoreItemRepository;
	
	@Autowired
	private OrderIntentService  orderIntentService;

    @Autowired
    private DriverRepository driverRepository;
	
	@Override
	public ListSlice<Goods> findListSlice(int from, int max) {
		Page<Goods> page = goodsRepository.findAll(new SimplePageable(from, max));
		return new ListSlice<Goods>(page.getTotalElements(), page.getContent());
	}

	@Override
	public Goods create(GoodsInfo info) {
		Goods goods = makeGoods(new Goods(), info);
		long userId = SessionInfo.getCurrent().getUserInfo().getUserId();
		goods.setGoodsOwnerId(userId);
		goods.setPublishTime(new Date());
		goods = goodsRepository.save(goods);
		logisticsOrderRepository.save(createOrder(goods));
		DictScoreItem scoreItem = scoreItemRepository.findInfobyCode(Consts.ScoreItem.GOODOWNER_PUBLISHED);
		scorRepository.save(new Score(userId, new Date(), scoreItem.getName(), scoreItem.getScore()));
		return goods;
	}

	@Override
	public Goods findOneByGoodsId(Long goodsId) {
		return goodsRepository.findOne(goodsId);
	}

	@Override
	public Goods update(Long goodsId, GoodsInfo info) {
		LogisticsOrder logisticsOrder = logisticsOrderRepository.findOneByGoodsId(goodsId);
		//如果该货物 已发布 或者 已抢单的状态， 可以修改， 其余状态不可修改。
		if (logisticsOrder == null || logisticsOrder.getStatus() > Consts.Order.STATUS_ORDER_INTENT) {
			throw new CodeException(ReturnCode.BUSINESS_ERROR, MessageDes.Goods.UPDATE_GUARD_STATUS);
		}
		Goods goods = goodsRepository.findOne(goodsId);
		if (goods != null) {
			return goodsRepository.save(makeGoods(goods, info));
		}
		return null;
	}

	@Override
	public void delete(Long goodsId) {
		
		LogisticsOrder logisticsOrder = logisticsOrderRepository.findOneByGoodsId(goodsId);
		//如果该货物 已发布 或者 已抢单的状态， 可以删除， 其余状态不可删除。
		if (logisticsOrder == null || logisticsOrder.getStatus() > Consts.Order.STATUS_ORDER_INTENT) {
			throw new CodeException(ReturnCode.BUSINESS_ERROR, MessageDes.Goods.DELETE_GUARD_STATUS);
		}
		
		//所有抢单标示为已拒绝
		Page<OrderIntent>  page = 
				orderIntentRepository.findByGoodsId(goodsId, new SimplePageable(0, Integer.MAX_VALUE));
		if (page != null && page.getSize() > 0) {
			for (OrderIntent orderIntent : page) {
//				orderIntent.setStatus(Consts.Intents.REFUSE);
//				orderIntentRepository.save(orderIntent);
				orderIntentService.refuseOrderIntent(orderIntent);
			}
		}	

		//删除货物
		Goods goods = goodsRepository.findOne(goodsId);
		if (goods != null) {
			goods.setIsDeleted(Consts.DELETED);
			goodsRepository.save(goods);
		}
	}

	@Override
	public ListSlice<GoodsInfo> findListSlice(Long goodsOwnerId, int from, int max) {
		long userId = SessionInfo.getCurrent().getUserInfo().getUserId();
		Page<Goods> page = goodsRepository.findNoFinishedByGoodsOwnerId(userId, new SimplePageable(from, max));
		List<GoodsInfo> goodsInfos = new ArrayList<GoodsInfo>();
		LogisticsOrder logisticsOrder = null;
		GoodsInfo goodsInfo = null;
		if (page != null && page.getSize() > 0) {
			for (Goods goods : page) {
				logisticsOrder = logisticsOrderRepository.findOneByGoodsId(goods.getId());
				goodsInfo = makeGoodsInfo(new GoodsInfo(),goods);
				//设置该货物当前的状态
				goodsInfo.setStatus(logisticsOrder.getStatus());
				goodsInfos.add(goodsInfo);
			}
		}
		return new ListSlice<GoodsInfo>(page.getTotalElements(), goodsInfos);
	}
	
	private Goods makeGoods(Goods goods, GoodsInfo info){
		goods.setGoodsName(info.getGoodsName() == 0 ? goods.getGoodsName() : info.getGoodsName());
		goods.setGoodsType(info.getGoodsType() == 0 ? goods.getGoodsType() : info.getGoodsType());
		goods.setShippingPrice(info.getShippingPrice() == -1 ? -1 : info.getShippingPrice() * 100);
		goods.setShippingType(info.getShippingType() == 0 ? goods.getShippingType() : info.getShippingType());
		goods.setQuantity(info.getQuantity() == 0 ? goods.getQuantity() : info.getQuantity());
		goods.setVolume(info.getVolume() == 0 ? goods.getVolume() : info.getVolume());
		goods.setWeight(info.getWeight() == 0 ? goods.getWeight() : info.getWeight());
		goods.setDepartureProvinceCode(info.getDepartureProvinceCode() == 0 ? goods.getDepartureProvinceCode() : info.getDepartureProvinceCode());
		goods.setDepartureCityCode(info.getDepartureCityCode() == 0 ? goods.getDepartureCityCode() : info.getDepartureCityCode());
		goods.setDeparture(info.getDeparture() == null ? goods.getDeparture() : info.getDeparture());
		goods.setDestinationProvinceCode(info.getDestinationProvinceCode() == 0 ? goods.getDestinationProvinceCode() : info.getDestinationProvinceCode());
		goods.setDestinationCityCode(info.getDestinationCityCode() == 0 ? goods.getDestinationCityCode() : info.getDestinationCityCode());
		goods.setDestination(info.getDestination() == null ? goods.getDestination() : info.getDestination());
		goods.setDepartureTime(info.getDepartureTime() == null ? goods.getDepartureTime() : info.getDepartureTime());
		goods.setValidity(info.getValidity() == null ? goods.getValidity() : info.getValidity());
		goods.setNotice(info.getNotice() == null ? goods.getNotice() : info.getNotice());
		goods.setLongitude(info.getLongitude() == 0 ? goods.getLongitude() : info.getLongitude());
		goods.setLatitude(info.getLatitude() == 0 ? goods.getLatitude() : info.getLatitude());
		goods.setPicture((info.getPicture() == "" || info.getPicture() == null) ? 
				goods.getPicture() : info.getPicture());
		goods.setGoodsAddress(info.getGoodsAddress() == null ? goods.getGoodsAddress() : info.getGoodsAddress());
		goods.setContactName(info.getContactName() == null ? goods.getContactName() : info.getContactName());
		goods.setContactMobile(info.getContactMobile() == null ? goods.getContactMobile() : info.getContactMobile());
		goods.setPhone(info.getPhone() == null ? goods.getPhone() : info.getPhone());
		goods.setRemark(info.getRemark() == null ? goods.getRemark() : info.getRemark());
		return goods;
	}
	
	private GoodsInfo makeGoodsInfo(GoodsInfo info, Goods goods){
		info.setId(goods.getId());
		info.setGoodsName(goods.getGoodsName());
		info.setGoodsType(goods.getGoodsType());
		info.setShippingPrice(goods.getShippingPrice());
		info.setShippingType(goods.getShippingType());
		info.setQuantity(goods.getQuantity());
		info.setVolume(goods.getVolume());
		info.setWeight(goods.getWeight());
		info.setDepartureProvinceCode(goods.getDepartureProvinceCode());
		info.setDepartureCityCode(goods.getDepartureCityCode());
		info.setDeparture(goods.getDeparture());
		info.setDestinationProvinceCode(goods.getDestinationProvinceCode());
		info.setDestinationCityCode(goods.getDestinationCityCode());
		info.setDestination(goods.getDestination());
		info.setDepartureTime(goods.getDepartureTime());
		info.setValidity(goods.getValidity());
		info.setNotice(goods.getNotice());
		info.setLongitude(goods.getLongitude());
		info.setLatitude(goods.getLatitude());
		info.setPicture(goods.getPicture());
		info.setPublishTime(goods.getPublishTime());
		info.setGoodsAddress(goods.getGoodsAddress());
		info.setContactName(goods.getContactName());
		info.setContactMobile(goods.getContactMobile());
		info.setPhone(goods.getPhone());
		info.setRemark(goods.getRemark());
		return info;
	}
	private LogisticsOrder createOrder(Goods goods){
		LogisticsOrder order = new LogisticsOrder();
		order.setGoodsId(goods.getId());
		order.setTruckOwnerIsEvaluated(Consts.Order.TRUCKOWNER_NOT_EVALUATED);
		order.setGoodsOwnerIsEvaluated(Consts.Order.GOODSOWNER_NOT_EVALUATED);
		order.setStatus(Consts.Order.STATUS_PUBLISHED);
		order.setOrderTime(new Date());
		return order;
	}

	/***
	 * 搜货物
	 */
	@Override
	public ListSlice<Goods> search(Goods goods, int from, int max) {
		long userId = SessionInfo.getCurrent().getUserInfo().getUserId();
        long role = SessionInfo.getCurrent().getUserInfo().getRole();
        long truckOwnerId = userId;
        if (role == Consts.Role.DRIVER) {
            Driver driver = driverRepository.findOnebyUserId(userId);
            truckOwnerId = driver.getParentUserId();
        }
    	TruckOwner truckOwner = truckOwnerRepository.findOneByUserId(truckOwnerId);
    	long delayTime = 0;
    	if (truckOwner != null) {
    		delayTime = vipLevelRepository.findOneByLevel(truckOwner.getVipLevel()).getDelayTime();
    		//延迟时间设为秒
    		delayTime = delayTime * 60 * 1000;
		}
    	goods.setPublishTime(new Date(new Date().getTime() - delayTime));
		return goodsDAO.findRange4Search(goods, new int[]{from, max});
	}
	
	
	/***
	 * 通过goodsOwnerId搜历史运单中的货物
	 */
	public ListSlice<Goods> findFinishedByGoodsOwnerId(Long goodsOwnerId, int from, int max){
		Page<Goods> page = goodsRepository.findFinishedByGoodsOwnerId(goodsOwnerId, new SimplePageable(from, max));
		return new ListSlice<Goods>(page.getTotalElements(), page.getContent());
	}
	
	
	/***
	 * 通过truckOwnerId搜历史运单中的货物
	 */
	public ListSlice<Goods> findFinishedByTruckOwnerId(Long truckOwnerId, int from, int max){
		Page<Goods> page = goodsRepository.findFinishedByTruckOwnerId(truckOwnerId, new SimplePageable(from, max));
		return new ListSlice<Goods>(page.getTotalElements(), page.getContent());
		
	}

	/***
	 * 获得未发货的货物列表
	 */
	public ListSlice<Goods> findWaitGoodsListSlice(int from, int max) {
		Page<Goods> page = goodsRepository.findWaitGoodsAll(new SimplePageable(from, max));
		return new ListSlice<Goods>(page.getTotalElements(), page.getContent());
	}

	/**
	 * 获取发布货物时的默认联系人、联系方式
	 */
	@Override
	public GoodsOwner findOneByUserId(Long userId) {
		GoodsOwner goodsOwner = goodsOwnerRepository.findOneByUserId(userId);
		return goodsOwner;
	}

	@Override
	public List<Goods> findWaitGoodsByGoodsOwnerId(Long goodsOwnerId) {
		List<Goods> list = goodsRepository.findWaitGoodsByGoodsOwnerId(goodsOwnerId);
		return list;
	}
}
