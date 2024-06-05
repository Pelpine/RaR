<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script>
    document.addEventListener('DOMContentLoaded', function() {
        var buttons = document.querySelectorAll('.submit_button');
        buttons.forEach(button => {
            button.addEventListener('click', function() {
                var buttonValue = this.value;
                var keywordField = document.getElementById('genre_keyword');
                keywordField.value = buttonValue;
                document.getElementById('genre_search_form').submit();
                searchField.value = ''; 
            });
        });
    });
</script>

<form action="booklist.do" id="genre_search_form" method="post">
    <input type="hidden" name="keyfield" value="5">
    <input type="hidden" name="key" id="genre_keyword" value="">
    
    <div class="genre-buttons">
        <input type="button" value="건강/취미" class="submit_button"> 
        <input type="button" value="경제경영" class="submit_button">
        <input type="button" value="고전" class="submit_button">
        <input type="button" value="과학" class="submit_button">
        <input type="button" value="대학교제/전문서적" class="submit_button">
        <input type="button" value="만화" class="submit_button">
        <input type="button" value="달력/기타" class="submit_button">
        <input type="button" value="사회과학" class="submit_button">
        <input type="button" value="소설/시/희곡" class="submit_button">
        <input type="button" value="수험서/자격증" class="submit_button">
        <input type="button" value="어린이" class="submit_button">
        <input type="button" value="에세이" class="submit_button">
        <input type="button" value="여행" class="submit_button">
        <input type="button" value="역사" class="submit_button">
        <input type="button" value="예술/대중문화" class="submit_button">
        <input type="button" value="요리/살림" class="submit_button">
        <input type="button" value="외국어" class="submit_button">
        <input type="button" value="유아" class="submit_button">
        <input type="button" value="인문학" class="submit_button">
        <input type="button" value="자기계발" class="submit_button">
        <input type="button" value="장르소설" class="submit_button">
        <input type="button" value="잡지" class="submit_button">
        <input type="button" value="전집/중고전집" class="submit_button">
        <input type="button" value="종교/역학" class="submit_button">
        <input type="button" value="좋은부모" class="submit_button">
        <input type="button" value="청소년" class="submit_button">
        <input type="button" value="컴퓨터/모바일" class="submit_button">
        <input type="button" value="초등학교참고서" class="submit_button">
        <input type="button" value="중학교참고서" class="submit_button">
        <input type="button" value="고등학교참고서" class="submit_button">
        <input type="button" value="고서/희귀본" class="submit_button">
    </div>
</form>
