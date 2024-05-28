package kr.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.rar.dao.MemberDAO;
import kr.rar.vo.MemberVO;
import kr.util.FileUtil;

public class DeleteUserAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		
		if(user_num == null) {//로그인이 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}
		
		//로그인 된 경우
		//전송된 데이터 인코딩 타입 지정
		request.setCharacterEncoding("utf-8");
		//전송된 데이터 반환
		String user_email = request.getParameter("user_email");
		String password = request.getParameter("password");
		//로그인한 아이디
		String login_email = (String)session.getAttribute("login_email");
		
		MemberDAO dao = MemberDAO.getInstance();
		MemberVO db_member = dao.checkMember(user_email);
		boolean check = false;
		//사용자가 입력한 아이디가 존재하고 로그인한 아이디와 일치하는지 체크,
		//입력한 이메일과 저장된 이메일 일치 여부 체크
		if(db_member!=null && user_email.equals(db_member.getUser_email())) {
			//비밀번호 일치 여부 체크
			check = db_member.isCheckedPassword(password);
		}
		if(check) {//인증 성공
			//회원정보 삭제
			dao.deleteMember(user_num);
			//프로필 사진 삭제
			FileUtil.removeFile(request, db_member.getUser_photo());
			//로그아웃
			session.invalidate();
		}
		
		request.setAttribute("check", check);
		return "/WEB-INF/views/member/deleteUser.jsp";
	}
}
