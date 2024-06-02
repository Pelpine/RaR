package kr.event.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import kr.controller.Action;
import kr.rar.dao.EventDAO;

public class AttendanceEventAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String,String> mapAjax = 
		          new HashMap<String,String>();
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		
		if(user_num == null) {
			mapAjax.put("result", "logout");
		}
		boolean checkat = false;
		EventDAO dao = EventDAO.getInstance();
		checkat = dao.checkAttendance(user_num);
		if(!checkat) {
			dao.attendance(user_num);
			int point = 50;
			int event_num = 100;
			dao.updatePoint(user_num , point,event_num);
			mapAjax.put("result", "success");		
		}else if(checkat) {
			mapAjax.put("result", "alreadyAttendance");
		}
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);
		
		request.setAttribute("ajaxData", ajaxData);
		
		return "/WEB-INF/views/common/ajax_view.jsp";
		}
}
