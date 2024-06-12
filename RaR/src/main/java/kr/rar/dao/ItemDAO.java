package kr.rar.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.rar.vo.BookVO;
import kr.rar.vo.CartVO;
import kr.rar.vo.ItemVO;
import kr.rar.vo.OrderDetailVO;
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
				item.setBk_num(rs.getInt("bk_num"));;
				item.setItem_status(rs.getInt("item_status"));
				
				BookVO book = new BookVO();
				book.setBk_name(rs.getString("bk_name"));
				book.setBk_price(rs.getInt("bk_price"));
				book.setBk_img(rs.getString("bk_img"));
				
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
	//상품 상세
	public ItemVO getItem(int item_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ItemVO item = null;
		String sql = null;
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "SELECT * FROM item JOIN book USING (bk_num) WHERE item_num=?";
			//PreparedStatememtn 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setInt(1, item_num);
			//SQL문 실행
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				item = new ItemVO();
				item.setItem_num(rs.getInt("item_num"));
				item.setItem_price(rs.getInt("item_price"));
				item.setItem_grade(rs.getInt("item_grade"));
				item.setItem_img(rs.getString("item_img"));
				item.setBk_num(rs.getInt("bk_num"));
				item.setApproval_id(rs.getInt("approval_id"));
				item.setItem_status(rs.getInt("item_status"));
				
				BookVO book = new BookVO();
				book.setBk_name(rs.getString("bk_name"));
				book.setBk_writer(rs.getString("bk_writer"));
				book.setBk_publisher(rs.getString("bk_publisher"));
				book.setBk_price(rs.getInt("bk_price"));
				book.setBk_img(rs.getString("bk_img"));
				book.setBk_genre(rs.getString("bk_genre"));
				book.setBk_isbn(rs.getString("bk_isbn"));
				book.setBk_description(rs.getString("bk_description"));
				
				item.setBookVO(book);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return item;
	}
	
	//판매 가능한 상품 총 개수
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
				sql = "SELECT COUNT(*) FROM item WHERE bk_num=? AND item_status=1";
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
		//아이템 목록 //사용자용
		public List<ItemVO> getListItem(
			     int start, int end, String keyfield,
			     String keyword,int status)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<ItemVO> list = null;
		String sql = null;
		String sub_sql = "";
		int cnt = 0;
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			
			if(keyword !=null && !"".equals(keyword)) {
				//검색 처리
				if(keyfield.equals("1")) sub_sql += "AND name LIKE '%' || ? || '%'";
				else if (keyfield.equals("2")) sub_sql += "AND detail LIKE '%' || ? || '%'";
			}
			
			//SQL문 작성
			sql = "SELECT * FROM (SELECT a.*,rownum rnum FROM "
				+ "(SELECT * FROM item JOIN book USING (bk_num) WHERE item_status = ? " + sub_sql
				+ " ORDER BY reg_date DESC)a) "
				+ "WHERE rnum >= ? AND rnum <= ?";
	
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setInt(++cnt, status);
			if(keyword!=null && !"".equals(keyword)) {
				pstmt.setString(++cnt, keyword);
			}
			pstmt.setInt(++cnt, start);
			pstmt.setInt(++cnt, end);
			//SQL문 실행
			rs = pstmt.executeQuery();
			list = new ArrayList<ItemVO>();
			while(rs.next()) {
				ItemVO item = new ItemVO();
				item.setItem_num(rs.getInt("item_num"));
				item.setItem_price(rs.getInt("item_price"));
				item.setItem_grade(rs.getInt("item_grade"));
				item.setItem_img(rs.getString("item_img"));
				item.setBk_num(rs.getInt("bk_num"));;
				item.setItem_status(rs.getInt("item_status"));
				
				
				BookVO book = new BookVO(); book.setBk_name(rs.getString("bk_name"));
				book.setBk_price(rs.getInt("bk_price"));
				book.setBk_img(rs.getString("bk_img"));
				 
				item.setBookVO(book);
				
				list.add(item);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}		
		return list;
	}
		//아이템 목록
		public List<ItemVO> getTopItem(
				int start, int end)throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			List<ItemVO> list = null;
			String sql = null;
			try {
				//커넥션풀로부터 커넥션 할당
				conn = DBUtil.getConnection();


				//SQL문 작성
				sql = "SELECT * FROM (SELECT a.*,rownum rnum FROM "
						+ "(SELECT i.bk_num, b.bk_img, r.item_name, r.bk_price, COUNT(i.bk_num) "
						+ "FROM item i JOIN rar_order_detail r USING(item_num) "
						+ "            JOIN book b ON i.bk_num = b.bk_num "
						+ "GROUP BY i.bk_num, b.bk_img, r.item_name, r.bk_price "
						+ "ORDER BY COUNT(i.bk_num) DESC)a) "
						+ "WHERE rnum >= ? AND rnum <=?";

				//PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				//?에 데이터 바인딩
				pstmt.setInt(1, start);
				pstmt.setInt(2, end);
				//SQL문 실행
				rs = pstmt.executeQuery();
				list = new ArrayList<ItemVO>();
				while(rs.next()) {
					ItemVO item = new ItemVO();
					item.setBk_num(rs.getInt("bk_num"));
					
					BookVO book = new BookVO();
					book.setBk_img(rs.getString("bk_img"));
					
					OrderDetailVO order = new OrderDetailVO();
					order.setItem_name(rs.getString("item_name"));
					order.setBk_price(rs.getInt("bk_price"));

					
					item.setBookVO(book);
					item.setOrderDetailVO(order);

					list.add(item);
				}
			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(rs, pstmt, conn);
			}		
			return list;
		}
		//관리자/사용자 - 전체 상품 개수/검색 상품 개수
		public int getItemCount(String keyfield,String keyword, int status)throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql = null;
			String sub_sql = "";
			int count = 0;
			try {
				//커넥션풀로부터 커넥션 할당
				conn = DBUtil.getConnection();
				
				if(keyword !=null && !"".equals(keyword)) {
					if(keyfield.equals("1")) sub_sql += "AND name LIKE '%' || ? || '%'";
				}
				
				sql = "SELECT COUNT(*) FROM item JOIN book USING(bk_num) WHERE item_status > ? " + sub_sql;
				//PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, status);
				if(keyword!=null && !"".equals(keyword)) {
					pstmt.setString(2, keyword);
				}
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
		//아이템 목록//관리자
		public List<ItemVO> getAdminListItem(
				int start, int end, String keyfield,
				String keyword,int status)throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			List<ItemVO> list = null;
			String sql = null;
			String sub_sql = "";
			int cnt = 0;
			try {
				//커넥션풀로부터 커넥션 할당
				conn = DBUtil.getConnection();

				if(keyword !=null && !"".equals(keyword)) {
					//검색 처리
					if(keyfield.equals("1")) sub_sql += "AND name LIKE '%' || ? || '%'";
					else if (keyfield.equals("2")) sub_sql += "AND detail LIKE '%' || ? || '%'";
				}

				//SQL문 작성
				//status가 0이면, 1(미표시),2(표시) 모두 호출 -> 관리자용
				//status가 1이면, 2(표시) 호출 -> 사용자용
				sql = "SELECT * FROM (SELECT a.*,rownum rnum FROM "
						+ "(SELECT * FROM item JOIN book USING (bk_num) WHERE item_status >= ? " + sub_sql
						+ " ORDER BY reg_date DESC)a) "
						+ "WHERE rnum >= ? AND rnum <= ?";

				//PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				//?에 데이터 바인딩
				pstmt.setInt(++cnt, status);
				if(keyword!=null && !"".equals(keyword)) {
					pstmt.setString(++cnt, keyword);
				}
				pstmt.setInt(++cnt, start);
				pstmt.setInt(++cnt, end);
				//SQL문 실행
				rs = pstmt.executeQuery();
				list = new ArrayList<ItemVO>();
				while(rs.next()) {
					ItemVO item = new ItemVO();
					item.setItem_num(rs.getInt("item_num"));
					item.setItem_price(rs.getInt("item_price"));
					item.setItem_grade(rs.getInt("item_grade"));
					item.setItem_img(rs.getString("item_img"));
					item.setReg_date(rs.getDate("reg_date"));
					item.setBk_num(rs.getInt("bk_num"));;
					item.setItem_status(rs.getInt("item_status"));


					BookVO book = new BookVO(); book.setBk_name(rs.getString("bk_name"));
					book.setBk_price(rs.getInt("bk_price"));
					book.setBk_img(rs.getString("bk_img"));

					item.setBookVO(book);

					list.add(item);
				}
			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(rs, pstmt, conn);
			}		
			return list;
		}
		//관리자 - 상품 수정
		public void updateItem(ItemVO item)throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			String sql = null;
			try {
				//커넥션풀로부터 커넥션 할당
				conn = DBUtil.getConnection();


				sql = "UPDATE item SET item_price=?, item_grade=?, item_status=? WHERE item_num=?";
				//PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				//?에 데이터 바인딩
				pstmt.setInt(1, item.getItem_price());
				pstmt.setInt(2, item.getItem_grade());
				pstmt.setInt(3, item.getItem_status());
				pstmt.setInt(4, item.getItem_num());
				//SQL문 실행
				pstmt.executeUpdate();

			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(null, pstmt, conn);
			}
		}
		//관리자 - 상품 삭제
		public void deleteItem(int item_num)throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			PreparedStatement pstmt2 = null;
			String sql = null;
			try {
				//커넥션풀로부터 커넥션 할당
				conn = DBUtil.getConnection();
				//오토 커밋 해제
				conn.setAutoCommit(false);
				
				sql = "DELETE FROM cart WHERE item_num=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, item_num);
				pstmt.executeUpdate();
				
				sql = "DELETE FROM item WHERE item_num=?";
				pstmt2 = conn.prepareStatement(sql);
				pstmt2.setInt(1, item_num);
				pstmt2.executeUpdate();
				
				//모든 SQL문이 성공하면 commit
				conn.commit();			
			}catch(Exception e) {
				//SQL문이 하나라도 실패하면 rollback
				conn.rollback();
				DBUtil.executeClose(null, pstmt, null);
				DBUtil.executeClose(null, pstmt2, conn);
			}
		}
}