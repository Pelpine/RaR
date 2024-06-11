package kr.rar.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import kr.rar.vo.ReviewVO;
import kr.util.DBUtil;

public class ReviewDAO {
	//싱글턴 패턴
		public static ReviewDAO instance = new ReviewDAO();
		
		public static ReviewDAO getInstance() {
			return instance;
		}
		private ReviewDAO() {}
		
		//리뷰 등록
		public void insertreview(ReviewVO vo)throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			String sql = null;
			try {
				conn = DBUtil.getConnection();
				
				sql = "insert into review(re_num,detail_num,user_num,re_comment,re_img,re_rating) values(re_num_seq.nextval,?,?,?,?,?)";
			
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, vo.getDetail_num());
				pstmt.setInt(2, vo.getUser_num());
				pstmt.setString(3, vo.getRe_comment());
				pstmt.setString(4, vo.getRe_img());
				pstmt.setInt(5, vo.getRe_rating());
				
				pstmt.executeUpdate();
			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(null, pstmt, conn);
			}
		}
}
