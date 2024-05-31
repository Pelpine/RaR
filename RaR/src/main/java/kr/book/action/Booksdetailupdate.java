package kr.book.action;//다른거

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.rar.dao.BookApprovalDAO;
import kr.rar.dao.BookDAO;
import kr.rar.vo.BookApprovalVO;

public class Booksdetailupdate implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		
		BookDAO da = BookDAO.getInstance();
		BookApprovalVO io = da.ckmem(user_num);
		
		String user_email = request.getParameter("user_email");
		
		if(user_num==null) {
			return "redirect:/member/loginForm.do";
		}
		if(io.getUser_email() == user_email) {
			return "redirect:/book/list.do";
		}
		
		int approval_id =Integer.parseInt(request.getParameter("approval_id"));
		
		BookApprovalDAO dao = BookApprovalDAO.getInstance();
		BookApprovalVO vo = dao.selectbook(approval_id);
		
		request.setAttribute("books", vo);
		return "/WEB-INF/views/book/bookreupdate.jsp";
	}
	
}
