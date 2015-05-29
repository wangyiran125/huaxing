package org.chinalbs.logistics.repository;

import org.chinalbs.logistics.domain.VipLevel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VipLevelRepository extends JpaRepository<VipLevel, Long> {

	public VipLevel findOneByLevel(int vipLevel);
}
