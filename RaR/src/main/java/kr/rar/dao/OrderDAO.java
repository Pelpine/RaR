package kr.rar.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.rar.vo.OrderVO;
import kr.util.DBUtil;

public class OrderDAO {
	//싱글턴 패턴
	private static OrderDAO instance = new OrderDAO();
	
	public static OrderDAO getInstance() {
		return instance;
	}
	public OrderDAO() {}
	
	//장바구니 등록
	
	//장바구니 목록
	
	//장바구니에 회원정보가 필요 없어보임. 의논 필요
	public List<OrderVO> getCartList(int user_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<OrderVO> list = null;
		String sql = null;
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "SELECT * FROM cart "
				+ "LEFT OUTER JOIN member USING (user_num) "
				+ "LEFT OUTER JOIN member_detail USING (user_num) "
				+ "LEFT OUTER JOIN book USING (bk_num) "
				+ "LEFT OUTER JOIN item USING (item_num) "
				+ "WHERE user_num=?";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setInt(1, user_num);
			//SQL문 실행
			rs = pstmt.executeQuery();
			
			list = new ArrayList<OrderVO>();
			while(rs.next()) {
				OrderVO cart = new OrderVO();
				cart.setUser_num(rs.getInt("user_num"));
				cart.setItem_num(rs.getInt("item_num"));
				cart.setBk_num(rs.getInt("bk_num"));
				cart.setBk_name(rs.getString("bk_name"));
				cart.setBk_price(rs.getInt("bk_price"));
				cart.setItem_grade(rs.getInt("item_grade"));
				cart.setItem_price(rs.getInt("item_price"));
				
				list.add(cart);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
		return list;
	}

	//장바구니 삭제
	
	//주문 정보 등록
	
	//주문 정보 삭제
}
