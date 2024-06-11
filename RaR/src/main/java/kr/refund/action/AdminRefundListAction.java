package kr.refund.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.rar.dao.RefundDAO;

import kr.rar.vo.RefundVO;
import kr.util.PagingUtil;

public class AdminRefundListAction implements Action{

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
		String pageNum = request.getParameter("pageNum");
		if(pageNum == null) {
			pageNum="1";
		}
		// 이벤트 등록 버튼 표시 여부 결정하기 위해 등급 조회
	
		RefundDAO dao = RefundDAO.getInstance();
		int count = dao.getRefundCount();
		
		//페이지 처리
		PagingUtil page = new PagingUtil(null,null,Integer.parseInt(pageNum),count,10,10,"eventList.do");
		
		List<RefundVO> list = null;
		List<String> itemList = new ArrayList<>();
		if(count > 0) {
			list = dao.getRefundList(page.getStartRow(), page.getEndRow());
			for (RefundVO refund : list) {
		        // 각 환불 항목의 item_num을 이용하여 상품 이름을 가져옴
		        String itemName = dao.getItemNameByItemNum(refund.getItem_num());
		        itemList.add(itemName);
		}
		
		
		// 이벤트 등록 버튼 표시 여부 결정하기 위해 등급 조회
		request.setAttribute("count", count);
		request.setAttribute("list", list);
		request.setAttribute("itemList", itemList);
		request.setAttribute("page", page.getPage());
		

	}
		return "/WEB-INF/views/refund/adminRefundList.jsp";
	}
}
