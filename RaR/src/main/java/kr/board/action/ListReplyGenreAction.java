package kr.board.action;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import kr.controller.Action;
import kr.rar.dao.BoardDAO;
import kr.rar.vo.GenreUserVO;
import kr.util.PagingUtil;

public class ListReplyGenreAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		
		String pageNum=request.getParameter("pageNum");
		if(pageNum==null) {
			pageNum="1";
		}
		String rowCount = request.getParameter("rowCount");
		if(rowCount == null) {
			rowCount = "10";
		}
		int bg_num = Integer.parseInt(request.getParameter("bg_num"));
		BoardDAO dao = BoardDAO.getInstance();
		int count = dao.getReplyGenreCount(bg_num);
		
		//ajax 방식으로 목록을 표시하기 때문에 PagingUtil은 페이지수 표시가 목적이아니라
		//목록 데이터의 처리를 위해 rownum 번호를 구하는 것이 목적임
		
		PagingUtil page = new PagingUtil(
				Integer.parseInt(pageNum),count,
							Integer.parseInt(rowCount));
		
		List<GenreUserVO> list = null;
		if(count > 0) {
			list = dao.getListReplyGenre(page.getStartRow(),page.getEndRow(),bg_num);
		}else {
			list=Collections.emptyList();
			
		}
		
		
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		
		Map<String,Object> mapAjax = 
							new HashMap<String,Object>();
		mapAjax.put("count", count);
		mapAjax.put("list", list);
		//로그인한 사람이 작성자인지 체크하기 위해서 로그인한 회원번호 전송
		mapAjax.put("user_num", user_num);
		
		//JSON 문자열로 변환
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);
		
		request.setAttribute("ajaxData", ajaxData);
		
		return "/WEB-INF/views/common/ajax_view.jsp";
	}

}
