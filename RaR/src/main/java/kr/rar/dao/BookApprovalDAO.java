package kr.rar.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.rar.vo.BookApprovalVO;
import kr.rar.vo.MemberVO;
import kr.util.DBUtil;

public class BookApprovalDAO {
	//싱글턴 패턴
		public static BookApprovalDAO instance = new BookApprovalDAO();
		
		public static BookApprovalDAO getInstance() {
			return instance;
		}
		private BookApprovalDAO() {}
		
		//책 등록 요청 저장
		public void insertBook(BookApprovalVO vo)throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			String sql = null;
			try {
				conn = DBUtil.getConnection();
				
				sql = "insert into book_approval(approval_id,item_grade,bk_name,ad_comment,user_num,author,cover,pubdate,categoryname,price) values(approval_id_seq.nextval,?,?,?,?,?,?,?,?,?)";
				
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, vo.getItem_grade());
				pstmt.setString(2, vo.getBk_name());
				pstmt.setString(3, vo.getAd_comment());
				pstmt.setInt(4, vo.getUser_num());
				pstmt.setString(5, vo.getAuthor());
				pstmt.setString(6, vo.getCoverUrl());
				pstmt.setString(7, vo.getPubDate());
				pstmt.setString(8, vo.getCategoryName());
				pstmt.setInt(9, vo.getPrice());
				
				pstmt.executeUpdate();
			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(null, pstmt, conn);
			}
		}
		
		//총 등록 요청의 개수,검색 개수
		public int getBookCount(String keyfield,String keyword)throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql = null;
			String sub_sql = "";
			int count = 0;
			try {
				conn = DBUtil.getConnection();
				
				if(keyword!=null) {
					//검색 처리
					if(keyfield.equals("1")) sub_sql += " where status = 1 "; 
					else if(keyfield.equals("2")) sub_sql += " where status = 2 ";
					else if(keyfield.equals("3")) sub_sql += " where status = 0 ";
				}
				
				sql = "select count(*) from book_approval " + sub_sql;
				
				pstmt = conn.prepareStatement(sql);
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
		//등록 요청 목록, 검색 목록
		public List<BookApprovalVO> getListbookapproval(int start, int end,String keyfield,String keyword)throws Exception{
			Connection conn = null;
			PreparedStatement pstmt =null;
			ResultSet rs = null;
			List<BookApprovalVO> list= null;
			String sql = null;
			String sub_sql = "";
			int cnt = 0;
			try {
				conn = DBUtil.getConnection();
				if(keyword!=null) {
					//검색 처리
					if(keyfield.equals("1")) sub_sql += " where status = 1 "; 
					else if(keyfield.equals("2")) sub_sql += "where status = 2 ";
					else if(keyfield.equals("3")) sub_sql += " where status = 0 ";
				}
				sql = "select * from(select a.*,rownum rnum from(select * from book_approval join member using (user_num) "+sub_sql+" order by approval_id desc)a) where rnum >= ? and rnum <= ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(++cnt, start);
				pstmt.setInt(++cnt, end);
				
				rs = pstmt.executeQuery();
				list = new ArrayList<BookApprovalVO>();
				while(rs.next()) {
					BookApprovalVO vo = new BookApprovalVO ();
					vo.setApproval_id(rs.getInt("approval_id"));
					vo.setStatus(rs.getInt("status"));
					vo.setRequest_at(rs.getDate("request_at"));
					vo.setApproved_at(rs.getDate("approved_at"));
					vo.setBk_name(rs.getString("bk_name"));
					
					MemberVO member = new MemberVO();
					member.setUser_email(rs.getString("user_email"));
					
					vo.setMemberVO(member);
					list.add(vo);
				}
				
			}catch(Exception e) {
				DBUtil.executeClose(rs, pstmt, conn);
			}finally {
				DBUtil.executeClose(rs, pstmt, conn);
			}
			return list;
		}
		
		//책 등록 요청 불러오기 상세
		public BookApprovalVO selectbook(int num)throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			BookApprovalVO vo = null;
			String sql = null;
			try {
				conn = DBUtil.getConnection();
				
				sql = "select * from book_approval join member using(user_num) where approval_id = ? ";
				
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, num);
				
				rs = pstmt.executeQuery();
				if(rs.next()) {
					vo = new BookApprovalVO();
					vo.setApproval_id(rs.getInt("approval_id"));//책코드
					vo.setStatus(rs.getInt("status"));//승인상태
					vo.setRequest_at(rs.getDate("request_at"));//요청날짜
					vo.setApproved_at(rs.getDate("approved_at"));//승인날짜
					vo.setItem_grade(rs.getInt("item_grade"));//상품상태
					vo.setBk_name(rs.getString("bk_name"));//도서명
					vo.setAd_comment(rs.getString("ad_comment"));//코맨트
					vo.setAuthor(rs.getString("author"));
					vo.setPubDate(rs.getString("pubDate"));
					vo.setCoverUrl(rs.getString("coverUrl"));
					vo.setCategoryName(rs.getString("categoryName"));
					
					MemberVO member = new MemberVO();
					member.setUser_email(rs.getString("user_email"));//유저 이메일
					vo.setMemberVO(member);
				}
			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(rs, pstmt, conn);
			}
			
			return vo;
		}
		
		//승인 상태 변경 및 수정
		public void updatestatus(BookApprovalVO vo)throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			String sql = null;
			try {
				conn = DBUtil.getConnection();
				
				sql = "update book_approval where approval_id = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, vo.getApproval_id());
				
				pstmt.executeUpdate();
			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(null, pstmt, conn);
			}
		}
}
