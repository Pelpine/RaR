package kr.rar.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import kr.rar.vo.BoardVO;
import kr.util.DBUtil;


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
		sql="INSERT INTO board(board_num,title,content"
				+ "filename,user_num,ip) VALUES (board_seq.nextval,"
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
			
		
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return count;
	}
	//글 목록, 검색 글 목록
	//글 상세
	//글 조회수 증가
	//글 수정
	//좋아요
	//총 좋아요 수
	//좋아요 삭제
	//댓글 등록
	//댓글 수
	//댓글 목록
	//댓글 상세
	//댓글 수정
	//댓글 삭제
}
