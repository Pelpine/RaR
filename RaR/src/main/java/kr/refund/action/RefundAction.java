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
		request.setCharacterEncoding("utf-8");
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
      int refund_point = Integer.parseInt(request.getParameter("refund_point"));
       
       RefundVO refund = new RefundVO();
       refund.setOrder_num(order_num);
       refund.setItem_num(item_num);
       refund.setUser_num(user_num);
       refund.setRefund_price(Integer.parseInt(request.getParameter("refund_price")));
       refund.setCollect_point(refund_point);
       refund.setReason(Integer.parseInt(request.getParameter("reason")));
       refund.setReason_other(request.getParameter("reason_other"));
       refund.setBank(request.getParameter("bank"));
       refund.setAccount(request.getParameter("account"));
       
       RefundDAO refundDAO = RefundDAO.getInstance();
       //아이템 구매로 얻은 모든 포인트(구매 포인트 + 룰렛 리워드) 삭제 
       refundDAO.deleteUserPointByRefund_point(user_num, refund_point);
      
       refundDAO.insertRefund(refund);
       
       request.setAttribute("notice_msg", "환불 신청 접수되었습니다.");
       request.setAttribute("notice_url", request.getContextPath()+"/order/userOrderListDetail.do?order_num="+order_num);
       return "/WEB-INF/views/common/alert_view.jsp";
	}
}
