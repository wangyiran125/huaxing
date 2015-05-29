package org.chinalbs.logistics.repository;

import org.chinalbs.logistics.domain.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
		
}
