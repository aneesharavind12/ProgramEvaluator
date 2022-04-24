package com.testing.web.dao;
import org.json.simple.JSONArray;

import java.io.*;

import org.json.simple.*;

public class EvaluatorThread implements Runnable {
	private JSONObject checkObject = new JSONObject();
	private String userExeFileName;
	
	public EvaluatorThread(JSONObject checkObject,String userExeFileName){
		this.checkObject = checkObject;
        this.userExeFileName = userExeFileName;
	}
	
	@Override
	
	public void run() {
//		 String userExeFileName = Utility.compile(cFileName,"E:/EvaluatorDatabase/User/");
         
         
         
         String input = (String) checkObject.get("input");

         File dir = new File("E:/EvaluatorDatabase/User/");
         Process run;
         try {
             run = Runtime.getRuntime().exec("cmd /C E:/EvaluatorDatabase/User/" + userExeFileName, null, dir);
             OutputStream os = run.getOutputStream();
             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
 
             
 
             bw.write(input);
 
             bw.close();
 
             BufferedReader in = new BufferedReader(new InputStreamReader(run.getInputStream()));
             String line = null;
             String output = "";
 
             while ((line = in.readLine()) != null) {
                 output = output + line + " ";
             }
             output = output.trim();
//             System.out.println(output);
 
             checkObject.put("Your Output",output);
             if(checkObject.get("ExpectedOutput").equals(output)){
                 checkObject.put("Result","Success");
                 Evaluate.successCaseArray.add(checkObject);
             }
             else {
                 checkObject.put("Result", "Failed");
                 Evaluate.failCaseArray.add(checkObject);
             }
//             System.out.println(Evaluate.successCaseArray);
//             System.out.println(Evaluate.failCaseArray);

//             File deleteUserExe = new File("E:\\EvaluatorDatabase\\User\\"+ userExeFileName);
//			 deleteUserExe.delete();
         } catch (Exception e) {
            
             System.out.println(e);
         }
		
	}

}
