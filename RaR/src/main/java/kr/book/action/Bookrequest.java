package kr.book.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.rar.dao.BookApprovalDAO;
import kr.rar.vo.BookApprovalVO;
import kr.util.FileUtil;

public class Bookrequest implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null){
			return "redirect:/member/loginForm.do";
		}
		BookApprovalDAO dao = BookApprovalDAO.getInstance();
		BookApprovalVO vo = new BookApprovalVO();
		 
        vo.setBk_name(request.getParameter("bk_name"));
        vo.setAuthor(request.getParameter("author"));
        vo.setPubDate(request.getParameter("pubdate"));
        vo.setCover(request.getParameter("cover"));
        vo.setCategoryName(request.getParameter("categoryname"));
        vo.setUser_num(user_num);
        vo.setAd_comment(request.getParameter("comment"));
        vo.setPrice(Integer.parseInt(request.getParameter("price")));
        vo.setItem_grade(Integer.parseInt(request.getParameter("item_grade")));
        vo.setPublisher(request.getParameter("publisher"));
        vo.setPhoto(FileUtil.createFile(request, "photo"));
        vo.setIsbn(request.getParameter("isbn"));
        vo.setDescription(request.getParameter("description"));
        
        dao.insertBook(vo);
        request.setAttribute("notice_msg", "글 등록 완료");
		request.setAttribute("notice_url", request.getContextPath()+"/main/main.do");
		
		return "/WEB-INF/views/common/alert_view.jsp";
	}

}
