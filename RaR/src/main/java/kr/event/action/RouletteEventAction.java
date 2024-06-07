package kr.event.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import kr.controller.Action;
import kr.rar.dao.EventDAO;

public class RouletteEventAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String,String> mapAjax = 
		          new HashMap<String,String>();
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		
		if(user_num == null) {
			mapAjax.put("result", "logout");
		}
		EventDAO dao = EventDAO.getInstance();
		int ticket = dao.getTicket(user_num);
		int point = Integer.parseInt(request.getParameter("point"));
		int event_num = 123;
		
		if(ticket <= 0) {
			mapAjax.put("result", "noTicket");
		}else if(ticket > 0) {
			dao.useTicket(user_num, point);
			dao.updatePoint(user_num, point, event_num);
			mapAjax.put("result", "success");
		}
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);
		
		request.setAttribute("ajaxData", ajaxData);
		
		return "/WEB-INF/views/common/ajax_view.jsp";
	}

}


