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
        Date deadline = refundDAO.getRefundDeadLineByOrder_Num(order_num);

        // 현재 날짜를 구함
        LocalDate today = LocalDate.now();
        LocalDate refundDeadline = deadline.toLocalDate();

        if (today.isAfter(refundDeadline)) {
            // 환불 기한을 넘긴 경우
            return "/WEB-INF/views/common/notice.jsp";
        }
        int item_num = Integer.parseInt(request.getParameter("item_num"));
        //각 주문에서 한 개 상품의 세부 정보
        OrderDetailVO item = new OrderDetailVO();
        item = refundDAO.getOrderDetailByItem_num(item_num);
        
        request.setAttribute("item", item);
        request.setAttribute("order_num", order_num);
        return "/WEB-INF/views/refund/refundForm.jsp";
    }
}
