package kr.item.action;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.rar.dao.ItemDAO;
import kr.rar.vo.BookVO;
import kr.rar.vo.ItemVO;
import kr.util.FileUtil;

public class AdminModifyAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null) {//로그인 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}
		
		Integer user_auth = 
				(Integer)session.getAttribute("user_auth");
		if(user_auth != 9) {//관리자로 로그인하지 않은 경우
			return "/WEB-INF/views/common/notice.jsp";
		}
		//관리자로 로그인 한 경우
		//전송된 데이터 인코딩 타입 지정
		request.setCharacterEncoding("utf-8");
		int item_num = Integer.parseInt(
				        request.getParameter("item_num"));
		
		//DB에 저장된 정보를 읽어옴
		ItemDAO dao = ItemDAO.getInstance();
		ItemVO db_item = dao.getItem(item_num);
		
		//전송된 데이터를 자바빈(VO)에 저장
		ItemVO item = new ItemVO();
		
		item.setItem_num(item_num);	
		item.setItem_price(Integer.parseInt(
				   request.getParameter("price")));
		item.setItem_status(Integer.parseInt(
				       request.getParameter("status")));
		item.setItem_grade(Integer.parseInt(request.getParameter("grade")));
		
		dao.updateItem(item);
		
		
		request.setAttribute("notice_msg", "정상적으로 수정되었습니다.");
		request.setAttribute("notice_url", 
				request.getContextPath()
				  +"/item/adminModifyForm.do?item_num="+item_num);
		
		return "/WEB-INF/views/common/alert_view.jsp";
	}

}



