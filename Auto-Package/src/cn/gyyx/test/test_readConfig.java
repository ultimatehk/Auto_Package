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



public class test_readConfig {
	public static void main(String[] args){
	    String sdkConfigFileName = "C:\\Users\\Administrator\\Desktop\\Test\\BDY_proj.android.guangyu_tongyi\\res\\raw\\config.txt";
	    String createConfig = "C:\\Users\\Administrator\\Desktop\\Test\\proj.android.gyyx\\res\\raw\\config2.txt";
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
	    //decrypted = decrypted.replace( "ba3c2edcd9ce27007d5099f01b48da95","2e2a3e9f557434582d5fd9445b135fbc");
		System.out.print(decrypted);
		
//		String encode = DesCyptoUtils.encode(decrypted, "gyyxgyyx");
//		FileWriter fw = null;
//		try {
//			fw = new FileWriter(createConfig);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		try {
//			fw.write(encode);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		try {
//			fw.close();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
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
