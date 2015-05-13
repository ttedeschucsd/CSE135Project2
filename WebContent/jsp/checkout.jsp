<%@page import="java.util.List" import="helpers.*"%>
<%
    ShoppingCart cart = PurchaseHelper.obtainCartFromSession(session);
    if ( cart.getProducts().isEmpty()) {
%>
You do not currently have any items in your shopping cart.
<%
    } else {
%>
<%
int sum = 0;
%>
<table class="table table-striped" align="center">
    <thead>
        <tr align="center">
            <th width="16%"><B>Category Name</B></th>
            <th width="16%"><B>SKU</B></th>
            <th width="16%"><B>Product Name</B></th>
            <th width="16%"><B>Price Per Unit</B></th>
            <th width="16%"><B>Quantity</B></th>
            <th width="16%"><B>Total Price</B></th>
        </tr>
    </thead>
    <tbody>
        <%
            for (int i = 0; i < cart.getQuantities().size(); i++) {
                    ProductWithCategoryName p = cart.getProducts().get(i);
                    int q = cart.getQuantities().get(i);
                    sum += q * p.getPrice();
        %>
        <tr>
            <td><%=p.getName()%></td>
            <td><%=p.getSKU()%></td>
            <td><%=p.getCategoryName()%></td>
            <td><%=p.getPrice()%></td>
            <td><%=q%></td>
            <td><%=q * p.getPrice()%></td>
        </tr>
        <%
            }
        %>
    </tbody>
</table>
<h4>Total Price : <%= sum %></h4>
<form action="confirm">
Credit cart number :<input type="text" name="quantity" id="quantity" value="" size="40">
<input type="submit" value="Purchase">
</form>
<%
    }
%>