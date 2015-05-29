package org.chinalbs.logistics.repository;

import java.util.List;

import org.chinalbs.logistics.domain.Goods;
import org.chinalbs.logistics.vo.ChartValueInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface GoodsRepository extends JpaRepository<Goods, Long>{

	@Query("SELECT g FROM Goods g ,LogisticsOrder lo WHERE g.id = lo.goodsId AND lo.status != 5 AND g.isDeleted = 0 AND lo.isDeleted = 0 ORDER BY g.publishTime DESC")
	public Page<Goods> findAll(Pageable pageable);

	@Query("SELECT g FROM Goods g ,LogisticsOrder lo WHERE g.id = lo.goodsId AND lo.status <= 2 AND g.isDeleted = 0 AND lo.isDeleted = 0 ORDER BY g.publishTime DESC")
	public Page<Goods> findWaitGoodsAll(Pageable pageable);
	
	@Query("SELECT g FROM Goods g ,LogisticsOrder lo WHERE g.goodsOwnerId = ?1 AND g.id = lo.goodsId AND lo.status <= 2 AND g.isDeleted = 0 AND lo.isDeleted = 0 ORDER BY g.publishTime DESC")
	public List<Goods> findWaitGoodsByGoodsOwnerId(Long goodsOwnerId);
	
	@Query("SELECT g FROM Goods g ,LogisticsOrder lo WHERE g.id = lo.goodsId AND g.goodsOwnerId = ?1 AND lo.status <= 4 AND g.isDeleted = 0 AND lo.isDeleted = 0 ORDER BY g.publishTime DESC")
	public Page<Goods> findNoFinishedByGoodsOwnerId(Long goodsOwnerId, Pageable pageable);
	
	@Query("SELECT g FROM Goods g ,LogisticsOrder lo WHERE g.id = lo.goodsId AND g.goodsOwnerId = ?1 AND lo.status = 5 AND g.isDeleted = 0 AND lo.isDeleted = 0 ORDER BY lo.orderTime DESC")
	public Page<Goods> findFinishedByGoodsOwnerId(Long goodsOwnerId, Pageable pageable);
	
	@Query("SELECT g FROM Goods g ,LogisticsOrder lo WHERE g.id = lo.goodsId AND lo.truckOwnerId = ?1 AND lo.status = 5 AND g.isDeleted = 0 AND lo.isDeleted = 0 ORDER BY lo.orderTime DESC")
	public Page<Goods> findFinishedByTruckOwnerId(Long truckOwnerId, Pageable pageable);

	/**
	 * 获取某时间段中，按月统计数据
	 * @param status
	 * @param start
	 * @param end
	 * @return
	 */
	@Query("SELECT new org.chinalbs.logistics.vo.ChartValueInfo(DATE_FORMAT(publishTime, '%m月') AS name, COUNT(id) AS y) "
			+ "FROM Goods g "
			+ "WHERE DATE_FORMAT(g.publishTime, '%Y%m')>=DATE_FORMAT(STR_TO_DATE(?1, '%Y%m%d'), '%Y%m') "
			+ "AND DATE_FORMAT(g.publishTime, '%Y%m')<=DATE_FORMAT(STR_TO_DATE(?2, '%Y%m%d'), '%Y%m') "
			+ "AND g.isDeleted = 0"
			+ "GROUP BY DATE_FORMAT(g.publishTime, '%Y%m')")
	public List<ChartValueInfo> findEachMonthCountByStatusAndDate(String start, String end);
	
	/**
	 * 获取某时间段中，按日统计数据
	 * @param status
	 * @param start
	 * @param end
	 * @return
	 */
	@Query("SELECT new org.chinalbs.logistics.vo.ChartValueInfo(DATE_FORMAT(publishTime, '%Y%m%d') AS name, COUNT(id) AS y) "
			+ "FROM Goods g "
			+ "WHERE DATE(g.publishTime)>=STR_TO_DATE(?1, '%Y%m%d') "
			+ "AND DATE(g.publishTime)<=STR_TO_DATE(?2, '%Y%m%d') "
			+ "AND g.isDeleted = 0"
			+ "GROUP BY DATE(g.publishTime)")
	public List<ChartValueInfo> findEachDayCountByStatusAndDate(String start, String end);

	@Query("SELECT COUNT(id) FROM Goods g WHERE g.goodsOwnerId = ?1 AND g.isDeleted = 0")
	public long countByGoodsOwnerId(long goodsOwnerId);

	@Query("SELECT COUNT(id) FROM Goods g WHERE g.isDeleted = 0")
	public long countNotDeleted();

	@Query("SELECT COUNT(id) FROM Goods g WHERE g.isDeleted = 0 AND DATE(g.publishTime)=STR_TO_DATE(?1, '%Y%m%d')")
	public double countByDateNotDeleted(String date);
	
	//后台要求的表数据
	//已完成运单
	@Query("SELECT g FROM Goods g ,LogisticsOrder lo WHERE g.id = lo.goodsId AND lo.status = ?1 AND g.isDeleted = 0 AND lo.isDeleted = 0 ORDER BY g.publishTime DESC")
	public Page<Goods> findOrderByStatus(int status, Pageable pageable);
	
	@Query("SELECT g FROM Goods g ,LogisticsOrder lo WHERE g.id = lo.goodsId AND lo.status = ?1 AND DATE(lo.orderTime)=STR_TO_DATE(?2, '%Y%m%d') AND g.isDeleted = 0 AND lo.isDeleted = 0 ORDER BY g.publishTime DESC")
	public Page<Goods> findOrderByStatusAndDate(int status, String date, Pageable pageable);
	
	@Query("SELECT g FROM Goods g ,LogisticsOrder lo WHERE g.id = lo.goodsId AND lo.status = ?1 AND (DATE(lo.orderTime)>=STR_TO_DATE(?2, '%Y%m%d') AND DATE(lo.orderTime)<=STR_TO_DATE(?3, '%Y%m%d')) AND g.isDeleted = 0 AND lo.isDeleted = 0 ORDER BY g.publishTime DESC")
	public Page<Goods> findOrderByStatusAndFromDateAndToDate(int staus, String fromDate, String toDate, Pageable pageable);
	
	//已发布货物
	@Query("SELECT g FROM Goods g WHERE g.isDeleted = 0 ORDER BY g.publishTime DESC ")
	public Page<Goods> findGoodsPublished(Pageable pageable);
	
	@Query("SELECT g FROM Goods g WHERE DATE(g.publishTime)=STR_TO_DATE(?1, '%Y%m%d') AND g.isDeleted = 0 ORDER BY g.publishTime DESC ")
	public Page<Goods> findGoodsByDate(String date, Pageable pageable);
	
	@Query("SELECT g FROM Goods g WHERE (DATE(g.publishTime)>=STR_TO_DATE(?1, '%Y%m%d') AND DATE(g.publishTime)<=STR_TO_DATE(?2, '%Y%m%d')) AND g.isDeleted = 0 ORDER BY g.publishTime DESC ")
	public Page<Goods> findGoodsByFromDateAndToDate(String fromDate, String toDate, Pageable pageable);
	
	//数据导出
	@Query("SELECT g FROM Goods g ,LogisticsOrder lo WHERE g.id = lo.goodsId AND lo.status = ?1 AND g.isDeleted = 0 AND lo.isDeleted = 0 ORDER BY g.publishTime DESC")
	public List<Goods> findOrderByStatus(int status);
	
	@Query("SELECT g FROM Goods g ,LogisticsOrder lo WHERE g.id = lo.goodsId AND lo.status = ?1 AND DATE(lo.orderTime)=STR_TO_DATE(?2, '%Y%m%d') AND g.isDeleted = 0 AND lo.isDeleted = 0 ORDER BY g.publishTime DESC")
	public List<Goods> findOrderByStatusAndDate(int status, String date);
	
	@Query("SELECT g FROM Goods g ,LogisticsOrder lo WHERE g.id = lo.goodsId AND lo.status = ?1 AND (DATE(lo.orderTime)>=STR_TO_DATE(?2, '%Y%m%d') AND DATE(lo.orderTime)<=STR_TO_DATE(?3, '%Y%m%d')) AND g.isDeleted = 0 AND lo.isDeleted = 0 ORDER BY g.publishTime DESC")
	public List<Goods> findOrderByStatusAndFromDateAndToDate(int staus, String fromDate, String toDate);
	
	@Query("SELECT g FROM Goods g WHERE g.isDeleted = 0 ORDER BY g.publishTime DESC ")
	public List<Goods> findGoodsPublished();
	
	@Query("SELECT g FROM Goods g WHERE DATE(g.publishTime)=STR_TO_DATE(?1, '%Y%m%d') AND g.isDeleted = 0 ORDER BY g.publishTime DESC ")
	public List<Goods> findGoodsByDate(String date);
	
	@Query("SELECT g FROM Goods g WHERE (DATE(g.publishTime)>=STR_TO_DATE(?1, '%Y%m%d') AND DATE(g.publishTime)<=STR_TO_DATE(?2, '%Y%m%d')) AND g.isDeleted = 0 ORDER BY g.publishTime DESC ")
	public List<Goods> findGoodsByFromDateAndToDate(String fromDate, String toDate);
	
	//某货主已发布所有货物
	@Query("SELECT g FROM Goods g WHERE g.isDeleted = 0 AND g.goodsOwnerId = ?1 AND DATE(g.publishTime)>= STR_TO_DATE(?2, '%Y-%m-%d') AND DATE(g.publishTime) <= STR_TO_DATE(?3, '%Y-%m-%d') ORDER BY g.publishTime DESC ")
	public Page<Goods> findGoodsPublishedByGoodsOwnerId(Long goodsOwnerId, String startTime, String endTime, Pageable pageable);

	@Query("SELECT g FROM Goods g WHERE g.isDeleted = 0 AND g.goodsOwnerId = ?1 AND DATE(g.publishTime)>= STR_TO_DATE(?2, '%Y-%m-%d') AND DATE(g.publishTime) <= STR_TO_DATE(?3, '%Y-%m-%d') ORDER BY g.publishTime DESC ")
	public List<Goods> findGoodsPublishedByGoodsOwnerId(Long goodsOwnerId, String startTime, String endTime);
	
	//某货主已完成的运单
	@Query("SELECT g FROM Goods g ,LogisticsOrder lo WHERE g.id = lo.goodsId AND g.goodsOwnerId = ?1 AND DATE(lo.orderTime)>= STR_TO_DATE(?2, '%Y-%m-%d') AND DATE(lo.orderTime)<= STR_TO_DATE(?3, '%Y-%m-%d') AND lo.status = 5 AND g.isDeleted = 0 AND lo.isDeleted = 0 ORDER BY lo.orderTime DESC")
	public Page<Goods> findFinishedByGoodsOwnerId(Long goodsOwnerId, String startTime, String endTime, Pageable pageable);
	
	@Query("SELECT g FROM Goods g ,LogisticsOrder lo WHERE g.id = lo.goodsId AND g.goodsOwnerId = ?1 AND DATE(lo.orderTime)>= STR_TO_DATE(?2, '%Y-%m-%d') AND DATE(lo.orderTime)<= STR_TO_DATE(?3, '%Y-%m-%d') AND lo.status = 5 AND g.isDeleted = 0 AND lo.isDeleted = 0 ORDER BY lo.orderTime DESC")
	public List<Goods> findFinishedByGoodsOwnerId(Long goodsOwnerId, String startTime, String endTime);
	
	//某车主已完成订单
	@Query("SELECT g FROM Goods g ,LogisticsOrder lo WHERE g.id = lo.goodsId AND lo.truckOwnerId = ?1 AND DATE(lo.orderTime)>= STR_TO_DATE(?2, '%Y-%m-%d') AND DATE(lo.orderTime)<= STR_TO_DATE(?3, '%Y-%m-%d') AND lo.status = 5 AND g.isDeleted = 0 AND lo.isDeleted = 0 ORDER BY lo.orderTime DESC")
	public Page<Goods> findFinishedByTruckOwnerId(Long truckOwnerId, String startTime, String endTime, Pageable pageable);
	
	//数据导出（某车主）
	@Query("SELECT g FROM Goods g ,LogisticsOrder lo WHERE g.id = lo.goodsId AND lo.truckOwnerId = ?1 AND DATE(lo.orderTime)>= STR_TO_DATE(?2, '%Y-%m-%d') AND DATE(lo.orderTime)<= STR_TO_DATE(?3, '%Y-%m-%d') AND lo.status = 5 AND g.isDeleted = 0 AND lo.isDeleted = 0 ORDER BY lo.orderTime DESC")
	public List<Goods> findFinishedByTruckOwnerId(Long truckOwnerId, String startTime, String endTime);
}
