package kr.book.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;
import kr.rar.dao.BookDAO;
import kr.rar.dao.ItemDAO;
import kr.rar.vo.BookVO;
import kr.rar.vo.ItemVO;
import kr.util.PagingUtil;


public class Bookslist implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		String pageNum = request.getParameter("pageNum");
		if(pageNum == null) pageNum="1";
		String keyfield = request.getParameter("keyfield");
		String keyword = request.getParameter("keyword");
		
		if (keyword == null || keyword.isEmpty()) {
		    keyword = request.getParameter("key");
		}
		
		BookDAO dao = BookDAO.getInstance();
		int count = dao.getBooksCount(keyfield, keyword);
		
		PagingUtil page = new PagingUtil(keyfield, keyword, Integer.parseInt(pageNum), count,20,10,"booklist.do");
		List<BookVO> list = null;
		if(count > 0) {
			list = dao.getListbookslist(page.getStartRow(), page.getEndRow(), keyfield, keyword);
			ItemDAO idao = ItemDAO.getInstance();
			for (BookVO book : list) {
                int bk_count = idao.getItemCount(book.getBk_num()); // bk_count 값을 계산
                book.setBk_count(bk_count); // bk_count 값을 BookVO 객체에 설정
                int bk_price = dao.price(book.getBk_num());
                book.setBk_minprice(bk_price);
            }
		}
		
		
		request.setAttribute("count", count);
		request.setAttribute("list", list);
		request.setAttribute("page", page.getPage());
		return "/WEB-INF/views/book/booklist.jsp";
	}
}
