package cn.gyyx.test;

import cn.gyyx.AuxiliaryTools.RunCmd;

public class test_batchAutoSign {
    final static String disk = "c:";
    final static String apkDownload = "C:\\Users\\Administrator\\Desktop\\batch001\\apk";
    final static String keystorePwd = "1986217";
    final static String keystoreAlias = "release";
    
	
	public static void main(String[] args){
		String cmdBatchAutoSign = String.format("cmd.exe /C %s && cd %s && setlocal EnableDelayedExpansion && "
			    + "for %%i in (*unsigned.apk) do (set name=%%i && set modify=%%%%i:_unsigned=%%"
			   // + "&& echo modify"
			   // + "&& echo %%name%%"
			   // + "&&echo %%i"
			    + "&&jarsigner -verbose -keystore autopackage.keystore -storepass %s  -signedjar %%i -digestalg SHA1 -sigalg MD5withRSA %%i %s"
			    + ")",
			    disk,apkDownload,keystorePwd,keystoreAlias
			    );
		System.out.println(cmdBatchAutoSign);
    RunCmd.runCmd(cmdBatchAutoSign);
	}
}
