package org.chinalbs.logistics.service;

import org.chinalbs.logistics.common.utils.ListSlice;
import org.chinalbs.logistics.domain.Score;

public interface ScoreService {

	public ListSlice<Score> findListSlice(Long userId, int from, int max);
	
	public Integer findTotalPoints(Long userId);
}
