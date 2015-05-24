$(document).ready(function(){
	$("#next_20").click(function(){
		console.log("next 20 pressed");
		$.ajax({
			  url: "list-analytics_ajax.jsp",
			  data: {
				  id: 123,
				  action: "next_20"
			  },
			  success: function(html){
				  console.log(html);
				  //html is the response from ajax file, contains table to be added
			    //$( "<div class=\"content\">").html( json.html ).appendTo( "body" );
			  }
			});
	});
	$("#next_10").click(function(){
		console.log("next 10 pressed");
		$.ajax({
			  url: "list-analytics_ajax.jsp",
			  data: {
				  id: 123,
				  action: "next_20"
			  },
			  success: function(json){
				  console.log(html);
			    //html is the response from ajax file
			  }
			});
	});
})