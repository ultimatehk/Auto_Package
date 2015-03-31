package cn.gyyx.test;

import java.io.File;

public class test_establishDownload {
	public static void main(String[] args){
		String apkDownload = "C:\\apkDownload\\001";
		File fdown = new File(apkDownload);
		if (!fdown.exists()) {
			fdown.mkdirs();
			System.out.println("建立成功~");
		}
	}

}
