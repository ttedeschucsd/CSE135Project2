<%@page
    import="java.util.List"
    import="helpers.*"%>
<%
	List<CategoryWithCount> categories = CategoriesHelper
			.listCategories();
%>

<div class="panel panel-default">
    <div class="panel-body">
        Filter By Category :
        <div class="bottom-nav">
            <ul class="nav nav-list">
                <%
                	String search = request.getParameter("search");
                	search = (search != null) ? search : "";
                	String actionName = request.getParameter("actionName");
                	actionName = (actionName != null) ? actionName : "products";
                %>
                <li>
                    <form
                        action="<%=actionName%>"
                        method="post">
                        <input
                            type="text"
                            name="search"
                            id="search"
                            value="<%=search%>"
                            style="display: none"> <input
                            type="text"
                            name="category"
                            id="category"
                            value="-1"
                            style="display: none"> <input
                            type="submit"
                            value="all">
                    </form>
                </li>
                <%
                	for (CategoryWithCount cwc : categories) {
                %>
                <li>
                    <form
                        action="<%=actionName%>"
                        method="post">
                        <input
                            type="text"
                            name="search"
                            id="search"
                            value="<%=search%>"
                            style="display: none"> <input
                            type="text"
                            name="category"
                            id="category"
                            value="<%=cwc.getId()%>"
                            style="display: none"> <input
                            type="submit"
                            value="<%=cwc.getName()%>">
                    </form>
                </li>
                <%
                	}
                %>
            </ul>
        </div>
    </div>
</div>