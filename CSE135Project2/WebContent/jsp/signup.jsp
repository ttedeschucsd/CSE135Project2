<%@ page contentType="text/html; charset=utf-8" language="java"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<jsp:include page="/html/head.html" />
</head>
<body class="page-index" data-spy="scroll" data-offset="60" data-target="#toc-scroll-target">

    <jsp:include page="/jsp/header.jsp" />
    <div class="container">
        <div class="row">
            <div class="span12">
                <div class="body-content">
                    <div class="section">
                        <div class="page-header">
                            <h4>Sign Up</h4>
                        </div>
                        <div class="row">
                            <%
                            	String name = null, role = null, state = null;
                            	Integer age = null;
                            	try {
                            		name = request.getParameter("name");
                            	} catch (Exception e) {
                            		name = null;
                            	}
                            	try {
                            		role = request.getParameter("role");
                            	} catch (Exception e) {
                            		role = null;
                            	}
                            	try {
                            		age = Integer.parseInt(request.getParameter("age"));
                            	} catch (Exception e) {
                            		age = null;
                            	}
                            	try {
                            		state = request.getParameter("state");
                            	} catch (Exception e) {
                            		state = null;
                            	}
                            	if (name != null && age != null && role != null && state != null)
                                    out.println(helpers.SignupHelper.signup(name, age, role, state));
                            %>
                            <jsp:include page="/html/signup-form.html" />
                        </div>
                        <jsp:include page="/html/footer.html" />
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
