package kr.rar.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import kr.rar.vo.EventVO;
import kr.util.DBUtil;

public class EventDAO {
	
	private static EventDAO instance = new EventDAO();

	public static EventDAO getInstance() {
		return instance;
	}
	private EventDAO(){}
	
	//이벤트 등록
	public void insertEvent(EventVO event) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		try {
			conn = DBUtil.getConnection();
			sql = "INSERT INTO EVENT_list(event_num,name,filename,content,start_date,end_date,user_num) VALUES(EVENT_SEQ.nextval,?,?,?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, event.getName());
			pstmt.setString(2, event.getFilename());
			pstmt.setString(3, event.getContent());
			pstmt.setDate(4, event.getStart_date());
			pstmt.setDate(5, event.getEnd_date());
			pstmt.setInt(6, event.getUser_num());
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	//이벤트 게시판 목록 가져오기(현재 진행중인 이벤트만)

	//이벤트 상세
	//이벤트 수정
	//이벤트 삭제
	//이벤트 개수 구하기
	public int getCount() throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		int count = 0;
		try {
			conn = DBUtil.getConnection();
			sql = "SELECT count(*) FROM EVENT_LIST";
			pstmt = conn.prepareStatement(sql);
			rs= pstmt.executeQuery();
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

}
