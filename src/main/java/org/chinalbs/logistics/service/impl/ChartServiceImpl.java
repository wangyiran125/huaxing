package org.chinalbs.logistics.service.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.chinalbs.logistics.common.utils.ListSlice;
import org.chinalbs.logistics.domain.Goods;
import org.chinalbs.logistics.domain.GoodsOwner;
import org.chinalbs.logistics.domain.LogisticsUser;
import org.chinalbs.logistics.domain.OrderIntent;
import org.chinalbs.logistics.domain.Truck;
import org.chinalbs.logistics.domain.TruckOwner;
import org.chinalbs.logistics.repository.DictDistrictRepository;
import org.chinalbs.logistics.repository.DictGoodsNameRepository;
import org.chinalbs.logistics.repository.GoodsOwnerRepository;
import org.chinalbs.logistics.repository.GoodsRepository;
import org.chinalbs.logistics.repository.LogisticsOrderRepository;
import org.chinalbs.logistics.repository.LogisticsUserRepository;
import org.chinalbs.logistics.repository.OrderIntentRepository;
import org.chinalbs.logistics.repository.ScoreRepository;
import org.chinalbs.logistics.repository.TruckOwnerRepository;
import org.chinalbs.logistics.repository.TruckRepository;
import org.chinalbs.logistics.service.ChartService;
import org.chinalbs.logistics.utils.Consts;
import org.chinalbs.logistics.utils.SimplePageable;
import org.chinalbs.logistics.vo.ChartInfo;
import org.chinalbs.logistics.vo.ChartSingleInfo;
import org.chinalbs.logistics.vo.ChartValueInfo;
import org.chinalbs.logistics.vo.OrderChartInfo;
import org.chinalbs.logistics.vo.UserChartInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ChartServiceImpl implements ChartService {

	@Autowired
	private LogisticsOrderRepository orderRepository;
	@Autowired
	private LogisticsUserRepository userRepository;
	@Autowired
	private GoodsRepository goodsRepository;
	@Autowired
	private TruckRepository truckRepository;
	@Autowired
	private DictGoodsNameRepository dictGoodsNameRepository;
	@Autowired
	private DictDistrictRepository dictDistrictRepository;
	@Autowired
	private OrderIntentRepository orderIntentRepository;
	@Autowired
	private GoodsOwnerRepository goodsOwnerRepository;
	@Autowired
	private TruckOwnerRepository truckOwnerRepository;
	@Autowired
	private ScoreRepository scoreRepository;
	@PersistenceContext()
	private EntityManager entityManager;  
	
	//订单状态：1、（货主）已发布；2、已抢单；3、（货主）确认发货 4、（车主）确认提货 5，（货主）确认到货， 
	private static final String[] statusDesc = {"成单数", "抢单数", "发货数"};
	private static final int[] status = {Consts.Order.STATUS_FINISHED, Consts.Order.STATUS_ORDER_INTENT, Consts.Order.STATUS_PUBLISHED};
	
	//用户角色
	private static final String[] userRoleDesc = {"货主", "车主", "仓库主", "司机", "管理员"};
	private static final int[] userRole = {Consts.Role.GOODSOWNER, Consts.Role.TRUCKOWNER, Consts.Role.WAREHOUSEOWNER, Consts.Role.DRIVER, Consts.Role.ADMIN};
	
	private static final String DATE_YYYYMMDD = "yyyyMMdd";
	private static final String DATE_YYYY_MM_DD = "yyyy-MM-dd";

	@Override
	public ChartInfo getOrderChart() {
		ChartValueInfo[] data = new ChartValueInfo[statusDesc.length];
		ChartInfo info = new ChartInfo();
		info.setxCategories(statusDesc);
		List<ChartSingleInfo> singleInfos = new ArrayList<ChartSingleInfo>();
		ChartSingleInfo  singleInfo = new ChartSingleInfo();
		singleInfo.setName("运单数量");
		for(int i = 0; i < status.length; i++){
			ChartValueInfo valueInfo = new ChartValueInfo();
			valueInfo.setName(statusDesc[i]);
			valueInfo.setY(getOrderCountByStatus(status[i]));
			data[i] = valueInfo;
		}
		singleInfo.setData(data);
		singleInfos.add(singleInfo);
		info.setSeries(singleInfos);
		return info;
	}
	
	private long getOrderCountByStatus(int status){
		long count = 0;
		switch (status) {
			case Consts.Order.STATUS_FINISHED:
				count = orderRepository.countByStatus(status);
				break;
			case Consts.Order.STATUS_ORDER_INTENT:
				count = orderIntentRepository.countNotDeleted();
				break;
			case Consts.Order.STATUS_PUBLISHED:
				count = goodsRepository.countNotDeleted();
				break;
			default:
				break;
		}
		return count;
	}
	
	private List<ChartValueInfo> getOrderEachMonthCountByStatusAndTime(int status, String start,
			String end) {
		List<ChartValueInfo> valueInfos = new ArrayList<ChartValueInfo>();
		switch (status) {
			case Consts.Order.STATUS_FINISHED:
				valueInfos = orderRepository.findEachMonthCountByStatusAndDate(status, start, end);
				break;
			case Consts.Order.STATUS_ORDER_INTENT:
				valueInfos = orderIntentRepository.findEachMonthCountByStatusAndDate(start, end);
				break;
			case Consts.Order.STATUS_PUBLISHED:
				valueInfos = goodsRepository.findEachMonthCountByStatusAndDate(start, end);
				break;
			default:
				break;
		}
		return valueInfos;
	}
	
	private List<ChartValueInfo> getOrderEachDayCountByStatusAndTime(int status, String start,
			String end) {
		List<ChartValueInfo> valueInfos = new ArrayList<ChartValueInfo>();
		switch (status) {
			case Consts.Order.STATUS_FINISHED:
				valueInfos = orderRepository.findEachDayCountByStatusAndDate(status, start, end);
				break;
			case Consts.Order.STATUS_ORDER_INTENT:
				valueInfos = orderIntentRepository.findEachDayCountByStatusAndDate(start, end);
				break;
			case Consts.Order.STATUS_PUBLISHED:
				valueInfos = goodsRepository.findEachDayCountByStatusAndDate(start, end);
				break;
			default:
				break;
		}
		return valueInfos;
	}

	@Override
	public ChartInfo getOrderIncreaseChart(String type) {
		String[] categories = getCategories(type);
		if(categories == null){
			categories = statusDesc;
		}
		ChartInfo info = new ChartInfo();
		info.setxCategories(categories);
		List<ChartSingleInfo> singleInfos = new ArrayList<ChartSingleInfo>();
		ChartValueInfo[] data = new ChartValueInfo[categories.length];
		List<ChartValueInfo> valueInfos = new ArrayList<ChartValueInfo>();
		if("date".equals(type)){
			ChartSingleInfo  singleInfo = new ChartSingleInfo();
			singleInfo.setName("运单数量");
			Calendar now = Calendar.getInstance();
			now.add(Calendar.DATE, -1);
			String date = DateFormatUtils.format(now, DATE_YYYYMMDD);
			for(int i = 0; i < status.length; i++){
				ChartValueInfo valueInfo = new ChartValueInfo();
				valueInfo.setName(statusDesc[i]);
				double count = 0;
				switch (status[i]) {
				case Consts.Order.STATUS_FINISHED:
					count = orderRepository.countByStatusAndDate(status[i], date);
					break;
				case Consts.Order.STATUS_ORDER_INTENT:
					count = orderIntentRepository.countByDateNotDeleted(date);
					break;
				case Consts.Order.STATUS_PUBLISHED:
					count = goodsRepository.countByDateNotDeleted(date);
					break;
				default:
					break;
			}
				valueInfo.setY(count);
				data[i] = valueInfo;
			}
			singleInfo.setData(data);
			singleInfos.add(singleInfo);
		}else{
			for(int x = 0; x < status.length; x++){
				ChartSingleInfo  singleInfo = new ChartSingleInfo();
				singleInfo.setName(statusDesc[x]);
				Calendar start = Calendar.getInstance();
				Calendar end = Calendar.getInstance();
				String s = null;
				String e = null;
				if("year".equals(type)){
					start.set(Calendar.MONTH, 0);
					s = DateFormatUtils.format(start, DATE_YYYYMMDD);
					e = DateFormatUtils.format(end, DATE_YYYYMMDD);
					valueInfos = getOrderEachMonthCountByStatusAndTime(status[x], s, e);
				}else if("month".equals(type)){
					start.set(Calendar.DAY_OF_MONTH, 1);
					s = DateFormatUtils.format(start, DATE_YYYYMMDD);
					e = DateFormatUtils.format(end, DATE_YYYYMMDD);
					valueInfos = getOrderEachDayCountByStatusAndTime(status[x], s, e);
				}else if("day".equals(type)){
					int day = start.get(Calendar.DAY_OF_WEEK);
					if(day == 1){
						start.add(Calendar.DATE, -6);
					}else{
						start.add(Calendar.DATE, 2 - day);
					}
					s = DateFormatUtils.format(start, DATE_YYYYMMDD);
					e = DateFormatUtils.format(end, DATE_YYYYMMDD);
					valueInfos = getOrderEachDayCountByStatusAndTime(status[x], s, e);
				}
				data = makeSingInfos(valueInfos, categories);
				singleInfo.setData(data);
				singleInfos.add(singleInfo);
			}
		}
		
		info.setSeries(singleInfos);
		return info;
	}

	private ChartValueInfo[] makeSingInfos(List<ChartValueInfo> valueInfos,
			String[] categories) {
		ChartValueInfo[] data = new ChartValueInfo[categories.length];
		for(int i = 0; i < categories.length; i++){
			String s = categories[i];
			ChartValueInfo info = null;
			for(ChartValueInfo vi : valueInfos){
				if(vi.getName().equals(s)){
					info = vi;
					break;
				}
			}
			if(info == null){
				info = new ChartValueInfo();
				info.setName(s);
				info.setY(0);
			}
			data[i] = info;
		}
		return data;
	}

	private String[] getCategories(String type) {
		String [] categories = null;
		List<String> cl = new ArrayList<String>();
		Calendar now = Calendar.getInstance();
		Calendar start = Calendar.getInstance();
		if("day".endsWith(type)){
			int day = start.get(Calendar.DAY_OF_WEEK);
			if(day == 1){
				start.add(Calendar.DATE, -6);
			}else{
				start.add(Calendar.DATE, 2 - day);
			}
			while(!start.after(now)){
				cl.add(DateFormatUtils.format(start, DATE_YYYYMMDD));
				start.add(Calendar.DATE, 1);
			}
			categories = new String[cl.size()];
			categories = cl.toArray(categories);
		}else if("month".endsWith(type)){
			start.set(Calendar.DAY_OF_MONTH, 1);
			int dayOfMonth = now.get(Calendar.DAY_OF_MONTH);
			int step = 1;
//			if(dayOfMonth > 20){
//				step = 3;
//			}
			while(!start.after(now)){
				dayOfMonth = start.get(Calendar.DAY_OF_MONTH);
				if(dayOfMonth % step == 0){
					cl.add(DateFormatUtils.format(start, DATE_YYYYMMDD));
				}else{
					cl.add("");
				}
				
				start.add(Calendar.DATE, 1);
			}
			categories = new String[cl.size()];
			categories = cl.toArray(categories);
		}else if("year".equals(type)){
			start.set(Calendar.MONTH, 0);
			while(!start.after(now)){
				cl.add(DateFormatUtils.format(start, "MM月"));
				start.add(Calendar.MONTH, 1);
			}
			categories = new String[cl.size()];
			categories = cl.toArray(categories);
		}
		return categories;
	}

	@Override
	public ChartInfo getUserChart() {
		ChartValueInfo[] data = new ChartValueInfo[userRole.length];
		ChartInfo info = new ChartInfo();
		info.setxCategories(userRoleDesc);
		List<ChartSingleInfo> singleInfos = new ArrayList<ChartSingleInfo>();
		ChartSingleInfo  singleInfo = new ChartSingleInfo();
		singleInfo.setName("用户数量");
		for(int i = 0; i < userRole.length; i++){
			ChartValueInfo valueInfo = new ChartValueInfo();
			valueInfo.setName(userRoleDesc[i]);
			valueInfo.setY(userRepository.countByRole(userRole[i]));
			data[i] = valueInfo;
		}
		singleInfo.setData(data);
		singleInfos.add(singleInfo);
		info.setSeries(singleInfos);
		return info;
	}

	@Override
	public ChartInfo getUnserIncreaseChart(String type) {
		String[] categories = getCategories(type);
		if(categories == null){
			categories = userRoleDesc;
		}
		ChartInfo info = new ChartInfo();
		info.setxCategories(categories);
		List<ChartSingleInfo> singleInfos = new ArrayList<ChartSingleInfo>();
		ChartValueInfo[] data = new ChartValueInfo[categories.length];
		List<ChartValueInfo> valueInfos = new ArrayList<ChartValueInfo>();
		if("date".equals(type)){
			ChartSingleInfo  singleInfo = new ChartSingleInfo();
			singleInfo.setName("用户数量");
			Calendar now = Calendar.getInstance();
			now.add(Calendar.DATE, -1);
			String date = DateFormatUtils.format(now, DATE_YYYYMMDD);
			for(int i = 0; i < userRole.length; i++){
				ChartValueInfo valueInfo = new ChartValueInfo();
				valueInfo.setName(userRoleDesc[i]);
				valueInfo.setY(userRepository.countByStatusAndDate(userRole[i], date));
				data[i] = valueInfo;
			}
			singleInfo.setData(data);
			singleInfos.add(singleInfo);
		}else{
			for(int x = 0; x < userRole.length; x++){
				ChartSingleInfo  singleInfo = new ChartSingleInfo();
				singleInfo.setName(userRoleDesc[x]);
				Calendar start = Calendar.getInstance();
				Calendar end = Calendar.getInstance();
				if("year".equals(type)){
					start.set(Calendar.MONTH, 0);
					String s = DateFormatUtils.format(start, DATE_YYYYMMDD);
					String e = DateFormatUtils.format(end, DATE_YYYYMMDD);
					valueInfos = userRepository.findEachMonthCountByStatusAndDate(userRole[x], s, e);
				}else if("month".equals(type)){
					start.set(Calendar.DAY_OF_MONTH, 1);
					String s = DateFormatUtils.format(start, DATE_YYYYMMDD);
					String e = DateFormatUtils.format(end, DATE_YYYYMMDD);
					valueInfos = userRepository.findEachDayCountByStatusAndDate(userRole[x], s, e);
				}else if("day".equals(type)){
					int day = start.get(Calendar.DAY_OF_WEEK);
					if(day == 1){
						start.add(Calendar.DATE, -6);
					}else{
						start.add(Calendar.DATE, 2 - day);
					}
					String s = DateFormatUtils.format(start, DATE_YYYYMMDD);
					String e = DateFormatUtils.format(end, DATE_YYYYMMDD);
					valueInfos = userRepository.findEachDayCountByStatusAndDate(userRole[x], s, e);
				}
				data = makeSingInfos(valueInfos, categories);
				singleInfo.setData(data);
				singleInfos.add(singleInfo);
			}
		}
		
		info.setSeries(singleInfos);
		return info;
	}

	@Override
	public ListSlice<UserChartInfo> getUserChartInfo(int role, String name, String phone,
			String startTime, String endTime, int from, int max) {
		ListSlice<UserChartInfo> infos = null;
		name = (name == null) ? "%" : String.format("%%%s%%", name);
		phone = (phone == null) ? "%" : String.format("%%%s%%", phone);
		if(startTime== null || !startTime.matches("\\d{4}-\\d{1,2}-\\d{1,2}")){
			Calendar now = Calendar.getInstance();
			now.setTimeInMillis(0);
			startTime = DateFormatUtils.format(now, DATE_YYYY_MM_DD);
		}
		if(endTime== null || !endTime.matches("\\d{4}-\\d{1,2}-\\d{1,2}")){
			Calendar now = Calendar.getInstance();
			endTime = DateFormatUtils.format(now, DATE_YYYY_MM_DD);
		}
//		Page<? extends UserCommon> users = null;
		String sql = null;
//		Page<UserChartInfo> page = null;
		if(role == Consts.Role.GOODSOWNER){
//			users = goodsOwnerRepository.findPageByNameAndPhone(name, phone, new SimplePageable(from, max));
//			page = goodsOwnerRepository.getGoodsOwnerCommon(name, phone, new SimplePageable(from, max));
			sql = "SELECT uo.userId, uo.username, uo.name, uo.phone, COALESCE(goodsNum, 0) AS goodsNum, 0 AS goodsIntentNum, COALESCE(orderNum, 0) AS orderNum, COALESCE(score, 0) AS score, totle "
					+ "FROM "
					+ "(SELECT u.id AS userId, u.username, o.name, o.phone "
					+ "FROM LogisticsUser u, GoodsOwner o "
					+ "WHERE u.id=o.userId "
					+ "AND COALESCE(o.name, '') LIKE :name "
					+ "AND COALESCE(o.phone, '') LIKE :phone "
					+ "LIMIT :from, :max) uo "
					+ "LEFT JOIN "
					+ "(SELECT g.id AS goodsId, g.goodsOwnerId, COUNT(g.id) AS goodsNum "
					+ "FROM Goods g WHERE g.isDeleted=0 "
					+ "AND DATE(g.publishTime)>=:startTime "
					+ "AND DATE(g.publishTime)<=:endTime "
					+ "GROUP BY g.goodsOwnerId) gc "
					+ "ON uo.userId=gc.goodsOwnerId "
					+ "LEFT JOIN "
					+ "(SELECT lo.goodsId, COUNT(lo.id) AS orderNum,g.goodsOwnerId as userid "
					+ "FROM LogisticsOrder lo,Goods g WHERE lo.goodsId=g.id AND lo.status=5 "
					+ "AND lo.isDeleted=0 "
					+ "AND DATE(lo.orderTime)>=:startTime "
					+ "AND DATE(lo.orderTime)<=:endTime "
					+ "GROUP BY g.goodsOwnerId) oc "
					+ "ON uo.userid=oc.userid "
					+ "LEFT JOIN "
					+ "(SELECT s.userId, SUM(s.points) AS score FROM Score s GROUP BY s.userId) sc "
					+ "ON uo.userId=sc.userId ,"
					+ "(SELECT COUNT(u.id) AS totle FROM LogisticsUser u, GoodsOwner o WHERE u.id=o.userId ) cu "
					+ "GROUP BY uo.userId";
		}else if(role == Consts.Role.TRUCKOWNER){
//			users = truckOwnerRepository.findPageByNameAndPhone(name, phone, new SimplePageable(from, max));
//			page = truckOwnerRepository.findPageTruckOwnerByNameAndPhone(name, phone, new SimplePageable(from, max));
			sql = "SELECT uo.userId, uo.username, uo.name, uo.phone, 0 AS goodsNum, COALESCE(goodsIntentNum, 0) AS goodsIntentNum, COALESCE(orderNum, 0) AS orderNum, COALESCE(score, 0) AS score, totle "
					+ "FROM "
					+ "(SELECT u.id AS userId, u.username, o.name, o.phone "
					+ "FROM LogisticsUser u, TruckOwner o "
					+ "WHERE u.id=o.userId "
					+ "AND COALESCE(o.name, '') LIKE :name "
					+ "AND COALESCE(o.phone, '') LIKE :phone "
					+ "LIMIT :from, :max) uo "
					+ "LEFT JOIN "
					+ "(SELECT oi.truckOwnerId, COUNT(oi.id) AS goodsIntentNum "
					+ "FROM OrderIntent oi WHERE oi.isDeleted=0 "
					+ "AND DATE(oi.applyTime)>=:startTime "
					+ "AND DATE(oi.applyTime)<=:endTime "
					+ "GROUP BY oi.truckOwnerId) oic "
					+ "ON uo.userId=oic.truckOwnerId "
					+ "LEFT JOIN "
					+ "(SELECT lo.truckOwnerId, COUNT(lo.id) AS orderNum "
					+ "FROM LogisticsOrder lo WHERE lo.status=5 "
					+ "AND lo.isDeleted=0 "
					+ "AND DATE(lo.orderTime)>=:startTime "
					+ "AND DATE(lo.orderTime)<=:endTime "
					+ "GROUP BY lo.truckOwnerId) oc "
					+ "ON uo.userId=oc.truckOwnerId "
					+ "LEFT JOIN "
					+ "(SELECT s.userId, SUM(s.points) AS score FROM Score s GROUP BY s.userId) sc "
					+ "ON uo.userId=sc.userId ,"
					+ "(SELECT COUNT(u.id) AS totle FROM LogisticsUser u, TruckOwner o WHERE u.id=o.userId ) cu "
					+ "GROUP BY uo.userId";
		}
		Query query = entityManager.createNativeQuery(sql);
		query.setParameter("name", name);
		query.setParameter("phone", phone);
		query.setParameter("startTime", startTime);
		query.setParameter("endTime", endTime);
		query.setParameter("from", from);
		query.setParameter("max", max);
//		query.setFirstResult(from);
//		query.setMaxResults(max);
		List<?> result = query.getResultList();
		List<UserChartInfo> userInfos = new ArrayList<UserChartInfo>();
		long totle = 0;
		for(Object o : result){
			UserChartInfo info = new UserChartInfo();
			Object[] objs = (Object[])o;
//			BeanUtils.copyProperties(o, info);
			info.setUserId(((BigInteger)objs[0]).longValue());
			info.setUsername((String) objs[1]);
			info.setName((String)objs[2]);
			info.setPhone((String)objs[3]);
			info.setGoodsNum(((BigInteger)objs[4]).longValue());
			info.setGoodsIntentNum(((BigInteger)objs[5]).longValue());
			info.setOrderNum(((BigInteger)objs[6]).longValue());
			info.setScore(((BigDecimal)objs[7]).intValue());
			userInfos.add(info);
			totle = ((BigInteger)objs[8]).longValue();
		}
		infos = new ListSlice<UserChartInfo>(totle, userInfos);
		return infos;
	}

	@Override
	public ListSlice<OrderChartInfo> getOrderByStatusAndType(int status, String type, int from, int max) {
		Page<?> page = null;
		List<OrderChartInfo> infos = null;
		Calendar start = null;
		Calendar end = null;
		if ("all".equals(type)) {
			page = getPage(status, null, null, from, max);
			infos = makeOrderChartInfos(page, new ArrayList<OrderChartInfo>(), status);
		}else if("date".equals(type)) {
			start = Calendar.getInstance();
			start.add(Calendar.DATE, -1);
			String date = DateFormatUtils.format(start, DATE_YYYYMMDD);
			page = getPage(status, date, null, from, max);
			infos = makeOrderChartInfos(page, new ArrayList<OrderChartInfo>(), status);
		}else if("day".equals(type)) {
			start = Calendar.getInstance();
			end = Calendar.getInstance();
			int day = start.get(Calendar.DAY_OF_WEEK);
			if(day == 1){
				start.add(Calendar.DATE, -6);
			}else{
				start.add(Calendar.DATE, 2 - day);
			}
			page = getPage(status,DateFormatUtils.format(start, DATE_YYYYMMDD), DateFormatUtils.format(end, DATE_YYYYMMDD), from, max);
			infos = makeOrderChartInfos(page, new ArrayList<OrderChartInfo>(), status);
		}else if("month".equals(type)) {
			start = Calendar.getInstance();
			end = Calendar.getInstance();
			start.set(Calendar.DAY_OF_MONTH, 1);
			page = getPage(status,DateFormatUtils.format(start, DATE_YYYYMMDD), DateFormatUtils.format(end, DATE_YYYYMMDD), from, max);
			infos = makeOrderChartInfos(page, new ArrayList<OrderChartInfo>(), status);
		}else if("year".equals(type)) {
			start = Calendar.getInstance();
			end = Calendar.getInstance();
			start.set(Calendar.MONTH, 0);
			page = getPage(status,DateFormatUtils.format(start, DATE_YYYYMMDD), DateFormatUtils.format(end, DATE_YYYYMMDD), from, max);
			infos = makeOrderChartInfos(page, new ArrayList<OrderChartInfo>(), status);
		}
		return new ListSlice<OrderChartInfo>(page.getTotalElements(), infos);
	}
	
	private Page<?> getPage(int status, String start, String end, int from, int max){
		Page<?> page = null;
		switch (status) {
		case Consts.Order.STATUS_FINISHED:
			page = (
					(start == null && end == null) ? goodsRepository.findOrderByStatus(status, new SimplePageable(from, max)) 
							: ((start != null && end == null) ? goodsRepository.findOrderByStatusAndDate(status, start, new SimplePageable(from, max))
						    : goodsRepository.findOrderByStatusAndFromDateAndToDate(status, start, end, new SimplePageable(from, max)))
				   );
			break;
		case Consts.Order.STATUS_ORDER_INTENT:
			page = (
					(start == null && end == null) ? orderIntentRepository.findOrderIntents(new SimplePageable(from, max)) 
							: ((start != null && end == null) ? orderIntentRepository.findOrderIntentByDate(start, new SimplePageable(from, max))
						    : orderIntentRepository.findOrderIntentByFromDateAndToDate(start, end, new SimplePageable(from, max)))
				   );
			break;
		case Consts.Order.STATUS_PUBLISHED:
			page = (
					(start == null && end == null) ? goodsRepository.findGoodsPublished(new SimplePageable(from, max)) 
							: ((start != null && end == null) ? goodsRepository.findGoodsByDate(start, new SimplePageable(from, max))
						    : goodsRepository.findGoodsByFromDateAndToDate(start, end, new SimplePageable(from, max)))
				   );
			break;
		default :
			break;
		}
		return page;
	}
	
	@Override
	public List<OrderChartInfo> getOrderByStatusAndType(int status, String type) {
		List<?> list = null;
		List<OrderChartInfo> infos = null;
		Calendar start = null;
		Calendar end = null;
		if ("all".equals(type)) {
			list = getList(status, null, null);
			infos = makeOrderChartInfos(list, new ArrayList<OrderChartInfo>(), status);
		}else if("date".equals(type)) {
			start = Calendar.getInstance();
			start.add(Calendar.DATE, -1);
			String date = DateFormatUtils.format(start, DATE_YYYYMMDD);
			list = getList(status, date, null);
			infos = makeOrderChartInfos(list, new ArrayList<OrderChartInfo>(), status);
		}else if("day".equals(type)) {
			start = Calendar.getInstance();
			end = Calendar.getInstance();
			int day = start.get(Calendar.DAY_OF_WEEK);
			if(day == 1){
				start.add(Calendar.DATE, -6);
			}else{
				start.add(Calendar.DATE, 2 - day);
			}
			list = getList(status,DateFormatUtils.format(start, DATE_YYYYMMDD), DateFormatUtils.format(end, DATE_YYYYMMDD));
			infos = makeOrderChartInfos(list, new ArrayList<OrderChartInfo>(), status);
		}else if("month".equals(type)) {
			start = Calendar.getInstance();
			end = Calendar.getInstance();
			start.set(Calendar.DAY_OF_MONTH, 1);
			list = getList(status,DateFormatUtils.format(start, DATE_YYYYMMDD), DateFormatUtils.format(end, DATE_YYYYMMDD));
			infos = makeOrderChartInfos(list, new ArrayList<OrderChartInfo>(), status);
		}else if("year".equals(type)) {
			start = Calendar.getInstance();
			end = Calendar.getInstance();
			start.set(Calendar.MONTH, 0);
			list = getList(status,DateFormatUtils.format(start, DATE_YYYYMMDD), DateFormatUtils.format(end, DATE_YYYYMMDD));
			infos = makeOrderChartInfos(list, new ArrayList<OrderChartInfo>(), status);
		}
		return infos;
	}
	
	private List<?> getList(int status, String start, String end){
		List<?> list = null;
		switch (status) {
		case Consts.Order.STATUS_FINISHED:
			list = (
					(start == null && end == null) ? goodsRepository.findOrderByStatus(status) 
							: ((start != null && end == null) ? goodsRepository.findOrderByStatusAndDate(status, start)
						    : goodsRepository.findOrderByStatusAndFromDateAndToDate(status, start, end))
				   );
			break;
		case Consts.Order.STATUS_ORDER_INTENT:
			list = (
					(start == null && end == null) ? orderIntentRepository.findOrderIntents() 
							: ((start != null && end == null) ? orderIntentRepository.findOrderIntentByDate(start)
						    : orderIntentRepository.findOrderIntentByFromDateAndToDate(start, end))
				   );
			break;
		case Consts.Order.STATUS_PUBLISHED:
			list = (
					(start == null && end == null) ? goodsRepository.findGoodsPublished() 
							: ((start != null && end == null) ? goodsRepository.findGoodsByDate(start)
						    : goodsRepository.findGoodsByFromDateAndToDate(start, end))
				   );
			break;
		default :
			break;
		}
		return list;
	}
	
	private OrderChartInfo makeOrderChartInfo(OrderIntent intent, int status){
		Goods goods = goodsRepository.findOne(intent.getGoodsId());
		TruckOwner truckOwner = truckOwnerRepository.findOneByUserId((intent.getTruckOwnerId()));
		return makeOrderChartInfo(goods, status, truckOwner);
	}

	private OrderChartInfo makeOrderChartInfo(Goods goods, int status, TruckOwner truckOwner){
		OrderChartInfo info = new OrderChartInfo();
		GoodsOwner goodsOwner = goodsOwnerRepository.findOneByUserId(goods.getGoodsOwnerId());
		LogisticsUser user = userRepository.findOne(goodsOwner.getUserId());
		info.setGoodsOwnerUsername(user.getUsername());
		info.setGoodsOwnerName(goodsOwner.getName());
		info.setGoodsName(parseTogoodsName((long) goods.getGoodsName()));
		if (status == Consts.Order.STATUS_FINISHED) {
			info.setVolume(goods.getVolume());
			info.setWeight(goods.getWeight());
		}
		info.setDeparture(parseToAddress(goods.getDepartureProvinceCode(), goods.getDepartureCityCode()));
		info.setDestination(parseToAddress(goods.getDestinationProvinceCode(), goods.getDestinationCityCode()));
		info.setDepartureTime(DateFormatUtils.format(goods.getDepartureTime(),DATE_YYYY_MM_DD ));
		info.setShippingPrice((goods.getShippingPrice() == -1) ? "面议" : String.format("%s元/吨", goods.getShippingPrice()/100));
		if (status == Consts.Order.STATUS_FINISHED) {
			truckOwner = truckOwnerRepository.findOneByUserId(orderRepository.findOneByGoodsId(goods.getId()).getTruckOwnerId());
		}
		if (status != Consts.Order.STATUS_PUBLISHED) {
			user = userRepository.findOne(truckOwner.getUserId());
			OrderIntent intent = orderIntentRepository.findByTruckOwnerIdAndGoodsId(user.getId(), goods.getId());
			if (intent != null) {
			    StringBuffer s = new StringBuffer();
			    String[] truckIds = intent.getTruckIds().split(Consts.SPLIT.COMMA);
				for (int i = 0 ; i < truckIds.length;i++) {
					Truck truck = truckRepository.findOne(Long.parseLong(truckIds[i]));
					if (truck != null) {
						s.append(truck.getLicensePlateNumber());
						if (i != truckIds.length -1) {
							s.append(Consts.SPLIT.COMMA);
						}
					}
				}
				info.setLicensePlateNumbers(s.toString());
			}
			info.setTruckOwnerUsername(user.getUsername());
			info.setTruckOwnerName(truckOwner.getName());
			
		}
		return info;
	}
	
	private List<OrderChartInfo> makeOrderChartInfos(Page<?> page, List<OrderChartInfo> infos, int status){
		if (page != null && page.getTotalElements() > 0) {
			if (status == Consts.Order.STATUS_ORDER_INTENT) {
				for (Object obj : page) {
					OrderChartInfo info = makeOrderChartInfo((OrderIntent)obj, status);
					infos.add(info);
				}
			}else {
				for (Object obj : page) {
					OrderChartInfo info = makeOrderChartInfo((Goods)obj, status, null);
					infos.add(info);
				}
			}
		}
		return infos;
	}
	
	private List<OrderChartInfo> makeOrderChartInfos(List<?> list, List<OrderChartInfo> infos, int status){
		if (list != null && list.size() > 0) {
			if (status == Consts.Order.STATUS_ORDER_INTENT) {
				for (Object obj : list) {
					OrderChartInfo info = makeOrderChartInfo((OrderIntent)obj, status);
					infos.add(info);
				}
			}else {
				for (Object obj : list) {
					OrderChartInfo info = makeOrderChartInfo((Goods)obj, status, null);
					infos.add(info);
				}
			}
		}
		return infos;
	}
	
	private String parseTogoodsName(Long code){
		return dictGoodsNameRepository.findOne(code).getName();
	}
	private String parseToAddress(long provinceCode, long cityCode){
		String province = dictDistrictRepository.findOne(provinceCode).getName();
		String city = dictDistrictRepository.findOne(cityCode).getName();
		return String.format("%s%s", province, city);
	}
//	private ListSlice<UserChartInfo> makeUserChartInfo(
//			Page<? extends UserCommon> users) {
//		List<? extends UserCommon> userCommons = users.getContent();
//		return new ListSlice<UserChartInfo>(users.getTotalElements(), makeUserChartInfos(userCommons));
//	}
//
//	private List<UserChartInfo> makeUserChartInfos(List<? extends UserCommon> userCommons) {
//		List<UserChartInfo> infos = new ArrayList<UserChartInfo>();
//		for(UserCommon u : userCommons){
//			infos.add(makeUserChartInfo(u));
//		}
//		return infos;
//	}
//
//	private UserChartInfo makeUserChartInfo(UserCommon u) {
//		UserChartInfo info = new UserChartInfo();
//		info.setName(u.getName());
//		info.setPhone(u.getPhone());
//		LogisticsUser user = userRepository.findOne(u.getUserId());
//		if(user != null){
//			info.setUserId(user.getId());
//			info.setUsername(user.getUsername());
//			if(user.getRole() == Consts.Role.GOODSOWNER){
//				info.setGoodsNum(goodsRepository.countByGoodsOwnerId(u.getId()));
//			}else if(user.getRole() == Consts.Role.TRUCKOWNER){
//				info.setGoodsIntentNum(orderIntentRepository.countByTruckOwnerId(u.getId()));
//			}
//			info.setScore(scoreRepository.findScoreByUserId(user.getId()));
//		}
//		
//		return info;
//	}

	@Override
	public ListSlice<OrderChartInfo> getOrderByUserId(Long userId, int status, String startTime, String endTime, int from, int max) {
		int role = userRepository.findOne(userId).getRole();
		Page<?> page = null;
		List<OrderChartInfo> infos = null;
		if(startTime== null || !startTime.matches("\\d{4}-\\d{1,2}-\\d{1,2}")){
			Calendar now = Calendar.getInstance();
			now.setTimeInMillis(0);
			startTime = DateFormatUtils.format(now, DATE_YYYY_MM_DD);
		}
		if(endTime== null || !endTime.matches("\\d{4}-\\d{1,2}-\\d{1,2}")){
			Calendar now = Calendar.getInstance();
			endTime = DateFormatUtils.format(now, DATE_YYYY_MM_DD);
		}
		if (role == Consts.Role.GOODSOWNER || role == Consts.Role.WAREHOUSEOWNER) {
			page = getPage4GoodsOwner(userId, status, startTime, endTime, from, max);
			infos = makeOrderChartInfos(page, new ArrayList<OrderChartInfo>(), status);
		}else if(role == Consts.Role.TRUCKOWNER || role == Consts.Role.DRIVER){
			page = getPage4TruckOwner(userId, startTime, endTime, status, from, max);
			infos = makeOrderChartInfos(page, new ArrayList<OrderChartInfo>(), status);
		}
		return new ListSlice<OrderChartInfo>(page.getTotalElements(), infos);
	}
	
	private Page<?> getPage4TruckOwner(Long userId, String startTime, String endTime, int status, int from,int max) {
		Page<?> page = null;
		switch (status) {
			case Consts.Order.STATUS_FINISHED:
				page = goodsRepository.findFinishedByTruckOwnerId(userId, startTime, endTime, new SimplePageable(from, max));
			break;
			case Consts.Order.STATUS_ORDER_INTENT:
				page = orderIntentRepository.findByTruckOwnerId(userId, startTime, endTime, new SimplePageable(from, max));
			default:
			break;
		}
		return page;
	}

	private Page<?> getPage4GoodsOwner(Long userId,int status, String startTime, String endTime, int from, int max){
		Page<?> page = null;
		switch (status) {
			case Consts.Order.STATUS_FINISHED:
				page = goodsRepository.findFinishedByGoodsOwnerId(userId, startTime, endTime, new SimplePageable(from, max));
			break;
			case Consts.Order.STATUS_PUBLISHED:
				page = goodsRepository.findGoodsPublishedByGoodsOwnerId(userId, startTime, endTime,  new SimplePageable(from, max));
			default:
				break;
		}
		return page;
	}
	
	@Override
	public List<OrderChartInfo> getOrderByUserId(Long userId, String startTime, String endTime, int status) {
		int role = userRepository.findOne(userId).getRole();
		List<?> list = null;
		List<OrderChartInfo> infos = null;
		if(startTime== null || !startTime.matches("\\d{4}-\\d{1,2}-\\d{1,2}")){
			Calendar now = Calendar.getInstance();
			now.setTimeInMillis(0);
			startTime = DateFormatUtils.format(now, DATE_YYYY_MM_DD);
		}
		if(endTime== null || !endTime.matches("\\d{4}-\\d{1,2}-\\d{1,2}")){
			Calendar now = Calendar.getInstance();
			endTime = DateFormatUtils.format(now, DATE_YYYY_MM_DD);
		}
		if (role == Consts.Role.GOODSOWNER || role == Consts.Role.WAREHOUSEOWNER) {
			list = getList4GoodsOwner(userId,startTime, endTime, status);
			infos = makeOrderChartInfos(list, new ArrayList<OrderChartInfo>(), status);
		}else if(role == Consts.Role.TRUCKOWNER || role == Consts.Role.DRIVER){
			list = getList4TruckOwner(userId, startTime, endTime, status);
			infos = makeOrderChartInfos(list, new ArrayList<OrderChartInfo>(), status);
		}
		return infos;
	}
	
	private List<?> getList4TruckOwner(Long userId, String startTime, String endTime, int status) {
		List<?> list = null;
		switch (status) {
			case Consts.Order.STATUS_FINISHED:
				list = goodsRepository.findFinishedByTruckOwnerId(userId, startTime, endTime);
			break;
			case Consts.Order.STATUS_ORDER_INTENT:
				list = orderIntentRepository.findByTruckOwnerId(userId, startTime, endTime);
			default:
			break;
		}
		return list;
	}

	private List<?> getList4GoodsOwner(Long userId,String startTime, String endTime, int status){
		List<?> list = null;
		switch (status) {
			case Consts.Order.STATUS_FINISHED:
				list = goodsRepository.findFinishedByGoodsOwnerId(userId, startTime, endTime);
			break;
			case Consts.Order.STATUS_PUBLISHED:
				list = goodsRepository.findGoodsPublishedByGoodsOwnerId(userId, startTime, endTime);
			default:
				break;
		}
		return list;
	}

}
