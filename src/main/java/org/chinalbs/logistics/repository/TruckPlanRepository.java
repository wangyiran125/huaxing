package org.chinalbs.logistics.repository;

import org.chinalbs.logistics.domain.TruckPlan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TruckPlanRepository extends JpaRepository<TruckPlan, Long> {

	@Query("SELECT t FROM TruckPlan t WHERE t.isDeleted = 0 ORDER BY t.publishTime DESC")
	public Page<TruckPlan> findAll(Pageable pageable);
	@Query("SELECT t FROM TruckPlan t WHERE t.truckOwnerId = ?1 AND t.isDeleted = 0 ORDER BY t.publishTime DESC")
	public Page<TruckPlan> findByTruckOwnerId(Long truckOnwerId, Pageable pageable);
}
