package org.chinalbs.logistics.repository;

import org.chinalbs.logistics.domain.DictScoreItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ScoreItemRepository extends JpaRepository<DictScoreItem, Long> {
	
	@Query("SELECT ms FROM DictScoreItem ms WHERE ms.code = ?1")
	public DictScoreItem findInfobyCode(Long code);
}
