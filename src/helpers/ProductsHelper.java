package helpers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public class ProductsHelper {

    public static List<ProductWithCategoryName> listProducts(HttpServletRequest request) {
        List<ProductWithCategoryName> productWithCategoryNames = new ArrayList<ProductWithCategoryName>();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String categoryFilter = "", searchFilter = "";
        try {
        	if (!request.getParameter("category").equals("-1")) {
                Integer cid = Integer.parseInt(request.getParameter("category"));
                if (cid != null)
                    categoryFilter = "cid = " + cid;	
        	}
        } catch (Exception e) {
        }
        try {
            String search = request.getParameter("search");
            if (search != null && !search.isEmpty())
                searchFilter = "name ~ '" + search + "'";
        } catch (Exception e) {
        }
        String filter = null;
        if (categoryFilter.isEmpty()) {
            if (searchFilter.isEmpty()) {
                filter = "";
            } else {
                filter = " WHERE " + searchFilter;
            }
        } else {
            if (searchFilter.isEmpty()) {
                filter = " WHERE " + categoryFilter;
            } else {
                filter = " WHERE " + categoryFilter + " AND " + searchFilter;
            }
        }
        try {
            try {
                conn = HelperUtils.connect();
            } catch (Exception e) {
                System.err.println("Internal Server Error. This shouldn't happen.");
                return new ArrayList<ProductWithCategoryName>();
            }
            stmt = conn.createStatement();
            String query = "WITH selected AS (SELECT * FROM products" + filter
                    + ") SELECT s.id, c.name, s.name, s.SKU, s.price FROM selected s JOIN categories c ON s.cid = c.id";
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                Integer id = rs.getInt(1);
                String cname = rs.getString(2);
                String name = rs.getString(3);
                String SKU = rs.getString(4);
                Integer price = rs.getInt(5);
                productWithCategoryNames.add(new ProductWithCategoryName(id, cname, name, SKU, price));
            }
            return productWithCategoryNames;
        } catch (Exception e) {
            System.err.println("Some error happened!<br/>" + e.getLocalizedMessage());
            return new ArrayList<ProductWithCategoryName>();
        } finally {
            try {
                stmt.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static String modifyProducts(HttpServletRequest request) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            try {
                conn = HelperUtils.connect();
            } catch (Exception e) {
                return HelperUtils.printError("Internal Server Error. This shouldn't happen.");
            }
            stmt = conn.createStatement();
            String action = null, id_str = null;
            String name = "", cname = "", sku = "", price = "", cid = "";
            try {
                action = request.getParameter("action");
            } catch (Exception e) {
                // No action means no modification thus no alert
                return "";
            }
            try {
                id_str = request.getParameter("id");
            } catch (Exception e) {
                id_str = null;
            }
            try {
                name = request.getParameter("name");
                cname = request.getParameter("cname");
                sku = request.getParameter("sku");
                price = request.getParameter("price");
                if (cname != null) {
                    String query = "SELECT id FROM categories WHERE name='" + cname + "'";
                    rs = stmt.executeQuery(query);
                    if (rs.next()) {
                        cid = rs.getString(1);
                    } else {
                        return HelperUtils.printError("Category chosen does not exist!");
                    }
                }
            } catch (Exception e) {
                name = null;
                cname = null;
                sku = null;
                price = null;
                cid = null;
            }
            if (("insert").equals(action)) {
                String SQL_1 = "INSERT INTO products (cid, name, SKU, price) VALUES(" + cid + ",'" + name + "','" + sku
                        + "', " + price + ");";
                try {
                    conn.setAutoCommit(false);
                    stmt.execute(SQL_1);
                    conn.commit();
                    conn.setAutoCommit(true);
                    return HelperUtils.printSuccess("Insertion successful");
                } catch (Exception e) {
                    return HelperUtils
                            .printError("Insert failed! Please <a href=\"products\" target=\"_self\">insert it</a> again.<br/>"
                                    + e.getLocalizedMessage());
                }
            } else if (("update").equals(action)) {
                String SQL_2 = "update products set name='" + name + "' , SKU='" + sku + "'  , price='" + price
                        + "' where id=" + id_str + ";";
                try {
                    conn.setAutoCommit(false);
                    stmt.execute(SQL_2);
                    conn.commit();
                    conn.setAutoCommit(true);
                    return HelperUtils.printSuccess("Update successful.");
                } catch (Exception e) {
                    return HelperUtils
                            .printError("Updated Failed! Please <a href=\"products\" target=\"_self\">Update it</a> again.<br/>"
                                    + e.getLocalizedMessage());
                }
            } else if (("delete").equals(action)) {
                String SQL_3 = "delete from products where id=" + id_str + ";";
                try {
                    conn.setAutoCommit(false);
                    stmt.execute(SQL_3);
                    conn.commit();
                    conn.setAutoCommit(true);
                    return HelperUtils.printSuccess("Deletion successful.");
                } catch (Exception e) {
                    return HelperUtils
                            .printError("Deletion Failed! Please try again in the <a href=\"products\" target=\"_self\">products page</a>.<br><br>");
                }
            } else {
                // Wrong action means no modification thus no alert
                return "";
            }
        } catch (Exception e) {
            return HelperUtils.printError("Some error happened!<br/>" + e.getLocalizedMessage());
        } finally {
            try {
                stmt.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}