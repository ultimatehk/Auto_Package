package cn.gyyx.test;

import cn.gyyx.AuxiliaryTools.RunCmd;

public class test_autoSign {
	
	public static void main(String[] args){
	String disk = "C:";
	String currentPath = "C:\\Users\\Administrator\\Desktop\\batch001\\apk";
	String keystorePwd = "1986217";
	String keystoreAlias = "release";
	String apk = "BDY_proj.android.baidu_mubao_unsigned.apk";
	String cmdSign = String.format("cmd.exe /C %s && cd %s && jarsigner -verbose -keystore autopackage.keystore -storepass %s  -signedjar %s  -digestalg SHA1 -sigalg MD5withRSA %s %s",
            disk,currentPath,
            keystorePwd,
            apk.substring(0, apk.lastIndexOf("unsigned")-1)+".apk",
            apk,
            keystoreAlias);
	RunCmd.runCmd(cmdSign);
	}
}
