package kr.rar.dao;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import kr.rar.vo.MemberVO;
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
			pstmt3.setString(2, member.getPassword());
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
	public MemberVO checkMember(String user_email)throws Exception{
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MemberVO member = null;
		String sql = null;
		
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			//zmember와 zmember_detail 테이블을 조인할 때
			//누락된 데이터가 보여야 id 중복 체크 가능
			sql = "SELECT * FROM member LEFT OUTER JOIN "
				+ "member_detail USING(user_num) WHERE user_email = ?";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setString(1, user_email);
			//SQL문 실행
			rs = pstmt.executeQuery();
			if(rs.next()) {
				member = new MemberVO();
				member.setUser_num(rs.getInt("user_num"));
				member.setUser_email(rs.getString("user_email"));
				member.setUser_auth(rs.getInt("user_auth"));
				member.setPassword(rs.getString("password"));
				member.setUser_photo(rs.getString("user_photo"));
			}
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}	
		return member;
	}
	
	//회원상세 정보
	public MemberVO getMember(int user_num) throws Exception{
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MemberVO member = null;
		String sql = null;
		
		try {
			//커넥션풀로부터 커넥션을 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "SELECT * FROM member JOIN member_detail "
				+ "USING(user_num) WHERE user_num=?";
			//PreparedStatement 객체를 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setInt(1, user_num);
			//SQL문 실행
			if(rs.next()) {
				member = new MemberVO();
				member.setUser_num(rs.getInt("user_num"));
				member.setUser_name(rs.getString("user_name"));
				member.setUser_email(rs.getString("user_email"));
				member.setUser_auth(rs.getInt("user_auth"));
				member.setUser_phone(rs.getString("user_phone"));
				member.setUser_zipcode(rs.getString("user_zipcode"));
				member.setUser_address1(rs.getString("user_address1"));
				member.setUser_address2(rs.getString("user_address2"));
				member.setUser_photo(rs.getString("user_photo"));
				member.setUser_date(rs.getDate("user_date"));
				member.setUser_point(rs.getInt(user_num));
				member.setUser_comment(rs.getString("user_comment"));
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return member;
	}
	//회원정보 수정
	public void updateMember(MemberVO member)throws Exception{
		System.out.println(member);
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			//커넥션풀로부터 커넥션을 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "UPDATE member_detail SET user_phone=?,user_zipcode=?,"
				+ "user_address1=?,user_address2=?,user_photo=?,"
				+ "user_comment=? WHERE user_num=?";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setString(1, member.getUser_phone());
			pstmt.setString(2, member.getUser_zipcode());
			pstmt.setString(3, member.getUser_address1());
			pstmt.setString(4, member.getUser_address2());
			pstmt.setString(5, member.getUser_photo());
			pstmt.setString(6, member.getUser_comment());
			pstmt.setInt(7, member.getUser_num());
			//SQL문 실행
			pstmt.executeUpdate();	
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	//비밀번호 수정
	public void updatePassword(String password,int user_num)
                                        throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "UPDATE member_detail SET password=? WHERE user_num=?";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setString(1, password);
			pstmt.setInt(2, user_num);
			//SQL문 실행
			pstmt.executeUpdate();
		}catch(Exception e){
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	//프로필 사진 수정
	
	//회원 탈퇴(회원정보 삭제)
	
	//관리자
	//전체 내용 개수, 검색 내용 개수
	
	//목록, 검색 목록
	
	//회원등급 수정
	
}



















