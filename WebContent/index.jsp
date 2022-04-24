<%@ page import="java.util.HashMap" %>

<!DOCTYPE html>
<html lang="en" dir="ltr">
  <head>
    <meta charset="utf-8">
    <title>Login Page</title>
  </head>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js" integrity="sha512-894YE6QWD5I59HgZOGReFYm4dnWc1Qt5NtvYSaNcOP+u1T9qYdvdihz0PPSiiqn/+/3e7Jo4EaG7TubfWGUrMQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
  <body>
    <center>
    <h1>C Program Evaluator</h1>
    <hr size = 4>
    <h2>Login</h2>

    
      <label for="username">Username</label>
      <input type="text" id="username" name= "username" value=""/><br><br>
      <label for="password">Password</label>
      <input type= "password" id="password" name="password"value=""/><br><br><br>
      <button type="button" onclick="login()">
      Login
      </button>
      <br/><br/>
      
		<label>
			Do not have account ? <a href= "signUp.html"> SignUp</a> 
		</label>
		<br/><br/>
		
		<label>
			 <a href= "admin.jsp"> Admin</a> 
		</label>
		<br/><br/>
		
		<label id="errormsg" >
		
		</label>

    </center>
	
  </body>
  <script type="text/javascript">
  function login() {
	  
		var username = document.getElementById("username").value;
		var password = document.getElementById("password").value;
		$.ajax({
			url: 'login',
			type: 'POST',
			data: {
				username: username,
				password: password
			},
			success: function(data){
					
				if(JSON.parse(data).flag === 1){
					window.location.href ="\evaluator.jsp"
				}
				else{
					$("#errormsg").text("Invalid User Name or Password")
				}
				
			}
			
		});
		
		
	};
  </script>
</html>