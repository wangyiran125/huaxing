package org.chinalbs.logistics.repository;

import java.util.List;

import org.chinalbs.logistics.domain.Warehouse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface WarehouseRepository extends JpaRepository<Warehouse, Long>{
	
	@Query("SELECT w FROM Warehouse w WHERE w.isDeleted = 0 ORDER BY w.createTime DESC")
	public Page<Warehouse> findAll(Pageable pageable);
	
	@Query("SELECT w FROM Warehouse w WHERE w.goodsOwnerId = ?1 AND w.isDeleted = 0 ORDER BY w.createTime DESC")
	public List<Warehouse> findByGoodsOwnerId(Long goodsOwnerId);
}
