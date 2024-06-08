package kr.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.rar.dao.MemberDAO;
import kr.rar.vo.MemberVO;

public class RegisterUserAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//전송된 데이터 인코딩 타입 지정 
		request.setCharacterEncoding("utf-8");
		//자바빈(VO) 생성
		MemberVO member = new MemberVO();
		member.setUser_email(request.getParameter("user_email"));
		member.setUser_name(request.getParameter("user_name"));
		member.setPassword(request.getParameter("password"));
		member.setUser_phone(request.getParameter("user_phone"));
		member.setUser_zipcode(request.getParameter("user_zipcode"));
		member.setUser_address1(request.getParameter("user_address1"));
		member.setUser_address2(request.getParameter("user_address2"));
		member.setUser_ip(request.getRemoteAddr());
		
		MemberDAO dao = MemberDAO.getInstance();
		// insertMember가 가입된 유저의 user_num을 반환하도록 변경함.
		int user_num = dao.insertMember(member);
		
		//바로 로그인 처리
		 HttpSession session = request.getSession();
	        session.setAttribute("user_num", user_num);
	        session.setAttribute("user_email", member.getUser_email());
	        session.setAttribute("user_auth", member.getUser_auth()); 
		
		request.setAttribute("result_title", "회원 가입 완료");
		request.setAttribute("result_msg", "회원 가입이 완료되었습니다.");
		//url1= 추천인 이벤트 미참여
		request.setAttribute("result_url1", request.getContextPath()+"/main/main.do");
		//url2= 추천인 이벤트 참여
		request.setAttribute("result_url2", request.getContextPath()+"/event/referenceEmailEventForm.do");
		return "/WEB-INF/views/event/registerUserResult.jsp";
	}

}
