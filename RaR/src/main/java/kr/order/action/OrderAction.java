package kr.order.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.rar.dao.CartDAO;
import kr.rar.vo.CartVO;
import kr.rar.vo.OrderDetailVO;

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
	  		
	  	}
		return null;
	}

}
