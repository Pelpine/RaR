package kr.rar.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

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
	//이벤트 게시판 목록 가져오기(현재 진행중인 이벤트만 볼 수 있게 선택 가능)
	public List<EventVO> getBoard(int start, int end, String keyfield, String keyword, String underway) throws Exception{
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    String sql = null;
	    String sub_sql = "";
	    String date_sql = "";
	    ResultSet rs = null;
	    int cnt = 0;
	    List<EventVO> list = null;
	    try {
	        conn = DBUtil.getConnection();
	        
	        if(keyword != null && !"".equals(keyword)) {
	            // 검색 처리
	            if(keyfield.equals("1")) sub_sql += "WHERE name LIKE '%' || ? || '%'";
	            else if(keyfield.equals("2")) sub_sql += "WHERE content LIKE '%' || ? || '%'";
	        }
	        
	        // 날짜 조건 처리
	        if(underway.equals("1")) {
	            if(sub_sql.isEmpty()) {
	                date_sql = "WHERE SYSDATE BETWEEN start_date AND end_date";
	            } else {
	            	date_sql = "AND SYSDATE BETWEEN start_date AND end_date";
	            }
	        }
	        
	        sql = "SELECT * FROM (SELECT a.*, rownum rnum FROM (SELECT * FROM event_list " + sub_sql + date_sql +
	        	      " ORDER BY event_num DESC) a) WHERE rnum >= ? AND rnum <= ?";

	        pstmt = conn.prepareStatement(sql);
	      	        
	        if(keyword != null && !"".equals(keyword)) {
	            pstmt.setString(++cnt, keyword);
	        }	        
	        pstmt.setInt(++cnt, start);
	        pstmt.setInt(++cnt, end);
	        rs = pstmt.executeQuery();
	        list = new ArrayList<EventVO>();
	        while(rs.next()) {
	        	EventVO event = new EventVO();
	        	event.setEvent_num(rs.getInt("event_num"));
	        	event.setName(rs.getString("name"));
	        	event.setStart_date(rs.getDate("start_date"));
	        	event.setEnd_date(rs.getDate("end_date"));
	        	
	        	list.add(event);
	        }
	    } catch(Exception e) {
	        throw new Exception(e);
	    } finally {
	        DBUtil.executeClose(rs, pstmt, conn);
	    }
	    return list;
	}
	//이벤트 상세
	public EventVO EventDetail(int event_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		EventVO event = null;
		try {
			conn = DBUtil.getConnection();
			sql = "SELECT * FROM EVENT_LIST WHERE event_num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, event_num);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				event = new EventVO();
				event.setEvent_num(event_num);
				event.setName(rs.getString("name"));
				event.setContent(rs.getString("content"));
				event.setFilename(rs.getString("filename"));
				event.setReg_date(rs.getDate("reg_date"));
				event.setStart_date(rs.getDate("start_date"));
				event.setEnd_date(rs.getDate("end_date"));
				event.setHit(rs.getInt("hit"));
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return event;
	}
	//이벤트 조회수 증가
	public void updateReadCount(int event_num) throws Exception {
		Connection conn = null;
		String sql = null;
		PreparedStatement pstmt = null;
		try {
			conn = DBUtil.getConnection();
			sql = "UPDATE event_list SET hit = hit+1 WHERE event_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, event_num);
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	//이벤트 수정
	public void updateEvent (EventVO event) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		try {
			conn = DBUtil.getConnection();
			sql = "UPDATE EVENT_LIST SET name=?, filename=?, start_date=?, end_date=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, event.getName());
			pstmt.setString(2, event.getFilename());
			pstmt.setDate(3, event.getStart_date());
			pstmt.setDate(4, event.getEnd_date());
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	//이벤트 삭제
	//이벤트 개수 구하기
	public int getBoardCount(String keyfield, String keyword, String underway) throws Exception{
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    String sql = null;
	    String sub_sql = "";
	    String date_sql = "";
	    ResultSet rs = null;
	    int cnt = 0;
	    int count = 0;
	    try {
	        conn = DBUtil.getConnection();
	        
	        if(keyword != null && !"".equals(keyword)) {
	            // 검색 처리
	            if(keyfield.equals("1")) sub_sql += "WHERE name LIKE '%' || ? || '%'";
	            else if(keyfield.equals("2")) sub_sql += "WHERE content LIKE '%' || ? || '%'";
	        }
	        
	        // 날짜 조건 처리
	        if(underway.equals("1")) {
	            if(sub_sql.isEmpty()) {
	                date_sql = "WHERE SYSDATE BETWEEN start_date AND end_date";
	            } else {
	                date_sql = " AND SYSDATE BETWEEN start_date AND end_date";
	            }
	        }

	        sql = "SELECT count(*) FROM event_list " + sub_sql + date_sql;
	        pstmt = conn.prepareStatement(sql);
	        
	        
	        if(keyword != null && !"".equals(keyword)) {
	            pstmt.setString(++cnt, keyword);
	        }
	        
	        
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
}
