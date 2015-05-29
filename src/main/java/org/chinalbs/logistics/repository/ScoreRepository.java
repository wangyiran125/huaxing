package org.chinalbs.logistics.repository;

import org.chinalbs.logistics.domain.Score;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ScoreRepository extends JpaRepository<Score, Long> {
	
	@Query("SELECT ms FROM Score ms WHERE ms.userId = ?1")
	public Page<Score> findByUserId(Long userId, Pageable pageable);

	@Query("SELECT COALESCE(sum(ms.points), 0) FROM Score ms WHERE ms.userId = ?1")
	public Integer sumScoreByUserId(Long userId);
}
