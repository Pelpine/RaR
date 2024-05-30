package kr.main.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;
import kr.rar.dao.EventDAO;
import kr.rar.vo.EventVO;

public class MainAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		EventDAO dao = EventDAO.getInstance();
		List<EventVO> list = dao.getBanner();
		request.setAttribute("list", list);

	
		//JSP 경로 반환
		return "/WEB-INF/views/main/main.jsp";
	}
}






