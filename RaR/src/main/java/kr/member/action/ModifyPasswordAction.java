package kr.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.rar.dao.MemberDAO;
import kr.rar.vo.MemberVO;

public class ModifyPasswordAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Integer user_num = 
				(Integer)session.getAttribute("user_num");
		if(user_num == null) {//로그인 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}
		//로그인 된 경우
		//전송된 데이터 인코딩 타입 지정
		request.setCharacterEncoding("utf-8");
		
		//현재 비밀번호
		String origin_password = 
				request.getParameter("origin_password");
		String password = request.getParameter("password");
		//현재 로그인 한 이메일
		String user_email = 
				(String)session.getAttribute("user_email");
		
		MemberDAO dao = MemberDAO.getInstance();
		MemberVO member = dao.checkMember(user_email);
		boolean check =false;
		
		//사용자가 입력한 아이디가 존재하고 로그인한 아이디와 일치하는지 체크
		check = member.isCheckedPassword(origin_password);
		
		if(check) {//인증 성공
			//비밀번호 변경
			dao.updatePassword(password,user_num);
		}
		
		request.setAttribute("check", check);
		//JSP 경로 반환
		return "/WEB-INF/views/member/modifyPassword.jsp";
	}

}






