package kr.book.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;
import kr.rar.dao.ItemDAO;
import kr.rar.vo.ItemVO;
import kr.util.PagingUtil;

public class Booklistdetail implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int bk_num = Integer.parseInt(request.getParameter("bk_num"));
		String pageNum = request.getParameter("pageNum");
		if(pageNum == null) pageNum = "1";
		
		ItemDAO dao = ItemDAO.getInstance();
		
		//상품 목록, 개수
		List<ItemVO> list =  dao.getItemList(bk_num);
		int count = dao.getItemCount(bk_num);
		
		//페이지 처리
		PagingUtil page = new PagingUtil(Integer.parseInt(pageNum),count,20,10,"bookdetail.do");
		
		request.setAttribute("list", list);
		request.setAttribute("count", count);
		request.setAttribute("page", page.getPage());
		
		return "/WEB-INF/views/book/booklistdetail.jsp";
	}

}
