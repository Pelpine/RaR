package kr.book.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.rar.dao.BookApprovalDAO;
import kr.rar.vo.BookApprovalVO;
import kr.util.FileUtil;

public class Bookupdate implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		
		if(user_num==null) {
			return "redirect:/book/loginForm.do";
		}
		String photo = FileUtil.createFile(request, "photo");
		
		int approval_id =Integer.parseInt(request.getParameter("approval_id"));
		
		BookApprovalDAO da = BookApprovalDAO.getInstance();
		BookApprovalVO io = da.selectbook(approval_id);
		String user_photo = io.getPhoto();
		
		if(!photo.equals(user_photo)) {
			FileUtil.removeFile(request, user_photo);
		}
		
		
		BookApprovalDAO dao = BookApprovalDAO.getInstance();
		BookApprovalVO vo = new BookApprovalVO();
		vo.setStatus(Integer.parseInt(request.getParameter("status")));
		vo.setCover(request.getParameter("cover"));
		vo.setBk_name(request.getParameter("bk_name"));
		vo.setAuthor(request.getParameter("author"));
		vo.setPubDate(request.getParameter("pubdate"));
		vo.setPrice(Integer.parseInt(request.getParameter("price")));
		vo.setCategoryName(request.getParameter("categoryname"));
		vo.setAd_comment(request.getParameter("comment"));
		vo.setItem_grade(Integer.parseInt(request.getParameter("item_grade")));
		vo.setPhoto(photo);
		vo.setPrivate_num(Integer.parseInt(request.getParameter("private_num")));
		vo.setDescription(request.getParameter("description"));
		vo.setIsbn(request.getParameter("isbn"));
		vo.setApproval_id(approval_id);
		
		dao.update(vo);
		
		request.setAttribute("notice_msg", "글 등록 완료");
		request.setAttribute("notice_url", request.getContextPath()+"/book/list.do");
		
		return "/WEB-INF/views/common/alert_view.jsp";
		
	}
}
