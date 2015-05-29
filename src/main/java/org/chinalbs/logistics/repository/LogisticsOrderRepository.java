package org.chinalbs.logistics.repository;

import java.util.Date;
import java.util.List;

import org.chinalbs.logistics.domain.LogisticsOrder;
import org.chinalbs.logistics.vo.ChartValueInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LogisticsOrderRepository extends JpaRepository<LogisticsOrder, Long>{
	
	@Query("SELECT lo FROM LogisticsOrder lo WHERE lo.status != 3 AND lo.isDeleted = 0")
	public Page<LogisticsOrder> findAll(Pageable pageable);

	@Query("SELECT lo FROM LogisticsOrder lo WHERE lo.goodsId = ?1")
	public LogisticsOrder findOneByGoodsId(Long goodsId);
	
	@Query("SELECT lo FROM LogisticsOrder lo WHERE lo.truckOwnerId = ?1 AND lo.isDeleted = 0  AND (lo.status = 3 OR lo.status = 4)" )
	public List<LogisticsOrder> findNoFinishedOrderByTruckOwner(Long truckOwnerId);

	@Query("SELECT COUNT(1) FROM LogisticsOrder lo WHERE lo.status = ?1 AND lo.isDeleted = 0")
	public long countByStatus(int tatus);

	@Query("SELECT COUNT(1) FROM LogisticsOrder lo WHERE lo.status = ?1 AND lo.isDeleted = 0 AND (lo.orderTime>=?2 AND lo.orderTime<?3)")
	public double countByStatusAndDate(int status, Date start, Date end);

	/**
	 * 查询某一天的某种状态运单总量
	 * @param type
	 * @param date
	 * @return
	 */
	@Query("SELECT COUNT(1) FROM LogisticsOrder lo WHERE lo.status = ?1 AND lo.isDeleted = 0 AND DATE(lo.orderTime)=STR_TO_DATE(?2, '%Y%m%d')")
	public double countByStatusAndDate(int type, String date);

	/**
	 * 获取某时间段中，按月统计数据
	 * @param status
	 * @param start
	 * @param end
	 * @return
	 */
	@Query("SELECT new org.chinalbs.logistics.vo.ChartValueInfo(DATE_FORMAT(orderTime, '%m月') AS name, COUNT(id) AS y) "
			+ "FROM LogisticsOrder l "
			+ "WHERE l.status=?1 AND l.isDeleted = 0 AND DATE_FORMAT(l.orderTime, '%Y%m')>=DATE_FORMAT(STR_TO_DATE(?2, '%Y%m%d'), '%Y%m') "
			+ "AND DATE_FORMAT(l.orderTime, '%Y%m')<=DATE_FORMAT(STR_TO_DATE(?3, '%Y%m%d'), '%Y%m') "
			+ "GROUP BY DATE_FORMAT(l.orderTime, '%Y%m')")
	public List<ChartValueInfo> findEachMonthCountByStatusAndDate(int status,
			String start, String end);
	
	/**
	 * 获取某时间段中，按日统计数据
	 * @param status
	 * @param start
	 * @param end
	 * @return
	 */
	@Query("SELECT new org.chinalbs.logistics.vo.ChartValueInfo(DATE_FORMAT(orderTime, '%Y%m%d') AS name, COUNT(id) AS y) "
			+ "FROM LogisticsOrder l "
			+ "WHERE l.status=?1 AND l.isDeleted = 0 AND DATE(l.orderTime)>=STR_TO_DATE(?2, '%Y%m%d') "
			+ "AND DATE(l.orderTime)<=STR_TO_DATE(?3, '%Y%m%d') "
			+ "GROUP BY DATE(l.orderTime)")
	public List<ChartValueInfo> findEachDayCountByStatusAndDate(int status,
			String start, String end);
}
