<%@page
    import="java.util.List"
    import="helpers.*"%>
    <script type="text/JavaScript" src="js/list-analytics.js"></script> 
    

<% List<CategoryWithCount> categories = CategoriesHelper.listCategories();
	AnalyticsHelper analyzer = new AnalyticsHelper();
	TableHelper tableHelper = analyzer.submitQuery(request);
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
			<option value="1">Alphabetical</option>
			<option value="2">Top-K</option>
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
			<td>Blank Cell</td>
			<td>Headers for items here($total price)</td>
		</tr>
		<tr>
			<td>First item in row always customer or state ($total amount)</td>
		</tr>
	</table>
	<br />
	<form>
		<button id="next_20">Next 20 VV</button>
	</form>
	<form>
		<button id="next_10">Next 10 >></button>
	</form>
</div>