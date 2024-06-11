package kr.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.rar.dao.BoardDAO;
import kr.rar.vo.GenreVO;
import kr.controller.Action;

public class UpdateGenreAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		Integer user_auth = (Integer)session.getAttribute("user_auth");
		if(user_num==null) {//로그인이 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}
		//로그인 된 경우
		//전송된 데이터 인코딩 타입 지정
		request.setCharacterEncoding("utf-8");
		//전송된 데이터 반환
		int bg_num = Integer.parseInt(request.getParameter("bg_num"));
		
		BoardDAO dao = BoardDAO.getInstance();
		//수정 전 데이터
		GenreVO genre = dao.getGenre(bg_num);
		//로그인 한 회원 번호와 작성자 회원번호 일치 여부 체크
		if(user_num != genre.getUser_num()) {
			//로그인한 회원번호와 작성자 회원번호 불일치
			return "/WEB-INF/views/common/notice.jsp";
		}
		//로그인한 회원번호와 작성자 회원번호 일치
		GenreVO board = new GenreVO();
		genre.setBg_num(bg_num);
		board.setBg_title(request.getParameter("bg_title"));
		board.setUser_num(user_num);
		
		
		dao.updateGenre(genre);
		
		
		
		return "redirect:/board/detailGenre.do?bg_num="+bg_num;
	}

}
