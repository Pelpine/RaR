package kr.board.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.filters.SetCharacterEncodingFilter;
import org.codehaus.jackson.map.ObjectMapper;

import kr.rar.dao.BoardDAO;
import kr.rar.vo.GenreUserVO;
import kr.controller.Action;

public class UpdateReplyGenreAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		
		//댓글 번호 반환
		int bgu_num=Integer.parseInt(
					request.getParameter("bgu_num"));
		
		BoardDAO dao = BoardDAO.getInstance();
		//수정 전 데이터
		GenreUserVO db_genre = dao.getReplyGenre(bgu_num);
		System.out.println(db_genre);
		
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		String user_email = (String) session.getAttribute("user_email");
		
		Map<String,String> mapAjax=
					new HashMap<String,String>();
		
		if(user_num == null) {//로그인이 되지 않은 경우
			mapAjax.put("result", "logout");
		}else if(user_num !=null && user_num == db_genre.getUser_num()) {
			//로그인한 회원번호와 작성자 회원번호가 일치한다는 의미
			GenreUserVO replyg = new GenreUserVO();
			replyg.setBgu_num(bgu_num);
			replyg.setBgu_content(request.getParameter("bgu_content"));
			
			dao.updateReplyGenre(replyg);
			
			mapAjax.put("result", "success");
		}else {
			//로그인한 회원번호와 작성자 회원번호가 불일치
			mapAjax.put("result", "wrongAccess");
		}
		
		//JSON 데이터 변환
		ObjectMapper mapper= new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);
		
		request.setAttribute("ajaxData", ajaxData);
		
		return "/WEB-INF/views/common/ajax_view.jsp";
	}

}
