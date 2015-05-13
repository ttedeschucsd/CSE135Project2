<div class="navbar navbar-default">
    <div class="navbar-inner">
        <div class="container">
            <div class="row">
                <div class="span12">
                    <p class="nav pull-right">
                        <%
                        	if (session.getAttribute("name") != null) {
                        %>
                        <b> Hello <%
                        	out.println(session.getAttribute("name"));
                        %>
                        &nbsp | &nbsp </b> <a href="purchase">Buy Shopping Cart</a>
                        <%
                        	}
                        %>
                    </p>

                    <h2>CSE 135 Project</h2>
                </div>
            </div>
            <!--/.nav-collapse -->
        </div>
    </div>
</div>