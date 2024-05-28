package kr.book.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import kr.controller.Action;

public class BookFormAction implements Action {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        return "/WEB-INF/views/book/book.jsp"; // 뷰 경로를 반환합니다.
    }
}
