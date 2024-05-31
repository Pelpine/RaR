package kr.book.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.rar.dao.BookApprovalDAO;
import kr.rar.dao.BookDAO;
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
		String isbn = request.getParameter("isbn");
		vo.setStatus(status);
		vo.setApproval_id(approval_id);

		dao.updatestatus(vo);

		int price = Integer.parseInt(request.getParameter("price"));
		int item_grade = Integer.parseInt(request.getParameter("item_grade"));
		Float prs = (float) 0;
		if(item_grade == 1) {
			prs = (float)(price * 0.80);
		}else if(item_grade == 2) {
			prs = (float) (price * 0.70);
		}else if(item_grade == 3) {
			prs = (float) (price * 0.70);
		}
		int truncatedPrs = (int) (prs / 10) * 10;

		if(status == 2) {
			BookDAO book = BookDAO.getInstance();
			if(book.selectbk(approval_id) == 0) {
				BookVO bkvo = new BookVO();
				ItemVO itemvo = new ItemVO();
				itemvo.setItem_price(truncatedPrs);
				itemvo.setItem_grade(item_grade);
				itemvo.setItem_img(request.getParameter("photo"));
				itemvo.setApproval_id(approval_id);
				bkvo.setBk_isbn(request.getParameter("isbn"));
				if(book.selectbks(isbn) == null) {
					bkvo.setBk_name(request.getParameter("bk_name"));
					bkvo.setBk_writer(request.getParameter("author"));
					bkvo.setBk_publisher(request.getParameter("publisher"));
					bkvo.setBk_price(price);
					bkvo.setBk_img(request.getParameter("cover"));
					bkvo.setBk_genre(request.getParameter("categoryName"));
					bkvo.setBk_description(request.getParameter("description"));
					book.insertBookslist(bkvo, itemvo);
				}else {
					book.intobook(bkvo, itemvo);
				}
			}else {
				return "redirect:/book/list.do";
			}

		}


		request.setAttribute("notice_msg", "책 등록 완료");
		request.setAttribute("notice_url", request.getContextPath()+"/book/list.do");

		return "/WEB-INF/views/common/alert_view.jsp";
	}

}
