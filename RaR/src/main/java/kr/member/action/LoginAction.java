package kr.member.action;

import java.io.Console;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.rar.dao.MemberDAO;
import kr.rar.vo.MemberVO;

public class LoginAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//전송된 데이터 인코딩 타입 지정
		request.setCharacterEncoding("utf-8");
		//전송된 데이터 반환
		String user_email = request.getParameter("user_email");
		String password = request.getParameter("password");
		
		MemberDAO dao = MemberDAO.getInstance();
		MemberVO member = dao.checkMember(user_email);
		boolean check = false;
		
		if(member!=null) {//동일한 id 존재
			//비밀번호 일치 여부
			check = member.isCheckedPassword(password);
			//정지회원의 경우 상태 표시
			request.setAttribute("auth", member.getUser_auth());
		}
		if(check) {//인증 성공
			//로그인 처리
			HttpSession session = request.getSession();
			session.setAttribute("user_num", member.getUser_num());
			session.setAttribute("user_email", member.getUser_email());
			session.setAttribute("user_auth", member.getUser_auth());
			session.setAttribute("user_photo", member.getUser_photo());
		
			//메인으로 리다이렉트
			return "redirect:/main/main.do";
		}
		//인증 실패
		return "/WEB-INF/views/member/login.jsp";
	}

}
