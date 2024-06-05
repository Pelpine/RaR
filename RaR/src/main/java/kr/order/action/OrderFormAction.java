package kr.order.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.rar.dao.CartDAO;
import kr.rar.dao.ItemDAO;
import kr.rar.dao.MemberDAO;
import kr.rar.vo.CartVO;
import kr.rar.vo.ItemVO;
import kr.rar.vo.MemberVO;
import kr.controller.Action;

public class OrderFormAction implements Action{

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
		CartDAO dao = CartDAO.getInstance();
		
		// 선택된 장바구니 항목 번호들 가져오기
	    String[] selectedCartNums = request.getParameterValues("selectedCartNums");
	    if (selectedCartNums != null) {
	    	dao.resetCartSelected();
	        for (String cart_num : selectedCartNums) {
	            dao.updateCartSelected(Integer.parseInt(cart_num));
	        }
	    }
	    
	    //장바구니 총액
	    int pay_total = dao.getSelectedCartTotal(user_num);
	    if(pay_total <= 0) {//이미 구매가 완료된 경우 재구매 방지 (back버튼 눌러서 돌아간 후 재구매하는 오류 방지)
	    	request.setAttribute("notice_msg", "정상적인 주문이 아니거나 상품이 판매중이 아닙니다.");
	    	request.setAttribute("notice_url", request.getContextPath()+"/item/itemList.do");
	    	return "/WEB-INF/views/common/alert_view.jsp";
	    }
	    
	    //배송비
	    int pay_ship = 0;
	    if(pay_total < 30000) pay_ship = 4000;
	    
	    //적립포인트
	    int order_points = (int)Math.floor(pay_total * 0.01);
	    
		//장바구니에 담겨있는 상품 정보 호출
		List<CartVO> selectedCartList = dao.getSelectedCartList(user_num);
		ItemDAO itemDAO = ItemDAO.getInstance();
		for(CartVO cart : selectedCartList) {
			ItemVO item = itemDAO.getItem(cart.getItem_num());
			if(item.getItem_status() == 2 || item.getItem_status() == 3) {
				//상품 판매 완료
				request.setAttribute("notice_msg", "장바구니에 담으신 [" + item.getBookVO().getBk_name() + "] 상품이 이미 판매되었거나 판매 중지되었습니다.");
				request.setAttribute("notice_url", request.getContextPath() + "/cart/cartList.do");
				return "/WEB-INF/views/common/alert_view.jsp";
			}
		}
		
		//사용자 보유 포인트
		MemberDAO memberDAO = MemberDAO.getInstance();
		MemberVO member = memberDAO.getMember(user_num);
		int user_points = member.getUser_point();
		
		request.setAttribute("list", selectedCartList);
		request.setAttribute("pay_total", pay_total);
		request.setAttribute("pay_ship", pay_ship);
		request.setAttribute("order_points", order_points);
		request.setAttribute("user_points", user_points);
		
		return "/WEB-INF/views/order/orderForm.jsp";
	}
}