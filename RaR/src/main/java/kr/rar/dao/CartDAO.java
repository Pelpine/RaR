package kr.rar.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.rar.vo.BookVO;
import kr.rar.vo.CartVO;
import kr.rar.vo.ItemVO;
import kr.rar.vo.MemberVO;
import kr.util.DBUtil;

public class CartDAO {
	//싱글턴 패턴
	private static CartDAO instance = new CartDAO();
	
	public static CartDAO getInstance() {
		return instance;
	}
	public CartDAO() {}
	
	//장바구니 등록
	public void insertCart(CartVO cart)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "INSERT INTO cart (cart_num,user_num,item_num,bk_num) "
					+ "VALUES (cart_seq.nextval,?,?,?)";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setInt(1, cart.getUser_num());
			pstmt.setInt(2, cart.getItem_num());
			pstmt.setInt(3, cart.getBk_num());
			//SQL문 실행
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	//장바구니 중복 체크
	public boolean isItemExists(int user_num, int item_num) throws Exception{
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    boolean isExists = false;
	    String sql = null;
	    int count = 0;
	    
	    try {
	        conn = DBUtil.getConnection();

	        sql = "SELECT COUNT(*) FROM cart WHERE user_num = ? AND item_num = ?";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, user_num);
	        pstmt.setInt(2, item_num);
	        rs = pstmt.executeQuery();

	        if (rs.next()) {
	            count = rs.getInt(1);
	            if (count > 0) {
	                isExists = true;
	            }
	        }
	    }catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	    return isExists;
	}
	//장바구니 목록
	public List<CartVO> getCartList(int user_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<CartVO> list = null;
		String sql = null;
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "SELECT * FROM cart "
				+ "JOIN member USING (user_num) "
				+ "JOIN member_detail USING(user_num) "
				+ "JOIN book USING (bk_num) "
				+ "JOIN item USING (item_num) "
				+ "WHERE user_num=? "
				+ "ORDER BY cart_num DESC";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setInt(1, user_num);
			//SQL문 실행
			rs = pstmt.executeQuery();
			
			list = new ArrayList<CartVO>();
			while(rs.next()) {
				CartVO cart = new CartVO();
				cart.setCart_num(rs.getInt("cart_num"));
				cart.setUser_num(rs.getInt("user_num"));
				cart.setItem_num(rs.getInt("item_num"));
				cart.setBk_num(rs.getInt("bk_num"));
				
				ItemVO item = new ItemVO();
				item.setItem_grade(rs.getInt("item_grade"));
				item.setItem_price(rs.getInt("item_price"));
				item.setItem_status(rs.getInt("item_status"));
				
				MemberVO member = new MemberVO();
				member.setUser_point(rs.getInt("user_point"));
				
				BookVO book = new BookVO();
				book.setBk_name(rs.getString("bk_name"));
				book.setBk_price(rs.getInt("bk_price"));
				book.setBk_img(rs.getString("bk_img"));
				
				cart.setItemVO(item);
				cart.setMemberVO(member);
				cart.setBookVO(book);
				
				list.add(cart);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
		return list;
	}
	
	//장바구니 삭제
	public void deleteCart(int cart_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "DELETE FROM cart WHERE cart_num=?";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setInt(1, cart_num);
			//SQL문 실행
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	//장바구니 상품 총개수
		public int getCartItemCount(int user_num)throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql = null;
			int count = 0;
			try {
				//커넥션풀로부터 커넥션 할당
				conn = DBUtil.getConnection();
				//SQL문 작성
				sql = "SELECT COUNT(*) FROM cart WHERE user_num=?";
				//PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				//?에 데이터 바인딩
				pstmt.setInt(1, user_num);
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
		
		
	//선택한 장바구니 선택 초기화
	public void resetCartSelected()throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "UPDATE cart SET selected=0";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//SQL문 실행
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	//선택한 장바구니 선택 업데이트
	public void updateCartSelected(int cart_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "UPDATE cart SET selected=1 WHERE cart_num=?";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setInt(1, cart_num);
			//SQL문 실행
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	//선택한 장바구니 목록
	public List<CartVO> getSelectedCartList(int user_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<CartVO> list = null;
		String sql = null;
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "SELECT * FROM cart "
				+ "JOIN member USING (user_num) "
				+ "JOIN member_detail USING(user_num) "
				+ "JOIN book USING (bk_num) "
				+ "JOIN item USING (item_num) "
				+ "WHERE user_num=? AND selected=1 "
				+ "ORDER BY cart_num DESC";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setInt(1, user_num);
			//SQL문 실행
			rs = pstmt.executeQuery();
			
			list = new ArrayList<CartVO>();
			while(rs.next()) {
				CartVO cart = new CartVO();
				cart.setCart_num(rs.getInt("cart_num"));
				cart.setUser_num(rs.getInt("user_num"));
				cart.setItem_num(rs.getInt("item_num"));
				cart.setBk_num(rs.getInt("bk_num"));
				
				ItemVO item = new ItemVO();
				item.setItem_grade(rs.getInt("item_grade"));
				item.setItem_price(rs.getInt("item_price"));
				
				MemberVO member = new MemberVO();
				member.setUser_point(rs.getInt("user_point"));
				
				BookVO book = new BookVO();
				book.setBk_name(rs.getString("bk_name"));
				book.setBk_price(rs.getInt("bk_price"));
				book.setBk_img(rs.getString("bk_img"));
				
				cart.setItemVO(item);
				cart.setMemberVO(member);
				cart.setBookVO(book);
				
				list.add(cart);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
		return list;
	}
		
	
	
	//선택한 장바구니 상품개수
	public int getSelectedCartItemCount(int user_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		int count = 0;
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "SELECT COUNT(*) FROM cart WHERE  user_num=? AND selected=1";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setInt(1, user_num);
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
	
	//선택한 장바구니 총 구매액
	public int getSelectedCartTotal(int user_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		int total = 0;
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "SELECT SUM(sub_total) FROM "
				+ "(SELECT user_num,item_price AS sub_total FROM cart "
				+ "JOIN item USING(item_num) WHERE selected=1) WHERE user_num=?";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setInt(1, user_num);
			//SQL문 실행
			rs = pstmt.executeQuery();
			if(rs.next()) {
				total = rs.getInt(1);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return total;
	}
}