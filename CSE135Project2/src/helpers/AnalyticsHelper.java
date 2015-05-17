	package helpers;

	import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

	public class AnalyticsHelper {
		private static String rowsItem, categoriesItem, orderingItem;
		
		public static void submitQuery(HttpServletRequest request){
	        Connection conn = null;
	        Statement stmt = null;
	        ResultSet rs = null;
	        
	        rowsItem = request.getParameter("rows_dropdown");	//Customers(1) or States(2)
	        categoriesItem = request.getParameter("categories_dropdown");	//All Categories(0)
	        orderingItem = request.getParameter("orders_dropdown");	//How the data should be ordered        
	        
	        try{
	        	try {
	                conn = HelperUtils.connect();
	            } catch (Exception e) {
	                System.err.println("Internal Server Error. This shouldn't happen.");
	                return;
	            }	
	        	stmt = conn.createStatement();	//Prepare the sql statement
	            String query = "SELECT c.id, c.name, c.description, COUNT(p.id) as count FROM Categories c LEFT OUTER JOIN Products p ON c.id=p.cid GROUP BY c.id, c.name, c.description";
	            //This is the query string
	            rs = stmt.executeQuery(query);	//Actually call the database here
	        } catch(Exception e){
	        	System.err.println("Query failed");
	        }
		}
		
		private String buildQuery(){
			String query = "";
			String custOrStates = "";
			String categoriesId = "";
			if(rowsItem == String.valueOf(1)){		//If customers is selected
				custOrStates = "u.name, u.id FROM users as u,";
			} else if(rowsItem == String.valueOf(2)){		//If states is selected
				custOrStates = "s.name, s.id FROM states as s,";
			} else if(rowsItem == String.valueOf(0)){
				return null;
			}
			
			if(categoriesItem == String.valueOf(0)){		//All categories
				categoriesId = "";
			} else{				//Category by ID
				categoriesId = "p.cid = " + categoriesItem;
			}
			
			query = "SELECT p.id, " + custOrStates + "products as p, sales as sa";
			return query; 
		}
}
