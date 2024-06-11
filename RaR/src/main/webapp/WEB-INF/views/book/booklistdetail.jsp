<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>상품 상세</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/yhl.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/booksdetail.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/cart_item_insert.js"></script>
</head>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<div class="page-main-top">
    <div class="book-img">
        <img alt="${book.bk_name}" src="${book.bk_img}">
    </div>
    <div class="book-details">
        <div class="book-title">제목 : ${book.bk_name}</div>
        <div>저자 : ${book.bk_writer}</div>
        <div>장르 : ${book.bk_genre}</div>
        <div id="formatted-date-container">출고일 : <span id="formatted-date"></span></div>
        <div>출판사 : ${book.bk_publisher}</div>
        <div>정가 : ${book.bk_price}</div>
        <div>최저가 : ${book.itemVO.item_price}</div>
        <div>설명 : ${book.bk_description}</div>
    </div>
</div>
<script>
    // 서버에서 전달된 문자열로 된 날짜 값
    var stringDate = "${book.bk_pubdate}";

    // 문자열 값을 Date 객체로 변환하기
    var date = new Date(stringDate);

    // 요일 배열 정의 (일요일부터 시작)
    var days = ['일요일', '월요일', '화요일', '수요일', '목요일', '금요일', '토요일'];

    // 월 배열 정의 (1월부터 시작)
    var months = ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'];

    // 날짜를 원하는 형식으로 변환하기
    var year = date.getFullYear();
    var month = months[date.getMonth()];
    var day = date.getDate();
    var dayOfWeek = days[date.getDay()];

    var formattedDate = year + '년 ' + month + ' ' + day + '일 ' + dayOfWeek;

    // 변환된 날짜 값을 HTML에 출력하기
    document.getElementById('formatted-date').textContent = formattedDate;
</script>
<jsp:include page="/WEB-INF/views/book/booklistdetail_include.jsp"/>
<hr width="100%" noshade="noshade">
</body>
<footer>
    <jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</footer>
</html>

