package kr.cart.action;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import kr.controller.Action;
import kr.rar.dao.CartDAO;
import kr.rar.vo.CartVO;

public class CartItemAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//유저 번호 가져오기
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		
		//인스턴스 생성
		CartDAO dao = CartDAO.getInstance();
		//장바구니 개수 가져오기
		int count = dao.getCartItemCount(user_num);
		
		//장바구니 목록 세팅
		List<CartVO> list = null;
		if(count > 0) {//장바구니 목록이 있을 경우
			list = dao.getCartList(user_num);
		}else {//장바구니 목록이 없을 경우
			list = Collections.emptyList();//빈 배열
		}
		//장바구니 개수,목록 AJAX 데이터로 변환
		Map<String, Object> mapAjax = new HashMap<String, Object>();
		mapAjax.put("count", count);
		mapAjax.put("list", list);
		//JSON 문자열로 변환
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);
		
		request.setAttribute("ajaxData", ajaxData);
		
		return "/WEB-INF/views/common/ajax_view.jsp";
	}
}