package org.chinalbs.logistics.repository;


import java.util.List;

import org.chinalbs.logistics.domain.LogisticsUser;
import org.chinalbs.logistics.vo.ChartValueInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LogisticsUserRepository extends JpaRepository<LogisticsUser, Long> {
	
	@Query("SELECT u FROM LogisticsUser u WHERE u.username = ?1 AND u.password = ?2")
	public LogisticsUser findOneByUsernameAndPassword(String username, String password);
	
	public LogisticsUser findOneByUsername(String username);

	@Query("SELECT u FROM LogisticsUser u WHERE u.role=?1")
	public Page<LogisticsUser> findPageByType(int type,
			Pageable pageable);

	@Query("SELECT COUNT(1) FROM LogisticsUser lu WHERE lu.role = ?1")
	public double countByRole(int i);

	/**
	 * 查询某一天的某种角色总量
	 * @param type
	 * @param date
	 * @return
	 */
	@Query("SELECT COUNT(1) FROM LogisticsUser lu WHERE lu.role = ?1 AND DATE(lu.registerTime)=STR_TO_DATE(?2, '%Y%m%d')")
	public double countByStatusAndDate(int role, String date);

	/**
	 * 获取某时间段中，按月统计数据
	 * @param role
	 * @param start
	 * @param end
	 * @return
	 */
	@Query("SELECT new org.chinalbs.logistics.vo.ChartValueInfo(DATE_FORMAT(registerTime, '%m月') AS name, COUNT(id) AS y) "
			+ "FROM LogisticsUser l "
			+ "WHERE l.role=?1 AND DATE_FORMAT(l.registerTime, '%Y%m')>=DATE_FORMAT(STR_TO_DATE(?2, '%Y%m%d'), '%Y%m') "
			+ "AND DATE_FORMAT(l.registerTime, '%Y%m')<=DATE_FORMAT(STR_TO_DATE(?3, '%Y%m%d'), '%Y%m') "
			+ "GROUP BY DATE_FORMAT(l.registerTime, '%Y%m')")
	public List<ChartValueInfo> findEachMonthCountByStatusAndDate(int role,
			String start, String end);
	
	/**
	 * 获取某时间段中，按日统计数据
	 * @param role
	 * @param start
	 * @param end
	 * @return
	 */
	@Query("SELECT new org.chinalbs.logistics.vo.ChartValueInfo(DATE_FORMAT(registerTime, '%Y%m%d') AS name, COUNT(id) AS y) "
			+ "FROM LogisticsUser l "
			+ "WHERE l.role=?1 AND DATE(l.registerTime)>=STR_TO_DATE(?2, '%Y%m%d') "
			+ "AND DATE(l.registerTime)<=STR_TO_DATE(?3, '%Y%m%d') "
			+ "GROUP BY DATE(l.registerTime)")
	public List<ChartValueInfo> findEachDayCountByStatusAndDate(int role,
			String start, String end);
}
