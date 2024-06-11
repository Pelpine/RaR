<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet"
        href="${pageContext.request.contextPath}/css/jsy.css" type="text/css">
    <script type="text/javascript">
        window.onload = function () {
            const myForm = document.getElementById('search_form');
            //이벤트 연결
            myForm.onsubmit = function () {
                const keyword = document.getElementById('keyword');

                if (keyword.value.trim() == '') {
                    alert('검색어를 입력하세요');
                    keyword.value = '';
                    keyword.focus();
                    return false;
                }
            };
        };
    </script>
</head>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp" />
    <div class="page-main">
        
        <div class="content-main">
            <h2>자유 게시판</h2>
            <form id="search_form" action="list.do" method="get">
                <ul class="search">
                    <li>
                        <select name="keyfield">
                            <option value="1" <c:if test="${param.keyfield==1}">selected</c:if>>제목</option>
                            <option value="2" <c:if test="${param.keyfield==2}">selected</c:if>>이메일</option>
                            <option value="3" <c:if test="${param.keyfield==3}">selected</c:if>>내용</option>
                        </select>
                    </li>
                    <li><input type="search" size="50" name="keyword" id="keyword" value="${param.keyword}"></li>
                    <li><input type="submit" value="검색"></li>
                </ul>
            </form>
            <div class="list-space align-right">
                <input type="button" value="글 쓰기"
                    onclick="location.href='writeForm.do'"
                    <c:if test="${empty user_num}">disabled="disabled"</c:if>>
                <input type="button" value="목록" onclick="location.href='list.do'">
                <input type="button" value="홈으로"
                    onclick="location.href='${pageContext.request.contextPath}/main/main.do'">
            </div>
            <c:if test="${count==0}">
                <div class="result-display">표시할 게시물이 없습니다.</div>
            </c:if>
			<ul id ="ulTable">
                       <li>
                            <ul>
                                <li>No</li>
                                <li>제목</li>
                                <li>시작일</li>
                                <li>종료일</li>
                                <li>조회수</li>
                            </ul>
                        </li>
			<c:forEach var="event" items="${elist}">
			<li>
                            <ul>
                                <c:if test="${event.notice == 1}"><li class="left">공지사항</li></c:if>
                                	<li><a href="${pageContext.request.contextPath}/event/eventDetail.do?event_num=${event.event_num}">${event.name}</a></li>
                                <c:if test="${event.start_date == '2000-01-01'}">
                                	<li>.</li>
                                	<li>.</li>
                                </c:if>
                                <c:if test="${event.start_date != '2000-01-01'}">
                                	<li>${event.start_date}</li>
                                	<li>${event.end_date}</li>
                                </c:if>
                                <li>${event.hit}</li>
                            </ul>
                        </li>
			</c:forEach>
			</ul>
            <c:if test="${count>0}">
                <table class="genre-table">
                    <thead>
                        <tr>
                            <th>게시글 번호</th>
                            <th>제목</th>
                            <th>이메일</th>
                            <th>작성일</th>
                            <th>조회수</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="board" items="${list}">
                            <tr>
                                <td>${board.board_num}</td>
                                <td><a href="detail.do?board_num=${board.board_num}">${board.title}</a></td>
                                <td>${board.user_email}</td>
                                <td>${board.reg_date}</td>
                                <td>${board.hit}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                <div class="align-center">${page}</div>
            </c:if>
        </div>
    </div>
</body>
</html>

















