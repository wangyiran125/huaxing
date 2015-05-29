package org.chinalbs.logistics.service;

import java.util.List;

import org.chinalbs.logistics.common.utils.ListSlice;
import org.chinalbs.logistics.domain.Feedback;
import org.chinalbs.logistics.vo.FeedBackInfo;

public interface FeedbackService {
		
	public List<Feedback> findAll();
    public ListSlice<Feedback> findSlice(int from, int max);
	public Feedback create(FeedBackInfo info) throws Exception;
}
