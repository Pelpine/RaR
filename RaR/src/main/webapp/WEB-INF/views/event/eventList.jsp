<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>이벤트 목록</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/eventList.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/footer.css" type="text/css">
</head>
<body>
<div class="page-main">
    <div class="content-main">
        <jsp:include page="/WEB-INF/views/common/header.jsp"/>
        <div id="mainWrapper">
            <ul>
                <!-- 검색 폼 영역 -->
               <li id="liSearchOption">
   					 <form id="search_form" action="eventList.do" method="get">
        				<ul class="search">
          					 <li>
             				   <select name="keyfield">
                    				<option value="1" <c:if test="${param.keyfield== 1}">selected</c:if>>제목</option>
                   					 <option value="2" <c:if test="${param.keyfield== 2}">selected</c:if>>내용</option>
               				   </select>
           					 </li>
            				 <li>
               					 <input type="search" size="16" name="keyword" id="keyword" value="${param.keyword}">
            				</li>
           					<li>
                				<input type="submit" value="검색">
            				</li>
				            <li class="block">
				                <label for="underway_btn">현재 진행중인 이벤트만 보기</label>
				                <input type="checkbox" name="underway" id="underway_btn">            
				            </li>
			</ul>
    </form>
</li>

				
                <!-- 게시판 목록  -->
                <c:if test="${count == 0 }">
                	<li>
                    <div class="result-display">
                        표시할 게시글이 없습니다.
                    </div>
                    </li>
                </c:if>
                <c:if test="${count > 0 }">
                <li>
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
                        <!-- 게시물이 출력될 영역 -->
                        <c:forEach var="event" items="${list}">
                        <li>
                            <ul>
                                <c:if test="${event.notice == 1}"><li class="left">공지사항</li></c:if>
                                <c:if test="${event.notice == 0}"><li class="left">${event.event_num}</li></c:if>
                                	<li><a href="eventDetail.do?event_num=${event.event_num}">${event.name}</a></li>
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
                </li>
                <li class="lastli">
                    <div class="list-space align-right">
                        <input type="button" value="이벤트 등록" onclick="location.href='insertEventForm.do'" 
                        <c:if test="${user_auth != 9 || empty user_num}">style="display: none;"</c:if>>            
                        <input type="button" value="홈으로" onclick="location.href='${pageContext.request.contextPath}/main/main.do'">
                    </div>
                </li>
                <!-- 게시판 페이징 영역 -->
                <li>
                    <div id="divPaging">
                        ${page}
                    </div>
                </li>
                </c:if>
            </ul>
        </div>
    </div>
</div>    
<footer>
    <jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</footer>
</body>
</html>
