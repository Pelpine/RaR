package kr.member.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import kr.controller.Action;
import kr.rar.dao.BookDAO;
import kr.rar.dao.MemberDAO;
import kr.rar.vo.BookApprovalVO;
import kr.rar.vo.MemberVO;

public class CheckDuplicatedIdAction implements Action{
 
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//전송된 데이터 인코딩 타입 지정
		request.setCharacterEncoding("utf-8");
		//전송된 데이터 반환
		String user_email = request.getParameter("user_email");
		
		MemberDAO dao = MemberDAO.getInstance();
		MemberVO member = dao.checkMember(user_email);

		Map<String,String> mapAjax = 
				new HashMap<String,String>();
		if(member == null) {//아이디 미중복
			mapAjax.put("result", "idNotFound");
		}else {//아이디 중복
			mapAjax.put("result", "idDuplicated");
		}
		/*
		 * JSON 형식으로 변환하기를 원하는 문자열을 HashMap에 key와 value의
		 * 쌍으로 저장한 후 ObjectMapper의 writeValueAsString에
		 * Map 객체를 전달해서 일반 문자열 데이터를 JSON 형식의 문자열 데이터로
		 * 변환 후 반환
		 */
		ObjectMapper mapper = new ObjectMapper();
		//JSON 문자열 반환
		String ajaxData = mapper.writeValueAsString(mapAjax);

		request.setAttribute("ajaxData", ajaxData);


		return "/WEB-INF/views/common/ajax_view.jsp";
	}

}
