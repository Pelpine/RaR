package kr.refund.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import kr.controller.Action;
import kr.rar.dao.OrderDAO;
import kr.rar.dao.RefundDAO;
import kr.rar.vo.OrderVO;
import kr.rar.vo.RefundVO;

public class RefundAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
        Integer user_num = (Integer)session.getAttribute("user_num");

        if(user_num == null) {
            return "redirect:/member/loginForm.do";
        }

        int order_num = Integer.parseInt(request.getParameter("order_num"));
        int item_num = Integer.parseInt(request.getParameter("item_num"));
        OrderDAO dao = OrderDAO.getInstance();
        // 주문정보 호출
        OrderVO order = dao.getOrder(order_num);
        if(order.getUser_num() != user_num) {
            // 구매자 회원번호와 로그인한 회원번호가 불일치할 경우
            return "/WEB-INF/views/common/notice.jsp";
        }
       request.setCharacterEncoding("utf-8");
        
       RefundVO refund = new RefundVO();
       refund.setOrder_num(order_num);
       refund.setItem_num(item_num);
       refund.setRefund_price(Integer.parseInt(request.getParameter("refund_price")));
       refund.setCollect_point(Integer.parseInt(request.getParameter("refund_point")));
       refund.setReason(Integer.parseInt(request.getParameter("reason")));
       refund.setReason_other(request.getParameter("reason_other"));
       refund.setBank(request.getParameter("bank"));
       refund.setAccount(request.getParameter("account"));
       
       RefundDAO refundDAO = RefundDAO.getInstance();
       refundDAO.insertRefund(refund);
        return "redirect:/order/userOrderListDetail.do?order_num="+order_num;
	}
}
