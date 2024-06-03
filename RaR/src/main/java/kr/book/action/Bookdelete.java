package kr.book.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.rar.dao.BookApprovalDAO;
import kr.rar.dao.BookDAO;
import kr.rar.vo.BookApprovalVO;

public class Bookdelete implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		String user_email = request.getParameter("user_email");
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num==null) {
			return "redirect:/book/loginForm.do";
		}
		BookDAO bookdao = BookDAO.getInstance();
		BookApprovalVO book = bookdao.ckmem(user_num);
		
		if(user_email.equals(book.getUser_email())) {
			int id = Integer.parseInt(request.getParameter("approval_id"));
			BookApprovalDAO dao = BookApprovalDAO.getInstance();
			dao.deletebookApproval(id);
			
			request.setAttribute("notice_msg", "삭제완료");
			request.setAttribute("notice_url", request.getContextPath()+"/book/list.do");
			
			return "/WEB-INF/views/common/alert_view.jsp";
		}
		
		return "redirect:/book/list.do";
	}
}
