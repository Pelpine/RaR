package kr.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		dao.insertMember(member);
		
		request.setAttribute("result_title", "회원 가입 완료");
		request.setAttribute("result_msg", "회원 가입이 완료되었습니다.");
		request.setAttribute("result_url", request.getContextPath()+"/main/main.do");
		
		return "/WEB-INF/views/common/result_view.jsp";
	}

}
