package kr.rar.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import kr.util.DBUtil;

public class OrderDAO {
	//싱글턴패턴
	private static OrderDAO instance = new OrderDAO();
	
	public OrderDAO getInstance() {
		return instance;
	}
	private OrderDAO() {}
	
	
	
	//주문등록
	//주문목록
	//주문목록 상세
}
