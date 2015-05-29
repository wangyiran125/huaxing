package org.chinalbs.logistics.repository;

import org.chinalbs.logistics.domain.TruckOwner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TruckOwnerRepository extends JpaRepository<TruckOwner, Long> {

	public TruckOwner findOneByUserId(Long userId);

	@Query("SELECT t FROM TruckOwner t")
	public Page<TruckOwner> findPage(Pageable pageable);

	@Query("SELECT t FROM TruckOwner t WHERE COALESCE(t.name, '') LIKE ?1 AND COALESCE(t.phone, '') LIKE ?2")
	public Page<TruckOwner> findPageByNameAndPhone(String name, String phone,
			Pageable pageable);
	
//	@Query(value="SELECT new org.chinalbs.logistics.vo.UserChartInfo(uo.userId, uo.username, uo.name, uo.phone, 0 AS goodsNum, COALESCE(goodsIntentNum, 0) AS goodsIntentNum, COALESCE(orderNum, 0) AS orderNum, COALESCE(score, 0) AS score) "
//	+ "FROM "
//	+ "(SELECT u.id AS userId, u.username, o.name, o.phone "
//	+ "FROM LogisticsUser u, TruckOwner o "
//	+ "WHERE u.id=o.userId "
//	+ "AND o.name LIKE ?1 "
//	+ "AND o.phone LIKE ?2) uo "
//	+ "LEFT JOIN "
//	+ "(SELECT oi.truckOwnerId, COUNT(oi.id) AS goodsIntentNum "
//	+ "FROM OrderIntent oi WHERE oi.isDeleted=0 GROUP BY oi.truckOwnerId) oic "
//	+ "ON uo.userId=oic.truckOwnerId "
//	+ "LEFT JOIN "
//	+ "(SELECT lo.truckOwnerId, COUNT(lo.id) AS orderNum "
//	+ "FROM LogisticsOrder lo WHERE lo.status=5 AND lo.isDeleted=0 GROUP BY lo.truckOwnerId) oc "
//	+ "ON uo.userId=oc.truckOwnerId "
//	+ "LEFT JOIN "
//	+ "(SELECT s.userId, SUM(s.points) AS score FROM Score s GROUP BY s.userId) sc "
//	+ "ON uo.userId=sc.userId "
//	+ "GROUP BY uo.userId", nativeQuery=true)
//@Query("SELECT new org.chinalbs.logistics.vo.UserChartInfo(u.id, u.username, t.name, t.phone) "
//	+ "FROM LogisticsUser u, TruckOwner t "
//	+ "WHERE u.id=t.userId AND t.name LIKE ?1 AND t.phone LIKE ?2")
//public Page<UserChartInfo> findPageTruckOwnerByNameAndPhone(String name, String phone, Pageable pageable);
}
