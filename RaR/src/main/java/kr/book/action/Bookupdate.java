package kr.book.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.rar.dao.BookApprovalDAO;
import kr.rar.dao.BookDAO;
import kr.rar.vo.BookApprovalVO;

public class Bookupdate implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		int user = Integer.parseInt(request.getParameter("user"));
		if(user_num==null) {
			return "redirect:/book/loginForm.do";
		}
		BookDAO daoa = BookDAO.getInstance();
		BookApprovalVO iod = daoa.ckmem(user_num);
		if(iod.getUser_num() == user) {
			return "redirect:/book/list.do";
		}
		BookApprovalDAO dao = BookApprovalDAO.getInstance();
		BookApprovalVO vo = new BookApprovalVO();
		vo.setCover(request.getParameter("cover"));
		vo.setBk_name(request.getParameter("bk_name"));
		vo.setAuthor(request.getParameter("author"));
		vo.setPubDate(request.getParameter("pubdate"));
		vo.setPrice(Integer.parseInt(request.getParameter("price")));
		vo.setCategoryName(null);
		
		
		return null;
	}

}
