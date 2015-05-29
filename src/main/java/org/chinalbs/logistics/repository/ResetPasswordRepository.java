package org.chinalbs.logistics.repository;

import org.chinalbs.logistics.domain.ResetPassword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ResetPasswordRepository extends JpaRepository<ResetPassword, Long> {

	@Query("SELECT rp FROM ResetPassword rp WHERE rp.token=?1")
	public ResetPassword findOneByToken(String token);
}
