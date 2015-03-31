package cn.gyyx.test;

import cn.gyyx.AuxiliaryTools.RunCmd;

public class test_delFolder {
	public static void main(String[] args){
	String decodePath = "C:\\Users\\Administrator\\Desktop\\Test\\lalala";
	String cmdKey = String.format("cmd.exe /C rd /s/q %s", decodePath);
	RunCmd.runCmd(cmdKey);
	}
}
