package kr.book.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.rar.dao.BookApprovalDAO;
import kr.rar.vo.BookApprovalVO;

public class BookrequestForm implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null){
			return "redirect:/member/loginForm.do";
		}
		String bk_name = request.getParameter("bk_name");
		String author = request.getParameter("author");
		String pubdate = request.getParameter("pubdate");
		String coverurl = request.getParameter("coverurl");
		String categoryname = request.getParameter("categoryname");
		
		BookApprovalVO vo = new BookApprovalVO();
        vo.setBk_name(bk_name);
        vo.setAuthor(author);
        vo.setPubDate(pubdate);
        vo.setCoverUrl(coverurl);
        vo.setCategoryName(categoryname);
        
        request.setAttribute("books", vo);
		
		return "/WEB-INF/views/book/bookrequestForm.jsp";
	}

}
