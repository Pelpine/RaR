package kr.rar.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.rar.vo.EventRewardVO;
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
			sql = "INSERT INTO EVENT_list(event_num,name,filename,content,start_date,end_date,user_num,notice,banner) VALUES(EVENT_SEQ.nextval,?,?,?,?,?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, event.getName());
			pstmt.setString(2, event.getFilename());
			pstmt.setString(3, event.getContent());
			pstmt.setDate(4, event.getStart_date());
			pstmt.setDate(5, event.getEnd_date());
			pstmt.setInt(6, event.getUser_num());
			pstmt.setInt(7, event.getNotice());
			pstmt.setString(8, event.getBanner());;
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
	        if(underway.equals("on")) {
	            if(sub_sql.isEmpty()) {
	                date_sql = "WHERE status=1";
	            } else {
	            	date_sql = "AND status=1";
	            }
	        }
	        
	        sql = "SELECT * FROM (SELECT a.*, rownum rnum FROM (SELECT * FROM event_list " + sub_sql + date_sql +
	        	      " ORDER BY notice DESC, event_num DESC) a) WHERE rnum >= ? AND rnum <= ?";

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
	        	event.setNotice(rs.getInt("notice"));
	        	event.setHit(rs.getInt("hit"));
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
				event.setUser_num(rs.getInt("user_num"));
				event.setContent(rs.getString("content"));
				event.setFilename(rs.getString("filename"));
				event.setReg_date(rs.getDate("reg_date"));
				event.setStart_date(rs.getDate("start_date"));
				event.setEnd_date(rs.getDate("end_date"));
				event.setHit(rs.getInt("hit"));
				event.setNotice(rs.getInt("notice"));
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
	public void updateEvent(EventVO event) throws Exception {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    int cnt = 0;
	    String sql = null;
	    String sub_sql = "";
	    try {
	        conn = DBUtil.getConnection();
	        if (event.getFilename() != null && !event.getFilename().isEmpty()) {
	            sub_sql += ",filename=?";
	        }
	        if (event.getBanner() != null && !event.getBanner().isEmpty()) {
	            sub_sql += ",banner=?";
	        }
	        sql = "UPDATE EVENT_LIST SET name=?" + sub_sql + ",content =?, start_date=?, end_date=?, notice=? WHERE event_num=?";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(++cnt, event.getName());
	        if (event.getFilename() != null && !event.getFilename().isEmpty()) {
	            pstmt.setString(++cnt, event.getFilename());
	        }
	        if (event.getBanner() != null && !event.getBanner().isEmpty()) {
	            pstmt.setString(++cnt, event.getBanner());
	        }
	        pstmt.setString(++cnt, event.getContent());
	        pstmt.setDate(++cnt, event.getStart_date());
	        pstmt.setDate(++cnt, event.getEnd_date());
	        pstmt.setInt(++cnt, event.getNotice());
	        pstmt.setInt(++cnt, event.getEvent_num());
	        pstmt.executeUpdate();
	    } catch (Exception e) {
	        throw new Exception(e);
	    } finally {
	        DBUtil.executeClose(null, pstmt, conn);
	    }
	}

	
	//이벤트 삭제
	public void deleteEvent(int event_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		try {
			conn = DBUtil.getConnection();
			sql = "DELETE FROM EVENT_LIST WHERE event_num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, event_num);
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
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
	        if(underway.equals("on")) {
	            if(sub_sql.isEmpty()) {
	                date_sql = "WHERE status=1";
	            } else {
	            	date_sql = "AND status=1";
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
	//오늘 출석 여부 파악
	public boolean checkAttendance(int user_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		boolean checkat = false;
		try {
			conn = DBUtil.getConnection();
			 sql = "SELECT * FROM event_attendance WHERE user_num=? AND TO_CHAR(attendance_date, 'YY-MM-DD') = TO_CHAR(SYSDATE, 'YY-MM-DD')";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, user_num);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				checkat = true;
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}		
		return checkat; // checkat가 1이면 출석, 0이면 미출석
	}
	
	//출석하기
	public void attendance(int user_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		try {
			conn = DBUtil.getConnection();
			sql = "INSERT INTO EVENT_ATTENDANCE(user_num) VALUES(?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, user_num);
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	//리워드 지급
	public void updatePoint(int user_num, int point, int event_num) throws Exception{
		Connection conn = null;
		String sql = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		try {
			// memberDetail 테이블에 포인트 지급
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false);
			sql = "UPDATE MEMBER_DETAIL SET user_point= user_point+? WHERE user_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, point);
			pstmt.setInt(2, user_num);
			pstmt.executeUpdate();
			// event_rewards 테이블에 포인트 지급내역 기록
			sql = "INSERT INTO event_rewards(reward_num,reward_point,event_num, user_num) VALUES(reward_seq.nextval,?,?,?)";
			pstmt2 = conn.prepareStatement(sql);
			pstmt2.setInt(1, point);
			pstmt2.setInt(2, event_num);
			pstmt2.setInt(3, user_num);
			pstmt2.executeUpdate();
			conn.commit();
		}catch(Exception e) {
			conn.rollback();
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	//리워드 지급 내역 가져오기 
	public List<EventRewardVO> getRewardListByUserNum(int user_num)throws Exception{
		Connection conn = null;
		String sql = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<EventRewardVO> list = null;
		try {
			conn = DBUtil.getConnection();
			sql = "SELECT * FROM event_rewards WHERE user_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, user_num);
			rs = pstmt.executeQuery();
			list = new ArrayList<EventRewardVO>();
			while(rs.next()) {
				EventRewardVO reward = new EventRewardVO();
				reward.setEvent_num(rs.getInt("event_num"));
				reward.setPoint(rs.getInt("point"));
				reward.setReward_num(rs.getInt("reward_num"));
				reward.setUser_num(rs.getInt("user_num"));
				reward.setProvide_date(rs.getDate("povide_date"));
				list.add(reward);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return list;
	}
	//현재 진행중인 이벤트 배너 가져오기
	public List<EventVO> getBanner() throws Exception{
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    String sql = null;
	    ResultSet rs = null;
	    List<EventVO> list = null;
	    try {
	        conn = DBUtil.getConnection();        
	        sql = "SELECT banner,event_num FROM event_list WHERE status=1";

	        pstmt = conn.prepareStatement(sql);
	      	  
	        rs = pstmt.executeQuery();
	        list = new ArrayList<EventVO>();
	        while(rs.next()) {
	        	EventVO event = new EventVO();
	        	event.setEvent_num(rs.getInt("event_num"));
	        	event.setBanner(rs.getString("banner"));
	        	list.add(event);
	        }
	    } catch(Exception e) {
	        throw new Exception(e);
	    } finally {
	        DBUtil.executeClose(rs, pstmt, conn);
	    }
	    return list;
	}
	
	//티켓 지급
	public void insertTicket(int user_num, int item_num) throws Exception {
		Connection conn = null;
		String sql = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		try {
			conn = DBUtil.getConnection();
			sql = "INSERT INTO roulette_ticket(ticket_num, user_num, item_num) VALUES (ticket_seq.nextval,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, user_num);
			pstmt.setInt(2, item_num);			
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
	}
	
	//현재 보유중인 룰렛 티켓 가져오기
	public int getTicket(int user_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int ticket = 0;
		String sql = null;
		try {
			conn = DBUtil.getConnection();
			sql = "SELECT count(*) FROM roulette_ticket WHERE user_num=? AND status=1";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, user_num);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				ticket = rs.getInt(1);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return ticket;
	}
	//현재 보유중인 룰렛 티켓중 가장 오래된 소모하여 룰렛 돌림
	public void useTicket(int user_num, int point) throws Exception{
	    Connection conn = null;
	    String sql = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    int ticketNum = -1;
	    try {
	        conn = DBUtil.getConnection();
	        
	        // Step 1: Get the ticket number
	        String subQuery = "SELECT ticket_num FROM (SELECT ticket_num FROM roulette_ticket WHERE user_num = ? and status = 1 ORDER BY get_date) WHERE ROWNUM = 1";
	        pstmt = conn.prepareStatement(subQuery);
	        pstmt.setInt(1, user_num);
	        rs = pstmt.executeQuery();
	        if (rs.next()) {
	            ticketNum = rs.getInt("ticket_num");
	        }
	        rs.close();
	        pstmt.close();
	        
	        // Step 2: Update the ticket status and reward if a ticket was found
	        if (ticketNum != -1) {
	            sql = "UPDATE roulette_ticket SET status = 0, reward = ? WHERE ticket_num = ?";
	            pstmt = conn.prepareStatement(sql);
	            pstmt.setInt(1, point);
	            pstmt.setInt(2, ticketNum);
	            pstmt.executeUpdate();
	        } else {
	            throw new Exception("No ticket found for the given user.");
	        }
	    } catch(Exception e) {
	        throw new Exception(e);
	    } finally {
	        DBUtil.executeClose(rs, pstmt, conn);
	    }
	}

	//추천인 이벤트 중복 참여 방지
	public boolean checkParticipationReferenceEvent(int user_num) throws Exception{
	boolean check = false;
	Connection conn = null;
	String sql = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	try {
		conn = DBUtil.getConnection();
		sql = "SELECT * FROM reference_id WHERE user_num=?";
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, user_num);
		rs = pstmt.executeQuery();
		if(rs.next()) {
			//이미 참여한 유저
			check = true;
		}
	}catch(Exception e) {
		throw new Exception(e);
	}finally {
		DBUtil.executeClose(rs, pstmt, conn);
	}
	return check;
}

	//reference_id 테이블 기록 
	public void particepateReferenceEvent(int user_num, String reference_id) throws Exception{
	Connection conn = null;	
	PreparedStatement pstmt = null;
	String sql = null;
	try {
		conn = DBUtil.getConnection();
		sql = "INSERT INTO reference_id(user_num, reference_id) VALUES(?,?)";
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, user_num);
		pstmt.setString(2, reference_id);
		pstmt.executeUpdate();
	}catch(Exception e) {
		throw new Exception(e);
	}finally {
		DBUtil.executeClose(null, pstmt, conn);
	}
	}
	
	//email 입력해서 user_num 반환
	public int getUser_numByEmail(String email) throws Exception{
		int user_num = 0;
		Connection conn = null;
		String sql = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DBUtil.getConnection();
			sql = "SELECT user_num FROM member WHERE user_email=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, email);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				user_num = rs.getInt("user_num");
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return user_num;
	}
}

