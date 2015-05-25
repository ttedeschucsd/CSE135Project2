	package helpers;

	import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

	public class AnalyticsHelper {
		private String rowsItem, categoriesItem, orderingItem;
		private int limitColEnd, limitRowEnd;
		public TableHelper table;
		private boolean byUser;
		private Connection conn;
		
		
		public TableHelper submitQuery(HttpServletRequest request){
	        Statement stmt = null;
	        ResultSet rowRs = null;
	        ResultSet colRs = null;
	        
	        table = new TableHelper();
	        limitRowEnd = 20;
	        limitColEnd = 10;
	        rowsItem = request.getParameter("rows_dropdown");	//Customers(1) or States(2)
	        categoriesItem = request.getParameter("categories_dropdown");	//All Categories(0)
	        orderingItem = request.getParameter("orders_dropdown");	//How the data should be ordered
	        
	        if(rowsItem == null || categoriesItem == null || orderingItem == null){		//If there was no request
	        	return null;
	        }
	        
	        if(rowsItem.equals("1")){		//If customers is selected
				byUser = true;
			} else if(rowsItem.equals("2")){		//If states is selected
				byUser = false;
			}
	        
	        try{
	        	try {
	                conn = HelperUtils.connect();
	            } catch (Exception e) {
	                System.err.println("Internal Server Error. This shouldn't happen.");
	                return null;
	            }
	        	createTempTables();
	        	stmt = conn.createStatement();	//Prepare the sql statement
	            getRowHeaders();
	            getColHeaders();
	            getAllItems();
	        } catch(Exception e){
	        	System.err.println("Query failed");
	        }
	        
	        return table;
		}
		
		private void createTempTables() throws SQLException{
			Statement stmt = null;
			stmt = conn.createStatement();
			String createColHeaders = "CREATE TEMPORARY TABLE col_headers(id SERIAL PRIMARY KEY  NOT NULL, pid integer, pname text, total integer DEFAULT 0)";
			String createRowHeaders = "CREATE TEMPORARY TABLE row_headers(id serial PRIMARY KEY NOT NULL, soruid integer, soruname text, stateid integer, userid integer, total integer);";
			stmt.execute(createColHeaders);
			stmt.execute(createRowHeaders);
		}
		
		private void getRowHeaders() throws SQLException{
			ResultSet rows = null;
			Statement stmt = null;
			stmt = conn.createStatement();
			String insert, select, group, order, tables, where, query;
			//Query for customers: SELECT u.id, u.name, SUM(sa.price*sa.quantity) FROM users as u LEFT JOIN sales as sa ON u.id = sa.uid GROUP BY u.id
			//Query for states: SELECT s.name, SUM(sa.price*sa.quantity) FROM users as u LEFT JOIN sales as sa ON u.id = sa.uid LEFT JOIN states as s ON u.state = s.id GROUP BY s.id
			insert = "INSERT INTO row_headers(soruid, soruname, stateid, userid, total)";
			
			if(byUser){				
				select = "(SELECT u.id, u.name, s.id, u.id, SUM(sa.price*sa.quantity) ";
				order = "";
				tables = "FROM users as u LEFT JOIN sales as sa ON u.id = sa.uid LEFT JOIN states as s on s.id = u.state ";
				group = " u.id, s.id ";
				if(orderingItem.equals("1")){ //alphabetical
					order = "u.name ASC ";
				} else if(orderingItem.equals("2")){
					order = "sum DESC ";
				}
			} else{
				select = "(SELECT s.id, s.name, s.id, u.id, SUM(sa.price*sa.quantity) ";
				order = "";
				tables = "FROM states as s LEFT JOIN users as u ON s.id = u.state LEFT JOIN sales as sa ON u.id = sa.uid ";
				group = " s.id, u.id ";
				if(orderingItem.equals("1")){ //alphabetical
					order = "s.name ASC ";
				} else if(orderingItem.equals("2")){
					order = "sum DESC ";
				}
			}
			where = "";
			if(!categoriesItem.equals("0")){
				tables += "LEFT JOIN products as p ON sa.pid = p.id LEFT JOIN categories as c ON c.id = p.cid ";
				where = "WHERE c.id = " + categoriesItem + " ";
			}
			query = insert + select + tables + where + "GROUP BY" + group + "ORDER BY " + order + "LIMIT " + limitRowEnd + ")";
			stmt.execute(query);
			query = "SELECT * FROM row_headers";
			rows = stmt.executeQuery(query);
			while (rows.next()) {
            	Integer id = rows.getInt(1);
                String name = rows.getString(3);
                Integer total = rows.getInt(6);
                table.addRowHeader(new Header(id, name, total));
            }
			return;
		}
			
		private void getColHeaders() throws SQLException{
			ResultSet cols = null;
			Statement stmt = null;
			
			String insert, select, order, group, query, where;			
			stmt = conn.createStatement();

			//Query: INSERT INTO col_headers(pid, pname, total) (SELECT p.id, p.name, SUM(sa.price*sa.quantity) FROM products as p LEFT JOIN sales as sa on p.id = sa.pid GROUP BY p.name, p.id ORDER BY p.name ASC)
			insert = "INSERT INTO col_headers(pid, pname, total)";
			select = "(SELECT p.id, p.name, SUM(sa.price*sa.quantity) FROM products as p LEFT JOIN sales as sa on sa.pid = p.id ";
			group = "GROUP BY p.name, p.id ";
			order = "";
			where = "";
			
			if(orderingItem.equals("1")){
				order = "ORDER BY p.name ASC LIMIT " + limitColEnd;
			} else if(orderingItem.equals("2")){
				order = "ORDER BY sum DESC LIMIT " + limitColEnd;
			}
			
			if(!categoriesItem.equals("0")){
				select += "LEFT JOIN categories as c on p.cid = c.id ";
				where = "WHERE c.id = " + categoriesItem + " ";
			}
			query = insert + select + where + group + order + ")";
			stmt.execute(query);
			query = "SELECT * FROM col_headers";
			cols = stmt.executeQuery(query);
			while (cols.next()){
            	Integer id = cols.getInt(1);
            	String name = cols.getString(3);
            	Integer total = cols.getInt(4);
            	table.addColHeader(new Header(id, name, total));
            }
			return;
		}
		
		private void getAllItems() throws SQLException{
			//HAVE TO REMEMBER TO LIMIT number of users/states AND number of products
			ResultSet items = null;
			Statement stmt = null;
			String query, userOrState, stateExtra;
			if(byUser){
				userOrState = "LEFT JOIN users as u ON row.soruid = u.id ";
				stateExtra = "";
			} else{
				userOrState = "LEFT JOIN states as s ON row.soruid = s.id ";
				stateExtra = "LEFT JOIN users as u on row.userid = u.id ";
			}
			
			stmt = conn.createStatement();
			
			query = "SELECT p.id, col.id AS column, row.id AS row, SUM(sa.price*sa.quantity) as total "
					+ "FROM row_headers AS row "
					+ userOrState
					+ stateExtra
					+ "LEFT JOIN sales as sa ON u.id = sa.uid "
					+ "RIGHT JOIN col_headers AS col ON col.pid = sa.pid "
					+ "LEFT JOIN products as p ON p.id = col.pid "
					+ "GROUP BY p.id, col.id, row.id";
			items = stmt.executeQuery(query);
			while(items.next()){
				table.addItem(items.getInt(3), items.getInt(2), items.getInt(4));
			}
			return;
		}
}
