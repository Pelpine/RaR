package kr.refund.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.rar.dao.RefundDAO;
import kr.rar.vo.RefundVO;

public class ModifyRefundAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
        Integer user_num = (Integer)session.getAttribute("user_num");
        int item_num = Integer.parseInt(request.getParameter("item_num"));
        int order_num = Integer.parseInt(request.getParameter("order_num"));
        if(user_num == null) {
            return "redirect:/member/loginForm.do";
        }
       int refund_num = Integer.parseInt(request.getParameter("refund_num"));
       int status = Integer.parseInt(request.getParameter("status"));
       if(status > 4) {
           // 환불 불가 상태이거나, 완료된 상태일 경우
           return "/WEB-INF/views/common/notice.jsp";
       }
       RefundVO refund = new RefundVO();
       
       refund.setReason(Integer.parseInt(request.getParameter("reason")));
       refund.setReason_other(request.getParameter("reason_other"));
       refund.setBank(request.getParameter("bank"));
       refund.setAccount(request.getParameter("account"));
       
       RefundDAO refundDAO = RefundDAO.getInstance();
       refundDAO.updateRefund(refund, refund_num);
       
       request.setAttribute("notice_msg", "환불 정보가 수정되었습니다.");
       request.setAttribute("notice_url","userRefundDetail.do?item_num="+item_num+"&order_num="+order_num);
       return "/WEB-INF/views/common/alert_view.jsp";
	}

}
