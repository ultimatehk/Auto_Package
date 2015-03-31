package cn.gyyx.test;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import cn.gyyx.AuxiliaryTools.DesCyptoUtils;
import cn.gyyx.Parameters.GyyxConfigParameters;

import com.google.gson.Gson;

public class test_modifyConfig {
	public static void main(String[] args){
    String sdkConfigFileName = "C:\\Users\\Administrator\\Desktop\\Test\\360\\res\\raw\\config.txt";
	StringBuilder content = new StringBuilder();
	BufferedReader bufferedReader = null;
	try {
		bufferedReader = new BufferedReader(
				new InputStreamReader(new FileInputStream(sdkConfigFileName),"utf-8"));
	} catch (UnsupportedEncodingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	String line = null;
	try {
		while ((line = bufferedReader.readLine()) != null) { 
			content.append(line);
		}
	} catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	try {
		bufferedReader.close();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	//String decrypted = CyptoUtils.decode("gyyxgyyx", content.toString());
	byte[] msgBytes = null;
	try {
		msgBytes = readInputStreamToByte(new FileInputStream(sdkConfigFileName));
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	String decrypted = null;
	try {
		decrypted = DesCyptoUtils.decode(new String(msgBytes, "UTF-8"), "gyyxgyyx");
	} catch (UnsupportedEncodingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	System.out.print(decrypted);
	
	try{
	Gson gson = new Gson();
	GyyxConfigParameters modifyParameters = gson.fromJson(decrypted,
			GyyxConfigParameters.class);
	//modifyParameters.setPlatformIdMd5(ConvertPlatformID.convertPlatformID("360"));
	modifyParameters.setExtendId("duoku");
	modifyParameters.setBatchNo("001");
	
	String newContent = gson.toJson(modifyParameters);
	System.out.println(" => " + newContent);
	//jiami
   String encode = DesCyptoUtils.encode(newContent, "gyyxgyyx");
	FileWriter fw = new FileWriter(sdkConfigFileName);
	fw.write(encode);
	fw.close();
	}catch (Exception e){
		
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
