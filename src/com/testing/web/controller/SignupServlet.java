package com.testing.web.controller;

import java.io.*;
import java.util.Iterator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class SignupServlet extends HttpServlet {

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String uname = req.getParameter("uname");
		String pwd = req.getParameter("pwd");
		PrintWriter out = res.getWriter();
		JSONArray userArray = new JSONArray();
		int flag = 0;
		try{
			FileReader file1 = new FileReader("E:/UserData.json");
            userArray = (JSONArray) new JSONParser().parse(file1);

			for (Object obj : userArray) {
				JSONObject user = (JSONObject) obj;
				if (user.get("username").equals(uname)) {
					out.println("<script type=\"text/javascript\">");
					out.println("alert('Username already exists');");
					out.println("location='index.jsp';");
					out.println("</script>");
					flag = 1;
				}
			}
		}
		catch(Exception e){
			System.out.println(e);
		}
		if(flag==0){
			JSONObject user = new JSONObject();
			user.put("username", uname);
			user.put("password", pwd);
			userArray.add(user);
			try {
				File f = new File("E:/UserData.json");
				f.createNewFile();
				FileWriter file = new FileWriter(f);
				file.write(userArray.toJSONString());
				file.close();
				RequestDispatcher rd = req.getRequestDispatcher("/index.jsp");
                rd.forward(req, res);
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}
}