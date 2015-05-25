$(document).ready(function(){
	$("#next_20").click(function(){
		console.log("next 20 pressed");
		var row = $("#hiddenrow").val();
		var col = $("#hiddencol").val();
		$.ajax({
			  url: "analytics.jsp?row=" + row + "&col=" + col,
			  data: {
				  id: 123,
				  action: "next_20"
			  },
			  success: function(html){
				  //html is the response from ajax file, contains table to be added
			    //$( "<div class=\"content\">").html( json.html ).appendTo( "body" );
			  }
			});
	});
	$("#next_10").click(function(){
		console.log("next 10 pressed");
		var row = $("#hiddenrow").val();
		var col = $("#hiddencol").val();
		$.ajax({
			  url: "analytics.jsp?row=" + row + "&col=" + col,
			  data: {
				  id: 123,
				  action: "next_10"
			  },
			  success: function(json){
			    //html is the response from ajax file
			  }
			});
	});
})