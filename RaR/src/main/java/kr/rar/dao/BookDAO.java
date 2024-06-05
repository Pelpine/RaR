package kr.rar.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.rar.vo.BookApprovalVO;
import kr.rar.vo.BookVO;
import kr.rar.vo.ItemVO;
import kr.rar.vo.MemberVO;
import kr.util.DBUtil;

public class BookDAO {
	//싱글턴 패턴
	public static BookDAO instance = new BookDAO();
	
	public static BookDAO getInstance() {
		return instance;
	}
	private BookDAO() {}
	
	//책 중복채크
	public BookVO selectbks(String isbn)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs =null;
		String sql = null;
		BookVO vo = null;
		try {
			conn = DBUtil.getConnection();
			
			sql = "select bk_isbn from book where bk_isbn=?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, isbn);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				vo = new BookVO();
				vo.setBk_isbn(rs.getString("bk_isbn"));
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return vo;
	}
	
	public int selectbk(int approval_id)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs =null;
		String sql = null;
		int vo = 0;
		try {
			conn = DBUtil.getConnection();
			
			sql = "select approval_id from item where approval_id = ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, approval_id);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				vo = rs.getInt(1);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return vo;
	}
	
	//책 정보 저장
	public void insertBookslist(BookVO vo, ItemVO io)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		ResultSet rs = null;
		String sql = null;
		int order_num = 0;
		try {
			conn = DBUtil.getConnection();
			
			conn.setAutoCommit(false);
			
			sql = "select book_seq.nextval from dual";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				order_num = rs.getInt(1);
			}
			
			sql = "insert into book (bk_num,bk_name,bk_writer,bk_publisher,bk_price,bk_img,bk_genre,bk_isbn,bk_description) values (?,?,?,?,?,?,?,?,?)";
			
			pstmt2 = conn.prepareStatement(sql);
			pstmt2.setInt(1, order_num);
			pstmt2.setString(2, vo.getBk_name());
			pstmt2.setString(3, vo.getBk_writer());
			pstmt2.setString(4, vo.getBk_publisher());
			pstmt2.setInt(5, vo.getBk_price());
			pstmt2.setString(6, vo.getBk_img());
			pstmt2.setString(7, vo.getBk_genre());
			pstmt2.setString(8, vo.getBk_isbn());
			pstmt2.setString(9, vo.getBk_description());
			
			pstmt2.executeUpdate();
			
			sql = "insert into item (item_num,item_price,item_grade,item_img,bk_num,approval_id,reg_date) values(item_seq.nextval,?,?,?,?,?,SYSDATE)";
			
			pstmt3 = conn.prepareStatement(sql);
			pstmt3.setInt(1, io.getItem_price());
			pstmt3.setInt(2, io.getItem_grade());
			pstmt3.setString(3, io.getItem_img());
			pstmt3.setInt(4, order_num);
			pstmt3.setInt(5, io.getApproval_id());
			pstmt3.setDate(6, io.getReg_date());
			pstmt3.executeUpdate();
			
