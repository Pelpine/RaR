package kr.review.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.rar.dao.OrderDAO;
import kr.rar.vo.OrderDetailVO;

public class ReviewinsertformAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null) {
			return "redirect:/book/loginForm.do";
		}
		String order_num = request.getParameter("order_num");
		
		OrderDAO dao = OrderDAO.getInstance();
		List<OrderDetailVO> vo = dao.getOrderListDetail(Integer.parseInt(order_num));
		
		request.setAttribute("order", vo);
		
		return "/WEB-INF/views/review/reviewinsertform.jsp";
	}

}
