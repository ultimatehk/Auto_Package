package cn.gyyx.test;

public class testGetRealName {
  public static void main(String[] args){
	    String apkName = "C:\\Users\\Administrator\\Desktop\\Test\\xxzh-gyyx-signed.apk";
	    String[] apkFilePath = apkName.split("\\\\");
		String shortApkName = apkFilePath[apkFilePath.length - 1];
		System.out.println(shortApkName.split(".apk")[0]);
  }
}
