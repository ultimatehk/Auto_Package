package cn.gyyx.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;

import cn.gyyx.Parameters.GyyxConfigParameters;

public class test_JSONConfig {
public static void main(String[] args){
	String configContents = null;
	String downloadPath = null;
	try{
	File parameterConfig = new File("C:\\Users\\Administrator\\Desktop\\Test"+"\\ParameterConfig.txt"); 
	BufferedReader br = new BufferedReader(new FileReader(parameterConfig));
	String line = null;
	while ((line = br.readLine()) != null) {
		configContents = line;
	}
	}catch(IOException e){
		e.printStackTrace();
	}
	System.out.println(configContents);
	
	Gson gson =new Gson();
	GyyxConfigParameters modifyParameters = gson.fromJson(configContents,GyyxConfigParameters.class);
    downloadPath = modifyParameters.getDownloadPath(); 
	System.out.println(downloadPath);
	
}
}
