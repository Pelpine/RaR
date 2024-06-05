package kr.rar.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.rar.vo.QuestionVO;
import kr.util.DBUtil;
import kr.util.StringUtil;

public class QuestionDAO {
	//싱글턴 패턴
	private static QuestionDAO instance = new QuestionDAO();
	
	public static QuestionDAO getInstance() {
		return instance;
	}
	private QuestionDAO() {}
	
	//QnA 등록
	public void insertQuestion(QuestionVO question)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			
			sql="INSERT INTO question(question_num,question_title,"
			   + "question_content,question_filename,user_num,"
			   + "user_auth,user_ip) VALUES (question_seq.nextval,"
			   + "?,?,?,?,?,?)";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, question.getQuestion_title());
			pstmt.setString(2, question.getQuestion_content());
			pstmt.setString(3, question.getQuestion_filename());
			pstmt.setInt(4, question.getUser_num());
			pstmt.setInt(5, question.getUser_auth());
			pstmt.setString(6, question.getUser_ip());
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	//총 QnA 개수, 검색 개수
	public int getQuestionCount(String keyfield,String keyword)
	                                          throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		String sub_sql = "";
		int count = 0;
		
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			
			if(keyword!=null && !"".equals(keyword)) {
				//검색 처리
				if(keyfield.equals("1")) sub_sql += "WHERE question_num = ?";
				else if(keyfield.equals("2")) sub_sql += "WHERE question_title LIKE '%' || ? || '%'";
				else if(keyfield.equals("3")) sub_sql += "WHERE user_email LIKE '%' || ? || '%'";
				else if(keyfield.equals("4")) sub_sql += "WHERE question_content LIKE '%' || ? || '%'";
			}
			//SQL문 작성
			sql = "SELECT COUNT(*) FROM question JOIN member USING(user_num) "
				+ sub_sql;
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			
			if(keyword!=null && !"".equals(keyword)) {
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
	
	//QnA 목록, 검색 목록
	public List<QuestionVO> getListQuestion(int start, int end,
			                               String keyfield,String keyword)
	                                       throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<QuestionVO> list = null;
		String sql = null;
		String sub_sql = "";
		int cnt = 0;
		
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			
			if(keyword!=null && !"".equals(keyword)) {
				//검색 처리
				if(keyfield.equals("1")) sub_sql += "WHERE question_num = ?";
				else if(keyfield.equals("2")) sub_sql += "WHERE question_title LIKE '%' || ? || '%'";
				else if(keyfield.equals("3")) sub_sql += "WHERE user_email LIKE '%' || ? || '%'";
				else if(keyfield.equals("4")) sub_sql += "WHERE question_content LIKE '%' || ? || '%'";
			}
			//SQL문 작성
			sql = "SELECT * FROM (SELECT a.*, rownum rnum FROM "
				+ "(SELECT * FROM question JOIN member USING(user_num) " + sub_sql
				+ "ORDER BY question_num DESC)a) WHERE rnum >= ? AND rnum <= ?";
			//PreparedStatement 객체생성
			pstmt = conn.prepareStatement(sql);
			if(keyword!=null && !"".equals(keyword)) {
				pstmt.setString(++cnt, keyword);
			}
			pstmt.setInt(++cnt, start);
			pstmt.setInt(++cnt, end);
			//SQL문 실행
			rs = pstmt.executeQuery();
			list = new ArrayList<QuestionVO>();
			while(rs.next()) {
				QuestionVO question = new QuestionVO();
				question.setQuestion_num(rs.getInt("question_num"));
				question.setQuestion_title(StringUtil.useNoHTML(rs.getString("question_title")));
				question.setQuestion_reg_date(rs.getDate("question_reg_date"));
				question.setUser_email(rs.getString("user_email"));
				
				list.add(question);
			}
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return list;
	}
}
