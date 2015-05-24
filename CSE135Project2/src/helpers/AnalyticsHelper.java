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
	            String colHeadQuery = getColHeaders();
	            //This is the query string
	           
	            
	            colRs = stmt.executeQuery(colHeadQuery);
	            
	            while (colRs.next()){
	            	Integer id = colRs.getInt(1);
	            	String name = colRs.getString(2);
	            	Integer total = colRs.getInt(3);
	            	table.addColHeader(new Header(id, name, total));
	            }
	            
	            getAllItems();
	            
	        } catch(Exception e){
	        	System.err.println("Query failed");
	        }
	        
	        return table;
		}
		
//		private String buildQuery(){
//			String query = "";
//			String custOrStates = "";
//			String categoriesId = "";
//			if(rowsItem.equals("1")){		//If customers is selected
//				byUser = true;
//				custOrStates = "u.name, u.id FROM users as u,";
//			} else if(rowsItem.equals("2")){		//If states is selected
//				custOrStates = "s.name, s.id FROM states as s,";
//				byUser = false;
//			} else if(rowsItem.equals("0")){
//				return null;
//			}
//			
//			if(categoriesItem.equals("0")){		//All categories
//				categoriesId = "";
//			} else{				//Category by ID
//				categoriesId = "p.cid = " + categoriesItem;
//			}
//			
//			query = "SELECT p.id, " + custOrStates + "products as p, sales as sa";
//			return query; 
//		}
		private void createTempTables() throws SQLException{
			Statement stmt = null;
			stmt = conn.createStatement();
			String createColHeaders = "CREATE TEMPORARY TABLE col_headers(id SERIAL PRIMARY KEY  NOT NULL, pid integer, pname text, total integer DEFAULT 0)";
			String createRowHeaders = "CREATE TEMPORARY TABLE row_headers(id serial PRIMARY KEY NOT NULL, soruid integer, soruname text, total integer);";
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
			insert = "INSERT INTO row_headers(soruid, soruname, total)";
			
			if(byUser){				
				select = "(SELECT u.id, u.name, SUM(sa.price*sa.quantity) ";
				order = "";
				tables = "FROM users as u LEFT JOIN sales as sa ON u.id = sa.uid ";
				group = " u.id ";
				if(orderingItem.equals("1")){ //alphabetical
					order = "u.name ASC ";
				} else if(orderingItem.equals("2")){
					order = "sum DESC ";
				}
			} else{
				select = "(SELECT s.name, SUM(sa.price*sa.quantity) ";
				order = "";
				tables = "FROM users as u LEFT JOIN sales as sa ON u.id = sa.uid LEFT JOIN states as s ON u.state = s.id ";
				group = " s.name ";
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
                Integer total = rows.getInt(4);
                table.addRowHeader(new Header(id, name, total));
            }
			return;
		}
		
		
		
		private String getColHeaders(){
			String insert, select, order, group, query, where;
			//Query: SELECT p.name, SUM(sa.price*sa.quantity) FROM sales as sa LEFT JOIN products as p on sa.pid = p.id GROUP BY p.name	ORDER BY sum DESC LIMIT 20
			insert = 
			select = "SELECT p.id, p.name, SUM(sa.price*sa.quantity) FROM sales as sa LEFT JOIN products as p on sa.pid = p.id ";
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
			query = select + where + group + order;
			return query;
		}
		
		private void getAllItems(){
			//HAVE TO REMEMBER TO LIMIT number of users/states AND number of products
			
			//Query for all items: SELECT u.id, p.id, u.name, SUM(sa.price*sa.quantity) as total FROM users as u JOIN sales as sa ON u.id = sa.uid JOIN products as p ON p.id = sa.pid GROUP BY p.id, u.id
			
			//Another way to get data more evenly without having to programmatically sort it:
			
			//For each row header, get all items for that row sorted correctly, and insert the data.
				//This way, there is less sorting, but more overall queries being run (less database calls the better?)
			
			String sql = "SELECT p.id, SUM(sa.price*sa.quantity) as total FROM users as u JOIN sales as sa ON u.id = sa.uid JOIN products as p ON p.id = sa.pid WHERE u.id = ? GROUP BY p.id, u.id";	//This needs to be updated
			ResultSet rs;
			try {
				PreparedStatement stmt = conn.prepareStatement(sql);
			
				ArrayList<Header> rows = table.rowHeaders;
			
				//In this case, i = row index in table
				for(int i=0; i<rows.size(); i++){
					int userId = rows.get(i).id;
					stmt.setInt(1, userId);
					rs = stmt.executeQuery();
					Map<Integer, Integer> totals = new HashMap<Integer, Integer>();
					while(rs.next()){
						totals.put(rs.getInt(1), rs.getInt(2));		//1 is the product id, 2 is the total
					}
					if(totals.size()>0){
						table.addTableRow(totals, i);
					}
				}
			
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return;
		}
}
