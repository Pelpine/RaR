package kr.cart.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import kr.rar.dao.CartDAO;
import kr.rar.dao.ItemDAO;
import kr.rar.vo.CartVO;
import kr.rar.vo.ItemVO;
import kr.controller.Action;

public class CartWriteAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, String> mapAjax = new HashMap<String, String>();
		
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		int item_num = Integer.parseInt(request.getParameter("item_num"));
		int bk_num = Integer.parseInt(request.getParameter("bk_num"));

		if(user_num==null) {//로그인이 되지 않은 경우
			mapAjax.put("result", "logout");
		}else {//로그인 된 경우
			//전송된 데이터 인코딩 타입 지정
			request.setCharacterEncoding("utf-8");
						
			ItemDAO itemDAO = ItemDAO.getInstance();
			ItemVO itemVO = itemDAO.getItem(item_num);

			//상품 상태가 판매상태가 아닐 경우
			if(itemVO.getItem_status() > 1) {
				mapAjax.put("result", "noSale");
			}else {//상품이 판매중일 경우
				CartDAO dao = CartDAO.getInstance();
				//상품 중복체크
				boolean isItemExists = dao.isItemExists(user_num, item_num);
				if (isItemExists) {// 중복된 아이템이 있을 경우
					mapAjax.put("result", "duplicated"); 
				}else {// 중복된 아이템이 없을 경우
					CartVO cart = new CartVO();
					cart.setUser_num(user_num);
					cart.setItem_num(item_num);
					cart.setBk_num(bk_num);
					
					dao.insertCart(cart);
					mapAjax.put("result", "success");
				}				
			}
		}
		//JSON 데이터 생성
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);
		
		request.setAttribute("ajaxData", ajaxData);
		
		return "/WEB-INF/views/common/ajax_view.jsp";
	}
}
