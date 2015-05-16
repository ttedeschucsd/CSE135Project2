	package helpers;

	import java.sql.Connection;
	import java.sql.ResultSet;
	import java.sql.SQLException;
	import java.sql.Statement;
	import java.util.ArrayList;
	import java.util.List;

	import javax.servlet.http.HttpServletRequest;

	public class AnalyticsHelper {
		
		public static void submitQuery(HttpServletRequest request){
			request.getParameter("rows_dropdown");	//Customers(1) or States(2)
			request.getParameter("categories_dropdown");	//All Categories(0)
			request.getParameter("orders_dropdown");	//How the data should be ordered
		}
}
