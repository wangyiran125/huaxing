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
import org.chinalbs.logistics.domain.LogisticsUser;
import org.chinalbs.logistics.domain.OrderIntent;
import org.chinalbs.logistics.domain.Score;
import org.chinalbs.logistics.domain.Truck;
import org.chinalbs.logistics.domain.TruckOwner;
import org.chinalbs.logistics.error.CodeException;
import org.chinalbs.logistics.repository.DriverRepository;
import org.chinalbs.logistics.repository.GoodsOwnerRepository;
import org.chinalbs.logistics.repository.GoodsRepository;
import org.chinalbs.logistics.repository.LogisticsOrderRepository;
import org.chinalbs.logistics.repository.LogisticsUserRepository;
import org.chinalbs.logistics.repository.OrderIntentRepository;
import org.chinalbs.logistics.repository.ScoreItemRepository;
import org.chinalbs.logistics.repository.ScoreRepository;
import org.chinalbs.logistics.repository.TruckOwnerRepository;
import org.chinalbs.logistics.repository.TruckRepository;
import org.chinalbs.logistics.service.OrderIntentService;
import org.chinalbs.logistics.session.SessionInfo;
import org.chinalbs.logistics.utils.Consts;
import org.chinalbs.logistics.utils.HTTPUtils;
import org.chinalbs.logistics.utils.MessageDes;
import org.chinalbs.logistics.utils.SimplePageable;
import org.chinalbs.logistics.vo.AddOrderIntentInfo;
import org.chinalbs.logistics.vo.IntentFromGoodsOwner;
import org.chinalbs.logistics.vo.OrderIntentInfo;
import org.chinalbs.logistics.vo.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OrderIntentServiceImpl implements OrderIntentService {

	@Autowired
	private OrderIntentRepository orderIntentRepository;
	@Autowired
	private GoodsRepository goodsRepository;
	@Autowired
	private TruckRepository truckRepository;
	@Autowired
	private GoodsOwnerRepository goodsOwnerRepository;
	@Autowired
	private TruckOwnerRepository truckOwnerRepository;
	
	@Autowired
	private LogisticsOrderRepository logisticsOrderRepository;
	
	@Autowired
	private ScoreRepository  scorRepository;
	
	@Autowired
	private ScoreItemRepository  scoreItemRepository;

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private LogisticsUserRepository logisticsUserRepository;
	
	/***
	 * 车主抢单列表
	 */
	@Override
	public ListSlice<OrderIntentInfo> findListSliceOfTruckOwner(Long truckOwnerId, int from, int max) {
		Page<OrderIntent> page = null ;
		page = orderIntentRepository.findByTruckOwnerId4Pc(truckOwnerId, new SimplePageable(from, max));
		if (page != null && page.getSize() > 0) {
			List<OrderIntentInfo> orderIntentInfos = new ArrayList<OrderIntentInfo>();
			Goods goods = null;
			for (OrderIntent orderIntent : page) {
				
				OrderIntentInfo orderIntentInfo = new OrderIntentInfo();
				LogisticsOrder logisticsOrder = logisticsOrderRepository.findOneByGoodsId(orderIntent.getGoodsId());
				if (logisticsOrder != null && logisticsOrder.getTruckOwnerId() == truckOwnerId) {
					if(logisticsOrder.getStatus() >= Consts.Order.STATUS_GOODOWNER_CONFIRM) {
						//货主已确认， 此时都按照运单状态进行
						orderIntent.setStatus(logisticsOrder.getStatus());
						orderIntentInfo.setCommentFromGoodsOwner(logisticsOrder.getCommentFromGoodsOwner());
						orderIntentInfo.setScoreFromGoodsOwner(logisticsOrder.getScoreFromGoodsOwner());
						orderIntentInfo.setGoodsOwnerIsEvaluated(logisticsOrder.getGoodsOwnerIsEvaluated());
					}
					
				}
				goods = goodsRepository.findOne(orderIntent.getGoodsId());
				orderIntentInfos.add(makeOrderIntentInfo(orderIntentInfo, orderIntent, goods));

			}
			return new ListSlice<OrderIntentInfo>(page.getTotalElements(), orderIntentInfos);
		}
		return null;
		//return findListSliceOfTruckOwner(truckOwnerId, from, max, 0 , 0);
	}
	
	/***
	 * 货主某货物的抢单列表
	 */
	@Override
	public ListSlice<OrderIntentInfo> findListSliceOfGoods(Long goodsId, int from, int max) {
		Page<OrderIntent> page = orderIntentRepository.findByGoodsId(goodsId, new SimplePageable(from, max));
		if (page != null && page.getSize() > 0) {
			List<OrderIntentInfo> orderIntentInfos = new ArrayList<OrderIntentInfo>();
			LogisticsOrder logisticsOrder = logisticsOrderRepository.findOneByGoodsId(goodsId);
			OrderIntentInfo orderIntentInfo = null;
			List<Truck> trucks = null;
			for (OrderIntent orderIntent : page) {
				TruckOwner truckOwner = truckOwnerRepository.findOneByUserId(orderIntent.getTruckOwnerId());
                Driver driver = null;
                if (truckOwner == null) {
                    driver = driverRepository.findOnebyUserId(orderIntent.getTruckOwnerId());
                }
				if (truckOwner != null || driver != null) {
					orderIntentInfo = new OrderIntentInfo();
					orderIntentInfo.setId(orderIntent.getId());
					if (logisticsOrder != null 
							&& logisticsOrder.getTruckOwnerId() == orderIntent.getTruckOwnerId() 
							&& logisticsOrder.getStatus()  >= Consts.Order.STATUS_GOODOWNER_CONFIRM) {
						//货主已确认， 此时都按照运单状态进行
						orderIntentInfo.setStatus(logisticsOrder.getStatus());
					}
					else  {
						orderIntentInfo.setStatus(orderIntent.getStatus());
					}
					orderIntentInfo.setInitiator(orderIntent.getInitiator());
					orderIntentInfo.setGoodsId(goodsId);
					orderIntentInfo.setTruckOwnerId(truckOwner != null ? truckOwner.getUserId() : driver.getUserId());
					orderIntentInfo.setTruckOwnerName(truckOwner != null ? truckOwner.getName() : driver.getName());
					orderIntentInfo.setTruckOwnerMobile(truckOwner != null ? truckOwner.getMobile() : driver.getMobile());
					orderIntentInfo.setPhone(truckOwner != null ? truckOwner.getPhone() : driver.getPhone());
					orderIntentInfo.setAvatar(truckOwner != null ? truckOwner.getAvatar() : driver.getAvatar());
					orderIntentInfo.setTruckLabel(truckOwner != null ? truckOwner.getTruckLabel() : driver.getTruckLabel());
					String[] truckIds = orderIntent.getTruckIds().split(",");
					trucks = new ArrayList<Truck>();
					for (String truckId : truckIds) {
						trucks.add(truckRepository.findOne(Long.valueOf(truckId)));
					}
                    if (truckOwner != null) {
                        orderIntentInfo.setVipLevel(truckOwner.getVipLevel());
                    } else {
                        TruckOwner parentOwner = truckOwnerRepository.findOneByUserId(driver.getParentUserId());
                        orderIntentInfo.setVipLevel(parentOwner.getVipLevel());
                    }
					orderIntentInfo.setTrucks(HTTPUtils.getTruckPositions(trucks));
                    System.out.println(orderIntentInfo.getTrucks().size());
                    orderIntentInfos.add(orderIntentInfo);
					
				}
			}
			return new ListSlice<OrderIntentInfo>(page.getTotalElements(), orderIntentInfos);
		}
		return null;
	}

	/*
	 * 车主/司机 抢单
	 * 
	 * 如果车主对某货物已抢单，则不能对该货物二次抢单。
	 * 如果货主已经主动选车 并 选择改车主， 该车主不能对此货物二次抢单
	 * 如果货主已经确认发货， 该车主不能对此货物二次抢单
	 * 如果该车主选择车辆中有满载车辆或不能抢单车辆，不能抢单
	 */
	@Override
	public AddOrderIntentInfo create(AddOrderIntentInfo info) {
		List<Long> truckIds = info.getTruckIds();
		if (truckIds != null && truckIds.size() > 0) {
			OrderIntent orderIntent = null;
			Long truckOwnerId = SessionInfo.getCurrent().getUserInfo().getUserId();
			//修改运单表状态
			LogisticsOrder order = logisticsOrderRepository.findOneByGoodsId(info.getGoodsId());
			if(order != null && order.getStatus() > Consts.Order.STATUS_ORDER_INTENT){ //如果该货物已经发货，则不能再被抢单
				throw new CodeException(ReturnCode.BUSINESS_ERROR, MessageDes.Intents.ORDER_HAS_INTENT);
			}
			OrderIntent intent = orderIntentRepository.findByTruckOwnerIdAndGoodsId(truckOwnerId, info.getGoodsId());
			if (intent != null) {
				if(intent.getStatus() == 2){
					throw new CodeException(ReturnCode.BUSINESS_ERROR, MessageDes.Intents.ORDER_REFUSD_TRUCKOWNER);
				}else if(intent.getStatus() == -1){
					throw new CodeException(ReturnCode.BUSINESS_ERROR, "您已经取消本次抢单，无法再次对本货物进行抢单操作，请选择其他货物");
				}
				if(intent.getInitiator() == 1){
					throw new CodeException(ReturnCode.BUSINESS_ERROR, "您已经对该货物进行过抢单，等待货主确认");
				}
				throw new CodeException(ReturnCode.BUSINESS_ERROR, MessageDes.Intents.ORDER_CONFLIT_TRUCKOWNER);
			}
			StringBuilder sTruckIds = new StringBuilder();
			orderIntent = new OrderIntent();
			orderIntent.setGoodsId(info.getGoodsId());
			for (int i = 0;i < truckIds.size(); i++) {
				//标示车辆为不可抢单
				Truck truck = truckRepository.findOne(truckIds.get(i));
				//如果车辆已经满载或者车辆不能抢单状态
				if(truck.getTruckStatus() == Consts.Truck.FULL || truck.getIntentPermission() == Consts.Truck.INTENT_NOPERMISION){
					throw new CodeException(ReturnCode.BUSINESS_ERROR, String.format("车辆（%s）正在使用中，请选择其他车辆发货", truck.getLicensePlateNumber()));
				}
				truck.setIntentPermission(Consts.Truck.INTENT_NOPERMISION);
				truckRepository.save(truck);
				sTruckIds.append(truckIds.get(i));
				if (i != truckIds.size() - 1) {
					sTruckIds.append(",");
				}
			}
			orderIntent.setTruckIds(sTruckIds.toString());
			orderIntent.setInitiator(info.getInitiator());
			orderIntent.setTruckOwnerId(truckOwnerId);
			orderIntent.setApplyTime(new Date());
			orderIntent = orderIntentRepository.save(orderIntent);
			
			order.setStatus(Consts.Order.STATUS_ORDER_INTENT); //已抢单
			logisticsOrderRepository.save(order);
			
			Goods goods = goodsRepository.findOne(info.getGoodsId());
			//提供返回信息
			AddOrderIntentInfo addOrderIntentInfo = new AddOrderIntentInfo();
			addOrderIntentInfo.setOrderIntentId(orderIntent.getId());
			addOrderIntentInfo.setContactName(goods.getContactName());
			addOrderIntentInfo.setContactMobile(goods.getContactMobile());
			return addOrderIntentInfo;
		}
		return null;
	}

	@Override
	public OrderIntentInfo findOneByOrderIntentId(Long orderIntentId) {
		OrderIntent orderIntent = orderIntentRepository.findOne(orderIntentId);
		if (orderIntent != null) {
			Goods goods = goodsRepository.findOne(orderIntent.getGoodsId());
			OrderIntentInfo info = makeOrderIntentInfo(new OrderIntentInfo(), orderIntent, goods);
			info.setGoodsName(goods.getGoodsName());
			info.setQuantity(goods.getQuantity());
			info.setVolume(goods.getVolume());
			info.setWeight(goods.getWeight());
			info.setValidity(goods.getValidity());
			info.setNotice(goods.getNotice());
			info.setContactName(goods.getContactName());
			info.setContactMobile(goods.getContactMobile());
			return info;
		}
		return null;
	}
	
	@Override
	public void update(Long orderIntentId) {
		OrderIntent orderIntent = orderIntentRepository.findOne(orderIntentId);
		if(orderIntent.getStatus() != Consts.Order.STATUS_PUBLISHED){
			throw new CodeException(ReturnCode.BUSINESS_ERROR, "该抢单已被拒绝或已被货主确认发货，无法修改操作");
		}
		long goodsId = orderIntent.getGoodsId();
		Goods goods = goodsRepository.findOne(goodsId);
		
		long userId = SessionInfo.getCurrent().getUserInfo().getUserId();
		LogisticsUser user = logisticsUserRepository.findOne(userId);
		if((user.getRole() == Consts.Role.DRIVER || user.getRole() == Consts.Role.TRUCKOWNER) && userId == orderIntent.getTruckOwnerId()){
			cancelOrderIntent(orderIntent);
		}else if((user.getRole() == Consts.Role.GOODSOWNER || user.getRole() == Consts.Role.WAREHOUSEOWNER) && goods.getGoodsOwnerId() == userId){
			refuseOrderIntent(orderIntent);
		}else{
			throw new CodeException(ReturnCode.NO_PERMISSION, "您无权操作该抢单");
		}
	}
	@Override
	public void refuseOrderIntent(OrderIntent orderIntent){
		if(orderIntent != null){
			orderIntent.setStatus(Consts.Intents.REFUSE);
			orderIntentRepository.save(orderIntent);
			//释放该车
			String[] truckIds = orderIntent.getTruckIds().split(Consts.SPLIT.COMMA);
			for (String truckId : truckIds) {
				Truck truck = truckRepository.findOne(Long.parseLong(truckId));
				if (truck != null) {
					truck.setIntentPermission(Consts.Truck.INTENT_PERMISION);
					truckRepository.save(truck);
				}
			}
		}
	}
	
	private void cancelOrderIntent(OrderIntent orderIntent){
		if(orderIntent != null){
			orderIntent.setStatus(Consts.Intents.CANCEL);
			orderIntentRepository.save(orderIntent);
			//释放该车
			String[] truckIds = orderIntent.getTruckIds().split(Consts.SPLIT.COMMA);
			for (String truckId : truckIds) {
				Truck truck = truckRepository.findOne(Long.parseLong(truckId));
				if (truck != null) {
					truck.setIntentPermission(Consts.Truck.INTENT_PERMISION);
					truckRepository.save(truck);
				}
			}
		}
	}

	@Override
	public void delete(Long orderIntentId) {
		OrderIntent orderIntent = orderIntentRepository.findOne(orderIntentId);
		if(orderIntent.getStatus() >= Consts.Order.STATUS_GOODOWNER_CONFIRM){
			throw new CodeException(ReturnCode.BUSINESS_ERROR, "该抢单已被货主确认发货，无法删除");
		}
		if (orderIntent != null) {
			orderIntent.setIsDeleted(Consts.DELETED);
			orderIntentRepository.save(orderIntent);
		}
		//释放该车
		String[] truckIds = orderIntent.getTruckIds().split(Consts.SPLIT.COMMA);
		for (String truckId : truckIds) {
			Truck truck = truckRepository.findOne(Long.parseLong(truckId));
			if (truck != null) {
				truck.setIntentPermission(Consts.Truck.INTENT_PERMISION);
				truckRepository.save(truck);
			}
		}
	}
	
	private OrderIntentInfo makeOrderIntentInfo(OrderIntentInfo orderIntentInfo,OrderIntent orderIntent, Goods goods){
		orderIntentInfo.setId(orderIntent.getId());
		orderIntentInfo.setGoodsId(orderIntent.getGoodsId());
		orderIntentInfo.setStatus(orderIntent.getStatus());
		String[] truckIds = orderIntent.getTruckIds().split(",");
		List<Truck> trucks = new ArrayList<Truck>();
		for (String truckId : truckIds) {
			trucks.add(truckRepository.findOne(Long.valueOf(truckId)));
		}
		orderIntentInfo.setTrucks(trucks);
		orderIntentInfo.setApplyTime(orderIntent.getApplyTime());
		orderIntentInfo.setInitiator(orderIntent.getInitiator());
		if (goods != null) {
			GoodsOwner goodsOwner = goodsOwnerRepository.findOneByUserId(goods.getGoodsOwnerId());
			LogisticsOrder order = logisticsOrderRepository.findOneByGoodsId(goods.getId());
			orderIntentInfo.setGoodsOwnerName(goodsOwner.getName());
			orderIntentInfo.setGoodsOwnerMobile(goodsOwner.getMobile());
			orderIntentInfo.setGoodsType(goods.getGoodsType());
			orderIntentInfo.setShippingType(goods.getShippingType());
			orderIntentInfo.setShippingPrice(goods.getShippingPrice());
			orderIntentInfo.setGoodsName(goods.getGoodsName());
			orderIntentInfo.setWeight(goods.getWeight());
			orderIntentInfo.setVolume(goods.getVolume());
			orderIntentInfo.setQuantity(goods.getQuantity());
			orderIntentInfo.setDepartureProvinceCode(goods.getDepartureProvinceCode());
			orderIntentInfo.setDepartureCityCode(goods.getDepartureCityCode());
			orderIntentInfo.setDeparture(goods.getDeparture());
			orderIntentInfo.setDestinationProvinceCode(goods.getDestinationProvinceCode());
			orderIntentInfo.setDestinationCityCode(goods.getDestinationCityCode());
			orderIntentInfo.setDestination(goods.getDestination());
			orderIntentInfo.setPicture(goods.getPicture());
			orderIntentInfo.setPublishTime(goods.getPublishTime());
			orderIntentInfo.setDepartureTime(goods.getDepartureTime());//出发日期
			orderIntentInfo.setValidity(goods.getValidity());//有效期
			orderIntentInfo.setRemark(goods.getRemark());//说明
			orderIntentInfo.setOrderId(order == null ? -1 : order.getId());
		}
		return orderIntentInfo;
	}

	@Override
	public OrderIntent findOnebyTruckOwnerIdAndGoodsId(Long truckOwnerId,
			Long goodsId) {
		return orderIntentRepository.findByTruckOwnerIdAndGoodsId(truckOwnerId,goodsId);
	}

	@Override
	public List<Truck> viewOrderIntentTrucks(Long orderIntentId) {
		OrderIntent orderIntent = orderIntentRepository.findOne(orderIntentId);
		List<Truck> listTruck = new ArrayList<Truck>();
		if ( orderIntent != null && orderIntent.getTruckIds() != null) {
			String[] truckIdList = orderIntent.getTruckIds().split(Consts.SPLIT.COMMA);
			for(String truckId : truckIdList) {
				listTruck.add(truckRepository.findOne(Long.parseLong(truckId)));
			}
		}
		return listTruck;

	}
	
    /*
     * 货主， 确认发货
     */
	@Override
	public void updateStatus(Long orderIntentId, List<Truck> truckList) {
		OrderIntent orderIntent = orderIntentRepository.findOne(orderIntentId);
		if ( orderIntent != null && orderIntent.getTruckIds() != null) {
			LogisticsOrder logisticsOrder = logisticsOrderRepository.findOneByGoodsId(orderIntent.getGoodsId());
			if (logisticsOrder != null && logisticsOrder.getStatus() == Consts.Order.STATUS_ORDER_INTENT && orderIntent.getStatus() == Consts.Intents.NO_CONFIRM) {
				boolean canUse = false;
				//如果未抢单 进制修改
				for (Truck truckInfo : truckList) {
					if (truckInfo.getId() == null || truckInfo.getTruckStatus()  == 0 
							|| !orderIntent.getTruckIds().contains(Long.toString(truckInfo.getId()))) {
						throw new CodeException(ReturnCode.INVALID_PARAMETER, "参数错误");
					}
					Truck truck = truckRepository.findOne(truckInfo.getId());
					if (truck.getTruckStatus() != Consts.Truck.FULL) {
						canUse = true;
					}
					truck.setTruckStatus(truckInfo.getTruckStatus());
					if(truckInfo.getTruckStatus() != Consts.Truck.FULL){
						truck.setIntentPermission(Consts.Truck.INTENT_PERMISION); //释放车辆
					}
					truckRepository.save(truck);
				}
				if(!canUse){
					throw new CodeException(ReturnCode.BUSINESS_ERROR, "车辆已经满载，无法选择该车辆发货，请联系车主确认车辆情况，或选择其他车辆发货");
				}
				logisticsOrder.setTruckOwnerId(orderIntent.getTruckOwnerId());
				logisticsOrder.setStatus(Consts.Order.STATUS_GOODOWNER_CONFIRM);
				orderIntent.setStatus(Consts.Order.STATUS_GOODOWNER_CONFIRM);
				logisticsOrderRepository.save(logisticsOrder);
				orderIntentRepository.save(orderIntent);
				
				//释放其他车辆
				Page<OrderIntent> page  = orderIntentRepository.findByGoodsId(orderIntent.getGoodsId(), 
						new SimplePageable(0, Integer.MAX_VALUE));
				if (page != null && page.getSize() > 0) {
					for (OrderIntent orderIntentTmp : page.getContent()) {
						if (orderIntentTmp.getTruckOwnerId() == orderIntent.getTruckOwnerId()) {
							orderIntentRepository.save(orderIntentTmp);
							continue;
						}
						refuseOrderIntent(orderIntentTmp);
//						String[] truckIds = orderIntentTmp.getTruckIds().split(Consts.SPLIT.COMMA);
//						for (String truckId : truckIds) {
//							Truck truck = truckRepository.findOne(Long.parseLong(truckId));
//							if (truck != null) {
//								truck.setIntentPermission(Consts.Truck.INTENT_PERMISION);
//								truckRepository.save(truck);
//							}
//						}
//						//其他抢单状态改为 已拒绝
//						orderIntentTmp.setStatus(Consts.Intents.REFUSE);
//						orderIntentRepository.save(orderIntentTmp);
					}
				}
			}
			else {
				throw new CodeException(ReturnCode.BUSINESS_ERROR, "该货物的运单不存在或者货单状态不正确，无法修改车辆的状态！");
			}
		}
		else {
			throw new CodeException(ReturnCode.INVALID_PARAMETER, "参数错误");
		}
	}
	
	
    /*
     * 车主， 确认提货
     */
	@Override
	public void updateResidualCapacity(Long orderIntentId, List<Truck> truckList) {
		OrderIntent orderIntent = orderIntentRepository.findOne(orderIntentId);
		if ( orderIntent != null && orderIntent.getTruckIds() != null) {
			LogisticsOrder logisticsOrder = logisticsOrderRepository.findOneByGoodsId(orderIntent.getGoodsId());
			if (logisticsOrder != null && logisticsOrder.getStatus() == Consts.Order.STATUS_GOODOWNER_CONFIRM && orderIntent.getStatus() == Consts.Order.STATUS_GOODOWNER_CONFIRM) {
				//如果货主未确认，禁止修改
				for (Truck truckInfo : truckList) {
					if (truckInfo.getId() == null  
							|| truckInfo.getResidualCapacity()  == null || truckInfo.getResidualCapacity().equals("") 
							|| !orderIntent.getTruckIds().contains(Long.toString(truckInfo.getId()))) {
						throw new CodeException(ReturnCode.INVALID_PARAMETER, "参数错误");
					}
					Truck truck = truckRepository.findOne(truckInfo.getId());
					if (truck.getTruckStatus() == Consts.Truck.HALF_FULL) {
						truck.setResidualCapacity(truckInfo.getResidualCapacity());
					}
					truckRepository.save(truck);
				}
				logisticsOrder.setStatus(Consts.Order.STATUS_TRUCKOWNER_CONFIRM);
				orderIntent.setStatus(Consts.Order.STATUS_TRUCKOWNER_CONFIRM);
				orderIntentRepository.save(orderIntent);
				logisticsOrderRepository.save(logisticsOrder);
			}
			else {
				throw new CodeException(ReturnCode.BUSINESS_ERROR, "该货物的运单不存在或者货单状态不正确，无法修改车辆的剩余容量");
			}
		}
		else {
			throw new CodeException(ReturnCode.INVALID_PARAMETER, "参数错误");
		}
		
	}

	
	/*
	 * 	货主，确认到货
	 */
	@Override
	public void confirmOrders(Long orderIntentId) {

		OrderIntent orderIntent = orderIntentRepository.findOne(orderIntentId);
		if ( orderIntent != null && orderIntent.getTruckIds() != null && orderIntent.getStatus() == Consts.Order.STATUS_TRUCKOWNER_CONFIRM) {
			String[] truckIdList = orderIntent.getTruckIds().split(Consts.SPLIT.COMMA);
			//1. 查找该货主其他未完成的运单 -- 从货主确认之后开始算起 状态为3或者4 
			List<LogisticsOrder>  logisticsOrderList = logisticsOrderRepository.findNoFinishedOrderByTruckOwner(orderIntent.getTruckOwnerId());
			for(String truckId : truckIdList) {			
				boolean findTrucks = false;
				Truck truck = truckRepository.findOne(Long.parseLong(truckId));
				for (LogisticsOrder logisticsOrder : logisticsOrderList) {
					if (logisticsOrder.getGoodsId() == orderIntent.getGoodsId()) {
						//当 运单 goods ID 和 本抢单的goods ID相等时， 忽略
						continue;
					}
					OrderIntent orderIntentTmp = 
							orderIntentRepository.findByTruckOwnerIdAndGoodsId(logisticsOrder.getTruckOwnerId(), logisticsOrder.getGoodsId());
					if (orderIntentTmp.getTruckIds().contains(truckId)) {
						//在其他的满足条件的运单当中， 找对应的抢单， 如果找到并包含车的id，则设置为半满
						truck.setTruckStatus(Consts.Truck.HALF_FULL);
						truck.setResidualCapacity("");
						findTrucks = true;
						break;
					}	
				}

				if (!findTrucks) {
					//3. 如果不存在，车设置为空
					truck.setTruckStatus(Consts.Truck.EMPTY);
					truck.setResidualCapacity("");
				}
				//释放所有的车辆到 可抢单的车列表中
				truck.setIntentPermission(Consts.Truck.INTENT_PERMISION);
				truckRepository.save(truck);
			}
			
			//4.运单状态改为5.
			for (LogisticsOrder logisticsOrder : logisticsOrderList) {
				if (logisticsOrder.getGoodsId() == orderIntent.getGoodsId()) {
					//当goodsId 和 truckOwner相等时， 找到了运单， 改状态为完成。
					logisticsOrder.setStatus(Consts.Order.STATUS_FINISHED);
					orderIntent.setStatus(Consts.Order.STATUS_FINISHED);
					orderIntentRepository.save(orderIntent);
					logisticsOrder.setOrderTime(new Date());
					logisticsOrderRepository.save(logisticsOrder);
					break;
				}
			}
			
			DictScoreItem scoreItem = scoreItemRepository.findInfobyCode(Consts.ScoreItem.GOODOWNER_FINISH_ORDER);
			scorRepository.save(new Score(SessionInfo.getCurrent().getUserInfo().getUserId(), 
					new Date(), scoreItem.getName(), scoreItem.getScore()));
		}
		else {
			throw new CodeException(ReturnCode.BUSINESS_ERROR, "抢单不存在或者抢单信息异常！");
		}
		
	}

	/*
	 * 货主主动选择车
	 * 
	 * 如果该车主已对该货物抢单，则货主不能再对此车辆选择用车
	 * 货主不能使用同一货物，对车主的不通车辆，分别选择用车
	 */
	@Override
	public IntentFromGoodsOwner create4GoodsOwner(
			IntentFromGoodsOwner info, UserInfo userInfo) {
		
		OrderIntent orderIntent = null;
		long goodsId = info.getGoodsId();
		//修改运单表状态
		LogisticsOrder order = logisticsOrderRepository.findOneByGoodsId(goodsId);
		if(order != null && order.getStatus() > Consts.Order.STATUS_ORDER_INTENT){ //如果该货物已经发货，则不能再被抢单
			throw new CodeException(ReturnCode.BUSINESS_ERROR, "该货物您已确认发货，请选择其他货物");
		}
		Truck truck = truckRepository.findOne(info.getTruckId());
		if(truck == null || truck.getTruckStatus() == Consts.Truck.FULL){
			//如果车辆已经满载或者车辆不能抢单状态
			throw new CodeException(ReturnCode.BUSINESS_ERROR, "该车源暂时不可使用，请选择其他车辆");
		}
        if (userInfo.getRole() == Consts.Role.DRIVER) {
            Driver driver = driverRepository.findOnebyUserId(userInfo.getUserId());
            info.setContactName(driver.getName());
            info.setContactMobile(driver.getMobile());
        } else {
            TruckOwner truckOwner = truckOwnerRepository.findOneByUserId(truck.getTruckOwnerId());
            info.setContactName(truckOwner.getName());
            info.setContactMobile(truckOwner.getMobile());
        }
		
		orderIntent = new OrderIntent();
		orderIntent.setTruckOwnerId(userInfo.getUserId());
		orderIntent.setGoodsId(goodsId);
		orderIntent.setTruckIds(Long.toString(info.getTruckId()));
		orderIntent.setInitiator(info.getInitiator());
		orderIntent.setApplyTime(new Date());
		orderIntent = orderIntentRepository.save(orderIntent);
		
		order.setStatus(Consts.Order.STATUS_ORDER_INTENT); //已抢单
		logisticsOrderRepository.save(order);		
		info.setOrderIntentId(orderIntent.getId());
		return info;
	}

	@Override
	public ListSlice<OrderIntentInfo> findListSliceOfTruckOwner(
			Long truckOwnerId, int from, int max, int status, int initiator) {
		Page<OrderIntent> page = null ;
		if (status == 0 && initiator == 0) {
			page = orderIntentRepository.findByTruckOwnerId(truckOwnerId, new SimplePageable(from, max));
		}
		else if (status == 0 && initiator != 0) {
			page = orderIntentRepository.findByTruckOwnerIdAndInitiator(truckOwnerId, initiator, new SimplePageable(from, max));
		}
		else if (status != 0 && initiator == 0) {
			page = orderIntentRepository.findByTruckOwnerIdAndStatus(truckOwnerId, status, new SimplePageable(from, max));
		}else {
			page = orderIntentRepository.findByTruckOwnerIdAndInitiatorAndStatus(truckOwnerId, initiator, status, new SimplePageable(from, max));
		}

		
		if (page != null && page.getSize() > 0) {
			List<OrderIntentInfo> orderIntentInfos = new ArrayList<OrderIntentInfo>();
			Goods goods = null;
			for (OrderIntent orderIntent : page) {
				
				OrderIntentInfo orderIntentInfo = new OrderIntentInfo();
				LogisticsOrder logisticsOrder = logisticsOrderRepository.findOneByGoodsId(orderIntent.getGoodsId());
				if (logisticsOrder != null && logisticsOrder.getTruckOwnerId() == truckOwnerId) {
					if(logisticsOrder.getStatus() >= Consts.Order.STATUS_GOODOWNER_CONFIRM) {
						//货主已确认，此时都按照运单状态进行，因为给客户端加了一个状态导致此处必须做特殊判断
						if (orderIntent.getStatus() != Consts.Order.STATUS_TRUCK_EVALUTED
								&& orderIntent.getStatus() != logisticsOrder.getStatus()) {
							orderIntent.setStatus(logisticsOrder.getStatus());
						}
						orderIntentInfo.setCommentFromGoodsOwner(logisticsOrder.getCommentFromGoodsOwner());
						orderIntentInfo.setScoreFromGoodsOwner(logisticsOrder.getScoreFromGoodsOwner());
						orderIntentInfo.setGoodsOwnerIsEvaluated(logisticsOrder.getGoodsOwnerIsEvaluated());
					}
					
				}
				goods = goodsRepository.findOne(orderIntent.getGoodsId());
				orderIntentInfos.add(makeOrderIntentInfo(orderIntentInfo, orderIntent, goods));

			}
			return new ListSlice<OrderIntentInfo>(page.getTotalElements(), orderIntentInfos);
		}
		return null;
	}

    @Override
    public OrderIntent findByTruckOwnerIdAndGoodsId(Long truckOwnerId, Long goodsId) {
        return orderIntentRepository.findByTruckOwnerIdAndGoodsId(truckOwnerId, goodsId);
    }
	
	

}
