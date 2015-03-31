package cn.gyyx.test;

import cn.gyyx.AuxiliaryTools.RunCmd;

public class test_apktool {
public static void main(String[] args){
	String jarPath = "C:\\Users\\Administrator\\Desktop\\Test";
	String apkPath = "C:\\Users\\Administrator\\Desktop\\batch001\\DNE_GY_141210_1.0.28.apk";
	String disk = apkPath.substring(0, 2);
	String cmdUnpack = String.format("cmd.exe /C %s && cd %s && java -jar apktool.jar d %s",disk,jarPath,apkPath);
	System.out.println(cmdUnpack);
	//RunCmd.runCmd(cmdUnpack);

}
}
