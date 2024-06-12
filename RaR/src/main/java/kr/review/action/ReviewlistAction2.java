package kr.review.action;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import kr.controller.Action;
import kr.rar.dao.MemberDAO;
import kr.rar.dao.ReviewDAO;
import kr.rar.vo.ReviewVO;
import kr.util.PagingUtil;
import java.util.logging.Logger;

public class ReviewlistAction2 implements Action {
	
	 private static final Logger logger = Logger.getLogger(ReviewlistAction2.class.getName());

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("utf-8");
        HttpSession session = request.getSession();
        Integer user_num = (Integer) session.getAttribute("user_num");

        String bk_num = request.getParameter("bk_num");
        int re_num = Integer.parseInt(request.getParameter("re_num"));
        
        String pageNum = request.getParameter("pageNum");
        if (pageNum == null) pageNum = "1";

        ReviewDAO dao = ReviewDAO.getInstance();
        MemberDAO mdao = MemberDAO.getInstance();

        int count = 0;
        List<ReviewVO> list = null;
        PagingUtil page = null;

        if (re_num == 1) {
            count = dao.getreviewCount(user_num, re_num);
            if (count > 0) {
                page = new PagingUtil(null, null, Integer.parseInt(pageNum), count, 20, 10, "reviewlist.do");
                list = dao.getListreviewlist(page.getStartRow(), page.getEndRow(), user_num, 1);
                for (ReviewVO vo : list) {
                    vo.setMemberVO(mdao.getMember(vo.getUser_num()));
                }
            }
        } else if (re_num == 2) {
            count = dao.getreviewCount(Integer.parseInt(bk_num), re_num);
            if (count > 0) {
                page = new PagingUtil(null, null, Integer.parseInt(pageNum), count, 20, 10, "reviewlist.do");
                list = dao.getListreviewlist(page.getStartRow(), page.getEndRow(), Integer.parseInt(bk_num), 2);
                for (ReviewVO vo : list) {
                    vo.setMemberVO(mdao.getMember(vo.getUser_num()));
                }
            }
        }

        request.setAttribute("count2", count);
        request.setAttribute("list2", list);
        if (page != null) {
            request.setAttribute("page2", page.getPage());
        }

        return "/WEB-INF/views/review/reviewlist2.jsp";
    }
}
