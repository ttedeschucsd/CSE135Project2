<!-- You will see on this form a CSS construct called a modal. Don't let
this scare you, this is just some pretty presentation. They allow you to create
boxes that appear when clicking on a button. You do not have to use them if you
don't want to. -->
<%@page
    import="java.util.List"
    import="helpers.*"%>
<%=ProductsHelper.modifyProducts(request)%>
<form action="products">
    <%
    	String search = request.getParameter("search");
    	search = (search != null) ? search : "";
    	String category = request.getParameter("category");
    	category = (category != null) ? category : "";
    %>
    <input
        type="text"
        name="category"
        id="category"
        value="<%=category%>"
        style="display: none" /> <input
        type="text"
        name="search"
        id="search"
        value="<%=search%>"
        size="40" /> <input
        type="submit"
        value="search">
</form>
<%
	List<ProductWithCategoryName> products = ProductsHelper
			.listProducts(request);
	List<CategoryWithCount> categories = CategoriesHelper
			.listCategories();
%>
<table
    class="table table-striped"
    align="center">
    <thead>
        <tr align="center">
            <th width="20%"><B>Product Name</B></th>
            <th width="20%"><B>SKU</B></th>
            <th width="20%"><B>Category Name</B></th>
            <th width="20%"><B>Price</B></th>
            <th
                width="20%"
                colspan="2"><B>Operations</B></th>
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
                <form
                    action="products"
                    method="post">
                    <input
                        type="text"
                        name="category"
                        id="category"
                        value="<%=category%>"
                        style="display: none" /> <input
                        type="text"
                        name="search"
                        id="search"
                        value="<%=search%>"
                        style="display:none;"
                        size="40" /> <input
                        type="text"
                        name="action"
                        id="action"
                        value="delete"
                        style="display: none"> <input
                        type="text"
                        name="id"
                        id="id"
                        value="<%=p.getId()%>"
                        style="display: none"> <input
                        type="submit"
                        value="Delete">
                </form>
                <button
                    data-toggle="modal"
                    data-target="#product-<%=p.getId()%>">Update</button>
                <div
                    class="modal fade"
                    id="product-<%=p.getId()%>"
                    aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button
                                    type="button"
                                    class="close"
                                    data-dismiss="modal"
                                    aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                                <h4
                                    class="modal-title"
                                    id="myModalLabel">Update Product</h4>
                            </div>
                            <div class="modal-body">

                                <!-- Update Product Form -->
                                <form
                                    action="products"
                                    method="post">
                                    <div>
                                        Name : <input
                                            type="text"
                                            name="name"
                                            id="name"
                                            value="<%=p.getName()%>"
                                            size="40">
                                    </div>
                                    <div>
                                        Category Name : <select
                                            id="cname"
                                            name="cname">
                                            <%
                                            	for (CategoryWithCount cwc : categories) {
                                            			if (cwc.getName().equals(p.getCategoryName())) {
                                            %>
                                            <option selected="selected">
                                                <%
                                                	} else {
                                                %>
                                            
                                            <option>
                                                <%
                                                	}
                                                %>
                                                <%=cwc.getName()%>
                                            </option>
                                            <%
                                            	}
                                            %>
                                        </select>
                                    </div>
                                    <div>
                                        SKU : <input
                                            type="text"
                                            name="sku"
                                            id="sku"
                                            value="<%=p.getSKU()%>"
                                            size="40">
                                    </div>
                                    <div>
                                        Price : <input
                                            type="text"
                                            name="price"
                                            id="price"
                                            value="<%=p.getPrice()%>"
                                            size="40">
                                    </div>
                                    <input
                                        type="text"
                                        name="category"
                                        id="category"
                                        value="<%=category%>"
                                        style="display: none" /> <input
                                        type="text"
                                        name="search"
                                        id="search"
                                        value="<%=search%>"
                                        size="40" /> <input
                                        type="text"
                                        name="id"
                                        id="id"
                                        value="<%=p.getId()%>"
                                        style="display: none"> <input
                                        type="text"
                                        name="action"
                                        id="action"
                                        value="update"
                                        style="display: none"> <input
                                        type="submit"
                                        value="Update">
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </td>
        </tr>
        <%
        	}
        %>
    </tbody>
</table>
<div class="panel panel-default">
    <div class="panel-body">

        <button
            data-toggle="modal"
            data-target="#newProduct">Insert</button>

        <div
            class="modal fade"
            id="newProduct"
            aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button
                            type="button"
                            class="close"
                            data-dismiss="modal"
                            aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                        <h4
                            class="modal-title"
                            id="myModalLabel">New Product</h4>
                    </div>
                    <div class="modal-body">


                        <!-- Insert New Product Form -->
                        <form
                            action="products"
                            method="post">
                            <div>
                                Name : <input
                                    type="text"
                                    name="name"
                                    id="name"
                                    value=""
                                    size="40">
                            </div>
                            <div>
                                Category Name : <select
                                    id="cname"
                                    name="cname">
                                    <%
                                    	for (CategoryWithCount cwc : categories) {
                                    %>
                                    <option>
                                        <%=cwc.getName()%>
                                    </option>
                                    <%
                                    	}
                                    %>
                                </select>
                            </div>
                            <div>
                                SKU : <input
                                    type="text"
                                    name="sku"
                                    id="sku"
                                    value=""
                                    size="40">
                            </div>
                            <div>
                                Price : <input
                                    type="text"
                                    name="price"
                                    id="price"
                                    value=""
                                    size="40">
                            </div>
                            <input
                                type="text"
                                name="category"
                                id="category"
                                value="<%=category%>"
                                style="display: none" /> <input
                                type="text"
                                name="search"
                                id="search"
                                value="<%=search%>"
                                size="40" /> <input
                                type="text"
                                name="action"
                                id="action"
                                value="insert"
                                style="display: none"> <input
                                type="submit"
                                value="Insert">
                        </form>



                    </div>
                </div>
            </div>
        </div>
    </div>
</div>