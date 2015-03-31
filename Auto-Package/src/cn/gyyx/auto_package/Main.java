package cn.gyyx.auto_package;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.gyyx.Parameters.GyyxConfigParameters;

import com.google.gson.Gson;

public class Main {

	public static final Logger LOGGER = LoggerFactory.getLogger("GYYX");

	public static void main(String[] args) {
		System.out.println("====**===分渠道自动打包工具===**====");
		System.out.println("====**====By Huangke====**====");
		LOGGER.debug("Start package...");

		String apk = args[0];//母包绝对路径
		String factory = args[1];//工作目录位置
		String sign = args[2];//决定打出的是渠道包还是推广包
		
		String keyStoreAlias = args[3];//keystore指定别名
		String keystorePassWord = args[4];//keystore密码
		
		String isSecret = args[5];
		String productPath = args[6];
		String downloadPath = null;
		
		//配置下载路径
		String configContents = null;
		try{
		File parameterConfig = new File(factory+"\\ParameterConfig.txt"); 
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
		Main.LOGGER.debug("downloadPath is: {}", downloadPath);
		
		if (sign.equals("PlatformSDK")) {
			new AutoPackageVPlatformSDK(apk, factory,keyStoreAlias,keystorePassWord,isSecret,downloadPath,productPath).packageForPlatforms();
		} else {
			new AutoPackageVExtend(apk, factory,keyStoreAlias,keystorePassWord,isSecret,downloadPath,productPath).packageForPlatforms();
		}
	}
}