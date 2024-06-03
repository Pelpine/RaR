<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title></title>
</head>
<body>
<script type="text/javascript">
window.onload = function() {
    var item_img = "${pageContext.request.getParameter('item_img')}";
    updateParent(item_img);
};
</script>

<img alt="item image" src="${pageContext.request.contextPath}/upload/${item_img}" width="100%">

</body>
</html>