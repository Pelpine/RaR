<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/psk.css" type="text/css">
<script>
    document.addEventListener('DOMContentLoaded', function() {
        var buttons = document.querySelectorAll('.submit_button');
        buttons.forEach(button => {
            button.addEventListener('click', function(event) {
                event.preventDefault(); // 기본 링크 동작 방지
                var buttonValue = this.textContent;
                var keywordField = document.getElementById('genre_keyword');
                keywordField.value = buttonValue;
                document.getElementById('genre_search_form').submit();
            });
        });
    });
</script>

<form action="booklist.do" id="genre_search_form" method="post">
    <input type="hidden" name="keyfield" value="5">
    <input type="hidden" name="key" id="genre_keyword" value="">
</form>

<div class="genre-buttons">
    <ul>
        <li><a href="#" class="submit_button">건강/취미</a></li>
        <li><a href="#" class="submit_button">경제경영</a></li>
        <li><a href="#" class="submit_button">고전</a></li>
        <li><a href="#" class="submit_button">과학</a></li>
        <li><a href="#" class="submit_button">대학교제/전문서적</a></li>
        <li><a href="#" class="submit_button">만화</a></li>
        <li><a href="#" class="submit_button">달력/기타</a></li>
        <li><a href="#" class="submit_button">사회과학</a></li>
        <li><a href="#" class="submit_button">소설/시/희곡</a></li>
        <li><a href="#" class="submit_button">수험서/자격증</a></li>
        <li><a href="#" class="submit_button">어린이</a></li>
        <li><a href="#" class="submit_button">에세이</a></li>
        <li><a href="#" class="submit_button">여행</a></li>
        <li><a href="#" class="submit_button">역사</a></li>
        <li><a href="#" class="submit_button">예술/대중문화</a></li>
        <li><a href="#" class="submit_button">요리/살림</a></li>
        <li><a href="#" class="submit_button">외국어</a></li>
        <li><a href="#" class="submit_button">유아</a></li>
        <li><a href="#" class="submit_button">인문학</a></li>
        <li><a href="#" class="submit_button">자기계발</a></li>
        <li><a href="#" class="submit_button">장르소설</a></li>
        <li><a href="#" class="submit_button">잡지</a></li>
        <li><a href="#" class="submit_button">전집/중고전집</a></li>
        <li><a href="#" class="submit_button">종교/역학</a></li>
        <li><a href="#" class="submit_button">좋은부모</a></li>
        <li><a href="#" class="submit_button">청소년</a></li>
        <li><a href="#" class="submit_button">컴퓨터/모바일</a></li>
        <li><a href="#" class="submit_button">초등학교참고서</a></li>
        <li><a href="#" class="submit_button">중학교참고서</a></li>
        <li><a href="#" class="submit_button">고등학교참고서</a></li>
        <li><a href="#" class="submit_button">고서/희귀본</a></li>
    </ul>
</div>
