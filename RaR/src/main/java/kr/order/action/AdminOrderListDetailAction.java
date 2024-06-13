package kr.order.action;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.rar.dao.OrderDAO;
import kr.rar.dao.RefundDAO;
import kr.rar.vo.OrderDetailVO;
import kr.rar.vo.OrderVO;


public class AdminOrderListDetailAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		
		if(user_num==null) {//로그인이 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}
		
		Integer user_auth = (Integer)session.getAttribute("user_auth");
		if(user_auth != 9) {//관리자로 로그인하지 않은 경우
			return "/WEB-INF/views/common/notice.jsp";
		}
		//관리자로 로그인한 경우
		int order_num = Integer.parseInt(request.getParameter("order_num"));
		OrderDAO dao = OrderDAO.getInstance();
		//주문정보 반환
		OrderVO order = dao.getOrder(order_num);
		
		//환불 기한
		RefundDAO refundDAO = RefundDAO.getInstance();
		Date deadline = refundDAO.getRefundDeadLineByOrder_Num(order_num);

		LocalDate today = LocalDate.now();
        Date currentDate = Date.valueOf(today);
        
		//주문한 개별상품 정보 반환
		List<OrderDetailVO> detailList = dao.getOrderListDetail(order_num);
		
		request.setAttribute("order", order);
		request.setAttribute("detailList", detailList);
		request.setAttribute("currentDate", currentDate);
		request.setAttribute("deadline", deadline);
		
		return "/WEB-INF/views/order/admin_order_detail.jsp";
	}

}
