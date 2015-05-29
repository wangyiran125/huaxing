package org.chinalbs.logistics.repository;

import org.chinalbs.logistics.domain.Driver;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DriverRepository extends JpaRepository<Driver, Long> {


	@Query("SELECT d FROM Driver d where d.userId = ?1 AND d.isDeleted = 0 ")
	public Driver findOnebyUserId(Long userId);
	
	@Query("SELECT d FROM Driver d where d.parentUserId = ?1 AND d.isDeleted = 0 ORDER BY d.userId DESC")
	public Page<Driver> findPagebyparentUserId(Pageable page, Long driverId);

    @Query("SELECT d FROM Driver d where d.truckId = ?1 AND d.isDeleted = 0 ")
    public List<Driver> findListByTruckId(Long truckId);
}
 