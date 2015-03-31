package cn.gyyx.test;

import java.io.File;
import java.util.Iterator;
import java.util.Vector;

public class testFile {
	public static void main(String[] args) {
		String pathResO = "C:\\Users\\Administrator\\Desktop\\Test\\xxzh-gyyx-signed\\res";
		String pathResR = "D:\\java\\GyyxAndroidBuild4Platforms\\AndroidApk\\resource\\gy\\res";
		// removeGyyx(pathResO, pathResR);
		Vector<String> difer = new Vector<String>();
				difer = getFiles(pathResO);
		Iterator i = difer.iterator();
		while (i.hasNext()) {
			System.out.println(i.next());
		}

	}
	
	/**
	 * 遍历某文件夹下所有文件名
	 * @param filePath
	 * @return
	 */
	 static Vector<String> getFiles(String filePath){
			Vector<String> filelist = new Vector<String>();
		    File root = new File(filePath);
		    File[] files = root.listFiles();
		    
		    for(File file:files){     
		     if(file.isDirectory()){
	          /*
		       * 递归调用
		       */
		      filelist.addAll(getFiles(file.getAbsolutePath()));
		      //filelist.add(file.getAbsolutePath());
		      //System.out.println("显示"+filePath+"下所有子目录及其文件"+file.getAbsolutePath());
		     }else{
		      //System.out.println("显示"+filePath+"下所有子目录"+file.getAbsolutePath());
		    	filelist.add(file.getAbsolutePath());
		     }     
		    
		    }
		    return filelist;
	}
}
