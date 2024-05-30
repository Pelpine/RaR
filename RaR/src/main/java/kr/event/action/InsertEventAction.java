package kr.event.action;

import java.sql.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import kr.controller.Action;
import kr.rar.dao.EventDAO;
import kr.rar.vo.EventVO;
import kr.util.FileUtil;

public class InsertEventAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num==null) {
			return "redirect:/member/loginForm.do";
		}Integer user_auth = (Integer) session.getAttribute("user_auth");
		if(user_auth!=9) {//관리자로 로그인하지 않은 경우
		return "/WEB-INF/views/common/notice.jsp";
		}
		request.setCharacterEncoding("utf-8");
		EventVO event = new EventVO();
		event.setName(request.getParameter("name"));
		event.setContent(request.getParameter("content"));
		String startDateStr = request.getParameter("start_date");
        String endDateStr = request.getParameter("end_date");
        event.setStart_date(Date.valueOf(startDateStr));
        event.setEnd_date(Date.valueOf(endDateStr));
		event.setFilename(FileUtil.createFile(request, "filename"));
		event.setBanner(FileUtil.createFile(request, "banner"));
		event.setUser_num(user_num);
		String notice = request.getParameter("notice");
		if(notice==null) {
		event.setNotice(0);
		}else if(notice.equals("1")){
		event.setNotice(1);
		}
		
	
		
		EventDAO dao = EventDAO.getInstance();
		dao.insertEvent(event);
		
		request.setAttribute("notice_msg", "이벤트 등록 완료");
		request.setAttribute("notice_url", request.getContextPath()+"/event/eventList.do");
		
		return "/WEB-INF/views/common/alert_view.jsp";
 }

}

