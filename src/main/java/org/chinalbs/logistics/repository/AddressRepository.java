package org.chinalbs.logistics.repository;

import java.util.List;

import org.chinalbs.logistics.domain.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AddressRepository extends JpaRepository<Address, Long>{
	@Query("SELECT a FROM Address a WHERE a.userId = ?1 ORDER BY a.flag DESC")
	public List<Address> findByUserId(long userId);
	public Address findByUserIdAndFlag(long userId, int flag);
}
