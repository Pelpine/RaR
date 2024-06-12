package kr.review.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.rar.dao.ReviewDAO;
import kr.rar.vo.ReviewVO;

public class ReviewinsertAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		int order_num = Integer.parseInt(request.getParameter("order_num"));
		int detail_num = Integer.parseInt(request.getParameter("detail_num"));
		if(user_num == null) {
			return "redirect:/book/loginForm.do";
		}
		ReviewDAO dao = ReviewDAO.getInstance();
		int count = dao.reviewnum(detail_num);
		ReviewVO vo = new ReviewVO();
		
		if(count == 1) {
			request.setAttribute("notice_msg", "이미 작성한 리뷰 입니다.");
			request.setAttribute("notice_url", request.getContextPath()+"/order/userOrderListDetail.do?order_num="+order_num);
			
			return "/WEB-INF/views/common/alert_view.jsp";
		}
		
		vo.setDetail_num(detail_num);
		vo.setRe_comment(request.getParameter("re_comment"));
		vo.setRe_img(request.getParameter("re_img"));
		vo.setRe_rating(Integer.parseInt(request.getParameter("rating")));
		vo.setUser_num(user_num);
		
		dao.insertreview(vo);
		
		request.setAttribute("notice_msg", "댓글 등록 완료");
		request.setAttribute("notice_url", request.getContextPath()+"/order/userOrderListDetail.do?order_num="+order_num);
		
		return "/WEB-INF/views/common/alert_view.jsp";
	}

}
