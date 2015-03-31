package cn.gyyx.test;

public class test_getPath {
	public static void main(String[] args){
String path = "";
String apkName = "C:\\Users\\Administrator\\Desktop\\batch001\\DNE_GY_141210_1.0.28.apk";
path = apkName.substring(0, apkName.lastIndexOf("\\"));
System.out.println(path);

	}
	}
