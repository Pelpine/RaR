package kr.rar.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

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
			sql="INSERT INTO rar_order_detail (detail_num,item_num,item_name,bk_img,bk_price,item_price,item_grade,order_num) "
					+ "VALUES (rar_order_detail_seq.nextval,?,?,?,?,?,?,?)";
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
			
			pstmt6.setInt(1, order.getPay_points());
			
			//모든 SQL문이 정상 수행
			conn.commit();
		}catch(Exception e) {
			//SQL문이 하나라도 실패
			conn.rollback();
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt5, null);
			DBUtil.executeClose(null, pstmt3, null);
			DBUtil.executeClose(null, pstmt4, null);
			DBUtil.executeClose(null, pstmt2, null);
			DBUtil.executeClose(rs, pstmt, conn);
		}
	}
	//주문목록
	//주문목록 상세
}
