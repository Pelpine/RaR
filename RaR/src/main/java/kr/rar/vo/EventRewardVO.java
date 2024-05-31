package kr.rar.vo;

import java.sql.Date;

public class EventRewardVO {
	private int event_num;
	private int user_num;
	private int reward_num;
	private int point;
	private Date provide_date;
	public int getEvent_num() {
		return event_num;
	}
	public void setEvent_num(int event_num) {
		this.event_num = event_num;
	}
	public int getUser_num() {
		return user_num;
	}
	public void setUser_num(int user_num) {
		this.user_num = user_num;
	}
	public int getReward_num() {
		return reward_num;
	}
	public void setReward_num(int reward_num) {
		this.reward_num = reward_num;
	}
	public int getPoint() {
		return point;
	}
	public void setPoint(int point) {
		this.point = point;
	}
	public Date getProvide_date() {
		return provide_date;
	}
	public void setProvide_date(Date provide_date) {
		this.provide_date = provide_date;
	}
	
	
	
	

}
