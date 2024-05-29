package kr.book.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.rar.dao.BookApprovalDAO;
import kr.rar.dao.BookDAO;
import kr.rar.vo.BookApprovalVO;
import kr.rar.vo.BookVO;

public class Bookdetailupdate implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		request.setCharacterEncoding("utf-8");
		Integer user_num = (Integer)session.getAttribute("user_num");
		int user_auth = Integer.parseInt(request.getParameter("user_auth"));
		if(user_num == null) {
			return "redirect:/member/loginForm.do";
		}
		if(user_auth != 9) {
			return "redirect:/book/list.do";
		}
		
		BookApprovalDAO dao = BookApprovalDAO.getInstance();
		BookApprovalVO vo = new BookApprovalVO();
		int status = Integer.parseInt(request.getParameter("status"));
		int approval_id = Integer.parseInt(request.getParameter("approval_id"));
		vo.setStatus(status);
		vo.setApproval_id(approval_id);
		
		dao.updatestatus(vo);
		
		if(status == 2) {
		BookDAO book = BookDAO.getInstance();
		BookVO bkvo = new BookVO();
		bkvo.setBk_name(request.getParameter("bk_name"));
		bkvo.setBk_writer(request.getParameter("author"));
		bkvo.setBk_publisher(request.getParameter("publisher"));
		bkvo.setBk_price(Integer.parseInt(request.getParameter("price")));
		bkvo.setBk_img(request.getParameter("cover"));
		bkvo.setBk_genre(request.getParameter("categoryName"));
		
		book.insertBookslist(bkvo);
		}
		request.setAttribute("notice_msg", "글 등록 완료");
		request.setAttribute("notice_url", request.getContextPath()+"/main/main.do");
		
		return "/WEB-INF/views/common/alert_view.jsp";
	}

}
