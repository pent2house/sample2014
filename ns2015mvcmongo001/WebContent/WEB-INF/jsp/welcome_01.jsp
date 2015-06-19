<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ page session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page import="java.util.List"%>
<%@ page import="com.ns.spring.model.RMA_HDR"%>

<html>
<head>
	<title>welcome_01 JSP</title>
	<style type="text/css">
		.tg  {border-collapse:collapse;border-spacing:0;border-color:#ccc;}
		.tg td{font-family:Arial, sans-serif;font-size:14px;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;border-color:#ccc;color:#333;background-color:#fff;}
		.tg th{font-family:Arial, sans-serif;font-size:14px;font-weight:normal;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;border-color:#ccc;color:#333;background-color:#f0f0f0;}
		.tg .tg-4eph{background-color:#f9f9f9}
	</style>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"> 
	<meta name="robots" content="noindex,nofollow"/> 
</head>
<body bgcolor="#74D78E">${message}
	<br>
	<br>
  <!--<form action="/ns2015mvcmongo001/welcome_01.html" method="post">-->
  <form action="${pageContext.request.contextPath}/welcome_01.html" method="post">

	<div align='center'
		style="font-family: verdana; padding: 10px; border-radius: 10px; font-size: 12px;">
		<table style="width:70%" border="0" cellspacing="0" cellspan="0" class="tg">
			  <tr>
			    <td align="center">
					<%
						List<RMA_HDR> rmaAll = (List<RMA_HDR>) request.getAttribute("rmaList");
						String idStr = "";
						String rmaNumStr = "";
						String rmaHdrStsCdStr = "";
					%>
					<table style="width:100%" border="1" cellspacing="0" cellspan="0">
					  <tr bgcolor="gray">
					    <td align="center" style="font-family:verdana" width="20%">ID</td>
					    <td align="center" style="font-family:verdana" width="40%">RMA number</td>
					    <td align="center" style="font-family:verdana" width="40%">Status</td>
					  </tr>
					</table>
					<table style="width:100%" border="1" cellspacing="0" cellspan="0" class="tg">
					<%
						if( rmaAll != null && rmaAll.size() > 0 ) {
							for (RMA_HDR rma : rmaAll) {
								int ts = rma.getTimestamp();
								pageContext.setAttribute("id", Integer.toString(rma.getTimestamp()) );
								pageContext.setAttribute("rmaNum", rma.getRma_num() );
								pageContext.setAttribute("rmaHdrStsCd", rma.getRma_hdr_sts_cd() );

								idStr =(String)pageContext.getAttribute("id");
								rmaNumStr =(String)pageContext.getAttribute("rmaNum");
								rmaHdrStsCdStr =(String)pageContext.getAttribute("rmaHdrStsCd");
					%>
						  <tr bgcolor="white">
						    <td style="font-family:verdana" width="20%">
						    	<a href="<c:url value='/authorize/${rmaNum}' />" ><%=idStr%></a>
						    </td>
						    <td style="font-family:verdana" width="40%"><%=rmaNumStr%></td>
						    <td style="font-family:verdana" width="40%"><%=rmaHdrStsCdStr%></td>
						  </tr>
					<%
							} // for
						} // if
					%>
					</table>
			    </td>
			  </tr>
		</table>
		<br><br>
		<input type="submit" value="WebService" name="webService" />
		<input type="submit" value="Cancel" name="cancel" />
	</div>
  </form>

</body>
</html>