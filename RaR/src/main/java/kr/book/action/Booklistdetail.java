package kr.book.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;

public class Booklistdetail implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int bk_num = Integer.parseInt(request.getParameter("bk_num"));
		
		return "/WEB-INF/views/book/booklistdetail.jsp";
	}

}
