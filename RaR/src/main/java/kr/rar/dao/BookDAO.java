package kr.rar.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.rar.vo.BookApprovalVO;
import kr.rar.vo.BookVO;
import kr.rar.vo.MemberVO;
import kr.util.DBUtil;

public class BookDAO {
	//싱글턴 패턴
	public static BookDAO instance = new BookDAO();
	
	public static BookDAO getInstance() {
		return instance;
	}
	private BookDAO() {}
	
	//책 정보 저장
	public void insertBookslist(BookVO vo)throws Exception{
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
	
	//총 등록 요청의 개수,검색 개수
			public int getBooksCount(String keyfield,String keyword)throws Exception{
				Connection conn = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				String sql = null;
				String sub_sql = "";
				int count = 0;
				try {
					conn = DBUtil.getConnection();
					
					if(keyword!=null && !"".equals(keyword)) {
						//검색 처리
						if(keyfield.equals("1")) sub_sql += " where bk_name = '%' || ? || '%' "; 
						else if(keyfield.equals("2")) sub_sql += " where bk_writer = '%' || ? || '%' ";
						else if(keyfield.equals("3")) sub_sql += " where bk_publisher = '%' || ? || '%' ";
					}
					
					sql = "select count(*) from book " + sub_sql;
					
					pstmt = conn.prepareStatement(sql);
					if(keyword!=null && !"".equals(keyword)) {
						pstmt.setString(1,keyword);
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
			//등록 요청 목록, 검색 목록
			public List<BookVO> getListbookslist(int start, int end,String keyfield,String keyword)throws Exception{
				Connection conn = null;
				PreparedStatement pstmt =null;
				ResultSet rs = null;
				List<BookVO> list= null;
				String sql = null;
				String sub_sql = "";
				int cnt = 0;
				try {
					conn = DBUtil.getConnection();
					if(keyword!=null) {
						//검색 처리
						if(keyfield.equals("1")) sub_sql += " where bk_name = '%' || ? || '%' "; 
						else if(keyfield.equals("2")) sub_sql += " where bk_writer = '%' || ? || '%' ";
						else if(keyfield.equals("3")) sub_sql += " where bk_publisher = '%' || ? || '%' ";
					}
					sql = "select * from(select a.*,rownum rnum from(select * from book "+sub_sql+" order by bk_num desc)a) where rnum >= ? and rnum <= ?";
					pstmt = conn.prepareStatement(sql);
					if(keyword!=null && !"".equals(keyword)) {
						pstmt.setString(++cnt,keyword);
					}
					pstmt.setInt(++cnt, start);
					pstmt.setInt(++cnt, end);
					
					rs = pstmt.executeQuery();
					list = new ArrayList<BookVO>();
					while(rs.next()) {
						BookVO vo = new BookVO();
						vo.setBk_num(rs.getInt("bk_num"));
						vo.setBk_name(rs.getNString("bk_name"));
						vo.setBk_writer(rs.getString("bk_writer"));
						vo.setBk_publisher(rs.getString("bk_publisher"));
						
						list.add(vo);
					}
					
				}catch(Exception e) {
					DBUtil.executeClose(rs, pstmt, conn);
				}finally {
					DBUtil.executeClose(rs, pstmt, conn);
				}
				return list;
			}
	
	//책 정보 디테일 불러오기
	public BookVO selectBookslist(int num)throws Exception{
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
