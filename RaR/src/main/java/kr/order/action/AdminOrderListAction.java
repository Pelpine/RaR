package kr.order.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.rar.dao.OrderDAO;
import kr.rar.vo.OrderVO;
import kr.util.PagingUtil;

public class AdminOrderListAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		
		if(user_num==null) {
			return "redirect:/member/loginForm.do";
		}
		
		Integer user_auth = (Integer)session.getAttribute("user_auth");
		if(user_auth != 9) {//관리자가 아닌 경우
			return "/WEB-INF/views/common/notice.jsp";
		}
		
		String pageNum = request.getParameter("pageNum");
		if(pageNum == null) pageNum = "1";
		
		String keyfield = request.getParameter("keyfield");
		String keyword = request.getParameter("keyword");
		
		OrderDAO dao = OrderDAO.getInstance();
		int count = dao.getAdminOrderCount(keyfield, keyword);
		
		//페이지 처리
		PagingUtil page = new PagingUtil(keyfield,keyword,Integer.parseInt(pageNum),count,20,10,"adminOrderList.do");
		
		List<OrderVO> list = null;
		if(count > 0) {
			list = dao.getAdminOrderList(page.getStartRow(), page.getEndRow(), keyfield, keyword);
		}
		request.setAttribute("count", count);
		request.setAttribute("list", list);
		request.setAttribute("page", page.getPage());
		
		return "/WEB-INF/views/order/admin_order_list.jsp";
	}
}