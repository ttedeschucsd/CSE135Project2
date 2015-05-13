<!-- You will see on this form a CSS construct called a modal. Don't let
this scare you, this is just some pretty presentation. They allow you to create
boxes that appear when clicking on a button. You do not have to use them if you
don't want to. -->
<%@page import="java.util.List" import="helpers.*"%>
<%=ProductsHelper.modifyProducts(request)%>
<form action="browse">
    <%
    String search = request.getParameter("search");
    search = (search != null)? search : "";
    String category = request.getParameter("category");
    category = (category != null) ? category : "";
    %>
    <input type="text" name="category" id="category" value="<%=category%>" style="display: none" /> <input
        type="text" name="search" id="search" value="<%=search%>" size="40" /> <input type="submit" value="search">
</form>
<%
	List<ProductWithCategoryName> products = ProductsHelper
			.listProducts(request);
	List<CategoryWithCount> categories = CategoriesHelper
			.listCategories();
%>
<table class="table table-striped" align="center">
    <thead>
        <tr align="center">
            <th width="20%"><B>Product Name</B></th>
            <th width="20%"><B>SKU</B></th>
            <th width="20%"><B>Category Name</B></th>
            <th width="20%"><B>Price</B></th>
            <th width="20%" colspan="2"><B>Order</B></th>
        </tr>
    </thead>
    <tbody>
        <%
        	for (ProductWithCategoryName p : products) {
        %>
        <tr>
            <td><%=p.getName()%></td>
            <td><%=p.getSKU()%></td>
            <td><%=p.getCategoryName()%></td>
            <td><%=p.getPrice()%></td>
            <td>
                <form action="product-order" method="post">
                    <input type="text" name="action" id="action" value="delete" style="display: none">
                    <input type="text" name="id" id="id" value="<%=p.getId()%>" style="display: none">
                    <input type="text" name="name" id="name" value="<%=p.getName()%>" style="display: none">
                    <input type="text" name="cname" id="cname" value="<%=p.getCategoryName()%>" style="display: none">
                    <input type="text" name="sku" id="sku" value="<%=p.getSKU()%>" style="display: none">
                    <input type="text" name="price" id="price" value="<%=p.getPrice()%>" style="display: none">
                        <input type="submit" value="Add to shopping cart">
                </form>
            </td>
        </tr>
        <%
        	}
        %>
    </tbody>
</table>