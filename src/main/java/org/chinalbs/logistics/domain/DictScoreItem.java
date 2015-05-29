package org.chinalbs.logistics.domain;

import javax.persistence.Entity;

import org.chinalbs.logistics.common.domain.BaseDict;

/**
 * 积分项表，继承自BaseDict，code作为键值，score表示本项分数，name表示本项得分说明
 * @author 
 *
 */
@Entity
public class DictScoreItem extends BaseDict<Long> {

	private int score;

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	@Override
	public String toString() {
		return "DictScoreItem [score=" + score + ", getCode()=" + getCode()
				+ ", getName()=" + getName() + "]";
	}
	
	
}
