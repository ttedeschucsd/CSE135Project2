package helpers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SignupHelper {

    public static String signup(String name, Integer age, String role, String state) {

        Connection conn = null;
        Statement stmt;

        try {
            int stateId = getStateId(state);
            String SQL = "INSERT INTO users (name, role, age, state) VALUES('" + name + "','" + role + "'," + age
                    + ",'" + stateId + "');";
            try {
                conn = HelperUtils.connect();
            } catch (Exception e) {
                System.out.println("Could not register PostgreSQL JDBC driver with the DriverManager");
            }
            stmt = conn.createStatement();
            try {
                conn.setAutoCommit(false);
                stmt.execute(SQL);
                conn.commit();
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                return HelperUtils.printError(e.getLocalizedMessage());
            }
            conn.close();
        } catch (Exception e) {
            String output = "A problem happened while interacting with the database : \n" + e.getLocalizedMessage();
            return HelperUtils.printError(output);
        }
        String output = "<h4>Registered successfully!</h4> <br>";
        output += "<table><tr><td>Name:</td><td>" + name + "</td></tr><tr><td>Role:</td><td>" + role
                + "</td></tr><tr><td>Age:</td><td>" + age + "</td></tr><tr><td>State:</td><td>" + state
                + "</td></tr></table>";
        return HelperUtils.printSuccess(output);
    }

    public static int getStateId(String stateName) throws SQLException {
        // Look up the state id.
        Connection conn = null;
        String query = "SELECT id FROM states WHERE name=\'" + stateName + "\'";
        try {
            conn = HelperUtils.connect();
        } catch (Exception e) {
            System.out.println("Could not register PostgreSQL JDBC driver with the DriverManager");
        }
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        if (rs.next()) {
            return rs.getInt(1);
        } else {
            throw new SQLException("There is no state with this name : " + stateName);
        }
    }
}
