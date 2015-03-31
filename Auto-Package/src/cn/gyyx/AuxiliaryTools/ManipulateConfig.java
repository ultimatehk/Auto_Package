package cn.gyyx.AuxiliaryTools;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import android.os.Bundle;
import cn.gyyx.Parameters.GyyxConfigParameters;

import com.google.gson.Gson;

public class ManipulateConfig {
	public static void main(String[] args){
		String filePath = "C:\\Users\\Administrator\\Desktop\\XML解析试验\\config.txt";	
		modifyExtendId("123", filePath);
		String[] bundle = getJsonAndExtendId(filePath);
		System.out.println(bundle[0]);
		System.out.println(bundle[1]);
	}
	
	/**
	 * 读取配置文件并返回JSon值与ExtendId的值
	 * @param filePath
	 * @return
	 */
	public static String[] getJsonAndExtendId(String filePath) {
		String[] bundle = new String[2];
		StringBuilder content = new StringBuilder();
		BufferedReader bufferedReader = null;
		try {
			bufferedReader = new BufferedReader(new InputStreamReader(
					new FileInputStream(filePath), "utf-8"));

			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				content.append(line);
			}

			bufferedReader.close();

			// String decrypted = CyptoUtils.decode("gyyxgyyx",
			// content.toString());
			byte[] msgBytes = null;
			msgBytes = readInputStreamToByte(new FileInputStream(filePath));
			String decrypted = null;
			decrypted = DesCyptoUtils.decode(new String(msgBytes, "UTF-8"),"gyyxgyyx");
			
			Gson gson = new Gson();
			GyyxConfigParameters modifyParameters = gson.fromJson(decrypted,GyyxConfigParameters.class);
		    String extendId = modifyParameters.getExtendId();
		    
		    bundle[0] = decrypted;
		    bundle[1] = extendId; 
		    		   			
		} catch (Exception e) {
           e.printStackTrace();
		}
		 return bundle;
	}
	
	/**
	 * 修改extend_id
	 * @param extendId
	 * @param writePath
	 */
	public static void modifyExtendId(String extendId,String writePath){
		      String[] bundle = getJsonAndExtendId(writePath);
		      String decrypted = bundle[0]; 
		      decrypted = decrypted.replace(bundle[1],extendId);
		      String encode = DesCyptoUtils.encode(decrypted, "gyyxgyyx");
		      FileWriter fw = null;		
		      try{
		      fw = new FileWriter(writePath);
		      fw.write(encode);
		      fw.close();
		      }catch(Exception e){
		    	  e.printStackTrace();
		      }
				
	}

	public static byte[] readInputStreamToByte(InputStream in)
			throws IOException {
		int length = 0;
		byte buffer[] = new byte[1024];
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		while ((length = in.read(buffer)) != -1) {
			out.write(buffer, 0, length);
		}

		return out.toByteArray();
	}
}
