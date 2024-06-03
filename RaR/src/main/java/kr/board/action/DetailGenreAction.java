package kr.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.rar.dao.BoardDAO;
import kr.rar.vo.GenreVO;
import kr.controller.Action;
import kr.util.StringUtil;

public class DetailGenreAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//글번호 반환
				int bg_num = Integer.parseInt(request.getParameter("bg_num"));
				
				BoardDAO dao = BoardDAO.getInstance();
				
				
				GenreVO genre = dao.getGenre(bg_num);
				//HTML을 사용하지 않음
				
				
				request.setAttribute("genre", genre);
				
				return "/WEB-INF/views/board/detailGenre.jsp";
	}

}
