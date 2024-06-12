package kr.refund.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.rar.dao.RefundDAO;
import kr.rar.vo.RefundVO;

public class AdminModifyRefundStatusAction implements Action{

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
		request.setCharacterEncoding("utf-8");
		//환불 번호
		int refund_num = Integer.parseInt(request.getParameter("refund_num"));
		//환불 번호
		int item_num = Integer.parseInt(request.getParameter("item_num"));
		//환불시 회수한 포인트
		int collect_point = Integer.parseInt(request.getParameter("collect_point"));
		//변경 전 환불 상태
		int unchangedStatus = Integer.parseInt(request.getParameter("unchangedStatus"));
		//환불 신청 이유
		int refund_reason = Integer.parseInt(request.getParameter("refund_reason"));
		//새롭게 지정한 환불 상태
	    int status = Integer.parseInt(request.getParameter("status"));
	    //환불 불가시 입력한 이유
	    String unable_reason = request.getParameter("unable_reason");
	    //환불을 신청한 유저넘버
	    int refund_user_num = Integer.parseInt(request.getParameter("refund_user_num"));
	    RefundDAO dao = RefundDAO.getInstance();
	   
		if(unchangedStatus == 3 || unchangedStatus == 4) {
	           // 환불 불가 상태이거나, 완료된 상태일 경우
	    	   request.setAttribute("accessMsg", "이미 환불 처리(혹은 불가판정이)가 끝난 상품입니다.");
	    	   request.setAttribute("accessUrl", request.getContextPath()+"/refund/adminRefundDetail.do?refund_num="+refund_num);
	           return "/WEB-INF/views/common/notice.jsp";
	    }
		//환불 상태가 환불 불가로 변경된 경우
		if(status == 3) {
			//룰렛이 사용된것이 아니라, 환불 신청으로 삭제처리 된것인지 확인
			int delete_ticket = dao.getDeleteStatusByItem_num(item_num);
			if(delete_ticket == 1) {//티켓 삭제처리시
				dao.getBackUnusedTicket(item_num);	
			}
			//환불 신청 유저의 포인트 복구
			dao.getBackPoint(collect_point, refund_user_num);
		}		
	    if(status == 4 && refund_reason ==1) {//환불 상태가 환불 완료로 변경되었고, 환불 이유가 단순 변심일 경우
	    	//아이템 판매 상태 판매중(1) 로 변경
	    	dao.returnItem(item_num, 1);
	    }else if (status ==4 && refund_reason == 2) {//환불 상태가 환불 완료로 변경되었고, 환불 이유가 상품 불량일 경우
	    	//아이템 판매 상태 판매중지(3)로 변경
	    	dao.returnItem(item_num, 3);
	    }
	      
	    dao.adminUpdateRefundStatus(refund_num, status, unable_reason);
	    request.setAttribute("accessMsg", "환불 상태가 변경되었습니다.");
 	    request.setAttribute("accessUrl", request.getContextPath()+"/refund/adminRefundDetail.do?refund_num="+refund_num);
        return "/WEB-INF/views/common/notice.jsp";
	}

}
