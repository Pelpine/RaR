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
	
	//책 정보 저장
	public void insertBooklist(BookVO vo)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		try {
			conn = DBUtil.getConnection();
			
			sql = "insert into book (bk_num,bk_name,bk_writer,bk_publisher,bk_price,bk_img,bk_genre) values (book_seq.nextval,?,?,?,?,?,?)";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getBk_name());
			pstmt.setString(2, vo.getBk_writer());
			pstmt.setString(3, vo.getBk_publisher());
			pstmt.setInt(4, vo.getBk_price());
			pstmt.setString(5, vo.getBk_img());
			pstmt.setString(6, vo.getBk_genre());
			
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	//책 정보 불러오기
	public BookVO selectBooklist(int num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		BookVO book = null;
		String sql = null;
		try {
			conn = DBUtil.getConnection();
			
			sql = "select * from Book where bk_num = ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				book = new BookVO();
				book.setBk_num(rs.getInt("bk_num"));
				book.setBk_name(rs.getString("bk_name"));
				book.setBk_writer(rs.getString("bk_writer"));
				book.setBk_publisher(rs.getString("bk_publisher"));
				book.setBk_price(rs.getInt("bk_price"));
				book.setBk_img(rs.getString("bk_img"));
				book.setBk_genre(rs.getString("bk_genre"));
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return book;
	}
}
