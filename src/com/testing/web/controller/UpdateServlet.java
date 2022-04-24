package com.testing.web.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.testing.web.dao.Admin;

public class UpdateServlet extends HttpServlet {
	public void doPost(HttpServletRequest req, HttpServletResponse res){
		PrintWriter out;
		try {
			out = res.getWriter();
			String values = req.getParameter("inputs");
			System.out.println(values);
			String programName = req.getParameter("programName");
			System.out.println(programName);
			Admin add = new Admin();
			String result = add.storeRangeofVariables(values,programName) ;
			out.println(result);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
}
