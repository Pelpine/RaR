package kr.refund.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.rar.dao.RefundDAO;
import kr.rar.vo.RefundVO;

public class DeleteRefundAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
        Integer user_num = (Integer)session.getAttribute("user_num");
        if(user_num == null) {
            return "redirect:/member/loginForm.do";
        }
        RefundDAO refundDAO = RefundDAO.getInstance();
        int refund_num = Integer.parseInt(request.getParameter("refund_num"));
        RefundVO refund = refundDAO.getRefundvo(refund_num);
        int status = refund.getStatus();
        if(status != 1) {
        	request.setAttribute("notice_msg", "이미 환불이 진행중이어서 환불이 불가능합니다.");
        	return "/WEB-INF/views/common/notice.jsp";
        }
        // 주문정보 호출
        if(refund.getUser_num() != user_num) {
            // 구매자 회원번호와 로그인한 회원번호가 불일치할 경우
            return "/WEB-INF/views/common/notice.jsp";
        }
        int refund_point = refund.getCollect_point();
        //환불 취소로 포인트 복구
        System.out.println(refund_point);
        System.out.println(user_num);
        refundDAO.getBackPoint(refund_point, user_num);
        refundDAO.deleteRefund(refund_num);
        request.setAttribute("notice_msg", "환불이 취소되었습니다. (회수 포인트 재지급)");
        request.setAttribute("notice_url",request.getContextPath()+"/refund/userRefundList.do");
        return "/WEB-INF/views/common/alert_view.jsp";
	}

}
