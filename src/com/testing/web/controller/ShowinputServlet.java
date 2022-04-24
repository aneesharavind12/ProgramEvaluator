package com.testing.web.controller;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.testing.web.dao.Admin;

public class ShowinputServlet extends HttpServlet {
	public void doPost(HttpServletRequest req, HttpServletResponse res){
		PrintWriter out;
		try {
			out = res.getWriter();
			String programName = req.getParameter("problemName");
			String numberOfUserTestCases = req.getParameter("numberOfUserTestCases"); 
			System.out.println(programName);
			System.out.println(numberOfUserTestCases);
			Admin add = new Admin();
			String result = add.getNumberOfInputs(programName,numberOfUserTestCases) ;
			out.println(result);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
}
