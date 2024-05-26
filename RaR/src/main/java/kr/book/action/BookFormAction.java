package kr.book.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import kr.controller.Action;

public class BookFormAction implements Action {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 필요한 로직을 여기에 작성합니다.
        // 예를 들어, 폼 데이터를 처리하고 적절한 뷰를 반환합니다.

        return "/WEB-INF/views/book/book.jsp"; // 뷰 경로를 반환합니다.
    }
}
