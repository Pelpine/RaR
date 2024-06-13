<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<form action="bookSearch.do" method="post" id="searchForm">
    <div class="search-bar">
        <div class="search-select">
            <select name="ck" id="ck">
                <option value="Keyword" <c:if test="${param.ck=='Keyword'}">selected</c:if>>제목,저자</option>
                <option value="Title" <c:if test="${param.ck=='Title'}">selected</c:if>>제목</option>
                <option value="Author" <c:if test="${param.ck=='Author'}">selected</c:if>>저자</option>
                <option value="Publisher" <c:if test="${param.ck=='Publisher'}">selected</c:if>>출판사</option>
            </select>
        </div>
        <div class="search-input">
            <input type="search" name="sheck" placeholder="검색어를 입력하세요" id="sheck" value="${param.sheck}">
            <input type="hidden" value="${param.start != null ? param.start : 1}" id="start" name="start">
            <input type="hidden" id="currentSearch" name="currentSearch" value="${param.sheck}">
            <input type="submit" value="검색" id="sk" class="submit-button">
        </div>
    </div>
    <div class="pagination">
        <button type="button" id="prev" class="pagination-button">이전 페이지</button>
        <label id="count">1</label>
        <button type="button" id="next" class="pagination-button">다음 페이지</button>
    </div>
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
        const form = document.getElementById('myForm');
        const input = document.getElementById('myInput');

        let currentPage = parseInt(startInput.value);
        count.textContent = currentPage;

        prevButton.addEventListener('click', function () {
            let currentPage = parseInt(startInput.value);
            if (currentPage <= 1) {
                alert('첫 페이지입니다.');
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

        searchForm.addEventListener('submit', function (event) {
            if (searchInput.value.trim() === '') {
                alert('검색어를 입력하세요.');
                event.preventDefault();
            }
            // 공백 검사 추가
            if (searchInput.value.includes(" ")) {
                event.preventDefault(); // 폼 제출 막기
                alert("검색어에 띄어쓰기가 포함되어 있습니다. 다시 입력해 주세요.");
                searchInput.value = ""; // 입력 필드 비우기
            }
        });

        form.addEventListener("submit", function(event) {
            var inputValue = input.value;

            if (inputValue.includes(" ")) {
                event.preventDefault(); // 폼 제출 막기
                alert("입력 값에 띄어쓰기가 포함되어 있습니다. 다시 입력해 주세요.");
                input.value = ""; // 입력 필드 비우기
            }
        });
    });
</script>
