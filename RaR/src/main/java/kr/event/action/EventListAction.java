package kr.event.action;

import java.util.List;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import kr.controller.Action;
import kr.rar.dao.EventDAO;
import kr.rar.vo.EventVO;
import kr.util.PagingUtil;

public class EventListAction implements Action{
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String pageNum = request.getParameter("pageNum");
		if(pageNum == null) {
			pageNum="1";
		}
		String keyfield = request.getParameter("keyfield");
		String keyword = request.getParameter("keyword");
		String underway = request.getParameter("underway");
		if(underway == null) {
			underway ="2";
		}
		EventDAO dao = EventDAO.getInstance();
		int count = dao.getBoardCount(keyfield, keyword, underway);
		
		//페이지 처리
		PagingUtil page = new PagingUtil(keyfield,keyword,Integer.parseInt(pageNum),count,10,10,"eventList.do");
		
		List<EventVO> list = null;
		if(count > 0) {
			list = dao.getBoard(page.getStartRow(), page.getEndRow(), keyfield, keyword,underway);
		}
		request.setAttribute("count", count);
		request.setAttribute("list", list);
		request.setAttribute("page", page.getPage());
		
		return "/WEB-INF/views/event/eventList.jsp";
	}

}
