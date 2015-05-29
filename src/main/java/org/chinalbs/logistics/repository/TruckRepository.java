package org.chinalbs.logistics.repository;

import org.chinalbs.logistics.domain.Truck;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TruckRepository extends JpaRepository<Truck, Long> {

	@Query("SELECT t FROM Truck t WHERE t.truckOwnerId = ?1 AND t.isDeleted = 0 order by t.id DESC ")
	public Page<Truck> findByTruckOwnerId(Long truckOwnerId, Pageable pageable);
	
	@Query("SELECT t FROM Truck t WHERE t.truckOwnerId = ?1 AND t.intentPermission = 1 AND t.truckStatus<>3 AND t.isDeleted = 0")
	public Page<Truck> findIntentsPermitByTruckOwnerId(Long truckOwnerId, Pageable pageable);
	
	@Query("SELECT t FROM Truck t WHERE t.licensePlateNumber = ?1 AND t.isDeleted = 0")
	public Page<Truck> findListByLicensePlateNumber(String licensePlateNumber, Pageable pageable);

    @Query("SELECT t FROM Truck t WHERE t.deviceKey = ?1 AND t.isDeleted = 0")
    public List<Truck> findListByDeviceKey(String deviceKey);
}
