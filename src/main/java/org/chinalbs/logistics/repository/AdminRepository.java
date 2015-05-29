package org.chinalbs.logistics.repository;

import org.chinalbs.logistics.domain.Admin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AdminRepository extends JpaRepository<Admin, Long> {

	@Query("SELECT a FROM Admin a WHERE userId=?1")
	public Admin findOneByUserId(Long userId);

	@Query("SELECT a FROM Admin a")
	public Page<Admin> findPage(Pageable pageable);

	@Query("SELECT a FROM Admin a WHERE COALESCE(a.name, '') LIKE ?1 AND COALESCE(a.phone, '') LIKE ?2")
	public Page<Admin> findPageByNameAndPhone(String name, String phone,
			Pageable pageable);
}