			conn.commit();
		}catch(Exception e) {
			conn.rollback();
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt3, null);
			DBUtil.executeClose(null, pstmt2, null);
			DBUtil.executeClose(rs, pstmt, conn);
		}
	}
	
	//책 중복시 등록
	public void intobook(BookVO vo, ItemVO io)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		ResultSet rs = null;
		String sql = null;
		int bk_num = 0;
		try {
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false);
			
			sql = "select bk_num from book where bk_isbn = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getBk_isbn());
			rs = pstmt.executeQuery();
			if(rs.next()) {
				bk_num = rs.getInt(1);
			}
			
			
			sql = "insert into item (item_num,item_price,item_grade,item_img,bk_num,approval_id) values(item_seq.nextval,?,?,?,?,?)";
			
			pstmt2 = conn.prepareStatement(sql);
			pstmt2.setInt(1, io.getItem_price());
			pstmt2.setInt(2, io.getItem_grade());
			pstmt2.setString(3, io.getItem_img());
			pstmt2.setInt(4, bk_num);
			pstmt2.setInt(5, io.getApproval_id());
			pstmt2.executeUpdate();
			
			conn.commit();
		}catch(Exception e) {
			conn.rollback();
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt2, null);
			DBUtil.executeClose(rs, pstmt, conn);
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
				int cnt = 0;
				try {
					conn = DBUtil.getConnection();
					
					if(keyword!=null && !"".equals(keyword)) {
						//검색 처리
						if(keyfield.equals("1")) sub_sql += " where bk_name like '%' || ? || '%' "; 
						else if(keyfield.equals("2")) sub_sql += " where bk_writer like '%' || ? || '%' ";
						else if(keyfield.equals("3")) sub_sql += " where bk_publisher like '%' || ? || '%' ";
						else if(keyfield.equals("4")) sub_sql += "where bk_name like '%' || ? || '%' or bk_writer like '%' || ? || '%' or bk_publisher like '%' || ? || '%'";
						else if(keyfield.equals("5")) sub_sql += " where bk_genre like '%' || ? || '%' ";
					}
					
					sql = "select count(*) from book " + sub_sql;
					
					pstmt = conn.prepareStatement(sql);
					if(keyword!=null && !"".equals(keyword)) {
						if(keyfield.equals("1") || keyfield.equals("2") || keyfield.equals("3") || keyfield.equals("5")) {
							pstmt.setString(++cnt, keyword);
						}
						if(keyfield.equals("4")) {
							pstmt.setString(++cnt, keyword);
							pstmt.setString(++cnt, keyword);
							pstmt.setString(++cnt, keyword);
						}
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
					if(keyword!=null && !"".equals(keyword)) {
						//검색 처리
						if(keyfield.equals("1")) sub_sql += " where bk_name like '%' || ? || '%' "; 
						else if(keyfield.equals("2")) sub_sql += " where bk_writer like '%' || ? || '%' ";
						else if(keyfield.equals("3")) sub_sql += " where bk_publisher like '%' || ? || '%' ";
						else if(keyfield.equals("4")) sub_sql += " where bk_name like '%' || ? || '%' or bk_writer like '%' || ? || '%' or bk_publisher like '%' || ? || '%'";
						else if(keyfield.equals("5")) sub_sql += " where bk_genre like '%' || ? || '%' ";
					}
					sql = "select * from(select a.*,rownum rnum from(select * from book "+sub_sql+" order by bk_num desc)a) where rnum >= ? and rnum <= ?";
					pstmt = conn.prepareStatement(sql);
					if(keyword!=null && !"".equals(keyword)) {
						if(keyfield.equals("1") || keyfield.equals("2") || keyfield.equals("3") || keyfield.equals("5")) {
							pstmt.setString(++cnt, keyword);
						}
						if(keyfield.equals("4")) {
							pstmt.setString(++cnt, keyword);
							pstmt.setString(++cnt, keyword);
							pstmt.setString(++cnt, keyword);
						}
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
						vo.setBk_img(rs.getString("bk_img"));
						vo.setBk_genre(rs.getString("bk_genre"));
						
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
	
	//중복체크
	public BookApprovalVO ckmem(int user_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		BookApprovalVO vo = null;
		try {
			conn = DBUtil.getConnection();
			
			sql = "select user_email from member where user_num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, user_num);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				vo = new BookApprovalVO();
				vo.setUser_email(rs.getString("user_email"));
			}
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return vo;
	}
	//책 상세보기 및 가격정보 뛰우기
	public BookVO vo(int num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		BookVO vo = null;
		try {
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false);
			
			sql = "SELECT b.*, MIN(i.item_price) OVER() as item_price FROM book b JOIN item i ON b.bk_num = i.bk_num WHERE b.bk_num = ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				vo = new BookVO();
				vo.setBk_num(rs.getInt("bk_num"));
				vo.setBk_name(rs.getString("bk_name"));
				vo.setBk_writer(rs.getString("bk_writer"));
				vo.setBk_publisher(rs.getString("bk_publisher"));
				vo.setBk_price(rs.getInt("bk_price"));
				vo.setBk_img(rs.getString("bk_img"));
				vo.setBk_genre(rs.getString("bk_genre"));
				vo.setBk_isbn(rs.getString("bk_isbn"));
				vo.setBk_description(rs.getString("bk_description"));
				
				ItemVO item = new ItemVO();
				item.setItem_price(rs.getInt("item_price"));
				
				vo.setItemVO(item);
			
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return vo;
	}
}
