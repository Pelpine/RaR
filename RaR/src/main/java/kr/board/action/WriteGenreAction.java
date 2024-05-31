package kr.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.rar.dao.BoardDAO;
import kr.rar.vo.GenreVO;


public class WriteGenreAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num==null) {//로그인 x
			return "redirect:/member/loginForm.do";
		}
		//로그인
		request.setCharacterEncoding("utf-8");
		GenreVO genre = new GenreVO();
		genre.setBg_title(request.getParameter("bg_title"));
		genre.setUser_num(user_num);
		
		
		BoardDAO dao = BoardDAO.getInstance();
		dao.insertGenre(genre);
		
		request.setAttribute("notice_msg", "글쓰기 완료");
		request.setAttribute("notice_url",
				request.getContextPath()+"/board/genreList.do");
		
		return "/WEB-INF/views/common/alert_view.jsp";
	}

}
