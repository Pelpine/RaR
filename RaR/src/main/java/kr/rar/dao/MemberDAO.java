package kr.rar.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import kr.member.vo.MemberVO;
import kr.util.DBUtil;

public class MemberDAO {
	//싱글턴 패턴
	private static MemberDAO instance = new MemberDAO();
	
	public static MemberDAO getInstance() {
		return instance;
	}
	public MemberDAO() {}
	
	//회원가입
	public void insertMember(MemberVO member)
	                         throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		ResultSet rs = null;
		String sql = null;
		int num = 0;//시퀀스 번호 저장
		
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			//오토 커밋 해제
			conn.setAutoCommit(false);
			//회원 번호(mem_num) 생성
			sql = "SELECT member_seq.nextval FROM dual";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				num = rs.getInt(1);
			}
			
			sql = "INSERT INTO member (user_num,user_email) VALUES (?,?)";
			pstmt2 = conn.prepareStatement(sql);
			pstmt2.setInt(1, num);//시퀀스 번호
			pstmt2.setString(2, member.getUser_email());//아이디
			pstmt2.executeUpdate();
			
			sql = "INSERT INTO member_detail (user_num,passwd,user_phone,"
				+ "user_zipcode,user_address1,user_address2,user_photo,"
				+ "user_date,user_ip,user_point,user_comment) VALUES ("
				+ "?,?,?,?,?,?,?,?,?,?,?)";
			pstmt3 = conn.prepareStatement(sql);
			pstmt3.setInt(1, num);
			pstmt3.setString(2, member.getUser_password());
			pstmt3.setString(3, member.getUser_phone());
			pstmt3.setString(4, member.getUser_zipcode());
			pstmt3.setString(5, member.getUser_address1());
			pstmt3.setString(6, member.getUser_address2());
			pstmt3.setString(7, member.getUser_photo());
			pstmt3.setDate(8, member.getUser_date());
			pstmt3.setString(9, member.getUser_ip());
			pstmt3.setInt(10, member.getUser_point());
			pstmt3.setString(11, member.getUser_comment());
			
			//SQL 실행시 모두 성공하면 commit
			conn.commit();
			
		}catch(Exception e) {
			//SQL문이 하나라도 실패하면 rollback
			conn.rollback();
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt3, null);
			DBUtil.executeClose(null, pstmt2, null);
			DBUtil.executeClose(rs, pstmt, conn);
		}
	}//회원가입 end
	
	//ID 중복 체크 및 로그인 처리
	public MemberVO checkMember(String id)throws Exception{
		
	}
	
}
