package kr.rar.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
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
	
	//상품 환불 상태 구하기 ()
	public int getRefundStatusByItem_num (int item_num) throws Exception{
		int status = 0;
		Connection conn = null;
		String sql = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DBUtil.getConnection();
			sql = "SELECT status FROM refund WHERE item_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, item_num);
			rs=  pstmt.executeQuery();
			if(rs.next()) {
				status = rs.getInt(1);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return status;
	}
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
	//상품 번호로 룰렛 티켓 사용 여부 구하기
	public int getTicketStatusByItem_num(int item_num) throws Exception{
		int status = 5;
		Connection conn = null;
		String sql = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DBUtil.getConnection();
			sql = "SELECT STATUS FROM roulette_ticket WHERE item_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, status);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				status = rs.getInt(1);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return status;
	}
	//상품번호로 룰렛 사용 리워드 구하기
	public int getTicketRewardByItem_num(int item_num) throws Exception{
		int reward = 0;
		Connection conn = null;
		String sql = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DBUtil.getConnection();
			sql = "SELECT reward FROM roulette_ticket WHERE item_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, reward);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				reward = rs.getInt(1);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return reward;
	}
	
	//유저 보유 포인트 구하기
	public int getPoint(int user_num) throws Exception{
		int point = 0;
		Connection conn = null;
		String sql = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DBUtil.getConnection();
			sql = "SELECT user_point FROM member_detail WHERE user_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, user_num);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				point = rs.getInt(1);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return point;	
	}
	//사용자 환불 목록 보기
	public List<RefundVO> getRefundListByUserNum(int start, int end, int user_num) throws Exception{
		List<RefundVO> list = null;
		Connection conn = null;
		String sql = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		    try {
		        conn = DBUtil.getConnection();        
		        sql=    "SELECT * FROM (SELECT ROWNUM AS RN, A.* FROM "
		        		+ "(SELECT * FROM refund WHERE user_num=? ORDER BY refund_date DESC) A) "
		        		+ "WHERE RN BETWEEN ? AND ?";
		        pstmt = conn.prepareStatement(sql);
		      	pstmt.setInt(1, user_num);     
		        pstmt.setInt(2, start);
		        pstmt.setInt(3, end);
		        rs = pstmt.executeQuery();
		        list = new ArrayList<RefundVO>();
		        while(rs.next()) {
		        	RefundVO refund = new RefundVO();
		        	refund.setRefund_num(rs.getInt("refund_num"));
		        	refund.setItem_num(rs.getInt("item_num"));
		        	refund.setOrder_num(rs.getInt("order_num"));
		        	refund.setReason(rs.getInt("reason"));
		        	refund.setReason_other(rs.getString("reason_other"));
		        	refund.setBank(rs.getString("bank"));
		        	refund.setAccount(rs.getString("account"));
		        	refund.setRefund_price(rs.getInt("refund_price"));
		        	refund.setCollect_point(rs.getInt("collect_point"));
		        	refund.setRequest_date(rs.getDate("request_date"));
		        	refund.setRefund_date(rs.getDate("refund_date"));
		        	refund.setStatus(rs.getInt("status"));
		        	list.add(refund);
		        }
		    } catch(Exception e) {
		        throw new Exception(e);
		    } finally {
		        DBUtil.executeClose(rs, pstmt, conn);
		    }
		    return list;
		}
	public int getRefundCountByUserNum(int user_num) throws Exception{
		int count = 0;
		Connection conn = null;
		String sql = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		    try {
		        conn = DBUtil.getConnection();        
		        sql=    "SELECT count(*) FROM refund WHERE user_num=? ";
		        pstmt = conn.prepareStatement(sql);
		      	pstmt.setInt(1, user_num);
		        rs = pstmt.executeQuery();
		        if(rs.next()) {
		        	count = rs.getInt(1);
		        }
		    } catch(Exception e) {
		        throw new Exception(e);
		    } finally {
		        DBUtil.executeClose(rs, pstmt, conn);
		    }
		    return count;
		}	
	//상품번호로 상품명 구하기
	public String getItemNameByItemNum(int item_num)throws Exception {
		Connection conn = null;
		String item_name = null;
		String sql = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DBUtil.getConnection();
			sql = "select item_name FROM rar_order_detail WHERE item_num =?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, item_num);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				item_name = rs.getString(1);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return item_name;
	}
	//------------------------------(관리자)
	//전체 사용자 환불 목록
	//환불 목록 카운트 구하기
	//환불 상태 변경
	//환불 완료 (포인트 회수, 아이템 복귀)
	//회수 포인트, 보유 포인트 우선 차감 후 잔액 반환
		public int deleteUserPointByRefund_point(int user_num, int refund_point) throws Exception{
			int user_point = 0;
			int shortage = 0;
			Connection conn = null;
			String sql = null;
			PreparedStatement pstmt = null;
			PreparedStatement pstmt2 = null;
			ResultSet rs = null;
			try {
				conn = DBUtil.getConnection();
				conn.setAutoCommit(false);
				sql = "SELECT user_point FROM member_detail WHERE user_num = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, user_num);
				rs = pstmt.executeQuery();
				if(rs.next()) {
					user_point = rs.getInt(1);
				}
				sql ="UPDATE member_detail SET user_point = ? WHERE user_num = ?";
				pstmt2 = conn.prepareStatement(sql);
				if(refund_point > user_point) {
					//유저 보유 포인트 0 으로 만듦
					pstmt2.setInt(1, 0);
					// 부족 포인트 구함.
					shortage = refund_point - user_point;
				}else if(user_point > refund_point) {
					pstmt2.setInt(1, user_point - refund_point);
				}
				pstmt2.setInt(2, user_num);
				pstmt2.executeUpdate();
				conn.commit();
			}catch(Exception e) {
				conn.rollback();
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(null, pstmt2, null);
				DBUtil.executeClose(rs, pstmt, conn);
			}
			return shortage;
		}
		
	//환불 완료 시 티켓을 사용하지 않았다면 티켓 삭제
		public void deleteUnusedTicket(int item_num) throws Exception{
			Connection conn = null;
			String sql = null;
			PreparedStatement pstmt = null;
			try {
				conn = DBUtil.getConnection();
				sql = "DELETE FROM roulette_ticket WHERE item_num=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, item_num);
				pstmt.executeUpdate();
			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(null, pstmt, conn);
			}
		}
	//------------------------------(사용자)
	//환불 신청(사용자)
	public void insertRefund(RefundVO refund) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		try {
			conn = DBUtil.getConnection();
			sql = "INSERT INTO refund(refund_num, order_num, item_num, reason, reason_other,"
					+ " collect_point, refund_price, bank, account, user_num) VALUES (refund_seq.nextval,?,?,?,?,?,?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, refund.getOrder_num());
			pstmt.setInt(2, refund.getItem_num());
			pstmt.setInt(3, refund.getReason());
			pstmt.setString(4, refund.getReason_other());
			pstmt.setInt(5, refund.getCollect_point());
			pstmt.setInt(6, refund.getRefund_price());
			pstmt.setString(7, refund.getBank());
			pstmt.setString(8, refund.getAccount());
			pstmt.setInt(9, refund.getUser_num());
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	//환불 취소 (환불 신청 단계에서만 가능) 

}
