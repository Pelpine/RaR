package kr.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.rar.dao.BoardDAO;
import kr.rar.vo.BoardVO;
import kr.util.FileUtil;

public class WriteAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num==null) {//로그인 x
			return "redirect:/member/loginForm.do";
		}
		//로그인
		request.setCharacterEncoding("utf-8");
		BoardVO board = new BoardVO();
		board.setTitle(request.getParameter("title"));
		board.setContent(request.getParameter("content"));
		board.setUser_ip(request.getRemoteAddr());
		board.setFilename(FileUtil.createFile(request, "filename"));
		board.setUser_num(user_num);//작성자 회원번호
		
		BoardDAO dao = BoardDAO.getInstance();
		dao.insertBoard(board);
		
		request.setAttribute("notice_msg", "글쓰기 완료");
		request.setAttribute("notice_url",
				request.getContextPath()+"/board/list.do");
		
		return "/WEB-INF/views/common/alert_view.jsp";
	}

}
