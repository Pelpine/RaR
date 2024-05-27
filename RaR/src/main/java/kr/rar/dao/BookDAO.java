package kr.rar.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import kr.rar.vo.BookVO;
import kr.util.DBUtil;

public class BookDAO {
	//싱글턴 패턴
		public static BookDAO instance = new BookDAO();
		
		public static BookDAO getInstance() {
			return instance;
		}
		private BookDAO() {}
		
		//책 등록 요청 저장
		public void insertBook(BookVO vo)throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			String sql = null;
			try {
				conn = DBUtil.getConnection();
				
				sql = "insert into book_approval(approval_id,item_grade,bk_name,ad_comment,user_num,author,cover,pubdate,categoryname) values(approval_id_seq.nextval,?,?,?,?,?,?,?,?)";
				
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, vo.getItem_grade());
				pstmt.setString(2, vo.getBk_name());
				pstmt.setString(3, vo.getAd_comment());
				pstmt.setInt(4, vo.getUser_num());
				pstmt.setString(5, vo.getAuthor());
				pstmt.setString(6, vo.getCoverUrl());
				pstmt.setString(7, vo.getPubDate());
				pstmt.setString(8, vo.getCategoryName());
				
				pstmt.executeUpdate();
			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(null, pstmt, conn);
			}
		}
		
		//책 등록 요청 불러오기
		public BookVO selectbook(int num)throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			BookVO vo = null;
			String sql = null;
			try {
				conn = DBUtil.getConnection();
				
				sql = "select * from book_approval where approval_id = ? ";
				
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, num);
				
				rs = pstmt.executeQuery();
				if(rs.next()) {
					vo = new BookVO();
					vo.setApproval_id(rs.getInt("approval_id"));
					vo.setStatus(rs.getInt("status"));
					vo.setRequest_at(rs.getDate("request_at"));
					vo.setApproved_at(rs.getDate("approved_at"));
					vo.setItem_grade(rs.getInt("item_grade"));
					vo.setBk_name(rs.getString("bk_name"));
					vo.setAd_comment(rs.getString("ad_comment"));
					vo.setUser_num(rs.getInt("user_num"));
				}
			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(rs, pstmt, conn);
			}
			
			return vo;
		}
		
}
