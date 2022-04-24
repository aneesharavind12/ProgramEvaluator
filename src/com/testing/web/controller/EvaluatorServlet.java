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

import com.testing.web.dao.Evaluate;

import javax.servlet.annotation.*;

@MultipartConfig
public class EvaluatorServlet extends HttpServlet{
	public void doPost(HttpServletRequest req, HttpServletResponse res){
		
		String problemName = req.getParameter("problemName");
		System.out.println("problemName:"+problemName);
		String numberOfTestCases = req.getParameter("numberOfRandomTestCases");
		if(numberOfTestCases.equals("")){
			numberOfTestCases = "0";
		}
		System.out.println("numberOfRandomTestCases:"+numberOfTestCases);
		String userTestCases = req.getParameter("testCase");
		System.out.println(userTestCases);
		String code = req.getParameter("code");
		System.out.println(code);
		
		try {
			PrintWriter out = res.getWriter();
//
			Evaluate test = new Evaluate();
			String result = test.evaluatingUserFile(problemName,numberOfTestCases,userTestCases,code) ;
			System.out.println(result);
			
			out.println(result);
		} catch (Exception e) {
			
			System.out.println(e);
		
		}
		
		
		
	}

}

