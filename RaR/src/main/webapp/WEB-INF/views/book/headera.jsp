<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<form action="bookSearch.do" method="post" id="searchForm">
    <input type="search" name="sheck" placeholder="검색어를 입력하세요" id="sheck" value="${param.sheck}">
    <input type="hidden" value="${param.start != null ? param.start : 1}" id="start" name="start">
    <input type="hidden" id="currentSearch" name="currentSearch" value="${param.sheck}">
    <input type="submit" value="검색" id="sk">
    <br>
    <button type="button" id="prev">이전 페이지</button>
    <div id="count"></div>
    <button type="button" id="next">다음 페이지</button>
</form>
<script type="text/javascript">
    document.addEventListener('DOMContentLoaded', function () {
        const startInput = document.getElementById('start');
        const searchForm = document.getElementById('searchForm');
        const prevButton = document.getElementById('prev');
        const nextButton = document.getElementById('next');
        const searchInput = document.getElementById('sheck');
        const currentSearchInput = document.getElementById('currentSearch');
        const count = document.getElementById('count');
        count.text() = 1;
        prevButton.addEventListener('click', function () {
            let currentPage = parseInt(startInput.value);
            if (currentPage <= 1) {
                alert('첫 페이지 입니다.');
                return;
            }
            currentPage -= 1;
            count.textContent = currentPage;
            startInput.value = currentPage;
            currentSearchInput.value = searchInput.value;
            searchForm.submit();
        });

        nextButton.addEventListener('click', function () {
            let currentPage = parseInt(startInput.value);
            currentPage += 1;
            count.textContent = currentPage;
            startInput.value = currentPage;
            currentSearchInput.value = searchInput.value;
            searchForm.submit();
        });

        searchForm.addEventListener('submit', function () {
            if (searchInput.value.trim() === '') {
                alert('검색어를 입력하세요.');
                return false;
            }
            return true;
        });
    });
</script>

