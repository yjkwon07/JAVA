<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<c:set var="contextPath"  value="${pageContext.request.contextPath}"  />
<%
  request.setCharacterEncoding("UTF-8");
%> 

<!DOCTYPE html >
<html>
<head>
<meta "charset=utf-8">
<title>파일업로드 하기</title>
<script src="http://code.jquery.com/jquery-latest.js"></script>
<script>
// 파일업로드 name 값을 다르게 하는 변수 
var cnt=1;
function fn_addFile(){
	// 파일추가를 클릭하면 동적으로 파일 업로드를 추가하낟. 
	// name속성의 값으로 'file'+cnt를 설정함으로써 값을 다르게 해준다.
	$("#d_file").append("<br>"+"<input  type='file' name='file"+cnt+"' />");
	cnt++;
}
</script>
</head>
<body>
<h1>파일 업로드 하기</h1>
<!-- 파일 업로드 시 encType은 반드시 multipart/form-data로 설정해야 한다. -->
<form method="post" action="${contextPath}/upload.do" enctype="multipart/form-data">
	<label>아이디:</label>
	<!-- 텍스트 박스에 ID를 입력 받아 전송 -->
    <input type="text" name="id"><br>
	<label>이름:</label>
	<!-- 텍스트 박스에 이름을 입력 받아 전송 -->
    <input type="text" name="name"><br>
    <!-- 파일 추가를 클릭하면 동적으로 파일 업로드를 추가한다. -->
	<input type="button"  value="파일추가" onClick="fn_addFile()"/><br>
	<!-- 자바스크립트를 이용해 <div>태그안에 파일 업로드를 추가한다. -->
	<div id="d_file">
  </div>
	<input type="submit"  value="업로드"/>
</form>
</body>
</html>