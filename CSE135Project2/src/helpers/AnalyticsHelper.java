	package helpers;

	import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

	public class AnalyticsHelper {
		private String rowsItem, categoriesItem, orderingItem;
		private int limitColEnd, limitRowEnd;
		public TableHelper table;
		private boolean byUser;
		
		public TableHelper submitQuery(HttpServletRequest request){
	        Connection conn = null;
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
	        	stmt = conn.createStatement();	//Prepare the sql statement
	            String rowHeadQuery = getRowHeaders();
	            String colHeadQuery = getColHeaders();
	            //This is the query string
	            rowRs = stmt.executeQuery(rowHeadQuery);	//Actually call the database here
	           
	            
	            while (rowRs.next()) {
	            	Integer id = rowRs.getInt(1);
	                String name = rowRs.getString(2);
	                Integer total = rowRs.getInt(3);
	                table.addRowHeader(new Header(id, name, total));
	            }
	            
	            colRs = stmt.executeQuery(colHeadQuery);
	            
	            while (colRs.next()){
	            	Integer id = colRs.getInt(1);
	            	String name = colRs.getString(2);
	            	Integer total = colRs.getInt(3);
	            	table.addColHeader(new Header(id, name, total));
	            }
	            
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
		
		
		
		private String getRowHeaders(){
			String select, group, order, tables, where, query;
			//Query for customers: SELECT u.id, u.name, SUM(sa.price*sa.quantity) FROM users as u LEFT JOIN sales as sa ON u.id = sa.uid GROUP BY u.id
			//Query for states: SELECT s.name, SUM(sa.price*sa.quantity) FROM users as u LEFT JOIN sales as sa ON u.id = sa.uid LEFT JOIN states as s ON u.state = s.id GROUP BY s.id
			
			if(byUser){
				select = "SELECT u.id, u.name, SUM(sa.price*sa.quantity) ";
				order = "";
				tables = "FROM users as u LEFT JOIN sales as sa ON u.id = sa.uid ";
				group = " u.id ";
				if(orderingItem.equals("1")){ //alphabetical
					order = "u.name ASC ";
				} else if(orderingItem.equals("2")){
					order = "sum DESC ";
				}
			} else{
				select = "SELECT s.name, SUM(sa.price*sa.quantity) ";
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
			
			query = select + tables + where + "GROUP BY" + group + "ORDER BY " + order + "LIMIT " + limitRowEnd;
			
			return query;
		}
		
		
		
		private String getColHeaders(){
			String select, order, group, query, where;
			//Query: SELECT p.name, SUM(sa.price*sa.quantity) FROM sales as sa LEFT JOIN products as p on sa.pid = p.id GROUP BY p.name	ORDER BY sum DESC LIMIT 20
			
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
}
