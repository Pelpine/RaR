package kr.rar.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import kr.rar.vo.OrderDetailVO;
import kr.rar.vo.RefundVO;
import kr.util.DBUtil;

public class RefundDAO {
	private static RefundDAO instance = new RefundDAO();
	public static RefundDAO getInstance() {
		return instance;
	}
	private RefundDAO() {};
	//-------------------------------(공통)
	//환불 기한 구하기 (구매 날짜 14일 이후)
	public Date getRefundDeadLineByOrder_Num(int order_num) throws Exception{
		Date deadline = null;
		Connection conn = null;
		String sql = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DBUtil.getConnection();
			sql = "SELECT order_date + INTERVAL '5' DAY AS refund_deadline FROM rar_order WHERE order_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, order_num);
			rs=  pstmt.executeQuery();
			if(rs.next()) {
				deadline = rs.getDate(1);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return deadline;
	}
	//상품 번호로 티켓 번호 구하기
	//티켓 번호로 status or reward 반환
	//상품 번호로 상품주문 정보 구하기
	public OrderDetailVO getOrderDetailByItem_num(int item_num) throws Exception{
		OrderDetailVO orderdetail = null;
		Connection conn = null;
		String sql = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "SELECT * FROM rar_order_detail WHERE item_num = ?";
			pstmt= conn.prepareStatement(sql);
			pstmt.setInt(1, item_num);
			rs = pstmt.executeQuery();
			orderdetail = new OrderDetailVO();
			if(rs.next()) {
				orderdetail.setOrder_num(rs.getInt("order_num"));
				orderdetail.setItem_name(rs.getString("item_name"));
				orderdetail.setItem_grade(rs.getInt("item_grade"));
				orderdetail.setItem_price(rs.getInt("item_price"));
				orderdetail.setBk_img(rs.getString("bk_img"));
			}
		}catch(Exception e){
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return orderdetail;
	}
	//------------------------------(관리자)
	//환불 목록
	//환불 목록 카운트 구하기
	//환불 상태 변경
	//환불 완료 (포인트 회수)
	//------------------------------(사용자)
	//환불 신청(사용자)
	public void insertRefund(RefundVO refund) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		try {
			conn = DBUtil.getConnection();
			sql = "INSERT INTO refund(refund_num, order_num, item_num, reason, reason_other,"
					+ " collect_point, refund_price, bank, account) VALUES (refund_seq.nextval,?,?,?,?,?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, refund.getOrder_num());
			pstmt.setInt(2, refund.getItem_num());
			pstmt.setInt(3, refund.getReason());
			pstmt.setString(4, refund.getReason_other());
			pstmt.setInt(5, refund.getCollect_point());
			pstmt.setInt(6, refund.getRefund_price());
			pstmt.setString(7, refund.getBank());
			pstmt.setString(8, refund.getAccount());
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	//환불 취소 (환불 신청 단계에서만 가능) 
	
	
}
