package kr.rar.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import kr.rar.vo.BooklistVO;
import kr.util.DBUtil;

public class BooklistDAO {
	//싱글턴 패턴
	public static BooklistDAO instance = new BooklistDAO();
	
	public static BooklistDAO getInstance() {
		return instance;
	}
	private BooklistDAO() {}
	
	//책 정보 저장
	public void insertBooklist(BooklistVO vo)throws Exception{
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
}
