package kr.book.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.rar.dao.BookApprovalDAO;
import kr.rar.dao.BookDAO;
import kr.rar.dao.ItemDAO;
import kr.rar.vo.BookApprovalVO;
import kr.rar.vo.BookVO;
import kr.rar.vo.ItemVO;

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
		
		int price = Integer.parseInt(request.getParameter("price"));
		int prs = 0;
		if(status == 1) {
			prs = price - (price * 20);
		}else if(status == 2) {
			prs = price - (price * 30);
		}else if(status == 3) {
			prs = price - (price * 40);
		}
		
		if(status == 2) {
		
		BookDAO book = BookDAO.getInstance();
		BookVO bkvo = new BookVO();
		bkvo.setBk_name(request.getParameter("bk_name"));
		bkvo.setBk_writer(request.getParameter("author"));
		bkvo.setBk_publisher(request.getParameter("publisher"));
		bkvo.setBk_price(price);
		bkvo.setBk_img(request.getParameter("cover"));
		bkvo.setBk_genre(request.getParameter("categoryName"));
		bkvo.setBk_description(request.getParameter("description"));
		bkvo.setBk_isbn(request.getParameter("isbn"));
		
		book.insertBookslist(bkvo);
		
		ItemVO itemvo = new ItemVO();
		itemvo.setItem_price(prs);
		itemvo.setItem_grade(status);
		itemvo.setItem_img(request.getParameter("photo"));
		itemvo.setBk_num(Integer.parseInt(request.getParameter("")));
		itemvo.setApproval_id(approval_id);
		}
		
		
		request.setAttribute("notice_msg", "글 등록 완료");
		request.setAttribute("notice_url", request.getContextPath()+"/main/main.do");
		
		return "/WEB-INF/views/common/alert_view.jsp";
	}

}
