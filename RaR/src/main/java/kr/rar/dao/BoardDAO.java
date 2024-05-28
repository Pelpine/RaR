package kr.rar.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import kr.rar.vo.BoardVO;
import kr.util.DBUtil;
import kr.util.StringUtil;


public class BoardDAO {
	//싱글턴 패턴
	private static BoardDAO instance = new BoardDAO();
	
	public static BoardDAO getInstance() {
		return instance;
	}
	private BoardDAO() {}
	
	//글 등록
	public void insertBoard(BoardVO board) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
	try {
		conn = DBUtil.getConnection();
		sql="INSERT INTO board(board_num,title,content,"
				+ "filename,user_num,user_ip) VALUES (board_seq.nextval,"
				+ "?,?,?,?,?)";
		
		pstmt=conn.prepareStatement(sql);
		pstmt.setString(1, board.getTitle());
		pstmt.setString(2, board.getContent());
		pstmt.setString(3, board.getFilename());
		pstmt.setInt(4, board.getUser_num());
		pstmt.setString(5, board.getUser_ip());
		
		pstmt.executeUpdate();
	}catch(Exception e) {
		throw new Exception(e);
		
	}finally {
		DBUtil.executeClose(null, pstmt, conn);
	}
	}
	//글 삭제
		//글 삭제시 글의 좋아요도 삭제
		//글의 댓글 삭제
		//부모글 삭제
	
	//총 글의 개수, 검색 개수
	public int getBoardCount(String keyfield, String keyword)
							throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		String sub_sql="";
		int count = 0;
		try {
			conn = DBUtil.getConnection();
			
			if(keyword!=null && !"".equals(keyword)) {
				//검색 처리 sub_sql 조건
				if(keyfield.equals("1"))sub_sql += "WHERE title LIKE '%' || ? || '%'";
				else if(keyfield.equals("2"))sub_sql += "WHERE email LIKE '%' || ? || '%'";
				else if(keyfield.equals("3"))sub_sql += "WHERE content LIKE '%' || ? || '%'";
			}
			sql = "SELECT COUNT(*) FROM board JOIN member USING(user_num)" +sub_sql;
			pstmt = conn.prepareStatement(sql);
			if(keyword!=null && !"".equals(keyword)) {
				pstmt.setString(1, keyword);
			}
			//SQL 실행
			rs=pstmt.executeQuery();
			if(rs.next()) {
				count=rs.getInt(1);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return count;
	}
	//글 목록, 검색 글 목록
	public List<BoardVO> getListBoard(int start, int end, String keyfield, String keyword)
			throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<BoardVO> list = null;
		String sql = null;
		String sub_sql="";
		int cnt = 0;
		try {
			conn=DBUtil.getConnection();
			if(keyword!=null && !"".equals(keyword)) {
			//글 검색
			if(keyfield.equals("1"))sub_sql += "WHERE title LIKE '%' || ? || '%'";
			else if(keyfield.equals("2"))sub_sql += "WHERE user_email LIKE '%' || ? || '%'";
			else if(keyfield.equals("3"))sub_sql += "WHERE content LIKE '%' || ? || '%'";
			}
			//SQL문 작성
			sql="SELECT * FROM (SELECT a.*,rownum rnum FROM "
					+ "(SELECT * FROM board JOIN member USING(user_num)" +sub_sql
					+ "ORDER BY board_num DESC)a) WHERE rnum >= ? AND rnum<= ?";
			
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			if(keyword!=null && !"".equals(keyword)) {
				pstmt.setString(++cnt, keyword);
			}
			pstmt.setInt(++cnt, start);
			pstmt.setInt(++cnt, end);
			
			//SQL문 실행
			rs = pstmt.executeQuery();
			list = new ArrayList<BoardVO>();
			while(rs.next()) {
				BoardVO board = new BoardVO();
				board.setBoard_num(rs.getInt("board_num"));
				board.setTitle(StringUtil.useNoHTML(rs.getString("title")));//HTML태그를 허용하지 않음
				board.setHit(rs.getInt("hit"));
				board.setReg_date(rs.getDate("reg_date"));
				board.setUser_email(rs.getString("user_email"));
				
				list.add(board);
			}			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return list;
	}
	//글 상세
	public BoardVO getBoard(int board_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		BoardVO board = null;
		String sql = null;
		try {
			conn=DBUtil.getConnection();
			sql="SELECT * FROM board JOIN member USING(user_num) "
				+ "LEFT OUTER JOIN member_detail "
				+ "USING(user_num) WHERE board_num=?";
			
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, board_num);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				board = new BoardVO();
				board.setBoard_num(rs.getInt("board_num"));
				board.setTitle(rs.getString("title"));
				board.setUser_email(rs.getString("user_email"));
				board.setContent(rs.getString("content"));
				board.setHit(rs.getInt("hit"));
				board.setReg_date(rs.getDate("reg_date"));
				board.setModify_date(rs.getDate("modify_date"));
				board.setUser_photo(rs.getString("user_photo"));
				board.setUser_num(rs.getInt("user_num"));
				board.setFilename(rs.getString("filename"));
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return board;
	}
	//글 조회수 증가
	public void updateReadCount(int board_num)throws Exception{
		Connection conn =null;
		PreparedStatement pstmt = null;
		String sql = null;
		try {
			conn=DBUtil.getConnection();
			sql="UPDATE board SET hit=hit+1 WHERE board_num=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, board_num);
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	//파일 삭제
	//글 수정
	public void updateBoard(BoardVO board)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		String sub_sql="";
		int cnt = 0;
		try {
			conn=DBUtil.getConnection();
			if(board.getFilename()!=null && !"".equals(board.getFilename())) {
				sub_sql+=",filename=?";
			}
			//SQL문장 작성
			sql="UPDATE board SET title=?,content=?,modify_date=SYSDATE,user_ip=?" +
			sub_sql+"WHERE board_num=?";
			
			pstmt=conn.prepareStatement(sql);
			
			pstmt.setString(++cnt, board.getTitle());
			pstmt.setString(++cnt, board.getContent());
			pstmt.setString(++cnt, board.getUser_ip());
			if(board.getFilename()!=null 
					&& !"".equals(board.getFilename())) {
				pstmt.setString(++cnt, board.getFilename());
			}
			pstmt.setInt(++cnt, board.getBoard_num());
			//SQL문 실행
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	//좋아요
	//총 좋아요 수
	//좋아요 삭제
	//댓글 등록
	//댓글 수
	//댓글 목록
	//댓글 상세
	//댓글 수정
	//댓글 삭제
	
	//내가 작성한 글 조회
	public List<BoardVO> getMyPosting(int start, int end,
			int user_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<BoardVO> list = null;
		String sql = null;
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			//(주의)zboard_fav의 회원번호(좋아요 클릭한 회원번호)로
			//검색되어야 하기때문에 f.mem_num으로 표기해야 함
			sql = "SELECT * FROM (SELECT a.*, rownum rnum FROM "
					+ "(SELECT * FROM board b JOIN "
					+ "member m USING(user_num) WHERE user_num=? ORDER BY "
					+ "board_num DESC)a) WHERE rnum >= ? AND rnum <= ?";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setInt(1, user_num);
			pstmt.setInt(2, start);
			pstmt.setInt(3, end);
			//SQL문 실행
			rs = pstmt.executeQuery();
			list = new ArrayList<BoardVO>();
			while(rs.next()) {
				BoardVO board = new BoardVO();
				board.setBoard_num(rs.getInt("board_num"));
				board.setTitle(StringUtil.useNoHTML(rs.getString("title")));//HTML태그를 허용하지 않음
				board.setHit(rs.getInt("hit"));
				board.setReg_date(rs.getDate("reg_date"));
				board.setUser_email(rs.getString("user_email"));
				
				list.add(board);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}		
		return list;
	}

}





