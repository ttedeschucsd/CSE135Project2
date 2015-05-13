<%@ page contentType="text/html; charset=utf-8" language="java"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<jsp:include page="/html/head.html" />
</head>
<body class="page-index" data-spy="scroll" data-offset="60" data-target="#toc-scroll-target">
    <%
    	String name = null;
    	try {
    		name = request.getParameter("name");
    	} catch (Exception e) {
    		name = null;
    	}
    	if (name != null)
    		out.println(helpers.IndexHelper.login(name, session));
    %>
    <jsp:include page="/jsp/header.jsp" />
    <div class="container">
        <div class="row">
            <div class="span12">
                <div class="body-content">
                    <div class="section">
                        <div class="page-header">
                            <h4>Home</h4>
                        </div>
                        <div class="row">
                            <%
                            	if (session.getAttribute("name") != null) {
                            %>
                            <%
                            	if (((String)session.getAttribute("role")).equalsIgnoreCase("owner")) {
                            %>
                            <div class="container">
                            <jsp:include page="/jsp/owner-menu.jsp" />
                            </div>
                            <%
                            	} else {
                            %>
                            <div class="container">
                            <jsp:include page="/jsp/customer-menu.jsp" />
                            </div>
                            <%
                            	}
                            %>
                            <%
                            	} else {
                            %>
                            <div class="alert alert-info">
                                You need to be logged into see this page. Want to <a href="login">login</a> or <a href="signup">signup</a>?
                            </div>
                            <%
                            	}
                            %>
                        </div>
                        <jsp:include page="/html/footer.html" />
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
