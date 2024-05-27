package kr.event.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.rar.dao.EventDAO;
import kr.rar.vo.EventVO;

public class UpdateEventFormAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Integer user_num = (Integer) session.getAttribute("user_num");
		if(user_num == null)
		{
			return "redirect:/member/loginForm.do";
		}
		Integer user_auth = (Integer) session.getAttribute("user_auth");
		if(user_auth!=9) {//관리자로 로그인하지 않은 경우
		return "/WEB-INF/views/common/notice.jsp";
		}
		Integer event_num = Integer.parseInt(request.getParameter("event_num"));
		EventDAO dao = EventDAO.getInstance();
		EventVO event = dao.EventDetail(event_num);
		
		request.setAttribute("event", event);
		return "/WEB-INF/views/event/updateEventForm.jsp";
	}

}
