package kr.board.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;


import kr.rar.dao.BoardDAO;
import kr.rar.vo.GenreUserVO;
import kr.controller.Action;

public class DeleteReplyGenreAction implements Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 전송된 데이터 인코딩 타입 지정
        request.setCharacterEncoding("utf-8");
        
        // 댓글 번호
        int bgu_num = Integer.parseInt(request.getParameter("bgu_num"));
        
        Map<String, String> mapAjax = new HashMap<String, String>();

        BoardDAO dao = BoardDAO.getInstance();
        GenreUserVO db_genre = dao.getGenreUser(bgu_num); // 댓글 정보를 가져오는 메서드

        HttpSession session = request.getSession();
        Integer user_num = (Integer) session.getAttribute("user_num");
        
        if (user_num != null && user_num.equals(db_genre.getUser_num())) {
            // 로그인한 회원번호와 작성자 회원번호 일치
            int deleteCount = dao.deleteReplyGenre(bgu_num);
            if (deleteCount > 0) {
                mapAjax.put("result", "success");
            } else {
                mapAjax.put("result", "failure");
            }
        } else {
            // 로그인한 회원번호와 작성자 회원번호 불일치 또는 비로그인
            mapAjax.put("result", "wrongAccess");
        }
        
        // JSON 변환
        ObjectMapper mapper = new ObjectMapper();
        String ajaxData = mapper.writeValueAsString(mapAjax);
        
        request.setAttribute("ajaxData", ajaxData);
        
        return "/WEB-INF/views/common/ajax_view.jsp";
    }
}