package kr.event.action;

import java.sql.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.rar.dao.EventDAO;
import kr.rar.vo.EventVO;
import kr.util.FileUtil;

public class UpdateEventAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num==null) {
			return "redirect:/member/loginForm.do";
		}
		request.setCharacterEncoding("utf-8");
		int event_num = Integer.parseInt(request.getParameter("event_num"));
	
		EventDAO dao = EventDAO.getInstance();
		//로그인한 회원 번호와 작성자 회원번호 일치 여부 체크
		Integer user_auth = (Integer) session.getAttribute("user_auth");
		if(user_auth!=9) {//관리자로 로그인하지 않은 경우
		return "/WEB-INF/views/common/notice.jsp";
		}
		
		EventVO event =  new EventVO();
		event.setEvent_num(event_num);
		event.setName(request.getParameter("name"));
		event.setContent(request.getParameter("content"));
		String startDateStr = request.getParameter("start_date");
        String endDateStr = request.getParameter("end_date");
        event.setStart_date(Date.valueOf(startDateStr));
        event.setEnd_date(Date.valueOf(endDateStr));
		event.setFilename(FileUtil.createFile(request, "filename"));
		dao.updateEvent(event);
		
		 if(event.getFilename()!=null && !event.getFilename().isEmpty()) {
			 //새 파일로 교체할 때 원래 파일 제거
			 FileUtil.removeFile(request, event.getFilename());
		 }
		
		return "redirect:/board/eventDetail.do?event_num="+event_num;
	}

}
