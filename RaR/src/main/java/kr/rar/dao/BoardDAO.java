package kr.rar.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import kr.rar.vo.BoardFavVO;
import kr.rar.vo.BoardReplyVO;
import kr.rar.vo.BoardVO;
import kr.rar.vo.GenreUserVO;
import kr.rar.vo.GenreVO;
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
	public void deleteBoard(int board_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		String sql = null;
		try {
			//커넥션 풀로부터 커넥션 할당
			conn=DBUtil.getConnection();
			//오토커밋 해제
			conn.setAutoCommit(false);
			
			//좋아요 삭제
			sql="DELETE FROM board_fav WHERE board_num=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, board_num);
			pstmt.executeUpdate();
			
			//댓글 삭제
			sql="DELETE FROM board_reply WHERE board_num=?";
			pstmt2=conn.prepareStatement(sql);
			pstmt2.setInt(1, board_num);
			pstmt2.executeUpdate();
			//부모글 삭제
			sql="DELETE FROM board WHERE board_num=?";
			pstmt3 = conn.prepareStatement(sql);
			pstmt3.setInt(1, board_num);
			pstmt3.executeUpdate();
			
			//예외 발생 없이 정상적으로 SQL문 실행
			conn.commit();
		}catch(Exception e) {
			//예외 발생
			conn.rollback();
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt3, null);
			DBUtil.executeClose(null, pstmt2, null);
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
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
				else if(keyfield.equals("2"))sub_sql += "WHERE user_email LIKE '%' || ? || '%'";
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
	public void deleteFile(int board_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		try {
			//커넥션 풀로부터 커넥션 할당
			conn=DBUtil.getConnection();
			//SQL문 작성
			sql="UPDATE board SET filename='' WHERE board_num=?";
			//PreparedStatement 객체생성
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, board_num);
			//SQL문실행
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
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
	public void insertFav(BoardFavVO favVO)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		try {
			conn = DBUtil.getConnection();
			sql="INSERT INTO board_fav (board_num,user_num)VALUES(?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, favVO.getBoard_num());
			pstmt.setInt(2, favVO.getUser_num());
			
			pstmt.executeUpdate();
		}catch(Exception e) {
			pstmt.executeUpdate();
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	//총 좋아요 수
	public int selectFavCount(int board_num)throws Exception{
		int count = 0;
		Connection conn =null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		try {
			conn = DBUtil.getConnection();
			sql="SELECT COUNT(*) FROM board_fav WHERE board_num=?";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, board_num);
			//SQL문 실행
			rs=pstmt.executeQuery();
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
	//회원 번호와 게시물 번호를 이용한 좋아요 정보
	//(회원이 게시물을 호출했을 때 좋아요 선택 여부 표시)
	public BoardFavVO selectFav(BoardFavVO favVO)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		BoardFavVO fav = null;
		String sql = null;
		try {
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql="SELECT * FROM board_fav WHERE board_num=? AND user_num=?";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setInt(1, favVO.getBoard_num());
			pstmt.setInt(2, favVO.getUser_num());
			//sql문 실행
			rs = pstmt.executeQuery();
			if(rs.next()) {
				fav= new BoardFavVO();
				fav.setBoard_num(rs.getInt("board_num"));
				fav.setUser_num(rs.getInt("user_num"));
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return fav;
	}
	//좋아요 삭제
	public void deleteFav(BoardFavVO favVO)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		try {
			conn=DBUtil.getConnection();
			sql="DELETE FROM board_fav WHERE board_num=? AND user_num=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, favVO.getBoard_num());
			pstmt.setInt(2, favVO.getUser_num());
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	//내가 선택한 좋아요 목록
		public List<BoardVO> getListBoardFav(int start, int end, int user_num)throws Exception{
			
			Connection conn = null;
			PreparedStatement pstmt = null;
			String sql = null;
			ResultSet rs = null;
			List<BoardVO> list = null;
			
			try {
				conn = DBUtil.getConnection();
				//SQL문 작성
				//(주의)board_fav의 회원번호(좋아요 클릭한 회원번호)
				//검색되어야 하기때문에 f.user_num으로 표기해야 함
				
				sql="SELECT * FROM (SELECT a.*, rownum rnum FROM "
					+ "(SELECT * FROM board b JOIN "
					+ "member m USING(user_num) JOIN board_fav f "
					+ "USING(board_num) WHERE f.user_num=? ORDER BY "
					+ "board_num DESC)a) WHERE rnum>=? AND rnum <= ?";
				//PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				//?에 데이터 바인딩
				pstmt.setInt(1, user_num);
				pstmt.setInt(2, start);
				pstmt.setInt(3, end);
				//SQL문장 실행
				rs=pstmt.executeQuery();
				list = new ArrayList<BoardVO>();
				while(rs.next()) {
					BoardVO board = new BoardVO();
					board.setBoard_num(rs.getInt("board_num"));
					board.setTitle(StringUtil.useNoHTML(rs.getString("title")));
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
	//댓글 등록
		public void insertReplyBoard(BoardReplyVO boardReply)throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			String sql = null;
			try {
				conn=DBUtil.getConnection();
				sql="INSERT INTO board_reply (re_num,content,user_ip,user_num,board_num)"
						+ "	VALUES(reply_seq.nextval,?,?,?,?)";
				pstmt = conn.prepareStatement(sql);
				//데이터 바인딩
				pstmt.setString(1, boardReply.getContent());
				pstmt.setString(2, boardReply.getUser_ip());
				pstmt.setInt(3, boardReply.getUser_num());
				pstmt.setInt(4, boardReply.getBoard_num());
				pstmt.executeUpdate();
				
			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(null, pstmt, conn);
			}
		}
	//댓글 수 
		public int getReplyBoardCount(int board_num)throws Exception{
			int count = 0;
			Connection conn = null;
			PreparedStatement pstmt = null;
			String sql = null;
			ResultSet rs = null;
			
			try {
			conn=DBUtil.getConnection();
			sql="SELECT COUNT(*)FROM board_reply WHERE board_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, board_num);
			rs= pstmt.executeQuery();
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
	//댓글 목록
		public List<BoardReplyVO> getListReplyBoard(
				int start, int end, int board_num)throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			List<BoardReplyVO> list = null;
			String sql = null;
			try {
				conn=DBUtil.getConnection();
				sql="SELECT * FROM(SELECT a.*, rownum rnum FROM "
					+ "(SELECT * FROM board_reply JOIN member USING(user_num) "
					+ "WHERE board_num=? ORDER BY re_num DESC)a) "
					+ "WHERE rnum >= ? AND rnum <= ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, board_num);
				pstmt.setInt(2, start);
				pstmt.setInt(3, end);
				
				rs=pstmt.executeQuery();
				list= new ArrayList<BoardReplyVO>();
				while(rs.next()) {
					BoardReplyVO reply = new BoardReplyVO();
					reply.setRe_num(rs.getInt("re_num"));
					//날짜->1분전, 1시간전, 1일전 형식의 문자열로 변환
					
					reply.setReg_date(rs.getDate("reg_date"));
					if(rs.getString("modify_date")!=null) {
						reply.setModify_date(rs.getDate("modify_date"));
					}	
					reply.setContent(StringUtil.useBrNoHTML(
											rs.getString("content")));
					
					reply.setBoard_num(rs.getInt("board_num"));
					reply.setUser_num(rs.getInt("user_num"));
					reply.setUser_email(rs.getString("user_email"));
					
					list.add(reply);
				}
				}catch(Exception e) {
					throw new Exception(e);
				}finally {
					DBUtil.executeClose(rs, pstmt, conn);
				}
			return list;
		}
	//댓글 상세
		public BoardReplyVO getReplyBoard(int re_num) throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			BoardReplyVO reply = null;
			String sql = null;
			try {
				conn=DBUtil.getConnection();
				sql="SELECT * FROM board_reply WHERE re_num=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, re_num);
				rs=pstmt.executeQuery();
				if(rs.next()) {
					reply = new BoardReplyVO();
					reply.setRe_num(rs.getInt("re_num"));
					reply.setUser_num(rs.getInt("user_num"));
				}
			}catch(Exception e) {
				
			}finally {
				DBUtil.executeClose(rs, pstmt, conn);
			}
			
			return reply;
		}
	//댓글 수정
		public void updateReplyBoard(BoardReplyVO reply) throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			String sql = null;
			try {
				conn=DBUtil.getConnection();
				sql="UPDATE board_reply SET content=?,modify_date=SYSDATE,"
					+ "user_ip=? WHERE re_num=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, reply.getContent());
				pstmt.setString(2, reply.getUser_ip());
				pstmt.setInt(3, reply.getRe_num());
				pstmt.executeUpdate();
			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(null, pstmt, conn);
			}
		}
	//댓글 삭제
		public void deleteReplyBoard(int re_num)throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			String sql = null;
			try {
				conn = DBUtil.getConnection();
				sql="DELETE FROM board_reply WHERE re_num=?";
				pstmt=conn.prepareStatement(sql);
				pstmt.setInt(1, re_num);
				pstmt.executeUpdate();
			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(null, pstmt, conn);
			}
		}
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
		//내가 작성한 댓글 조회
		public List<BoardReplyVO> getMyReply(int start, int end, int user_num) throws Exception {
		    Connection conn = null;
		    PreparedStatement pstmt = null;
		    ResultSet rs = null;
		    List<BoardReplyVO> list = null;
		    String sql = null;
		    try {
		        // 커넥션풀로부터 커넥션 할당
		        conn = DBUtil.getConnection();
		        
		        // 댓글 조회 SQL문 작성
		        sql = "SELECT * FROM (SELECT a.*, rownum rnum FROM "
		        		+ "(SELECT Board_num,b.title,r.reg_date, r.content "
		        		+ "FROM board b JOIN board_reply r USING(board_num) "
		        		+ "WHERE r.user_num=? ORDER BY board_num DESC)a) "
		        		+ "WHERE rnum >= ? AND rnum <= ?";
		        
		        pstmt = conn.prepareStatement(sql);
		        
		        pstmt.setInt(1, user_num);
		        pstmt.setInt(2, start);
		        pstmt.setInt(3, end);
		        
		        rs = pstmt.executeQuery();
		        list = new ArrayList<BoardReplyVO>();
		        while(rs.next()) {
		        	BoardReplyVO reply = new BoardReplyVO();
		        	reply.setBoard_num(rs.getInt("board_num"));
		        	reply.setContent(rs.getString("content"));
		        	reply.setReg_date(rs.getDate("reg_date"));
		        	
		            BoardVO board = getBoard(rs.getInt("board_num"));
		            reply.setBoard(board);
		        
		        	list.add(reply);
		        }
		       
		    } catch (Exception e) {
		        throw new Exception(e);
		    } finally {
		        DBUtil.executeClose(rs, pstmt, conn);
		    }
		    return list;
		}
		
		
		//장르등록
		public void insertGenre(GenreVO genre)throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			String sql = null;
			
			try {
				conn=DBUtil.getConnection();
				sql="INSERT INTO board_genre(bg_num, bg_title, user_num) VALUES (board_genre_seq.nextval, ?, ?)";
				pstmt=conn.prepareStatement(sql);
				pstmt.setString(1, genre.getBg_title());
				pstmt.setInt(2, genre.getUser_num());
				
				pstmt.executeUpdate();
			}catch(Exception e) {
				throw new Exception(e); 
			}finally {
				DBUtil.executeClose(null, pstmt, conn);
			}
		}
		//장르 목록
		public List<GenreVO> getListGenre(int start, int end, String keyfield, String keyword)
				throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			List<GenreVO> list = null;
			String sql = null;
			int cnt = 0;
			String sub_sql="";

			try {
				conn=DBUtil.getConnection();
				if(keyword!=null && !"".equals(keyword)) {
				//글 검색
				if(keyfield.equals("1"))sub_sql += "WHERE bg_title LIKE '%' || ? || '%'";
		
				}
				//SQL문 작성
				sql = "SELECT * FROM (SELECT a.*, rownum rnum FROM "
				           + "(SELECT * FROM board_genre " + sub_sql
				           + " ORDER BY bg_title DESC)a) WHERE rnum >= ? AND rnum <= ?";				
				//PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				if(keyword!=null && !"".equals(keyword)) {
					pstmt.setString(++cnt, keyword);
				}
				pstmt.setInt(++cnt, start);
				pstmt.setInt(++cnt, end);

				//SQL문 실행
				rs = pstmt.executeQuery();
				list = new ArrayList<GenreVO>();
				while(rs.next()) {
					GenreVO genre = new GenreVO();
					genre.setBg_num(rs.getInt("bg_num"));
					genre.setBg_title(rs.getString("bg_title"));
					genre.setUser_num(rs.getInt("user_num"));
					list.add(genre);
				}			
			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(rs, pstmt, conn);
			}
			return list;
		}
		//장르 수
		public int getGenreCount(String keyfield, String keyword)
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
					
				}
				sql = "SELECT COUNT(*) FROM board_genre " +sub_sql;
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
		//장르 상세
		public GenreVO getGenre(int bg_num) throws Exception {
		    Connection conn = null;
		    PreparedStatement pstmt = null;
		    ResultSet rs = null;
		    String sql = null;
		    GenreVO genre = null;
		    try {
		        conn = DBUtil.getConnection();
		        sql = "SELECT * FROM board_genre "
		                + "JOIN member USING (user_num) "
		                + "LEFT OUTER JOIN member_detail USING (user_num) "
		                + "WHERE bg_num = ?";
		        
		        pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, bg_num );
		        rs = pstmt.executeQuery();
		        if (rs.next()) {
		            genre = new GenreVO();
		            genre.setBg_num(rs.getInt("bg_num"));
		            genre.setBg_title(rs.getString("bg_title"));
		            genre.setUser_num(rs.getInt("user_num"));
		        }
		    } catch (Exception e) {
		        throw new Exception(e);
		    } finally {
		        DBUtil.executeClose(rs, pstmt, conn);
		    }
		    
		    return genre;
		}
		//장르 글 등록 (댓글)
				public void insertReplyGenre(GenreUserVO genreUser)throws Exception{
					Connection conn = null;
					PreparedStatement pstmt = null;
					String sql = null;
					try {
						conn=DBUtil.getConnection();
						sql="INSERT INTO board_genre_user (bgu_num,bgu_content,bg_num)"
								+ "	VALUES(reply_seq.nextval,?,?)";
						pstmt = conn.prepareStatement(sql);
						//데이터 바인딩
						pstmt.setInt(1, genreUser.getBgu_num());
						pstmt.setString(2, genreUser.getBgu_content());
						pstmt.setInt(3, genreUser.getBg_num());
						pstmt.executeUpdate();
						
					}catch(Exception e) {
						throw new Exception(e);
					}finally {
						DBUtil.executeClose(null, pstmt, conn);
					}
				}
			//장르 게시판 댓글 수 
				public int getReplyGenreCount(int bg_num)throws Exception{
					int count = 0;
					Connection conn = null;
					PreparedStatement pstmt = null;
					String sql = null;
					ResultSet rs = null;
					
					try {
					conn=DBUtil.getConnection();
					sql="SELECT COUNT(*)FROM board_genre_user WHERE bg_num=?";
					pstmt = conn.prepareStatement(sql);
					pstmt.setInt(1, bg_num);
					rs= pstmt.executeQuery();
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
				//장르게시판 댓글 목록
				public List<GenreUserVO> getListReplyGenre(
						int start, int end, int bg_num)throws Exception{
					Connection conn = null;
					PreparedStatement pstmt = null;
					ResultSet rs = null;
					List<GenreUserVO> list = null;
					String sql = null;
					try {
						conn=DBUtil.getConnection();
				
						sql="SELECT * FROM(SELECT a.*, rownum rnum FROM "
								+ "(SELECT * FROM board_genre_user JOIN member USING(user_num) "
								+ "WHERE bg_num=? ORDER BY re_num DESC)a) "
								+ "WHERE rnum >= ? AND rnum <= ?";
						
						pstmt = conn.prepareStatement(sql);
						pstmt.setInt(1, bg_num);
						pstmt.setInt(2, start);
						pstmt.setInt(3, end);
						
						rs=pstmt.executeQuery();
						list= new ArrayList<GenreUserVO>();
						while(rs.next()) {
						    GenreUserVO replyg = new GenreUserVO();
						    replyg.setBgu_num(rs.getInt("bgu_num"));
						    // 날짜->1분전, 1시간전, 1일전 형식의 문자열로 변환
						    replyg.setBgu_date(rs.getDate("bgu_date"));
						    if(rs.getDate("bgu_redate") != null) {
						        replyg.setBgu_redate(rs.getDate("bgu_redate"));
						    }
						    replyg.setBgu_content(StringUtil.useBrNoHTML(rs.getString("bgu_content")));
						    replyg.setBg_num(rs.getInt("bg_num"));
						    // 추가된 부분
						    replyg.setUser_num(rs.getInt("user_num"));
						    replyg.setUser_email(rs.getString("user_email"));

						    list.add(replyg);
						}
						}catch(Exception e) {
							throw new Exception(e);
						}finally {
							DBUtil.executeClose(rs, pstmt, conn);
						}
					return list;
				}
				//장르게시판 댓글 수정
				public void updateReplyGenre(GenreUserVO replyg) throws Exception{
					Connection conn = null;
					PreparedStatement pstmt = null;
					String sql = null;
					try {
						conn=DBUtil.getConnection();
						sql="UPDATE board_genre_user SET bgu_content=?,modify_date=SYSDATE,"
							+ "user_email=? WHERE bgu_num=?";
						pstmt = conn.prepareStatement(sql);
						pstmt.setString(1, replyg.getBgu_content());
						pstmt.setString(2, replyg.getUser_email());
						pstmt.setInt(3, replyg.getBgu_num());
						pstmt.executeUpdate();
					}catch(Exception e) {
						throw new Exception(e);
					}finally {
						DBUtil.executeClose(null, pstmt, conn);
					}
				}
				//댓글 삭제
				public void deleteReplyGenre(int bgu_num)throws Exception{
					Connection conn = null;
					PreparedStatement pstmt = null;
					String sql = null;
					try {
						conn = DBUtil.getConnection();
						sql="DELETE FROM board_reply WHERE bgu_num=?";
						pstmt=conn.prepareStatement(sql);
						pstmt.setInt(1, bgu_num);
						pstmt.executeUpdate();
					}catch(Exception e) {
						throw new Exception(e);
					}finally {
						DBUtil.executeClose(null, pstmt, conn);
					}
				}
	}







