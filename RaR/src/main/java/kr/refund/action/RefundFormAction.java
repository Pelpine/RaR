package kr.refund.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.rar.dao.OrderDAO;
import kr.rar.vo.OrderVO;

public class RefundFormAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		
		if(user_num==null) {
			return "redirect:/member/loginForm.do";
		}
		
		int order_num = Integer.parseInt(request.getParameter("order_num"));
		OrderDAO dao = OrderDAO.getInstance();
		//주문정보 호출
		OrderVO order = dao.getOrder(order_num);
		if(order.getUser_num() != user_num) {
			//구매자 회원번호와 로그인한 회원번호가 불일치할 경우
			return "/WEB-INF/views/common/notice.jsp";
		}
		
		request.setAttribute("order", order);
		
		return "/WEB-INF/views/refund/refundForm.jsp";
	}

}
