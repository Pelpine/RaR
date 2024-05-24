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
		String start_date = request.getParameter("start_date");
		String end_date = request.getParameter("end_date");
		EventDAO dao = EventDAO.getInstance();
		int count = dao.getBoardCount(keyfield, keyword, start_date, end_date);
		
		//페이지 처리
		PagingUtil page = new PagingUtil(keyfield,keyword,Integer.parseInt(pageNum),count,20,10,"list.do");
		
		List<EventVO> list = null;
		if(count > 0) {
			list = dao.getBoard(page.getStartRow(), page.getEndRow(), keyfield, keyword, start_date, end_date);
		}
		request.setAttribute("count", count);
		request.setAttribute("list", list);
		request.setAttribute("page", page.getPage());
		
		return "/WEB-INF/views/board/list.jsp";
	}

}
