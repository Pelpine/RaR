package kr.rar.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.rar.vo.BookVO;
import kr.rar.vo.CartVO;
import kr.rar.vo.ItemVO;
import kr.util.DBUtil;

public class ItemDAO {
	//싱글턴 패턴
	private static ItemDAO instance = new ItemDAO();
	
	public static ItemDAO getInstance() {
		return instance;
	}
	private ItemDAO() {}
	
	//아이템 목록
	public List<ItemVO> getItemList(int bk_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<ItemVO> list = null;
		String sql = null;
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "SELECT * FROM item "
				+ "JOIN book USING (bk_num) "
				+ "WHERE bk_num=?";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setInt(1, bk_num);
			//SQL문 실행
			rs = pstmt.executeQuery();
			
			list = new ArrayList<ItemVO>();
			while(rs.next()) {
				ItemVO item = new ItemVO();
				item.setItem_num(rs.getInt("item_num"));
				item.setItem_price(rs.getInt("item_price"));
				item.setItem_grade(rs.getInt("item_grade"));
				item.setItem_img(rs.getString("item_img"));
				item.setBk_num(rs.getInt("bk_num"));
				item.setApproval_id(rs.getInt("approval_id"));
				
				BookVO book = new BookVO();
				book.setBk_name(rs.getString("bk_name"));
				book.setBk_price(rs.getInt("bk_price"));
				book.setBk_img(rs.getString("bk_img"));
				book.setBk_genre(rs.getString("bk_genre"));
				book.setBk_publisher(rs.getString("bk_publisher"));
				book.setBk_writer(rs.getString("bk_writer"));
				
				item.setBookVO(book);
				
				list.add(item);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
		return list;
	}
	//상품 총 개수
		public int getItemCount(int bk_num)throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql = null;
			int count = 0;
			try {
				//커넥션풀로부터 커넥션 할당
				conn = DBUtil.getConnection();
				//SQL문 작성
				sql = "SELECT COUNT(*) FROM item WHERE bk_num=?";
				//PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, bk_num);
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
}