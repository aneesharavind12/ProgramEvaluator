package com.testing.web.dao;


import java.io.FileReader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Programs {
	static public JSONArray getPrograms(){
		//read the files and get the program name from the file by iterating 
		FileReader file;
		try {
			file = new FileReader("E:/EvaluatorDatabase/Admin/admin.json");
			JSONArray adminData = (JSONArray) new JSONParser().parse(file);
			JSONObject displayObject = new JSONObject();
			JSONArray displayArray = new JSONArray();
			for(Object obj : adminData){
				displayObject = (JSONObject)obj;
				displayArray.add(displayObject.get("programName"));
			}
			return displayArray;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e);
		}
		return null;
	}
	
	static public int getInputNumber(){
		FileReader file;
	    return 0;
	}
}
