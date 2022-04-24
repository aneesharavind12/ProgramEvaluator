<%@page import="org.json.simple.JSONArray"%>
<%@page import="com.testing.web.dao.Programs"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Evaluator</title>
</head>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js" integrity="sha512-894YE6QWD5I59HgZOGReFYm4dnWc1Qt5NtvYSaNcOP+u1T9qYdvdihz0PPSiiqn/+/3e7Jo4EaG7TubfWGUrMQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
  <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.10.2/dist/umd/popper.min.js" integrity="sha384-7+zCNj/IqJ95wo16oMtfsKbZ9ccEh31eOz1HGyDuCQ6wgnyJNSYdrPa03rtR1zdB" crossorigin="anonymous"></script>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.min.js" integrity="sha384-QJHtvGhmr9XOIpI6YVutG+2QOK9T+ZnN4kzFN1RtK3zEFEIsxhlmWl5/YESvpZ13" crossorigin="anonymous"></script>
  <script language="javascript" type="text/javascript" src="https://codemirror.net/lib/codemirror.js"></script>
	<script language="javascript" type="text/javascript" src="https://codemirror.net/mode/perl/perl.js"></script>

	<link rel="stylesheet" type="text/css" href="https://codemirror.net/lib/codemirror.css"></link>
	<link rel="stylesheet" type="text/css" href="https://codemirror.net/theme/abbott.css"></link>

<style>
table {
  font-family: arial, sans-serif;
  border-collapse: collapse;
  width: 100%;
}

td, th {
  border: 1px solid #dddddd;
  text-align: left;
  padding: 8px;
}

tr:nth-child(even) {
  background-color: #dddddd;
  padding: 8px;
}



</style>

<body>
    <center>
    <h1>C Program Evaluator</h1>

    <hr size = 4>
    <h2>User</h2>
    <%if(request.getSession().getAttribute("user")==null){
		response.sendRedirect("/MyServletApp/index.jsp");  
	}else{
		out.println("Welcome  "+request.getSession().getAttribute("user"));
	}%>
	<br><br><br><br>
    
     <form id = "myForm" enctype="multipart/form-data" method ="post">
	<%JSONArray array = Programs.getPrograms(); %>
	<%String programName=""; %>
	<label>Select Problem :</label>
	<select id="problemName" name="problemName"  >
		<%for(Object obj:array){ %>
			<option value="<%=((String)obj)%>"><%=((String)obj) %></option>
		<%} %>
	</select><br><br>
	<label for="numberOfTestCases">Number of User Test Cases</label><br><br><input type="text" id="numberOfTestCases" name= "numberOfTestCases" value=""/><br><br>
    <button type="button" onclick="select()">Select</button><br><br>
	
	
    
    	<label id="msg" >
			
		</label>
		
    <div id="table"></div>
    
    <input type="hidden" name = "testCase" value = ""></input>
    
	
    </form>
	

	
	

 <script>
 function select() {
		console.log("select call");
		var problemName = $('#problemName').find(":selected").text();
		var numberOfUserTestCases = document.getElementById('numberOfTestCases').value;
		console.log(problemName);
		console.log(numberOfUserTestCases);
		     $.ajax({
			url : "showinput",
			type : 'POST',
			data: {
				problemName:problemName,
				numberOfUserTestCases:numberOfUserTestCases
			},
			
			success: function(data){
			
				var data1 = JSON.parse(data);
				var rowsNumber = data1[0];
				console.log(data1);

				var div = '<textarea rows="40" cols="70" name="code" id = "code">';
				div = div + '//Enter your code here</textarea><br><br>';
				
				div= div  +' <table >'+
				'<thead>'+
				'<tr>';
				for(let i=1;i<data1.length;i++){
					div=div+'<th >'+data1[i]+'</th>';
				}
				div = div + '<th>Expected output</th>';
				
				
				div=div+'<table >'+
				'<tbody>';
				for(let k=0;k<rowsNumber;k++){
					div=div+'<tr>';
					for(let j=1;j<data1.length + 1;j++){
						div = div + '<td><input type = "text" name = "input" id = "input"></td>';
					 }
					div=div+'</tr>';
				}
				
				div=div+'</tbody>';
				
				div=div+'</table><br><br>';
				
				div = div + '<label for="numberOfRandomTestCases">Number of Random Test Cases</label><br><br><input type="text" id="numberOfRandomTestCases" name= "numberOfRandomTestCases" value=""/><br><br>'
				div = div + ' <button type="button" onclick="evaluator()">Evaluate</button><br><br>';
				document.getElementById("table").innerHTML=div;
			}
		})
	    }
 
 function evaluator() {
	 console.log("evaluate");
	 var rows = $('tbody').find('tr');
	    var values = [];
	    for(var i = 0; i < rows.length; i++){
	        var row = rows[i];
	        var cells = $(row).find('td');
	        var value = [];
	        for(var j = 0; j < cells.length; j++){
	            var cell = cells[j];
	            var input = $(cell).find('input');
			var select = $(cell).find('select');
			if(input.length > 0){
				var input1 = $(cell).find('input')[0];
			
				value.push($(input).val());
			}
			else if(select.length > 0){
				var select1 = $(cell).find('select')[0];
				value.push($(select).val());
			}
	        }
	        values.push(value);
	    }
	    console.log(values); 
	    document.getElementsByName("testCase")[0].value = JSON.stringify(values)
	    var testCase = document.getElementsByName("testCase")[0].value;
	    console.log(testCase);
	    var numberOfRandomTestCases = document.getElementById('numberOfRandomTestCases').value;
	    console.log(numberOfRandomTestCases);
	    var problemName = $('#problemName').find(":selected").text();
	    console.log(numberOfRandomTestCases);
	    var code = document.getElementById('code').value;
	    console.log(code);
	    $.ajax({
			url : "evaluator",
			type : 'POST',
			data: {
				testCase:testCase,
				problemName:problemName,
				numberOfRandomTestCases:numberOfRandomTestCases,
				code:code
				
			},
			
			success: function(data){
			
				if (data.toString().trim() !== "Compilation Error") {
						var data1 = JSON.parse(data);
					  var totalCase = data1.length;
					  var totalSuccess=0;
						for(var x=0;x<data1.length;x++){
							data1[x]["Result"]=="Success"?totalSuccess++:"";

						}
						var div='<div><label>Total Cases Passed : </label><label>'+totalSuccess+'/'+totalCase+'</label></div> <table >'+
						'<thead>'+
						'<tr>';
						var keys=Object.keys(data1[0]);
						for(let i=0;i<keys.length;i++){
							div=div+'<th >'+keys[i]+'</th>';
						}
						div=div+'</tr>'+'</thead>'+'</table>';
				
						div=div+'<table >'+
						'<tbody>';
						for(let j=0;j<data1.length;j++){
							let obj=data1[j];
							div=div+'<tr>';
							for(let i=0;i<keys.length;i++){
								div=div+'<td >'+obj[keys[i]]+'</td>';
							}
							div=div+'</tr>';
						}
						div=div+'</tbody>'+'</table>';
						document.getElementById("msg").innerHTML= " ";
						document.getElementById("table").innerHTML=div;
					}
					else{
						document.getElementById("table").innerHTML= " ";
					$("#msg").text(data)
					}
					
			}
		})
}
</script>
</body>
</center>
