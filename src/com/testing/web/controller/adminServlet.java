package com.testing.web.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import java.io.*;

import java.util.Random;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.testing.web.dao.Admin;

import javax.servlet.annotation.*;

@MultipartConfig
public class adminServlet extends HttpServlet {
    
	public void doPost(HttpServletRequest req, HttpServletResponse res){
		
		String programName = req.getParameter("programName");
		System.out.println("programName:"+programName);
//		String inputDataType = req.getParameter("datatypes");
//		System.out.println("inputDataType:"+inputDataType);s
//		String inputs = req.getParameter("inputs");
//		System.out.println(inputs);
		try {
			PrintWriter out = res.getWriter();
			Part filepart = req.getPart("cFile");
			String fileName = filepart.getSubmittedFileName();
			Random ran = new Random();
			String randomNumber = Integer.toString(ran.nextInt(50));
			fileName = fileName.substring(0, fileName.length() - 2);
			fileName = fileName+randomNumber+".c";
			System.out.println(fileName);
			for (Part part : req.getParts()){
				part.write("E:\\EvaluatorDatabase\\Admin\\" + fileName);
			}
			Admin add = new Admin();
			String result = add.storingAdminFile(fileName, programName) ;
			out.println(result);
		} catch (Exception e) {
			
			System.out.println(e);
		
		}
		
		
		
	}
}
