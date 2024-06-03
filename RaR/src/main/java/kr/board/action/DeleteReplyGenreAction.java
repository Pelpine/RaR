package kr.board.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import kr.rar.dao.BoardDAO;
import kr.rar.vo.BoardVO;
import kr.rar.vo.GenreUserVO;
import kr.controller.Action;

public class DeleteReplyGenreAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//전송된 데이터 인코딩 타입 지정
		request.setCharacterEncoding("utf-8");
		
		//댓글 번호
		int bgu_num = Integer.parseInt(request.getParameter("bgu_num"));
		
		Map<String,String> mapAjax =
						new HashMap<String,String>();
		
		BoardDAO dao = BoardDAO.getInstance();
		BoardVO db_reply = dao.getBoard(bgu_num);
		
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null && user_num == db_reply.getUser_num()) {
			//로그인한 회원번호와 작성자 회원번호 일치
			dao.deleteReplyGenre(bgu_num);
			
			mapAjax.put("result", "wrongAccess");
		}
		//JSON
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);
		
		request.setAttribute("ajaxData", ajaxData);
		
		return "/WEB-INF/views/common/ajax_view.jsp";
	}

}
