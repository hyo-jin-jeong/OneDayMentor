<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ page import="user.UserDAO" %> <!-- userdao�� Ŭ���� ������ -->
<%@ page import="java.io.PrintWriter" %> <!-- �ڹ� Ŭ���� ��� -->
<% request.setCharacterEncoding("UTF-8"); %>

<jsp:useBean id="user" class="user.User" scope="page" />
<jsp:setProperty name="user" property="userID" />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JSP �Խ��� ������Ʈ</title>
</head>
<body>
      <%
      String userID = null;
      if(session.getAttribute("userID") != null){
         userID = (String) session.getAttribute("userID");
      }
      
      UserDAO userDAO = new UserDAO(); //�ν��Ͻ�����
      int result = userDAO.leave(userID);
      if(result == -1){
         PrintWriter script = response.getWriter();
         script.println("<script>");
         script.println("alert('Ż�� ����')");
         script.println("history.back()");
         script.println("</script>");
      }
      else{
         PrintWriter script = response.getWriter();
         script.println("<script>");
         script.println("alert('���������� Ż�� ����')");
         session.invalidate();
         script.println("location.href = 'main.jsp'");
         script.println("</script>");
      }
      %>
</body>
</html>