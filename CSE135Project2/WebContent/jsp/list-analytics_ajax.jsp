//This file handles the ajax request and returns json encoded data to populate the added table


<%@page
    import="java.util.*"
    import="helpers.*" %>
    
<%
ArrayList<String> stringList = new ArrayList<>();
String json = new Gson().toJson(stringList);


%>