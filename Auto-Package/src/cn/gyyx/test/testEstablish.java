package cn.gyyx.test;

import java.io.File;
import java.io.IOException;

import cn.gyyx.AuxiliaryTools.FileUtil;

public class testEstablish {
	public static void main(String[] args){
		String  path1 = "C:\\Users\\Administrator\\Desktop\\Test\\backup";
		String  path2 = "C:\\Users\\Administrator\\Desktop\\Test";
		establish(path1, path2);
		
	}
	public static void establish(String sourcePath,String targetPath){
		String trueTargetPath = targetPath+"\\workEnvironment";
		File dir = new File(trueTargetPath,"AndroidManifest.xml");
		if (!dir.exists()) {
			try {
				dir.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//FileUtil.folderCopy(sourcePath,trueTargetPath);
	}
  
}
