package kr.review.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import kr.controller.Action;
import kr.rar.dao.ReviewDAO;
import kr.rar.vo.ReviewVO;

public class ReviewdeleteAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, String> mapAjax = new HashMap<String, String>();
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		int re_num = Integer.parseInt(request.getParameter("re_num"));
		
		if(user_num == null) {
			mapAjax.put("result", "logout");
		}else {
			ReviewDAO dao = ReviewDAO.getInstance();
			ReviewVO vo = dao.vo(re_num);
			if(!user_num.equals(vo.getUser_num())) {
				mapAjax.put("result", "pass");
			}else {
				dao.delete(re_num);
				mapAjax.put("result", "success");
			}
			
		}
		//JSON 데이터로 변환
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);
		
		request.setAttribute("ajaxData", ajaxData);
		
		return "/WEB-INF/views/common/ajax_view.jsp";
	}

}
