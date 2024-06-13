package kr.refund.action;

import java.sql.Date;
import java.time.LocalDate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.rar.dao.OrderDAO;
import kr.rar.dao.RefundDAO;
import kr.rar.vo.OrderDetailVO;
import kr.rar.vo.OrderVO;

public class RefundFormAction implements Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        Integer user_num = (Integer)session.getAttribute("user_num");

        if(user_num == null) {
            return "redirect:/member/loginForm.do";
        }

        int order_num = Integer.parseInt(request.getParameter("order_num"));
        OrderDAO dao = OrderDAO.getInstance();
        // 주문정보 호출
        OrderVO order = dao.getOrder(order_num);
        if(order.getUser_num() != user_num) {
            // 구매자 회원번호와 로그인한 회원번호가 불일치할 경우
            return "/WEB-INF/views/common/notice.jsp";
        }

        RefundDAO refundDAO = RefundDAO.getInstance();
        //환불 기한을 구함
        Date deadline = refundDAO.getRefundDeadLineByOrder_Num(order_num);
        
        int item_num = Integer.parseInt(request.getParameter("item_num"));
        //환불 상태를 구함
        int status = refundDAO.getRefundStatusByItem_num(item_num);

        // 현재 날짜를 구함
        LocalDate today = LocalDate.now();
        LocalDate refundDeadline = deadline.toLocalDate();
        
        if (today.isAfter(refundDeadline)) {
            // 환불 기한을 넘긴 경우
        	request.setAttribute("notice_msg", "환불 기한이 지났습니다.");
        	request.setAttribute("notice_url", request.getContextPath()+"/order/userOrderListDetail.do?order_num="+order_num);
            return "/WEB-INF/views/common/alert_view.jsp";
        }
        if(status != 0) {
        	//이미 환불 신청한 경우
        	request.setAttribute("notice_msg", "이미 환불 신청되었습니다.");
        	request.setAttribute("notice_url", request.getContextPath()+"/order/userOrderListDetail.do?order_num="+order_num);
            return "/WEB-INF/views/common/alert_view.jsp";
        }
        /* ------------환불기한 내이고 아직 환불 신청을 하지 않았을 때 -------------*/
        
        //각 주문에서 한 개 상품의 세부 정보
        OrderDetailVO item = new OrderDetailVO();
        item = refundDAO.getOrderDetailByItem_num(item_num);
        int roulette_reward = refundDAO.getTicketRewardByItem_num(item_num);
        
        //환불해야 하는 포인트
        int refund_point = (item.getItem_price()/ 100) + roulette_reward;
        //유저 보유 포인트
        int point = refundDAO.getUserPoint(user_num);
        //환불 포인트 대비 유저 부족분
        int shortage = 0;
        //환불 후 보유 포인트
        int afterRefund = 0;
        if(refund_point > point) {
           shortage = Math.abs(point - refund_point);
           System.out.println(roulette_reward);
        }else if (point > refund_point) {
        	shortage = 0;
        	afterRefund = point - refund_point;
        	System.out.println(roulette_reward);
        	System.out.println(item_num);
        }
        //아이템 구매로 얻은 룰렛 티켓 사용 여부 구하기
        
        
        //최종 환불 금액
        int refund_price = item.getItem_price() - shortage - roulette_reward;
        request.setAttribute("shortage", shortage);
        request.setAttribute("point", point);
        request.setAttribute("afterRefund", afterRefund);
        request.setAttribute("refund_point", refund_point);;
        request.setAttribute("item", item);
        request.setAttribute("item_num", item_num);
        request.setAttribute("order_num", order_num);
        request.setAttribute("roulette_reward", roulette_reward);
        request.setAttribute("refund_price", refund_price);
        return "/WEB-INF/views/refund/refundForm.jsp";
    }
}
