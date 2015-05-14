package helpers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.http.HttpSession;

/**
 * @author Jules Testard
 */
public class IndexHelper {

    public static String login(String name, HttpSession session) {
        Connection conn = null;
        Statement stmt;
        try {

            try {
                conn = HelperUtils.connect();
            } catch (Exception e) {
                System.out.println("Driver error");
                e.printStackTrace();
            }
            stmt = conn.createStatement();
            ResultSet rs = null;
            rs = stmt.executeQuery("SELECT * FROM  users where name='" + name + "';");

            String role = null;
            int userID = 0;
            int t = 0;
            if (rs.next()) {
                userID = rs.getInt(1);
                role = rs.getString(3);
                session.setAttribute("name", name);
                session.setAttribute("uid", userID);
                session.setAttribute("role", role);
                session.setAttribute("cart", new ShoppingCart());
                t++;
            }
            if (t == 0) {
                String data = "No user found with this name!";
                return HelperUtils.printError(data);
            }
            stmt.close();
            conn.close();
            String data = "You have successfully logged in!";
            return HelperUtils.printSuccess(data);
        } catch (Exception e) {
            String data = "Error, can not access the database, please check the database connection..." + e.getLocalizedMessage();
            return HelperUtils.printError(data);
        }
    }
}
