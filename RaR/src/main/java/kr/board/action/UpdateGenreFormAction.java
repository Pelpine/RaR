package kr.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.rar.dao.BoardDAO;
import kr.rar.vo.GenreVO;
import kr.util.StringUtil;

public class UpdateGenreFormAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num==null) {//로그인이 되지않았다
			return "redirect:/member/loginForm.do";
		}
		//로그인 된경우
		int bg_num=Integer.parseInt(request.getParameter("bg_num"));
		BoardDAO dao = BoardDAO.getInstance();
		GenreVO genre = dao.getGenre(bg_num);
		
		if(user_num!=genre.getUser_num()) {
			return "/WEB-INF/views/common/notice.jsp";
		}
		
		//큰 따옴표 처리
		//수정폼의 input 태그에서 큰 따옴표 오류 보정
		genre.setBg_title(StringUtil.parseQuot(genre.getBg_title()));
		
		//로그인 상태 회원번호 = 작성자 번호
		request.setAttribute("genre", genre);
		
		return "/WEB-INF/views/board/updateGenreForm.jsp";
	}
}
