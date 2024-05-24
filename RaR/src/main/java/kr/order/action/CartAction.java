package kr.order.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.rar.dao.OrderDAO;
import kr.rar.vo.OrderVO;

public class CartAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null) {//로그인이 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}
		//로그인 된 경우
		
		OrderDAO dao = OrderDAO.getInstance();
		
		List<OrderVO> list = null;
		list = dao.getCartList(user_num);
		
		request.setAttribute("list", list);
		
		return "/WEB-INF/views/order/cart.jsp";
	}
}