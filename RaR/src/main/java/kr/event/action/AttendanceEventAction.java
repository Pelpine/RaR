package kr.event.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.rar.dao.EventDAO;

public class AttendanceEventAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null) {
			return "redirect:/member/loginForm.do";
		}
		boolean checkat = false;
		EventDAO dao = EventDAO.getInstance();
		checkat = dao.checkAttendance(user_num);
		if(checkat) {
			dao.attendance(user_num);
			dao.updatePoint(user_num);			
			request.setAttribute("notice_msg", "출석체크 완료!");
			request.setAttribute("notice_url", request.getContextPath()+"/member/myPage.do");		
		}else if(!checkat) {
			request.setAttribute("notice_msg", "오늘은 이미 출석 체크 완료!");
			request.setAttribute("notice_url", request.getContextPath()+"/member/myPage.do");
				
		}
		return "/WEB-INF/views/common/alert_view.jsp";
		}
		
		

}
