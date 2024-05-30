package kr.event.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;
import kr.rar.dao.EventDAO;
import kr.rar.vo.EventVO;
import kr.util.StringUtil;

public class EventDetailAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int event_num = Integer.parseInt(request.getParameter("event_num"));
		
		EventDAO dao = EventDAO.getInstance();
		dao.updateReadCount(event_num);
		EventVO event= dao.EventDetail(event_num);
		
		event.setName(StringUtil.useNoHTML(event.getName()));
		//HTML를 허용하지 않으면서 줄바꿈
		event.setContent(StringUtil.useBrNoHTML(event.getContent()));
		request.setAttribute("event", event);
		
		return "/WEB-INF/views/event/eventDetail.jsp";
	}

}
