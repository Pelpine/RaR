<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>책 등록 요청</title>
<script type="text/javascript">
function showPopup() { window.open("/RaR/book/bookForm.do", "a", "width=400, height=300, left=100, top=50");}
function updateParent(bk_name, author, pubDate, cover, categoryName, price, publisher, isbn, description) {
    document.getElementById("bk_name").value = bk_name;
    document.getElementById("author").value = author;
    document.getElementById("pubdate").value = pubDate;
    document.getElementById("cover").src = cover;
    document.getElementById("coverUrl").value = cover;
    document.getElementById("categoryname").value = categoryName;
    document.getElementById("price").value = price;
    document.getElementById("publisher").value = publisher;
    document.getElementById("isbn").value = isbn;
    document.getElementById("description").value = description;
}
</script>
<script type="text/javascript">
window.onload = function() {
    // 이미지 미리 보기
    let photo_path = $('.my-photo').attr('src');

    $('#photo').change(function() {
        let my_photo = this.files[0];
        if (!my_photo) {
            // 선택을 취소하면 원래 화면으로 되돌림
            $('.my-photo').attr('src', photo_path);
            return;
        }
        if (my_photo.size > 1024 * 1024) {
            alert(Math.round(my_photo.size / 1024) + 'kbytes(1024kbytes까지만 업로드 가능)');
            $('.my-photo').attr('src', photo_path); // 원래 사진으로 변경
            $(this).val(''); // 선택한 파일 정보 지우기
            return;
        }
        // 화면에 이미지 미리 보기
        const reader = new FileReader();
        reader.readAsDataURL(my_photo);

        reader.onload = function() {
            $('.my-photo').attr('src', reader.result);
        };
    });

    // 이미지 미리 보기 취소
    $('#photo_reset').click(function() {
        // 초기 이미지 표시
        $('.my-photo').attr('src', photo_path);
        $('#photo').val('');
    });

    // 폼 제출
    $('#test').submit(function(event) {
        event.preventDefault(); // 기본 제출 이벤트를 방지
        const form_data = new FormData(this);
        // 폼 데이터를 전송하거나 다른 작업 수행
    });
}
</script>
</head>
<body>
<div>
	<h4>책 등록 요청</h4>
	<form action="bookupdates.do" method="post" name="test" enctype="multipart/form-data" id="test">
		<ul>
			<li>
				<img src="${books.cover}" id="cover">
				<input type="hidden" value="${books.cover}" name="cover" id="coverUrl">
			</li>
			<li>
				<label for="bk_name">책 이름</label>
				<input type="button" value="책목록" onclick="showPopup();" />
				<input type="text" id="bk_name" name="bk_name" value="${books.bk_name}" readonly>
			</li>
			<li>
				<label for="author">저자</label>
				<input type="text" id="author" name="author" value="${books.author}" readonly>
			</li>
			<li>
				<label for="pubdate">출간일</label>
				<input type="text" id="pubdate" name="pubdate" value="${books.pubDate}" readonly>
			</li>
			<li>
				<label for="price">정가</label>
				<input type="number" id="price" name="price" value="${books.price}" readonly>
			</li>
			<li>
				<label for="categoryname">장르</label>
				<input type="text" id="categoryname" name="categoryname" value="${books.categoryName}" readonly>
			</li>
			<li>
				<label for="publisher">출판사</label>
				<input type="text" id="publisher" name="publisher" value="${books.publisher}" readonly>
			</li>
			<li>
				<label for="item_grade">상품상태</label>
				<select name="item_grade">
					<option value="1" <c:if test="${books.item_grade==1}">selected</c:if>>좋음</option>
					<option value="2" <c:if test="${books.item_grade==2}">selected</c:if>>보통</option>
					<option value="3" <c:if test="${books.item_grade==3}">selected</c:if>>좋지않음</option>
				</select>
			</li>
				<li>
				<c:if test="${empty books.photo}">
				</c:if>
				<c:if test="${!empty books.photo}">
				<img src="${pageContext.request.contextPath}/upload/${books.photo}" width="200" height="200" class="my-photo">
				</c:if>
				<label for="photo">상품사진</label>
				<input type="file" name="photo" class="input-check" id="photo" accept="image/gif,image/png,image/jpeg">
				<input type="button" value="취소" id="photo_reset">
			</li>
			<li>
				<label for="comment">코맨트</label>
				<textarea rows="5" cols="30" id="comment" name="comment">${books.ad_comment}</textarea>
			</li>
			<li>
				<label for="private_num">공개 비공개 설정</label>
				<input type="radio" name="private_num" value="1" id="private_num1" <c:if test="${books.private_num==1}">checked</c:if>>공개
				<input type="radio" name="private_num" value="2" id="private_num2" <c:if test="${books.private_num==2}">checked</c:if>>비공개
			</li>
			<li>
				<div>설명</div>
				<input type="text" id="description" name="description" value="${books.description}" readonly>
			</li>
		</ul>
		<input type="hidden" value="${books.isbn}" name="isbn" id="isbn">
		<input type="hidden" value="${books.approval_id}" name="approval_id" id="approval_id">
		<input type="hidden" value="1" name="status">
		<input type="submit" value="수정">
		<input type="button" value="취소" onclick="location.href='${pageContext.request.contextPath}/main/main.do'">
	</form>
</div>
</body>
</html>
