package org.chinalbs.logistics.service.impl;

import java.util.Date;
import java.util.List;

import org.chinalbs.logistics.common.utils.ListSlice;
import org.chinalbs.logistics.domain.Feedback;
import org.chinalbs.logistics.repository.FeedbackRepository;
import org.chinalbs.logistics.service.FeedbackService;
import org.chinalbs.logistics.session.SessionInfo;
import org.chinalbs.logistics.utils.OrderedPageable;
import org.chinalbs.logistics.vo.FeedBackInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@Transactional
public class FeedbackServiceImpl implements FeedbackService {

	@Autowired
	private FeedbackRepository feedbackRepository;
	
	@Override
	public List<Feedback> findAll() {		
		List<Feedback> feedbacklist = feedbackRepository.findAll(new Sort(Direction.DESC,"createTime"));
		return feedbacklist;
	}

	@Override
	public Feedback create(@RequestBody FeedBackInfo info){
	
		Feedback feedback = new Feedback();
		feedback.setUserId(SessionInfo.getCurrent().getUserInfo().getUserId());
		feedback.setContent(info.getContent());
		feedback.setMobile(info.getMobile());
        feedback.setEmail(info.getEmail());
		feedback.setCreateTime(new Date());
		return feedbackRepository.save(feedback);
	}

	@Override
	public ListSlice<Feedback> findSlice(final int from, final int max) {
		
	    Page<Feedback> page = feedbackRepository.findAll(new OrderedPageable(from, max, new Sort(Direction.DESC, "createTime")));		
		List<Feedback> feedbacks = page.getContent();	
		return new ListSlice<Feedback>(page.getTotalElements(), feedbacks) ;
		
	}
	
	

}
