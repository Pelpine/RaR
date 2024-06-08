package kr.event.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.rar.dao.EventDAO;

public class ReferenceEmailEventAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null) {
			return "redirect:/member/loginForm.do";
		}
		EventDAO dao = EventDAO.getInstance();
		//추천인 이벤트 참여 여부 check가 true이면 참여유저
		boolean check = dao.checkParticipationReferenceEvent(user_num);
		if(check) {
			request.setAttribute("result_title", "추천인 이벤트 참여 불가능");
			request.setAttribute("result_msg", "추천인 이벤트는 중복 참여가 불가능합니다.");
			//url1= 추천인 이벤트 미참여
			request.setAttribute("result_url", request.getContextPath()+"/main/main.do");
		}else if(!check) {
			
		String reference_id = request.getParameter("email");
		dao.particepateReferenceEvent(user_num, reference_id);
		int reference_user_num = dao.getUser_numByEmail(reference_id);
		int event_num = 124; // 친구초대 이벤트 이벤트 번호
		int point = 500;
		//추천한 사람의 포인트 추가
		dao.updatePoint(reference_user_num, point, event_num);
		//추천 받은 사람의 포인트 추가
		dao.updatePoint(user_num, point, event_num);
		
		request.setAttribute("result_title", "추천인 이벤트 참여 완료");
		request.setAttribute("result_msg", "추천인과 회원님에게 포인트가 지급되었습니다!");
		//url1= 추천인 이벤트 미참여
		request.setAttribute("result_url", request.getContextPath()+"/main/main.do");
		}
        return "/WEB-INF/views/common/result_view.jsp";
	
		}

}
