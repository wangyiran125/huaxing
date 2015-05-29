package org.chinalbs.logistics.repository;

import java.util.List;

import org.chinalbs.logistics.domain.OrderIntent;
import org.chinalbs.logistics.vo.ChartValueInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderIntentRepository extends JpaRepository<OrderIntent, Long> {
	
	@Query("SELECT o FROM OrderIntent o,LogisticsOrder lo WHERE o.truckOwnerId = ?1 AND o.goodsId = lo.goodsId AND lo.status <= 4 AND o.isDeleted = 0 ORDER BY o.applyTime DESC")
	public Page<OrderIntent> findByTruckOwnerId4Pc(Long truckOwnerId, Pageable pageable);

	@Query("SELECT o FROM OrderIntent o WHERE o.truckOwnerId = ?1 AND o.isDeleted = 0 ORDER BY o.applyTime DESC")
	public Page<OrderIntent> findByTruckOwnerId(Long truckOwnerId, Pageable pageable);
	
	@Query("SELECT o FROM OrderIntent o WHERE o.truckOwnerId = ?1 AND o.status = ?2 AND o.isDeleted = 0 ORDER BY o.applyTime DESC")
	public Page<OrderIntent> findByTruckOwnerIdAndStatus(Long truckOwnerId, int status, Pageable pageable);
	
	@Query("SELECT o FROM OrderIntent o WHERE o.truckOwnerId = ?1 AND o.initiator = ?2 AND o.isDeleted = 0 ORDER BY o.applyTime DESC")
	public Page<OrderIntent> findByTruckOwnerIdAndInitiator(Long truckOwnerId, int initiator, Pageable pageable);
	
	@Query("SELECT o FROM OrderIntent o WHERE o.truckOwnerId = ?1 AND o.initiator = ?2 AND o.status = ?3 AND o.isDeleted = 0 ORDER BY o.applyTime DESC")
	public Page<OrderIntent> findByTruckOwnerIdAndInitiatorAndStatus(Long truckOwnerId,int initiator, int status,  Pageable pageable);
	
	@Query("SELECT o FROM OrderIntent o WHERE o.truckOwnerId = ?1 AND (o.status = 1 OR o.status = 3 OR o.status = 4) AND o.isDeleted = 0 ORDER BY o.applyTime DESC")
	public Page<OrderIntent> findNoRefusedByTruckOwnerId(Long truckOwnerId, Pageable pageable);
	
	@Query("SELECT o FROM OrderIntent o WHERE o.goodsId = ?1 AND o.isDeleted = 0")
	public Page<OrderIntent> findByGoodsId(Long goodsId, Pageable pageable);
	
	@Query("SELECT o FROM OrderIntent o WHERE o.truckOwnerId = ?1 AND o.goodsId = ?2 AND o.isDeleted = 0")
	public OrderIntent findByTruckOwnerIdAndGoodsId(Long truckOwnerId, Long goodsId);

	/**
	 * 
	 * @param status
	 * @param start
	 * @param end
	 * @return
	 */
	@Query("SELECT new org.chinalbs.logistics.vo.ChartValueInfo(DATE_FORMAT(applyTime, '%m月') AS name, COUNT(id) AS y) "
			+ "FROM OrderIntent oi "
			+ "WHERE oi.isDeleted = 0 AND DATE_FORMAT(oi.applyTime, '%Y%m')>=DATE_FORMAT(STR_TO_DATE(?1, '%Y%m%d'), '%Y%m') "
			+ "AND DATE_FORMAT(oi.applyTime, '%Y%m')<=DATE_FORMAT(STR_TO_DATE(?2, '%Y%m%d'), '%Y%m') "
			+ "GROUP BY DATE_FORMAT(oi.applyTime, '%Y%m')")
	public List<ChartValueInfo> findEachMonthCountByStatusAndDate(String start, String end);
	
	/**
	 * 获取某时间段中，按日统计数据
	 * @param status
	 * @param start
	 * @param end
	 * @return
	 */
	@Query("SELECT new org.chinalbs.logistics.vo.ChartValueInfo(DATE_FORMAT(applyTime, '%Y%m%d') AS name, COUNT(id) AS y) "
			+ "FROM OrderIntent oi "
			+ "WHERE oi.isDeleted = 0 AND DATE(oi.applyTime)>=STR_TO_DATE(?1, '%Y%m%d') "
			+ "AND DATE(oi.applyTime)<=STR_TO_DATE(?2, '%Y%m%d') "
			+ "GROUP BY DATE(oi.applyTime)")
	public List<ChartValueInfo> findEachDayCountByStatusAndDate(String start, String end);

	@Query("SELECT COUNT(id) FROM OrderIntent o WHERE o.truckOwnerId = ?1 AND o.isDeleted = 0")
	public long countByTruckOwnerId(long truckOwnerId);

	@Query("SELECT COUNT(id) FROM OrderIntent o WHERE o.isDeleted = 0")
	public long countNotDeleted();

	@Query("SELECT COUNT(1) FROM OrderIntent oi WHERE oi.isDeleted = 0 AND DATE(oi.applyTime)=STR_TO_DATE(?1, '%Y%m%d')")
	public double countByDateNotDeleted(String date);
	
	//抢单
	@Query("SELECT oi FROM OrderIntent oi WHERE oi.isDeleted = 0 ORDER BY oi.applyTime DESC")
	public Page<OrderIntent> findOrderIntents(Pageable pageable);
	
	@Query("SELECT oi FROM OrderIntent oi WHERE oi.isDeleted = 0 AND DATE(oi.applyTime)=STR_TO_DATE(?1, '%Y%m%d') ORDER BY oi.applyTime DESC")
	public Page<OrderIntent> findOrderIntentByDate(String date, Pageable pageable);
		
	@Query("SELECT oi FROM OrderIntent oi WHERE oi.isDeleted = 0 AND (DATE(oi.applyTime)>=STR_TO_DATE(?1, '%Y%m%d') AND DATE(oi.applyTime)<=STR_TO_DATE(?2, '%Y%m%d')) ORDER BY oi.applyTime DESC")
	public Page<OrderIntent> findOrderIntentByFromDateAndToDate(String fromDate, String toDate, Pageable pageable);
	
	//数据导出
	@Query("SELECT oi FROM OrderIntent oi WHERE oi.isDeleted = 0 ORDER BY oi.applyTime DESC")
	public List<OrderIntent> findOrderIntents();
	
	@Query("SELECT oi FROM OrderIntent oi WHERE oi.isDeleted = 0 AND DATE(oi.applyTime)=STR_TO_DATE(?1, '%Y%m%d') ORDER BY oi.applyTime DESC")
	public List<OrderIntent> findOrderIntentByDate(String date);
	
	@Query("SELECT oi FROM OrderIntent oi WHERE oi.isDeleted = 0 AND (DATE(oi.applyTime)>=STR_TO_DATE(?1, '%Y%m%d') AND DATE(oi.applyTime)<=STR_TO_DATE(?2, '%Y%m%d')) ORDER BY oi.applyTime DESC")
	public List<OrderIntent> findOrderIntentByFromDateAndToDate(String fromDate, String toDate);
	
	//某车主抢单
	@Query("SELECT o FROM OrderIntent o WHERE o.truckOwnerId = ?1 AND (DATE(o.applyTime)>=STR_TO_DATE(?2, '%Y-%m-%d') AND DATE(o.applyTime)<=STR_TO_DATE(?3, '%Y-%m-%d')) AND o.isDeleted = 0 ORDER BY o.applyTime DESC")
	public Page<OrderIntent> findByTruckOwnerId(Long truckOwnerId, String startTime, String endTime, Pageable pageable);
	
	//数据导出（某车主）
	@Query("SELECT o FROM OrderIntent o WHERE o.truckOwnerId = ?1 AND (DATE(o.applyTime)>=STR_TO_DATE(?2, '%Y-%m-%d') AND DATE(o.applyTime)<=STR_TO_DATE(?3, '%Y-%m-%d')) AND o.isDeleted = 0 ORDER BY o.applyTime DESC")
	public List<OrderIntent> findByTruckOwnerId(Long truckOwnerId, String startTime, String endTime);
}
