<%@page
    import="java.util.List"
    import="helpers.*"%>

<% List<CategoryWithCount> categories = CategoriesHelper.listCategories();
	AnalyticsHelper.submitQuery(request);
	
%>
<div id="dropdowns">
	<form name="query_form" action="analytics" method="post">
		<label for="rows_dropdown">Rows</label>
		<select name="rows_dropdown">
			<option value="0">--Please Select--</option>
			<option value="1">Customers</option>
			<option value="2">States</option>
		</select>
		<label for="orders_dropdown"></label>
		<select name="orders_dropdown">
			<option>Alphabetical</option>
			<option>Top-K</option>
		</select>
		<label for="categories_dropdown"></label>
		<select name="categories_dropdown">
			<option value = "0">All Categories</option>
		<%
        	for (CategoryWithCount cwc : categories) {
        %>
			<option value = "<%=cwc.getId()%>"><%=cwc.getName()%></option>
		<%} %>
		</select>
		<button type="submit">Run Query</button>
	</form>
	
</div>
<div id="table">
	<table>
		<tr>
			<td></td>
		</tr>
		
	</table>
</div>