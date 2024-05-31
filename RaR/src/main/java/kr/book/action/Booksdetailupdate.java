package kr.book.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;

public class Booksdetailupdate implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		int user = Integer.parseInt(request.getParameter("user_email"));
		if(user_num==null) {
			return "redirect:/book/loginForm.do";
		}
		if(user_num == user) {
			return "redirect:/book/list.do";
		}
		
		return null;
	}
	
}
