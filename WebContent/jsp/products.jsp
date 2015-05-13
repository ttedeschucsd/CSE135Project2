<%@ page contentType="text/html; charset=utf-8" language="java"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<jsp:include page="/html/head.html" />
</head>
<%
	boolean nameNotNull = session.getAttribute("name") != null;
	String role = (String) session.getAttribute("role");
	boolean roleIsOwner = (role != null) ? role
			.equalsIgnoreCase("owner") : false;
%>
<body class="page-index" data-spy="scroll" data-offset="60" data-target="#toc-scroll-target">
    <jsp:include page="/jsp/header.jsp" />
    <div class="container">
        <div class="row">
            <div class="span12">
                <div class="body-content">
                    <div class="section">
                        <div class="row">
                            <div class="col-md-3">
                                <%
                                	if (nameNotNull) {
                                %>
                                <%
                                	if (roleIsOwner) {
                                %>
                                <jsp:include page="/jsp/owner-menu.jsp" />
                                <jsp:include page="/jsp/sidebar-categories.jsp">
                                    <jsp:param value="products" name="actionName" />
                                </jsp:include>
                                <%
                                	} else {
                                %>
                                <jsp:include page="/jsp/customer-menu.jsp" />
                                <%
                                	}
                                %>
                                <%
                                	}
                                %>
                            </div>
                            <div class="col-md-9">
                                <div class="page-header">
                                    <h3>Products</h3>
                                </div>
                                <%
                                	if (nameNotNull && roleIsOwner) {
                                %>
                                <jsp:include page="/jsp/list-products.jsp" />
                                <%
                                	} else {
                                %>
                                <div class="alert alert-info">
                                    You need to be logged as an owner to see this page. Want to <a href="login">login</a>?
                                </div>
                                <%
                                	}
                                %>
                            </div>
                        </div>
                        <jsp:include page="/html/footer.html" />
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
