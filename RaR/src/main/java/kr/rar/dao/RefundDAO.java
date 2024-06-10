package kr.rar.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import kr.rar.vo.RefundVO;
import kr.util.DBUtil;

public class RefundDAO {
	private static RefundDAO instance = new RefundDAO();
	public static RefundDAO getInstance() {
		return instance;
	}
	private RefundDAO() {};
	//------------------------------(관리자)
	//환불 목록
	//환불 목록 카운트 구하기
	//환불 상태 변경
	
	//------------------------------(사용자)
	//환불 신청(사용자)
	public void insertRefund(RefundVO refund) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		try {
			conn = DBUtil.getConnection();
			sql = "INSERT INTO refund(refund_num, order_num, item_num, reason, reason_other,"
					+ " collect_point, refund_price) VALUES (refund_seq.nextval,?,?,?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, refund.getOrder_num());
			pstmt.setInt(2, refund.getItem_num());
			pstmt.setInt(3, refund.getReason());
			pstmt.setString(4, refund.getReason_other());
			pstmt.setInt(5, refund.getCollect_point());
			pstmt.setInt(6, refund.getRefund_price());
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	//환불 취소 (환불 신청 단계에서만 가능) 
	//환불 완료 (포인트 회수)
	//구매 날짜 구하기
	//상품 번호로 티켓 번호 구하기
	//티켓 번호로 status or reward 반환
}
