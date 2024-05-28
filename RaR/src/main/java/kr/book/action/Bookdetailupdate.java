package kr.book.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.rar.dao.BookApprovalDAO;
import kr.rar.vo.BookApprovalVO;

public class Bookdetailupdate implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		request.setCharacterEncoding("utf-8");
		Integer user_num = (Integer)session.getAttribute("user_num");
		Integer user_auth = (Integer)session.getAttribute("user_auth");
		if(user_num == null) {
			return "redirect:/member/loginForm.do";
		}
		if(user_auth != 9) {
			return "redirect:/book/list.do";
		}
		BookApprovalDAO dao = BookApprovalDAO.getInstance();
		BookApprovalVO vo = new BookApprovalVO();
		vo.setStatus(Integer.parseInt(request.getParameter("status")));
		
		//더해야함
		
		
		request.setAttribute("notice_msg", "글 등록 완료");
		request.setAttribute("notice_url", request.getContextPath()+"/main/main.do");
		
		return "/WEB-INF/views/common/alert_view.jsp";
	}

}
