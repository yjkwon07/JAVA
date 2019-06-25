<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"  isELIgnored="false"
 %>
 <!-- 자바의 import문처럼 타일즈를 사용하기 위해 반드시 추가해야 한다. -->
 <%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8">
    <style>
      #container {
        width: 100%;
        margin: 0px auto;
          text-align:center;
        border: 0px solid #bcbcbc;
      }
      #header {
        padding: 5px;
        margin-bottom: 5px;
        border: 0px solid #bcbcbc;
         background-color: lightgreen;
      }
      #sidebar-left {
        width: 15%;
        height:700px;
        padding: 5px;
        margin-right: 5px;
        margin-bottom: 5px;
        float: left;
         background-color: yellow;
        border: 0px solid #bcbcbc;
        font-size:10px;
      }
      #content {
        width: 75%;
        padding: 5px;
        margin-right: 5px;
        float: left;
        border: 0px solid #bcbcbc;
      }
      #footer {
        clear: both;
        padding: 5px;
        border: 0px solid #bcbcbc;
         background-color: lightblue;
      }
      
    </style>
    <!-- 
    	tiels_member.xml의 <definition>의 하위 태그인 
    	<put-attribute>태그의 name이 title인 값(value)을 표시 
    -->
    <title><tiles:insertAttribute name="title" /></title>
  </head>
    <body>
    <div id="container">
      <div id="header">
    <!-- 
    	tiels_member.xml의 <definition>의 하위 태그인 
    	<put-attribute>태그의 name이 header인 JSP를 표시.
    -->
         <tiles:insertAttribute name="header"/>
      </div>
      <div id="sidebar-left">
    <!-- 
    	tiels_member.xml의 <definition>의 하위 태그인 
    	<put-attribute>태그의 name이 side인 JSP를 표시.
    -->      
          <tiles:insertAttribute name="side"/> 
      </div>
      <div id="content">
    <!-- 
    	tiels_member.xml의 <definition>의 하위 태그인 
    	<put-attribute>태그의 name이 body인 JSP를 표시.
    -->          
          <tiles:insertAttribute name="body"/>
      </div>
      <div id="footer">
    <!-- 
    	tiels_member.xml의 <definition>의 하위 태그인 
    	<put-attribute>태그의 name이 footer인 JSP를 표시.
    -->          
          <tiles:insertAttribute name="footer"/>
      </div>
    </div>
  </body>
</html>