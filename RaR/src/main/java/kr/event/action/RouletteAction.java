package kr.event.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.rar.dao.EventDAO;

public class RouletteAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Integer user_num = (Integer) session.getAttribute("user_num");
		if(user_num == null)
		{
			return "redirect:/member/loginForm.do";
		}
		EventDAO dao = EventDAO.getInstance();
		int ticket = dao.getTicket(user_num);
		
		request.setAttribute("ticket", ticket);
		return "/WEB-INF/views/event/roulette.jsp";
	}

}
