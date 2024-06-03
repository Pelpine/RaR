package kr.book.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;
import kr.rar.dao.BookDAO;
import kr.rar.vo.BookVO;
import kr.util.PagingUtil;

public class Bookslist implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		String pageNum = request.getParameter("pageNum");
		if(pageNum == null) pageNum="1";
		
		String keyfield = request.getParameter("keyfield");
		String keyword = request.getParameter("keyword");
		
		System.out.println("keyfield : " + keyfield);
		
		BookDAO dao = BookDAO.getInstance();
		int count = dao.getBooksCount(keyfield, keyword);
		
		PagingUtil page = new PagingUtil(keyfield, keyword, Integer.parseInt(pageNum), count,20,10,"booklist.do");
		List<BookVO> list = null;
		if(count > 0) {
			list = dao.getListbookslist(page.getStartRow(), page.getEndRow(), keyfield, keyword);
		}
		
		request.setAttribute("count", count);
		request.setAttribute("list", list);
		request.setAttribute("page", page.getPage());
		return "/WEB-INF/views/book/booklist.jsp";
	}

}
