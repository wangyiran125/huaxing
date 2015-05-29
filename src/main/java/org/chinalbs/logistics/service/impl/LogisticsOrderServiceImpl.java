package org.chinalbs.logistics.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.chinalbs.logistics.common.json.ReturnCode;
import org.chinalbs.logistics.common.utils.ListSlice;
import org.chinalbs.logistics.domain.DictScoreItem;
import org.chinalbs.logistics.domain.Goods;
import org.chinalbs.logistics.domain.LogisticsOrder;
import org.chinalbs.logistics.domain.OrderIntent;
import org.chinalbs.logistics.domain.Score;
import org.chinalbs.logistics.domain.Truck;
import org.chinalbs.logistics.error.CodeException;
import org.chinalbs.logistics.repository.LogisticsOrderRepository;
import org.chinalbs.logistics.repository.OrderIntentRepository;
import org.chinalbs.logistics.repository.ScoreItemRepository;
import org.chinalbs.logistics.repository.ScoreRepository;
import org.chinalbs.logistics.service.GoodsService;
import org.chinalbs.logistics.service.LogisticsOrderService;
import org.chinalbs.logistics.service.TruckService;
import org.chinalbs.logistics.service.UserService;
import org.chinalbs.logistics.session.SessionInfo;
import org.chinalbs.logistics.utils.Consts;
import org.chinalbs.logistics.utils.MessageDes;
import org.chinalbs.logistics.vo.HistoryOderInfo;
import org.chinalbs.logistics.vo.OrderDetailInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LogisticsOrderServiceImpl implements LogisticsOrderService{
	
	@Autowired
	private LogisticsOrderRepository logisticsOrderRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private GoodsService goodsService;
	
	@Autowired
	private TruckService truckService;
	
	@Autowired
	private OrderIntentRepository orderIntentRepository;
	
	@Autowired
	private ScoreRepository  scorRepository;
	
	@Autowired
	private ScoreItemRepository  scoreItemRepository;
	
	
	@Override
	public LogisticsOrder findOneByGoodsId(Long goodsId){
		return logisticsOrderRepository.findOneByGoodsId(goodsId);
		
	}

	@Override
	public LogisticsOrder findOneById(Long orderId) {
		return logisticsOrderRepository.findOne(orderId);
	}
	
	/*
	 * 获得所有运单一览
	 */
	@Override
	public ListSlice<HistoryOderInfo> findHistoryOrder(Long userId, int from, int max) {
		ListSlice<HistoryOderInfo>  ListSliceHistoryOder = null;
		
		int role = SessionInfo.getCurrent().getUserInfo().getRole();
		ListSlice<Goods> listSliceGoods = null; 
		if (role == Consts.Role.GOODSOWNER
				|| role == Consts.Role.WAREHOUSEOWNER) {
			//货主
			listSliceGoods = goodsService.findFinishedByGoodsOwnerId(userId, from, max);
			
		}
		else if (role == Consts.Role.TRUCKOWNER
				|| role == Consts.Role.DRIVER) {
			//车主
			listSliceGoods = goodsService.findFinishedByTruckOwnerId(userId, from, max);
		}
		else {
			throw new CodeException(ReturnCode.BUSINESS_ERROR, MessageDes.User.ROLE_ERROR);
		}
		
		long total = listSliceGoods.getTotal();
		List<HistoryOderInfo> listHistoryOder = new ArrayList<HistoryOderInfo>();
		ListSliceHistoryOder = new ListSlice<HistoryOderInfo>(total,listHistoryOder);
		//组合订单 和 货物 进行输出。
		if (total > 0) {
			List<Goods> listGoods = listSliceGoods.getList();
			for (Goods good : listGoods) {
				LogisticsOrder logisticsOrder = findOneByGoodsId(good.getId());
				HistoryOderInfo historyOder = new HistoryOderInfo();
				historyOder.setGoods(good);
				historyOder.setLogisticsOrder(logisticsOrder);
				listHistoryOder.add(historyOder);
			}
		}
		return ListSliceHistoryOder;
		
	}

	/*
	 * 获得某一个运单的详细  
	 *  
	 */
	@Override
	public OrderDetailInfo orderDetail(Long orderId) {
		OrderDetailInfo orderDetailInfo = new OrderDetailInfo();
		LogisticsOrder logisticsOrder= findOneById(orderId);
		if (logisticsOrder == null) {
			throw new CodeException(ReturnCode.BUSINESS_ERROR, MessageDes.Order.NO_ORDER_ID);
		}
		orderDetailInfo.setLogisticsOrder(logisticsOrder);
		orderDetailInfo.setGoods(goodsService.findOneByGoodsId(logisticsOrder.getGoodsId()));
		//司机和车主的情况都已处理，如果司机被删除是否会有异常 TODO
		orderDetailInfo.setTruckOwner(userService.findById(logisticsOrder.getTruckOwnerId()));
		OrderIntent orderIntent = orderIntentRepository.findByTruckOwnerIdAndGoodsId(logisticsOrder.getTruckOwnerId(), logisticsOrder.getGoodsId());
		List<Truck> listTruck = new ArrayList<Truck>();
		orderDetailInfo.setTruckList(listTruck);
		//注意车如果被删除是否会有异常 TODO
		if ( orderIntent != null && orderIntent.getTruckIds() != null  ) {
			String[] truckIdList = orderIntent.getTruckIds().split(Consts.SPLIT.COMMA);
			for(String truckId : truckIdList) {
				listTruck.add(truckService.findOneByTruckId(Long.parseLong(truckId)));
			}
		}
		return orderDetailInfo;
	}

	
	/*
	 * 运单评价
	 *  
	 */
	@Override
	public LogisticsOrder orderEvaluate(LogisticsOrder logisticsOrder, Long userId) {
		
		LogisticsOrder logisticsOrderResult = null;
		if (logisticsOrder.getId() == null ) {
			throw new CodeException(ReturnCode.INVALID_PARAMETER, MessageDes.PARAM_ERROR);
		}
		
		int role = SessionInfo.getCurrent().getUserInfo().getRole();
		if (role == Consts.Role.GOODSOWNER
				|| role == Consts.Role.WAREHOUSEOWNER) {
			//货主 , 参数判断
			if (logisticsOrder.getScoreFromGoodsOwner() < Consts.ScoreItem.TRUCKOWNER_GOT_GOOD_REVIEW
					|| logisticsOrder.getScoreFromGoodsOwner() > Consts.ScoreItem.TRUCKOWNER_GOT_BAD_REVIEW) {
				throw new CodeException(ReturnCode.INVALID_PARAMETER, MessageDes.PARAM_ERROR);
			}
			
			logisticsOrderResult = logisticsOrderRepository.findOne(logisticsOrder.getId());
			if (logisticsOrderResult != null 
					&& logisticsOrderResult.getGoodsOwnerIsEvaluated() == Consts.Order.GOODSOWNER_EVALUATED) {
				//货主已经评价，不可再次评价
				throw new CodeException(ReturnCode.BUSINESS_ERROR, MessageDes.Order.GOODS_EVLUATED);
			}
			
			//记录积分流水
			DictScoreItem scoreItem = scoreItemRepository.findInfobyCode(Long.parseLong(
					Integer.toString(logisticsOrder.getScoreFromGoodsOwner())));
			if (scoreItem == null) {
				throw new CodeException(ReturnCode.BUSINESS_ERROR, MessageDes.Order.NO_SCORE_ITEM);
			}
			scorRepository.save(new Score(logisticsOrderResult.getTruckOwnerId(), 
					new Date(), scoreItem.getName(), scoreItem.getScore()));
			
			//当前订单记录积分 和 评价
			logisticsOrderResult.setScoreFromGoodsOwner(scoreItem.getScore());
			logisticsOrderResult.setCommentLevelFromGoodsOwner(logisticsOrder.getCommentLevelFromGoodsOwner());
			if (logisticsOrder.getCommentFromGoodsOwner() != null 
					&& !logisticsOrder.getCommentFromGoodsOwner().equals("")) {
				logisticsOrderResult.setCommentFromGoodsOwner(logisticsOrder.getCommentFromGoodsOwner());
			}
			
			//设置货主已评价
			logisticsOrderResult.setCommentTimeFromGoodsOwner(new Date());
			logisticsOrderResult.setGoodsOwnerIsEvaluated(Consts.Order.GOODSOWNER_EVALUATED);
			logisticsOrderRepository.save(logisticsOrderResult);

			
		}
		else if (role == Consts.Role.TRUCKOWNER
				|| role == Consts.Role.DRIVER) {
			//车主， 参数判断
			if (logisticsOrder.getScoreFromTruckOwner() < Consts.ScoreItem.GOODOWNER_GOT_GOOD_REVIEW
					|| logisticsOrder.getScoreFromTruckOwner() > Consts.ScoreItem.GOODOWNER_GOT_BAD_REVIEW
					) {
				throw new CodeException(ReturnCode.INVALID_PARAMETER, MessageDes.PARAM_ERROR);
			}
			
			
			logisticsOrderResult = logisticsOrderRepository.findOne(logisticsOrder.getId());
			if (logisticsOrderResult != null 
					&& logisticsOrderResult.getTruckOwnerIsEvaluated() == Consts.Order.TRUCKOWNER_EVALUATED) {
				//车主 已经评价，不可再次评价
				throw new CodeException(ReturnCode.BUSINESS_ERROR, MessageDes.Order.TRUCK_EVLUATED);
			}
			
			//记录积分流水
			DictScoreItem scoreItem = scoreItemRepository.findInfobyCode(Long.parseLong(
					Integer.toString(logisticsOrder.getScoreFromTruckOwner())));
			Goods goods = goodsService.findOneByGoodsId(logisticsOrderResult.getGoodsId());
			if (goods != null ) {
				scorRepository.save(new Score(goods.getGoodsOwnerId(),
						new Date(), scoreItem.getName(), scoreItem.getScore()));
			}
			
			//当前订单记录积分 和 评价
			logisticsOrderResult.setCommentLevelFromTruckOwner(logisticsOrder.getCommentLevelFromTruckOwner());
			logisticsOrderResult.setScoreFromTruckOwner(scoreItem.getScore());
			if (logisticsOrder.getCommentFromTruckOwner() != null 
					&& !logisticsOrder.getCommentFromTruckOwner().equals("")) {
				logisticsOrderResult.setCommentFromTruckOwner(logisticsOrder.getCommentFromTruckOwner());
			}
			
			//设置车主已评价
			logisticsOrderResult.setCommentTimeFromTruckOwner(new Date());
			logisticsOrderResult.setTruckOwnerIsEvaluated(Consts.Order.TRUCKOWNER_EVALUATED);
			logisticsOrderRepository.save(logisticsOrderResult);
			
			//根据客户端的要求，车主评价完 状态设置为6
			LogisticsOrder fullLogisticsOrder = logisticsOrderRepository.findOne(logisticsOrder.getId());
			OrderIntent orderIntent = orderIntentRepository.findByTruckOwnerIdAndGoodsId(userId, fullLogisticsOrder.getGoodsId());
			if (orderIntent != null ) {
				orderIntent.setStatus(Consts.Order.STATUS_TRUCK_EVALUTED);
				orderIntentRepository.save(orderIntent);
			}
		}
		else {
			throw new CodeException(ReturnCode.BUSINESS_ERROR, MessageDes.User.ROLE_ERROR);
		}

		return logisticsOrderResult;
	}
	
	
}
