package kr.refund.action;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.rar.dao.RefundDAO;
import kr.rar.vo.OrderDetailVO;
import kr.rar.vo.RefundVO;

public class ModifyRefundFormAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
	    HttpSession session = request.getSession();
        Integer user_num = (Integer)session.getAttribute("user_num");

        if(user_num == null) {
            return "redirect:/member/loginForm.do";
        }

        RefundDAO refundDAO = RefundDAO.getInstance();
        int refund_num = Integer.parseInt(request.getParameter("refund_num"));
        RefundVO refund = refundDAO.getRefundvoByRefund_num(refund_num);
        //각 주문에서 한 개 상품의 세부 정보
        OrderDetailVO item = new OrderDetailVO();
        int item_num = refund.getItem_num();
        item = refundDAO.getOrderDetailByItem_num(item_num);
       
        request.setAttribute("item", item);
        request.setAttribute("refund", refund);
        return "/WEB-INF/views/refund/modifyRefundForm.jsp";
	}

}
