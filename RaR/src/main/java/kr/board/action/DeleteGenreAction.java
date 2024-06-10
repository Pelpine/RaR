package kr.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.rar.dao.BoardDAO;
import kr.rar.vo.GenreVO;


public class DeleteGenreAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num==null) {//로그인이 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}
		//로그인 된 경우
				int bg_num = Integer.parseInt(request.getParameter("bg_num"));
				BoardDAO dao = BoardDAO.getInstance();
				GenreVO db_genre = dao.getGenre(bg_num);
				//로그인한 회원번호와 작성자 회원번호 일치 여부 체크
				if(user_num != db_genre.getUser_num()) {
					return"/WEB-INF/views/common/notice.jsp";
				}
				//로그인한 회원번호와 작성자 회원번호 일치
				dao.deleteGenre(bg_num);
				
				
				request.setAttribute("notice_msg", "글 삭제 완료");
				request.setAttribute("notice_url",
						request.getContextPath()+"/board/genreList.do");
				
				return "/WEB-INF/views/common/alert_view.jsp";
			}

		}

