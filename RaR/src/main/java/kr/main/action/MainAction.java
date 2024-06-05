package kr.main.action;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;
import kr.rar.dao.BookDAO;
import kr.rar.dao.EventDAO;
import kr.rar.dao.ItemDAO;
import kr.rar.vo.BookVO;
import kr.rar.vo.EventVO;
import kr.rar.vo.ItemVO;
import kr.util.PagingUtil;

public class MainAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		EventDAO dao = EventDAO.getInstance();
		List<EventVO> list = dao.getBanner();
		request.setAttribute("list", list);
		
		
		ItemDAO itemDAO = ItemDAO.getInstance();
		List<ItemVO> itemList = null;
		
		itemList = 
				itemDAO.getListItem(
				              1,5,null,null,1);//현재 진행중인 이벤트만 반환
		
		request.setAttribute("itemList", itemList);
		//JSP 경로 반환
		return "/WEB-INF/views/main/main.jsp";
	}
}






