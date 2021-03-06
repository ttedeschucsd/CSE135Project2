<%@page
    import="java.util.List"
    import="helpers.*"%>
    <script type="text/JavaScript" src="js/list-analytics.js"></script>
    
    <style>
		table{
    		border: 1px solid black;
 		}
 		
 		td{
 			border: 1px solid black;
 			text-align: center;
 			padding: 10px;
 		} 
	</style> 
    

<% List<CategoryWithCount> categories = CategoriesHelper.listCategories();
	AnalyticsHelper analyzer = new AnalyticsHelper(request);
	TableHelper itemTable = analyzer.submitQuery(request);
%>
<% if(analyzer.tempRow == null && analyzer.tempCol == null) {%>
<div id="dropdowns">
	<form name="query_form" action="analytics" method="post">
		<label for="rows_dropdown">Rows</label>
		<select name="rows_dropdown">
			<% if(analyzer.rowsItem != null){ %>
				<option value="0">--Please Select--</option>
				<option value="1" <%=(analyzer.rowsItem.equals("1")) ? "selected" : ""%>>Customers</option>
				<option value="2" <%=(analyzer.rowsItem.equals("2")) ? "selected" : ""%>>States</option>
			<%} else { %>
				<option value="0">--Please Select--</option>
				<option value="1" >Customers</option>
				<option value="2" >States</option>
			<% }%>
		</select>
		<label for="orders_dropdown"></label>
		<select name="orders_dropdown">
			<% if(analyzer.orderingItem != null){ %>
				<option value="1" <%=(analyzer.orderingItem.equals("1")) ? "selected" : ""%>>Alphabetical</option>
				<option value="2" <%=(analyzer.orderingItem.equals("2")) ? "selected" : ""%>>Top-K</option>
			<% } else { %>
				<option value="1">Alphabetical</option>
				<option value="2">Top-K</option>
			<% } %>
		</select>
		<label for="categories_dropdown"></label>
		<select name="categories_dropdown">
			<option value = "0">All Categories</option>
		<%
        	for (CategoryWithCount cwc : categories) {
        %>
	        <% if( analyzer.categoriesItem != null){ %>
				<option value = "<%=cwc.getId()%>" <%=(analyzer.categoriesItem.equals(Integer.toString(cwc.getId()))) ? "selected" : ""%>><%=cwc.getName()%></option>
			<% } else { %>
				<option value = "<%=cwc.getId()%>"><%=cwc.getName()%></option>
			<% } %>
		<% } %>
		</select>
		<button type="submit">Run Query</button>
	</form>
<% } %>
	
</div>
<div id="table">
<%if (itemTable != null){ %>
	<table>
		<tr>
			<td>     </td>
			<% 
				for(Header col : itemTable.colHeaders){ 
			%>
				<td><b><%= (col.name.length() < 10) ? col.name : col.name.substring(0,9) %></b> (<%= col.total%>)</td>
			<% } %>
		</tr>
		<% 
			int size = itemTable.colHeaders.size()+1;
			for(Header row : itemTable.rowHeaders){ 
		%>
			<tr>
				<td><b><%= row.name %></b> (<%= row.total %>)</td>
				<%
					for(int i=1; i<size; i++){
				%>
					<td><%= itemTable.itemTotals[row.id][i] %></td>
				<% } %>
			</tr>
		<% } %>
	</table>
<% } %>
	<br />
	<% if(itemTable == null){ %>
		<form action="analytics">
			<input type="hidden" id="row" value="<%= analyzer.limitRowEnd%>">
			<input type="hidden" id="col" value="<%= analyzer.limitColEnd%>">
			<input type="hidden" id="rows_dropdown" value="<%=analyzer.rowsItem %>">
			<input type="hidden" id="categories_dropdown" value="<%=analyzer.categoriesItem %>">
			<input type="hidden" id="orders_dropdown" value="<%=analyzer.orderingItem %>">
			<button id="next_20">Next 20 VV</button>
		</form>
	<% } else if(itemTable.rowHeaders.size()<20) { %>
	<%} else{ %>
	<form action="analytics">
		<input type="hidden" id="row" value="<%= analyzer.limitRowEnd%>">
		<input type="hidden" id="col" value="<%= analyzer.limitColEnd%>">
		<input type="hidden" id="rows_dropdown" value="<%=analyzer.rowsItem %>">
		<input type="hidden" id="categories_dropdown" value="<%=analyzer.categoriesItem %>">
		<input type="hidden" id="orders_dropdown" value="<%=analyzer.orderingItem %>">
		<button id="next_20">Next 20 VV</button>
	</form>
	<% } %>
	<form action = "analytics">
		<button id="next_10">Next 10 >></button>
		<input type="hidden" id="row" value="<%= analyzer.limitRowEnd%>">
		<input type="hidden" id="col" value="<%= analyzer.limitColEnd%>">
	</form>
</div>