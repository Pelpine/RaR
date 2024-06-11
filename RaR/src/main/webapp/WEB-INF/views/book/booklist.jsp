<%@page import="kr.rar.dao.ItemDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>책 목록</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/psk.css" type="text/css">
</head>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp" />
    <div class="container1">
        <div class="sidebar1">
            <div class="genre-buttons1">
                <jsp:include page="/WEB-INF/views/book/genre.jsp" />
            </div>
        </div>
        <div class="main-content1">
            <div class="content-main1">
            <h4>도서</h4>
                <c:if test="${count == 0}">
                    <div class="result-display1">표시할 게시물이 없습니다.</div>
                    <ul class="search1">
                        <li><select name="keyfield">
                                <option value="1" <c:if test="${param.keyfield==1}">selected</c:if>>책이름</option>
                                <option value="2" <c:if test="${param.keyfield==2}">selected</c:if>>작가</option>
                                <option value="3" <c:if test="${param.keyfield==3}">selected</c:if>>출판사</option>
                        </select></li>
                        <li><input type="search" size="16" name="keyword" id="keyword" value="${param.keyword}"></li>
                        <li><input type="submit" value="검색"></li>
                    </ul>
                </c:if>
                <c:if test="${count > 0 }">
                    <div class="book-list1">
                        <c:forEach var="book" items="${list}">
                            <div class="book-item1">
                                <div class="book-img1">
                                    <a href="booksdetail.do?bk_num=${book.bk_num}"><img src="${book.bk_img}" alt="${book.bk_name}"></a>
                                </div>
                                <div class="book-details1">
                                    <div class="book-name1">
                                        <a href="booksdetail.do?bk_num=${book.bk_num}">${book.bk_name}</a>
                                    </div>
                                    <div class="book-writer1">${book.bk_writer}</div>
                                    <div class="book-publisher1">${book.bk_publisher}</div>
                                    <div class="book-count1">현재 판매 매수: ${book.bk_count}</div>
                                </div>
                                <div class="book-price1">
                                    <div class="original-price1">원가: ${book.bk_price} 원</div>
                                    <div class="discount-price1">최저가: ${book.bk_minprice} 원</div>
                                    <input type="button" value="구매바로가기" onclick="location.href='booksdetail.do?bk_num=${book.bk_num}#to'" class="list-space2" <c:if test="${book.bk_count <= 0}">disabled</c:if>>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                    <div class="list-space1 buttons-right1">
                        <input type="button" value="등록요청목록" onclick="location.href='${pageContext.request.contextPath}/book/list.do'"> 
                        <input type="button" value="목록" onclick="location.href='${pageContext.request.contextPath}/book/bookslist.do'"> 
                        <input type="button" value="홈으로" onclick="location.href='${pageContext.request.contextPath}/main/main.do'">
                    </div>
                    <form action="booklist.do" id="search_form1" method="post">
                        <ul class="search1">
                            <li><select name="keyfield">
                                    <option value="1" <c:if test="${param.keyfield==1}">selected</c:if>>책이름</option>
                                    <option value="2" <c:if test="${param.keyfield==2}">selected</c:if>>작가</option>
                                    <option value="3" <c:if test="${param.keyfield==3}">selected</c:if>>출판사</option>
                            </select></li>
                            <li><input type="search" size="16" name="keyword" id="keyword" value="${param.keyword}"></li>
                            <li><input type="submit" value="검색"></li>
                        </ul>
                    </form>
                    <div class="align-center1">${page}</div>
                </c:if>
            </div>
        </div>
    </div>
<hr width="100%" noshade="noshade">
</body>
<footer>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</footer>
</html>
