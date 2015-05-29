package org.chinalbs.logistics.repository;

import java.util.List;

import org.chinalbs.logistics.domain.DictDistrict;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DictDistrictRepository extends JpaRepository<DictDistrict, Long> {

	@Query("SELECT d FROM DictDistrict d WHERE d.parentCode = ?1")
	public List<DictDistrict> findByParentCode(Long code);
}
