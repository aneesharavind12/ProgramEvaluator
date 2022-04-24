package com.testing.web.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Admin {
	
	
	public String storingAdminFile(String cFileName,String programName){
		int flag = 0;
		int inputOrder = 1;
		String result = Utility.compile(cFileName,"E:\\EvaluatorDatabase\\Admin\\");
		if(result.equals("Compilation Error")){
			File deleteAdminCFile = new File("E:\\EvaluatorDatabase\\Admin\\"+ cFileName);
			deleteAdminCFile.delete();
			return result;
		}
	

		else {
			String exeFileName = result;
			File delete = new File("E:\\EvaluatorDatabase\\Admin\\"+ exeFileName);
			delete.delete();

         
			
			
			
            JSONArray storeUserInputsArray = new JSONArray();
            BufferedReader bufReader;
            try {
                bufReader = new BufferedReader(new FileReader("E:\\EvaluatorDatabase\\Admin\\"+ cFileName));
                ArrayList<String> listOfLines = new ArrayList<>();

                String line = bufReader.readLine();

                while (line != null) {
                 
                    line = line.trim();
                    if(!(line.trim().isEmpty())){
                      listOfLines.add(line);
                    }
                    
                  
                  
                  line = bufReader.readLine();
                }
            
                bufReader.close();
                

             
                
               System.out.println(listOfLines);
               
                ArrayList<String> scanf = new ArrayList<>();
                
           

                for(int i = 0;i<listOfLines.size();i++){
                    
                    if(listOfLines.get(i).trim().contains("scanf")){
                        scanf.add(listOfLines.get(i));
                    }
                  
                    
                }
                
                
                JSONArray inputInformation = new JSONArray();
                
                // System.out.println(scanf);
                
                for(String element : scanf){
                     JSONObject inputdata = new JSONObject();
                     inputdata.put("inputNumber", Integer.toString(inputOrder));
                     element = element.substring(element.indexOf("(") + 1, element.indexOf(")"));
                     String getFormatSpecifier = element.split(",")[0];
                     
                     getFormatSpecifier = getFormatSpecifier.replace("'", "").replace("\"", "").replace("`", "");
                     System.out.println(getFormatSpecifier);
                     getFormatSpecifier = getFormatSpecifier.trim();
                     if(getFormatSpecifier.equals("%d")){
                         inputdata.put("inputDataType", "int");
                     }
                     else if(getFormatSpecifier.equals("%c")){
                        inputdata.put("inputDataType", "char");
                     }
                     else if(getFormatSpecifier.equals("%s")){
                        inputdata.put("inputDataType", "string");
                     }
                     else if(getFormatSpecifier.equals("%f")){
                        inputdata.put("inputDataType", "float");
                     }
                     else if(getFormatSpecifier.equals("%lf")){
                        inputdata.put("inputDataType", "double");
                     }

                     String getVariable = element.split(",")[1];
                     getVariable = getVariable.replace("&", "");
                     getVariable = getVariable.trim();

                     if((getVariable.contains("[")) && (getVariable.contains("]"))){
                         getVariable = getVariable.substring(0,getVariable.indexOf("["));
                         inputdata.put("Type of Input","arrayInput");
                         inputdata.put("Variable Name",getVariable);
                     }
                     else{
                        inputdata.put("Type of Input","normalInput");
                        inputdata.put("Variable Name",getVariable);
                     }
                     storeUserInputsArray.add(inputdata);
                     inputOrder++;
                }
                System.out.println(inputInformation);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
//                try {
//                    userInputsArray = (JSONArray) parser.parse(inputs);
//                   
//                    
//                    String read = "";
//                    int id = 1;
//                    for(Object obj : userInputsArray){
//                        getUserInputsArray = (JSONArray) obj;
//                        
//                        JSONObject storeUserInputsObject = new JSONObject();
//                        storeUserInputsObject.put("id",Integer.toString(id));
//                        read = (String)getUserInputsArray.get(0);
//                        if (read.equals("")){
//                            return "Give Input";
//                            
//                        }
//                        read = read.trim();
//                        storeUserInputsObject.put("inputDataType",read);
//                        
//                        read = (String)getUserInputsArray.get(1);
//                        read = read.trim();
//                        storeUserInputsObject.put("Type of Input",read);
//    
//                        if(read.equals("arrayInput")){
//                            String sizeId = (String)getUserInputsArray.get(2);
//                            sizeId = sizeId.trim();
//                            int temp = Integer.parseInt(sizeId) + 1;
//                           
//                            storeUserInputsObject.put("ArraySizeId",Integer.toString(temp));
//                        }
//    
//                        read = (String)getUserInputsArray.get(3);
//                        read = read.trim();
//                        storeUserInputsObject.put("Minimum Value",read);
//    
//                        read = (String)getUserInputsArray.get(4);
//                        read = read.trim();
//                        storeUserInputsObject.put("Maximum Value",read);
//    
//                        storeUserInputsArray.add(storeUserInputsObject);
//                        numberOfInputs++;
//                        id++;
//    
    
//                }
//                } catch (ParseException e1) {
//                    e1.printStackTrace();
//                    
//                }
 

            
			
			try {
                
                FileReader file = new FileReader("E:/EvaluatorDatabase/Admin/admin.json");
				JSONArray adminData = (JSONArray) new JSONParser().parse(file);
				int maxId = 0;
				 for (Object obj : adminData) {
	                    JSONObject jsonObj = (JSONObject) obj;
	                    if (maxId < Integer.parseInt((String) jsonObj.get("id"))) {
	                        maxId = Integer.parseInt((String) jsonObj.get("id"));
	                    }
	                }
				JSONObject newData = new JSONObject();
				newData.put("id",Integer.toString(maxId + 1));
				newData.put("programName", programName);
				
				newData.put("cFileName",cFileName);
				newData.put("numberOfInputs",Integer.toString(inputOrder - 1));
				newData.put("exeFileName",exeFileName);
                newData.put("inputs", storeUserInputsArray);
                
				adminData.add(newData);
				System.out.println(adminData);
				
				FileWriter f = new FileWriter("E:/EvaluatorDatabase/Admin/admin.json");
	            f.write(adminData.toJSONString());
	            f.close();
				
				
			} catch (Exception e) {
				System.out.println(e);
				flag = 1;
			}
			
			if(flag == 1){
				File f = new File("E:/EvaluatorDatabase/Admin/admin.json");
				try {
					f.createNewFile();
					JSONArray adminData = new JSONArray();
					JSONObject newData = new JSONObject();
					newData.put("id","1");
					newData.put("programName", programName);
					newData.put("numberOfInputs",Integer.toString(inputOrder - 1));
					newData.put("cFileName",cFileName);
					// newData.put("inputDataType",inputDataType);
					newData.put("exeFileName",exeFileName);
                    newData.put("inputs",storeUserInputsArray );
					adminData.add(newData);
					System.out.println(adminData);
					FileWriter f1 = new FileWriter("E:/EvaluatorDatabase/Admin/admin.json");
					f1.write(adminData.toJSONString());
					f1.close();
					
				} catch (Exception e) {
					System.out.println(e);
					
				}
			}
			
			return storeUserInputsArray.toJSONString();
			
		}
	}
	
	public String storeRangeofVariables(String values,String programName){
		
		FileReader file;
		try {
			file = new FileReader("E:/EvaluatorDatabase/Admin/admin.json");
			JSONArray adminData = (JSONArray) new JSONParser().parse(file);
			JSONArray getInputs = new JSONArray();
			JSONObject readObject = new JSONObject();
			for(Object obj : adminData){
				readObject = (JSONObject) obj;
				if(readObject.get("programName").equals(programName)){
					getInputs = (JSONArray) readObject.get("inputs");
					break;
				}
			}
			JSONParser parser = new JSONParser();
			JSONArray getRange = new JSONArray();
			getRange =  (JSONArray) parser.parse(values);
			JSONArray getArray = new JSONArray();
			JSONArray storeValues = new JSONArray();
			String read = "";
			String typeOfInput = "";
			int count = 0;
			for(Object obj1 : getRange){
				getArray = (JSONArray) obj1;
				JSONObject storeTestCasesObject = new JSONObject();
				storeTestCasesObject = (JSONObject)getInputs.get(count);
				typeOfInput = (String) storeTestCasesObject.get("Type of Input");
				System.out.println(getArray);
				read = (String)getArray.get(0);
				if (read.equals("")){
				break;
				}
				read = read.trim();
				storeTestCasesObject.put("Minimum Value",read);
				read = (String)getArray.get(1);
				read = read.trim();
				storeTestCasesObject.put("Maximum Value",read);
				if(typeOfInput.equals("arrayInput")){
					read = (String)getArray.get(2);
					read = read.trim();
					storeTestCasesObject.put("arraySize",read);
				}
				count++;
				storeValues.add(storeTestCasesObject);
			}
			
			JSONObject updateObject = new JSONObject();
			for(Object obj : adminData){
				updateObject = (JSONObject) obj;
				if(updateObject.get("programName").equals(programName)){
					updateObject.put("inputs",storeValues);
					break;
				}
			}
			System.out.println(adminData);
			FileWriter f1 = new FileWriter("E:/EvaluatorDatabase/Admin/admin.json");
			f1.write(adminData.toJSONString());
			f1.close();
			return "Updated Successfully";
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e);
			return "Error while Updating";
			
		}  
		
		
		
	}
	
	public String getNumberOfInputs(String programName,String numberOfTestCases){
		FileReader file;
		try {
			file = new FileReader("E:/EvaluatorDatabase/Admin/admin.json");
			JSONArray adminData = (JSONArray) new JSONParser().parse(file);
			String getNumberOfInputs  = "";
			JSONObject readObject = new JSONObject();
			for(Object obj : adminData){
				readObject = (JSONObject) obj;
				if(readObject.get("programName").equals(programName)){
					getNumberOfInputs = (String) readObject.get("numberOfInputs");
					break;
				}
				
			}
			int numberOfInputs = Integer.parseInt(getNumberOfInputs);
			JSONArray inputArray = new JSONArray();
			inputArray.add(Integer.parseInt(numberOfTestCases));
			
			for(int i = 0 ;i < numberOfInputs;i++){
				inputArray.add("Input" +" " + Integer.toString(i + 1));
			}
			
			System.out.println(inputArray);
			return inputArray.toJSONString();
		}catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println(e);
			}
		return null;
	}

}
