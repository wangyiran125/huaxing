package org.chinalbs.logistics.service;

import java.util.Map;

public interface ScoreItemService {

	public Map<String, Integer> getAllScoreItem();

	public void updateScoreItem(Map<String, Integer> items);
}
