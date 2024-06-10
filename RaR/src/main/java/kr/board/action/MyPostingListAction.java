package kr.board.action;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.rar.dao.BoardDAO;
import kr.rar.vo.BoardVO;
import kr.controller.Action;
import kr.util.PagingUtil;

public class MyPostingListAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		
		String pageNum = request.getParameter("pageNum");
		if(pageNum==null)pageNum = "1";
		
		String keyfield  = request.getParameter("keyfield");
		String keyword = request.getParameter("keyword");
		
		BoardDAO dao = kr.rar.dao.BoardDAO.getInstance();
		int count = dao.getBoardCount(keyfield, keyword);
		
		//페이지 처리
		PagingUtil page = new PagingUtil(keyfield, keyword,
				Integer.parseInt(pageNum),count,20,10,"myPostingList.do");
		
		List<BoardVO> list = null;
		if(count > 0) {
			list = dao.getMyPosting(page.getStartRow(),page.getEndRow(), user_num);
		}
		
		request.setAttribute("count", count);
		request.setAttribute("list", list);
		request.setAttribute("page", page.getPage());
		
		
		return "/WEB-INF/views/board/myPostingList.jsp";
	}

}
