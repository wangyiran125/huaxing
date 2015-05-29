package org.chinalbs.logistics.repository;

import org.chinalbs.logistics.domain.GoodsOwner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface GoodsOwnerRepository extends JpaRepository<GoodsOwner, Long> {

	public GoodsOwner findOneByUserId(Long userId);

	@Query("SELECT g FROM GoodsOwner g")
	public Page<GoodsOwner> findPage(Pageable pageable);

	@Query("SELECT g FROM GoodsOwner g WHERE g.name LIKE ?1 AND g.phone LIKE ?2")
	public Page<GoodsOwner> findPageByNameAndPhone(String name, String phone,
			Pageable pageable);

	@Query("SELECT g FROM GoodsOwner g, LogisticsUser u WHERE u.id=g.userId AND u.role=?1 AND COALESCE(g.name, '') LIKE ?2 AND COALESCE(g.phone, '') LIKE ?3")
	public Page<GoodsOwner> findPageByRoleAndNameAndPhone(int role, String name,
			String phone, Pageable pageable);
	
//	@Query(value="SELECT new org.chinalbs.logistics.vo.UserChartInfo(uo.userId, uo.username, uo.name, uo.phone, COALESCE(goodsNum, 0) AS goodsNum, 0 AS goodsIntentNum, COALESCE(orderNum, 0) AS orderNum, COALESCE(score, 0) AS score) "
//			+ "FROM "
//			+ "(SELECT u.id AS userId, u.username, o.name, o.phone "
//			+ "FROM LogisticsUser u, GoodsOwner o "
//			+ "WHERE u.id=o.userId "
//			+ "AND o.name LIKE ?1 "
//			+ "AND o.phone LIKE ?2 ) uo "
//			+ "LEFT JOIN "
//			+ "(SELECT g.id AS goodsId, g.goodsOwnerId, COUNT(g.id) AS goodsNum "
//			+ "FROM Goods g WHERE g.isDeleted=0 GROUP BY g.goodsOwnerId) gc "
//			+ "ON uo.userId=gc.goodsOwnerId "
//			+ "LEFT JOIN "
//			+ "(SELECT lo.goodsId, COUNT(lo.id) AS orderNum "
//			+ "FROM LogisticsOrder lo WHERE lo.status=5 AND lo.isDeleted=0 GROUP BY lo.goodsId) oc "
//			+ "ON gc.goodsId=oc.goodsId "
//			+ "LEFT JOIN "
//			+ "(SELECT s.userId, SUM(s.points) AS score FROM Score s GROUP BY s.userId) sc "
//			+ "ON uo.userId=sc.userId "
//			+ "GROUP BY uo.userId", nativeQuery=false)
//	public Page<UserChartInfo> findAll(String name, String phone, Pageable pageable);
}
