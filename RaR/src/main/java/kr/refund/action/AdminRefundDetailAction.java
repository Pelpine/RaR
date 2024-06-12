package kr.refund.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.rar.dao.RefundDAO;
import kr.rar.vo.OrderDetailVO;
import kr.rar.vo.RefundVO;

public class AdminRefundDetailAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
        Integer user_num = (Integer)session.getAttribute("user_num");
        Integer user_auth = (Integer)session.getAttribute("user_auth");
        if(user_num == null) {
            return "redirect:/member/loginForm.do";
        }
        if(user_auth != 9) {
        	return "/WEB-INF/views/common/notice.jsp";
        }
        
        
        RefundDAO refundDAO = RefundDAO.getInstance();
        int refund_num = Integer.parseInt(request.getParameter("refund_num"));
        RefundVO refund = refundDAO.getRefundvo(refund_num);
        int item_num = refund.getItem_num();

        //환불 신청한 상품의 세부 정보
        OrderDetailVO item = new OrderDetailVO();
        item = refundDAO.getOrderDetailByItem_num(item_num);

        request.setAttribute("refund", refund);
        request.setAttribute("item", item);
		return "/WEB-INF/views/refund/adminRefundDetail.jsp";
	}

}
