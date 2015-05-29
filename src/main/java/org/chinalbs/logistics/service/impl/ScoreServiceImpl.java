package org.chinalbs.logistics.service.impl;

import org.chinalbs.logistics.common.utils.ListSlice;
import org.chinalbs.logistics.domain.Score;
import org.chinalbs.logistics.repository.ScoreRepository;
import org.chinalbs.logistics.service.ScoreService;
import org.chinalbs.logistics.utils.SimplePageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ScoreServiceImpl implements ScoreService{

	@Autowired
	private ScoreRepository scoreRepository;
	
	@Override
	public ListSlice<Score> findListSlice(Long userId, int from, int max) {
		Page<Score> page = scoreRepository.findByUserId(userId, new SimplePageable(from, max));
		return new ListSlice<Score>(page.getTotalElements(), page.getContent());
	}

	@Override
	public Integer findTotalPoints(Long userId) {
		Integer totalPoints = scoreRepository.sumScoreByUserId(userId);
		return (totalPoints == null ? 0 : totalPoints);
	}

}
