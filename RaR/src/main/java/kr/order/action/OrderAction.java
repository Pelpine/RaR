package kr.order.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.rar.dao.CartDAO;
import kr.rar.dao.OrderDAO;
import kr.rar.vo.CartVO;
import kr.rar.vo.OrderDetailVO;
import kr.rar.vo.OrderVO;

public class OrderAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		
		if(user_num==null) {//로그인이 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}
		//로그인 된 경우
		//POST방식의 접근만 허용
		if(request.getMethod().toUpperCase().equals("GET")) {
			return "/WEB-INF/views/common/notice.jsp";
		}

		//전송된 데이터 인코딩 타입 지정
		request.setCharacterEncoding("utf-8");
		
		CartDAO dao = CartDAO.getInstance();
		
		//장바구니 총액
	    int pay_total = dao.getSelectedCartTotal(user_num);
	    if(pay_total <= 0) {//이미 구매가 완료된 경우 재구매 방지 (back버튼 눌러서 돌아간 후 재구매하는 오류 방지)
	    	request.setAttribute("notice_msg", "정상적인 주문이 아니거나 상품이 판매중이 아닙니다.");
	    	request.setAttribute("notice_url", request.getContextPath()+"/item/itemList.do");
	    	return "/WEB-INF/views/common/alert_view.jsp";
	    }
	    //장바구니에 담겨있는 상품 정보 호출
	  	List<CartVO> selectedCartList = dao.getSelectedCartList(user_num);
	  	
	  	//개별상품 정보 담기
	  	List<OrderDetailVO> orderDetailList = new ArrayList<OrderDetailVO>();
	  	for(CartVO cart : selectedCartList) {
	  		OrderDetailVO orderDetail = new OrderDetailVO();
	  		orderDetail.setItem_num(cart.getItem_num());
	  		orderDetail.setItem_name(cart.getBookVO().getBk_name());
	  		orderDetail.setBk_img(cart.getBookVO().getBk_img());
	  		orderDetail.setBk_price(cart.getBookVO().getBk_price());
	  		orderDetail.setItem_price(cart.getItemVO().getItem_price());
	  		orderDetail.setItem_grade(cart.getItemVO().getItem_grade());
	  		orderDetail.setItem_img(cart.getItemVO().getItem_img());
	  		orderDetail.setBk_num(cart.getBk_num());
	  		
	  		orderDetailList.add(orderDetail);
	  	}
	  	
	  	//구매 정보 담기
	  	OrderVO order = new OrderVO();
	  	order.setPay_total(Integer.parseInt(request.getParameter("pay_total")));
	  	order.setPay_ship(Integer.parseInt(request.getParameter("pay_ship")));
	  	order.setPay_points(Integer.parseInt(request.getParameter("pay_points")));
	  	order.setOrder_points(Integer.parseInt(request.getParameter("order_points")));
	  	order.setReceive_name(request.getParameter("receive_name"));
	  	order.setReceive_post(request.getParameter("receive_post"));
	  	order.setReceive_address1(request.getParameter("receive_address1"));
	  	order.setReceive_address2(request.getParameter("receive_address2"));
	  	order.setReceive_phone(request.getParameter("receive_phone"));
	  	order.setPay_payment(Integer.parseInt(request.getParameter("pay_payment")));
	  	order.setNotice(request.getParameter("notice"));
	  	order.setUser_num(user_num);
	  	
	  	OrderDAO orderDAO = OrderDAO.getInstance();
	  	
	  	orderDAO.insertOrder(order, orderDetailList);
	  	
	  	//Refresh 정보를 응답 헤더에 추가
  		String url = request.getContextPath()+"/main/main.do";
  		response.addHeader("Refresh", "2;url="+url);
  		request.setAttribute("result_title", "상품 주문 완료");
  		request.setAttribute("result_msg", "주문이 완료되었습니다.");
  		request.setAttribute("result_url", url);
  		
  		return "/WEB-INF/views/common/result_view.jsp";
	}
}