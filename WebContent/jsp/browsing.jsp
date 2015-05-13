<%@ page contentType="text/html; charset=utf-8" language="java"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<jsp:include page="/html/head.html" />
</head>
<%
    boolean nameNotNull = session.getAttribute("name") != null;
    String role = (String) session.getAttribute("role");
    boolean roleIsCustomer = (role != null) ? role
            .equalsIgnoreCase("customer") : false;
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
                                    if (roleIsCustomer) {
                                %>
                                <jsp:include page="/jsp/customer-menu.jsp" />
                                <%
                                    } else {
                                %>
                                <jsp:include page="/jsp/owner-menu.jsp" />
                                <%
                                    }
                                %>
                                <jsp:include page="/jsp/sidebar-categories.jsp">
                                    <jsp:param value="browse" name="actionName" />
                                </jsp:include>
                                <%
                                    }
                                %>
                            </div>
                            <div class="col-md-9">
                                <div class="page-header">
                                    <h3>Product Browsing</h3>
                                </div>
                                <%
                                    if (nameNotNull) {
                                %>
                                <jsp:include page="/jsp/list-browsing.jsp" />
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
