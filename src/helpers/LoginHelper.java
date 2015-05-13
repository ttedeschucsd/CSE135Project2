package helpers;

import javax.servlet.http.HttpSession;

public class LoginHelper {

	
	public static Integer obtainUserFromSession(HttpSession session) {
		Integer user;
	    try { 
		     user = (Integer) session.getAttribute("uid");
		    if (user == null) {
		        user = -1;
		    }
	    } catch (Exception e) {
	        user = -1;	    	
	    }
	    session.setAttribute("uid",user);
	    return user;
	}
}
