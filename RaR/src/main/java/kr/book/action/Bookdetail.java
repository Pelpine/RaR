package kr.book.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.rar.dao.BookApprovalDAO;
import kr.rar.vo.BookApprovalVO;

public class Bookdetail implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		request.setCharacterEncoding("utf-8");
		Integer user_num = (Integer)session.getAttribute("user_num");
		Integer user_auth = (Integer)session.getAttribute("user_auth");
		if(user_num == null) {
			return "redirect:/book/loginForm.do";
		}
		int id = Integer.parseInt(request.getParameter("appapproval_id"));
		BookApprovalDAO dao = BookApprovalDAO.getInstance();
		BookApprovalVO vo = dao.selectbook(id);
		
		request.setAttribute("user_auth", user_auth);
		request.setAttribute("detail", vo);
		return "/WEB-INF/views/book/bookdetail.jsp";
	}
}
