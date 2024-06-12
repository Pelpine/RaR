package kr.rar.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.rar.vo.BookVO;
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
		//리뷰 가져오기
		public int getreviewCount(int bk_num, int user_num)throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql = null;
			String sub_sql = "";
			int count = 0;
			try {
				conn = DBUtil.getConnection();
				
				if(user_num == 1) sub_sql += "user_num=?";
				else if(user_num == 2) sub_sql += "bk_num=?";
				sql = "select count(*) from review join rar_order_detail using(detail_num) where "+ sub_sql;
				
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, bk_num);
				
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
		public List<ReviewVO> getListreviewlist(int start, int end,int bk_num,int user_num)throws Exception{
			Connection conn = null;
			PreparedStatement pstmt =null;
			ResultSet rs = null;
			List<ReviewVO> list= null;
			String sql = null;
			String sub_sql = "";
			try {
				conn = DBUtil.getConnection();
				
				if(user_num == 1) sub_sql += " user_num=? and ";
				else if(user_num == 2) sub_sql += " bk_num=? and ";
				
				sql = "select * from(select a.*,rownum rnum from(select * from review join rar_order_detail using(detail_num) order by bk_num desc)a) where "+ sub_sql +" rnum >= ? and rnum <= ?";
				
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, bk_num);
				pstmt.setInt(2, start);
				pstmt.setInt(3, end);
				
				rs = pstmt.executeQuery();
				list = new ArrayList<ReviewVO>();
				while(rs.next()) {
					ReviewVO vo = new ReviewVO();
					vo.setRe_num(rs.getInt("re_num"));
					vo.setDetail_num(rs.getInt("re_num"));
					vo.setRe_comment(rs.getString("re_comment"));
					vo.setRe_img(rs.getString("re_img"));
					vo.setRe_rating(rs.getInt("re_rating"));
					vo.setUser_num(rs.getInt("user_num"));
					vo.setReg_date(rs.getDate("reg_date"));
					vo.setModify_date(rs.getDate("modify_date"));
					
					list.add(vo);
				}
			}catch(Exception e) {
				DBUtil.executeClose(rs, pstmt, conn);
			}finally {
				DBUtil.executeClose(rs, pstmt, conn);
			}
			return list;
		}
		//중복체크
		public int reviewnum(int detail_num)throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql = null;
			int reviewnum = 0;
			try {
				conn = DBUtil.getConnection();
				
				sql = "select count(*) from review where detail_num=?";
				
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, detail_num);
				
				rs = pstmt.executeQuery();
				if(rs.next()) {
					reviewnum = rs.getInt(1);
				}
			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(rs, pstmt, conn);
			}
			
			return reviewnum;
		}
		//리뷰수정
		public void update(ReviewVO vo)throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			String sql = null;
			try {
				conn = DBUtil.getConnection();
				
				sql = "update review set comment=?, modify_date=sysdate where re_num=?";
				
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, vo.getRe_comment());
				pstmt.setInt(1, vo.getRe_num());
				
				pstmt.executeUpdate();
			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(null, pstmt, conn);
			}
		}
		//리뷰 삭제
		public void delete(int re_num)throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			String sql = null;
			try {
				conn = DBUtil.getConnection();
				
				sql = "delete from review where re_num=?"; 
				
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, re_num);
				
				pstmt.executeUpdate();
			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(null, pstmt, conn);
			}
		}
		
		//상세정보 가져오기
		public ReviewVO vo(int re_num)throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql = null;
			ReviewVO vo = null;
			try {
				conn = DBUtil.getConnection();
				
				sql = "select * from review where re_num=?";
				
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, re_num);
				
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					vo = new ReviewVO();
					vo.setDetail_num(rs.getInt("detail"));
					vo.setRe_num(rs.getInt("re_num"));
					vo.setRe_img(rs.getString("re_img"));
					vo.setRe_rating(rs.getInt("re_rating"));
					vo.setUser_num(rs.getInt("user_num"));
					vo.setRe_comment(rs.getString("comment"));
				}
			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(rs, pstmt, conn);
			}
			
			return vo;
		}
}

