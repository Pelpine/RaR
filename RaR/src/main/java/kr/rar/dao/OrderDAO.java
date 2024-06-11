package kr.rar.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.rar.vo.MemberVO;
import kr.rar.vo.OrderDetailVO;
import kr.rar.vo.OrderVO;
import kr.util.DBUtil;

public class OrderDAO {
	//싱글턴패턴
	private static OrderDAO instance = new OrderDAO();
	
	public static OrderDAO getInstance() {
		return instance;
	}
	private OrderDAO() {}
	

	//주문등록
	public void insertOrder(OrderVO order, List<OrderDetailVO> orderDetailList)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		PreparedStatement pstmt4 = null;
		PreparedStatement pstmt5 = null;
		PreparedStatement pstmt6 = null;
		ResultSet rs = null;
		String sql = null;
		int order_num = 0;
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			//오토 커밋 해제
			conn.setAutoCommit(false);
			
			//order_num 구하기
			sql = "SELECT rar_order_seq.nextval FROM dual";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				order_num = rs.getInt(1);
			}
			
			//주문정보 처리
			sql = "INSERT INTO rar_order (order_num,order_points,pay_total,pay_points,pay_ship,pay_payment,"
				+ "receive_name,receive_post,receive_address1,receive_address2,receive_phone,notice,user_num) "
				+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
			pstmt2 = conn.prepareStatement(sql);
			pstmt2.setInt(1, order_num);
			pstmt2.setInt(2, order.getOrder_points());
			pstmt2.setInt(3, order.getPay_total());
			pstmt2.setInt(4, order.getPay_points());
			pstmt2.setInt(5, order.getPay_ship());
			pstmt2.setInt(6, order.getPay_payment());
			pstmt2.setString(7, order.getReceive_name());
			pstmt2.setString(8, order.getReceive_post());
			pstmt2.setString(9, order.getReceive_address1());
			pstmt2.setString(10, order.getReceive_address2());
			pstmt2.setString(11, order.getReceive_phone());
			pstmt2.setString(12, order.getNotice());
			pstmt2.setInt(13, order.getUser_num());
			pstmt2.executeUpdate();
			
			//주문상세정보 처리
			sql="INSERT INTO rar_order_detail (detail_num,item_num,item_name,bk_img,bk_price,item_price,item_grade,order_num,item_img,bk_num) "
					+ "VALUES (rar_order_detail_seq.nextval,?,?,?,?,?,?,?,?,?)";
			pstmt3 = conn.prepareStatement(sql);
			
			for(int i=0;i<orderDetailList.size();i++) {
				OrderDetailVO orderDetail = orderDetailList.get(i);
				pstmt3.setInt(1, orderDetail.getItem_num());
				pstmt3.setString(2, orderDetail.getItem_name());
				pstmt3.setString(3, orderDetail.getBk_img());
				pstmt3.setInt(4, orderDetail.getBk_price());
				pstmt3.setInt(5, orderDetail.getItem_price());
				pstmt3.setInt(6, orderDetail.getItem_grade());
				pstmt3.setInt(7, order_num);
				pstmt3.setString(8, orderDetail.getItem_img());
				pstmt3.setInt(9, orderDetail.getBk_num());
				pstmt3.addBatch();//쿼리를 메모리에 올림
				
				//계속 추가하면 outOfMemory 발생, 1000개 단위로 executeBatch()
				if(i % 1000 == 0) {
					pstmt3.executeBatch();
				}
			}
			pstmt3.executeBatch();//쿼리를 전송
			
			//상품상태 변경
			sql = "UPDATE item SET item_status=2 WHERE item_num=?";
			pstmt4 = conn.prepareStatement(sql);
			for(int i=0;i<orderDetailList.size();i++) {
				OrderDetailVO orderDetail = orderDetailList.get(i);
				pstmt4.setInt(1, orderDetail.getItem_num());
				pstmt4.addBatch();//쿼리를 메모리에 올림
				
				if(i % 1000 == 0) {
					pstmt4.executeBatch();
				}
			}
			pstmt4.executeBatch();//쿼리 전송
			
			//장바구니에서 선택한 주문상품 삭제
			sql = "DELETE FROM cart WHERE user_num=? AND selected=1";
			pstmt5 = conn.prepareStatement(sql);
			pstmt5.setInt(1, order.getUser_num());
			pstmt5.executeUpdate();
			
			//사용자 사용포인트 업데이트
			sql = "UPDATE member_detail SET user_point=? WHERE user_num=?";
			pstmt6 = conn.prepareStatement(sql);
			
			//포인트 계산 (보유 포인트 - 사용 포인트 + 적립 포인트)
			MemberDAO memberDAO = MemberDAO.getInstance();
			MemberVO member = memberDAO.getMember(order.getUser_num());
			
			int points_result = member.getUser_point() - order.getPay_points() + order.getOrder_points();
			pstmt6.setInt(1, points_result);
			pstmt6.setInt(2, order.getUser_num());
			pstmt6.executeUpdate();
			
			//모든 SQL문이 정상 수행
			conn.commit();
		}catch(Exception e) {
			//SQL문이 하나라도 실패
			conn.rollback();
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt6, null);
			DBUtil.executeClose(null, pstmt5, null);
			DBUtil.executeClose(null, pstmt3, null);
			DBUtil.executeClose(null, pstmt4, null);
			DBUtil.executeClose(null, pstmt2, null);
			DBUtil.executeClose(rs, pstmt, conn);
		}
	}
	//주문개수
	public int getOrderCount(String keyfield, String keyword, int user_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		String sub_sql = "";
		int count = 0;
		
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			
			if(keyword != null && !"".equals(keyword)) {
				//검색 글 개수
				if(keyfield.equals("1")) sub_sql += "AND item_name LIKE '%' || ? || '%'";
				else if(keyfield.equals("2")) sub_sql += "AND order_num=?";
			}
			//SQL문 작성
			sql = "SELECT COUNT(*) FROM rar_order "
				+ "JOIN (SELECT order_num, LISTAGG(item_name,',') WITHIN GROUP (ORDER BY item_name) item_name "
				+ "FROM rar_order_detail GROUP BY order_num) USING (order_num) "
				+ "WHERE user_num=? " + sub_sql;
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setInt(1, user_num);
			if(keyword != null && !"".equals(keyword)) {
				pstmt.setString(2, keyword);
			}
			//SQL문 실행
			rs = pstmt.executeQuery();
			if(rs.next()) {
				count = rs.getInt(1);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return count;
	}
	//주문목록
	public List<OrderVO> getOrderList(int start,int end,String keyfield,String keyword,int user_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<OrderVO> list = null;
		String sql = null;
		String sub_sql = "";
		int cnt = 0;
		
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			
			if(keyword != null && !"".equals(keyword)) {
				//검색 글 개수
				if(keyfield.equals("1")) sub_sql += "AND item_name LIKE '%' || ? || '%'";
				else if(keyfield.equals("2")) sub_sql += "AND order_num=?";
			}
			//SQL문 작성
			sql = "SELECT * FROM (SELECT a.*,rownum rnum FROM ("
				+ "SELECT * FROM rar_order JOIN (SELECT order_num, LISTAGG(item_name,', ') WITHIN GROUP (ORDER BY item_name) item_name "
				+ "FROM rar_order_detail GROUP BY order_num) USING (order_num) "
				+ "WHERE user_num=? " + sub_sql
				+ " ORDER BY order_num DESC)a) WHERE rnum>=? AND rnum<=?";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setInt(++cnt, user_num);
			if(keyword != null && !"".equals(keyword)) {
				pstmt.setString(++cnt, keyword);
			}
			pstmt.setInt(++cnt, start);
			pstmt.setInt(++cnt, end);
			
			//SQL문 실행
			rs = pstmt.executeQuery();
			list = new ArrayList<OrderVO>();
			while(rs.next()) {
				OrderVO order = new OrderVO();
				order.setOrder_num(rs.getInt("order_num"));
				order.setPay_total(rs.getInt("pay_total"));
				order.setPay_ship(rs.getInt("pay_ship"));
				order.setPay_points(rs.getInt("pay_points"));
				order.setItem_name(rs.getString("item_name"));
				order.setOrder_status(rs.getInt("order_status"));
				order.setOrder_date(rs.getDate("order_date"));
				
				list.add(order);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return list;
	}
	
	//주문목록 상세
	public OrderVO getOrder(int order_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		OrderVO order = null;
		String sql = null;
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "SELECT * FROM rar_order WHERE order_num=?";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setInt(1, order_num);
			//SQL문 실행
			rs = pstmt.executeQuery();
			if(rs.next()) {
				order = new OrderVO();
				order.setOrder_num(rs.getInt("order_num"));
				order.setOrder_date(rs.getDate("order_date"));
				order.setOrder_status(rs.getInt("order_status"));
				order.setOrder_points(rs.getInt("order_points"));
				order.setPay_total(rs.getInt("pay_total"));
				order.setPay_points(rs.getInt("pay_points"));
				order.setPay_ship(rs.getInt("pay_ship"));
				order.setPay_payment(rs.getInt("pay_payment"));
				order.setReceive_name(rs.getString("receive_name"));
				order.setReceive_post(rs.getString("receive_post"));
				order.setReceive_address1(rs.getString("receive_address1"));
				order.setReceive_address2(rs.getString("receive_address2"));
				order.setReceive_phone(rs.getString("receive_phone"));
				order.setNotice(rs.getString("notice"));
				order.setUser_num(rs.getInt("user_num"));
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return order;
	}
	
	//개별상품 상세
	public List<OrderDetailVO> getOrderListDetail(int order_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<OrderDetailVO> list = null;
		String sql = null;
		
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "SELECT * FROM rar_order_detail "
				+ "JOIN item USING (item_num) "
				+ "WHERE order_num=? ORDER BY item_num DESC";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setInt(1, order_num);
			//SQL문 실행
			rs = pstmt.executeQuery();
			list = new ArrayList<OrderDetailVO>();
			while(rs.next()) {
				OrderDetailVO detail = new OrderDetailVO();
				detail.setItem_num(rs.getInt("item_num"));
				detail.setItem_name(rs.getString("item_name"));
				detail.setBk_img(rs.getString("bk_img"));
				detail.setBk_price(rs.getInt("bk_price"));
				detail.setItem_price(rs.getInt("item_price"));
				detail.setItem_grade(rs.getInt("item_grade"));
				detail.setOrder_num(rs.getInt("order_num"));
				detail.setItem_img(rs.getString("item_img"));
				detail.setBk_num(rs.getInt("bk_num"));
				detail.setItem_status(rs.getInt("item_status"));
				
				list.add(detail);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return list;
	}
	//배송지 정보 수정
	public void updateOrder(OrderVO order)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "UPDATE rar_order SET receive_name=?,receive_post=?,receive_address1=?,receive_address2=?,"
								  + "receive_phone=?,notice=?,modify_date=SYSDATE WHERE order_num=?";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setString(1, order.getReceive_name());
			pstmt.setString(2, order.getReceive_post());
			pstmt.setString(3, order.getReceive_address1());
			pstmt.setString(4, order.getReceive_address2());
			pstmt.setString(5, order.getReceive_phone());
			pstmt.setString(6, order.getNotice());
			pstmt.setInt(7, order.getOrder_num());
			//SQL문 실행
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	//주문 취소
	public void updateOrderCancel(int order_num,int user_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		String sql = null;
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false);
			//배송상태 변경 -> 5:주문취소
			sql = "UPDATE rar_order SET order_status=5,modify_date=SYSDATE WHERE order_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, order_num);
			pstmt.executeUpdate();
			
			//사용포인트, 적립포인트 반환
			sql = "UPDATE member_detail SET user_point=? WHERE user_num=?";
			pstmt2 = conn.prepareStatement(sql);
			//포인트 계산
			OrderVO order = getOrder(order_num);
			MemberVO member = MemberDAO.getInstance().getMember(user_num);
			int point_result = member.getUser_point() + order.getPay_points() - order.getOrder_points();
	
			pstmt2.setInt(1, point_result);
			pstmt2.setInt(2, user_num);
			pstmt2.executeUpdate();
			
			//상품상태 변경 -> 1:판매중
			sql = "UPDATE item SET item_status=1 WHERE item_num=?";
			pstmt3 = conn.prepareStatement(sql);
			List<OrderDetailVO> list = getOrderListDetail(order_num);
			for(int i=0;i<list.size();i++) {
				pstmt3.setInt(1, list.get(i).getItem_num());
				pstmt3.addBatch();
				
				if(i%1000==0) {
					pstmt3.executeBatch();
				}
			}		
			pstmt3.executeBatch();
			
			conn.commit();
		}catch(Exception e) {
			conn.rollback();
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt3, null);
			DBUtil.executeClose(null, pstmt2, null);
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	//관리자 - 전체 주문 개수/검색 주문 개수
		public int getAdminOrderCount(String keyfield, String keyword) throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql = null;
			String sub_sql = "";
			int count = 0;
			
			try {
				//커넥션풀로부터 커넥션 할당
				conn = DBUtil.getConnection();
				
				if(keyword != null && !"".equals(keyword)) {
					//검색 글 개수
					if(keyfield.equals("1")) sub_sql += "WHERE order_num=?";
					else if(keyfield.equals("2")) sub_sql += "WHERE user_email LIKE '%' || ? || '%'";
					else if(keyfield.equals("3")) sub_sql += "WHERE item_name LIKE '%' || ? || '%'";
				}
				//SQL문 작성
				sql = "SELECT COUNT(*) FROM rar_order "
					+ "JOIN (SELECT order_num, LISTAGG(item_name,',') WITHIN GROUP (ORDER BY item_name) item_name "
					+ "FROM rar_order_detail GROUP BY order_num) USING (order_num) JOIN member USING(user_num) " + sub_sql;
				//PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				//?에 데이터 바인딩
				if(keyword != null && !"".equals(keyword)) {
					pstmt.setString(1, keyword);
				}
				//SQL문 실행
				rs = pstmt.executeQuery();
				if(rs.next()) {
					count = rs.getInt(1);
				}
			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(rs, pstmt, conn);
			}
			return count;
		}
		//관리자 - 전체 주문 목록/검색 주문 목록
		public List<OrderVO> getAdminOrderList(int start,int end,String keyfield,String keyword) throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			List<OrderVO> list = null;
			String sql = null;
			String sub_sql = "";
			int cnt = 0;

			try {
				//커넥션풀로부터 커넥션 할당
				conn = DBUtil.getConnection();

				if(keyword != null && !"".equals(keyword)) {
					//검색 글 개수
					if(keyfield.equals("1")) sub_sql += "WHERE order_num=?";
					else if(keyfield.equals("2")) sub_sql += "WHERE user_email LIKE '%' || ? || '%'";
					else if(keyfield.equals("3")) sub_sql += "WHERE item_name LIKE '%' || ? || '%'";
				}
				//SQL문 작성
				sql = "SELECT * FROM (SELECT a.*,rownum rnum FROM ("
					+ "SELECT * FROM rar_order JOIN (SELECT order_num, LISTAGG(item_name,', ') WITHIN GROUP (ORDER BY item_name) item_name "
					+ "FROM rar_order_detail GROUP BY order_num) USING (order_num) JOIN member USING(user_num) " + sub_sql
					+ " ORDER BY order_num DESC)a) WHERE rnum>=? AND rnum<=?";
				//PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				//?에 데이터 바인딩
				if(keyword != null && !"".equals(keyword)) {
					pstmt.setString(++cnt, keyword);
				}
				pstmt.setInt(++cnt, start);
				pstmt.setInt(++cnt, end);
				//SQL문 실행
				rs = pstmt.executeQuery();
				list = new ArrayList<OrderVO>();
				rs = pstmt.executeQuery();
				list = new ArrayList<OrderVO>();
				while(rs.next()) {
					OrderVO order = new OrderVO();
					order.setOrder_num(rs.getInt("order_num"));
					order.setPay_total(rs.getInt("pay_total"));
					order.setPay_ship(rs.getInt("pay_ship"));
					order.setPay_points(rs.getInt("pay_points"));
					order.setItem_name(rs.getString("item_name"));
					order.setOrder_status(rs.getInt("order_status"));
					order.setOrder_date(rs.getDate("order_date"));
					order.setUser_email(rs.getString("user_email"));
					
					list.add(order);
				}
			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(rs, pstmt, conn);
			}
			return list;
		}
		
		//관리자 - 배송상태 수정
		public void updateOrderStatus(OrderVO order)throws Exception{  
			Connection conn = null;
			PreparedStatement pstmt = null;
			PreparedStatement pstmt2 = null;
			String sql = null;
			try {
				//커넥션풀로부터 커넥션 할당
				conn = DBUtil.getConnection();
				//오토 커밋 해제
				conn.setAutoCommit(false);
				//SQL문 작성
				sql = "UPDATE rar_order SET order_status=?,modify_date=SYSDATE WHERE order_num=?";
				//PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, order.getOrder_status());
				pstmt.setInt(2, order.getOrder_num());
				pstmt.executeUpdate();
				
				//주문 취소일 경우만 상품개수 조정
				if(order.getOrder_status() == 5) {
					//주문번호에 해당하는 상품정보 구하기
					List<OrderDetailVO> detailList = getOrderListDetail(order.getOrder_num());
					//주문 취소로 주문상품의 상품상태 변경
					sql = "UPDATE item SET item_status=1 WHERE item_num=?";
					pstmt2 = conn.prepareStatement(sql);
					for(int i=0;i<detailList.size();i++) {
						OrderDetailVO detail = detailList.get(i);
						pstmt2.setInt(1, detail.getItem_num());
						pstmt2.addBatch();
						
						if(i%1000==0) {
							pstmt2.executeBatch();
						}
					}//end of for
					pstmt2.executeBatch();				
				}//end of if
				
				//모든 SQL문이 성공하면 커밋
				conn.commit();
			}catch(Exception e) {
				//SQL문이 하나라도 실패하면 롤백
				conn.rollback();
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(null, pstmt2, null);
				DBUtil.executeClose(null, pstmt, conn);
			}
		}
}