package com.testing.web.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class LoginServlet extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        String uname = req.getParameter("username");
        String pwd = req.getParameter("password");
        PrintWriter out = res.getWriter();

        JSONArray userArray = new JSONArray();
        JSONParser jsonParser = new JSONParser();
        int flag = 0;
        try {
            FileReader file = new FileReader("E:/UserData.json");
            userArray = (JSONArray) jsonParser.parse(file);
            for (Object obj : userArray) {
                JSONObject user = (JSONObject) obj;
                String username = (String) user.get("username");
                String password = (String) user.get("password");
                if (username.equals(uname) && password.equals(pwd)) {
                    flag = 1;
                    req.getSession().setAttribute("user", username);
                } 
                }
            
           
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        JSONObject obj = new JSONObject();
        if (flag == 0){
        	obj.put("flag", flag);
        	
        }
        else if (flag == 1){
        	obj.put("flag", flag);
        }
        out.println(obj.toString());
        out.flush();
         }
}

