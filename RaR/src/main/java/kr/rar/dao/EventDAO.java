package kr.rar.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import kr.util.DBUtil;

public class EventDAO {
	
	private static EventDAO instance = new EventDAO();

	public static EventDAO getInstance() {
		return instance;
	}
	private EventDAO(){}
	
	//이벤트 게시판 목록 가져오기(현재 진행중인 이벤트만)
	//이벤트 게시판 등록
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
