package cn.gyyx.test;

import cn.gyyx.AuxiliaryTools.RunCmd;

public class test_moveFile {
	public static void main(String[] args){
		String copy_source = "C:\\Users\\Administrator\\Desktop\\Test\\lyjl_UC\\dist\\*.apk";
		String copy_destiny = "C:\\Users\\Administrator\\Desktop\\Test\\apk";
		String cmdMove = String.format("cmd.exe /C move %s %s", copy_source,copy_destiny);
		//RunCmd.runCmd(cmdMove);

	}

}
