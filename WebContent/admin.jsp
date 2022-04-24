<!DOCTYPE html>

<html lang="en" dir="ltr">
  <head>
    <meta charset="utf-8">
    <title>Admin</title>
  </head>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js" integrity="sha512-894YE6QWD5I59HgZOGReFYm4dnWc1Qt5NtvYSaNcOP+u1T9qYdvdihz0PPSiiqn/+/3e7Jo4EaG7TubfWGUrMQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
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
    <h2>Admin</h2>
    
    <form id = "myForm" enctype="multipart/form-data" method ="post">
        <label for="programName">Add Program</label>
    <input type="text" id="programName" name= "programName" value=""/><br><br>
    <br><br>
    
   	
	
    
    
    <label for="File">Upload your C program File</label><br><br>
    <input type="file" id="File" name= "cFile" value=""/><br><br>
    <br><br>
      
     
      <button type="submit" id="button"><span class="spinner-border spinner-border-sm" role="status" id="spin1" ></span>Add</button><br><br>
		
		
    
    </form>
    
    	
		
		<div id="table"></div><br><br>
	
        <input type="hidden" name = "inputs" value = ""/>
        
        <label id="msg" ></label>
		
  <script>
         
      function update() {
		console.log("second call");
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
		    document.getElementsByName("inputs")[0].value = JSON.stringify(values)
		    var inputs = document.getElementsByName("inputs")[0].value;
		    var programName = document.getElementById('programName').value;
		     $.ajax({
			url : "update",
			type : 'POST',
			data: {
				inputs:inputs,
				programName:programName
			},
			
			success: function(data){
			
				data = data.toString().trim();
				console.log(data)
				$("#msg").text(data)
			}
		})
	    }
	
		
  		$(document).ready(function (){
			
  			
  			console.log("page is ready")
  			
  		  
  			
	      


			
  			$("#myForm").on('submit',function(event){
  				
  				event.preventDefault();
  				
	  			console.log("form is sumbited")
	  			
				
  				
  				let f = new FormData(this);
  				$.ajax({
  					url : "admin",
  					data : f,
  					type : 'POST',
  					processData : false,
  					contentType : false,
  					mimeType : "multipart/form-data",
  					success: function(data){
  						
  			  		if (data.toString().trim() !== "Compilation Error") {
							var data1 = JSON.parse(data);
						
	  						var div=' <table >'+
							'<thead>'+
							'<tr>';
							var keys=Object.keys(data1[0]);
							for(let i=0;i<keys.length;i++){
								div=div+'<th >'+keys[i]+'</th>';
							}
							div = div + '<th>Minimum Value</th>';
							div = div + '<th>Maximum Value</th>';
							div = div + '<th>ArraySize variable</th>';
							div=div+'</tr>'+'</thead>'+'</table>';
					
							div=div+'<table >'+
							'<tbody>';
							for(let j=0;j<data1.length;j++){
								var flag = 1;
								let obj=data1[j];
								console.log(obj)
								div=div+'<tr>';
								for(let i=0;i<keys.length;i++){
									div=div+'<td >'+obj[keys[i]]+'</td>';
									// console.log();
									// if(obj[keys[i]] === "normalInput"){
									// 	flag = 0;
									// }
								}
								if(obj["Type of Input"] === "normalInput"){
										flag = 0;
									}
									console.log(flag)
								div = div + '<td><input type = "text" name = "minValue" id = "minValue"></td>';
								div = div + '<td><input type = "text" name = "maxValue" id = "maxValue"></td>';
								if(flag === 1){
									div = div + '<td><select name = "arraySize" id = "arraySize">';
									for(let x=0;x<data1.length;x++){
										if(data1[x]["Type of Input"] === "normalInput"){
											div = div + '<option value='+data1[x]["Variable Name"]+'>'+data1[x]["Variable Name"]+'</option>';
										}
									}
									div = div + '</select></td>';
								}
								div=div+'</tr>';
							}
							div=div+'</tbody>';
							div = div + '</table><br><br>';
							div = div + '<button type="button" onclick="update()">Update</button><br><br>';
							
							
							
							document.getElementById("table").innerHTML=div;
						}
						else{
							document.getElementById("table").innerHTML= " ";
						$("#msg").text(data)
						}
  					}
  				})
  			})
  		
  		})
</script>


    </center>

  </body>