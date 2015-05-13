<%@page import="java.util.List" import="helpers.*"%>
<%
	ShoppingCart cart = PurchaseHelper.obtainCartFromSession(session);
	boolean validRequest = true;
	String name = null, cname = null, sku = null;
	Integer id = null, price = null;
	try {
		id = Integer.parseInt(request.getParameter("id"));
		name = request.getParameter("name");
		cname = request.getParameter("cname");
		sku = request.getParameter("sku");
		price = Integer.parseInt(request.getParameter("price"));
		if (!(id != null && name != null && cname != null
				&& sku != null && price != null)) {
			validRequest = false;
		}
	} catch (Exception e) {
		validRequest = false;
	}
	String qStr = request.getParameter("quantity");
	if (qStr != null) {
		try {
			Integer quantity = Integer.parseInt(qStr);
			ProductWithCategoryName p = new ProductWithCategoryName(id,
					name, cname, sku, price);
			cart.addToShoppingCart(quantity, p);
			out.println(HelperUtils
					.printSuccess("Product added to shopping cart"));
		} catch (Exception e) {

		}
	}
%>

<%
	if (cart.getProducts().isEmpty()) {
%>
You do not currently have any items in your shopping cart.
<%
	} else {
%>
<table class="table table-striped" align="center">
    <thead>
        <tr align="center">
            <th width="20%"><B>Product Name</B></th>
            <th width="20%"><B>SKU</B></th>
            <th width="20%"><B>Category Name</B></th>
            <th width="20%"><B>Price</B></th>
            <th width="20%"><B>Quantity</B></th>
        </tr>
    </thead>
    <tbody>
        <%
        	for (int i = 0; i < cart.getQuantities().size(); i++) {
        			ProductWithCategoryName p = cart.getProducts().get(i);
        			int q = cart.getQuantities().get(i);
        %>
        <tr>
            <td><%=p.getName()%></td>
            <td><%=p.getSKU()%></td>
            <td><%=p.getCategoryName()%></td>
            <td><%=p.getPrice()%></td>
            <td><%=q%></td>
        </tr>
        <%
        	}
        %>
    </tbody>
</table>
<%
	}
%>
<%
	if (validRequest) {
%>
<div class="panel panel-default">
    <div class="panel-body">
        <h4>Product Details</h4>
        <!-- Insert New Product Form -->
        <form action="product-order" method="post">
            <div>
                Name :
                <%=name%></div>
            <div>
                Category Name :
                <%=cname%></div>
            <div>
                SKU :
                <%=sku%></div>
            <div>
                Price :
                <%=price%></div>
            <div>
                Quantity :<input type="text" name="quantity" id="quantity" value="" size="40">
            </div>
            <input type="text" name="id" id="id" value="<%=id%>" style="display: none"> <input type="text" name="name"
                id="name" value="<%=name%>" style="display: none"> <input type="text" name="cname" id="cname"
                value="<%=cname%>" style="display: none"> <input type="text" name="sku" id="sku"
                value="<%=sku%>" style="display: none"> <input type="text" name="price" id="price"
                value="<%=price%>" style="display: none"> <input type="submit" value="Add">
        </form>
    </div>
</div>
<%
	} else {
%>
<%=HelperUtils.printError("Invalid Request!")%>
<%
	}
%>