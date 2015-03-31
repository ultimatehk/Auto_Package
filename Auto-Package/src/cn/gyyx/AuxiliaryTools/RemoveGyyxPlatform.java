package cn.gyyx.AuxiliaryTools;

import java.io.File;
import java.io.ObjectInputStream.GetField;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

import org.dom4j.Element;

import cn.gyyx.AuxiliaryTools.FileUtil;

public class RemoveGyyxPlatform {

	public static void main(String[] args) {
		String pathResO = "C:\\Users\\Administrator\\Desktop\\Test\\小小战魂_GY";
		String pathResR = "C:\\Users\\Administrator\\Desktop\\Test\\resource\\gy";
		romoveGyyx(pathResO, pathResR);
		//Vector<String> difer = getDifferences(pathResO, pathResR);
//		Iterator i = difer.iterator();
//		while (i.hasNext()) {
//			System.out.println(i.next());
//		}

	}

	/**
	 * 遍历某文件夹下所有文件名
	 * 
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
		      //getFiles(file.getAbsolutePath(),list);
		      //filelist.add(file.getAbsolutePath());
		      //System.out.println("显示"+filePath+"下所有子目录及其文件"+file.getAbsolutePath());
		     }else{
		        //System.out.println("显示"+filePath+"下所有子目录"+file.getAbsolutePath());
		      filelist.add(file.getAbsolutePath());
		     }     
		    
		    }
		    return filelist;
	}

	/**
	 * 比对两个文件夹，将其中不一样的文件存在一个Vector当中
	 * 
	 * @param oringinfilePath
	 * @param resourcefilePath2
	 * @return
	 */
//	static Vector<String> getDifferences(String oringinfilePath,
//			String resourcefilePath) {
//		Vector<String> originFilelist = getFiles(oringinfilePath);
//		Vector<String> resourceFilelist = getFiles(resourcefilePath);
//		Vector<String> deferlist = new Vector<String>();
//		Iterator<String> i = originFilelist.iterator();
//		while (i.hasNext()) {
//			boolean signal = false;
//			String str1 = i.next().trim();
//			String str3 = str1.substring(str1.lastIndexOf("\\"));
//			Iterator<String> j = resourceFilelist.iterator();
//			while (j.hasNext()) {
//				String str2 = j.next().trim();
//				str2 = str2.substring(str2.lastIndexOf("\\"));
//				if (str3.equals(str2)) {
//					signal = true;  
//					break;
//				}
//			}
//			if (signal){
//				//System.out.println(str1);
//			    deferlist.add(str1);
//			}
//		}
//		return deferlist;
//	}
	public static Vector<String> getDifferences(String oringinfilePath,
			String resourcefilePath2) {
		Vector<String> originFilelist = getFiles(oringinfilePath);
		Vector<String> resourceFilelist = getFiles(resourcefilePath2);
		Vector<String> deferlist = new Vector<String>();
		Iterator<String> i = originFilelist.iterator();
		while (i.hasNext()) {
			boolean signal = false;
			String str1 = i.next().trim();
			str1 = str1.substring(oringinfilePath.length());
			Iterator<String> j = resourceFilelist.iterator();
			while (j.hasNext()) {
				String str2 = j.next().trim();
				str2 = str2.substring(resourcefilePath2.length());
				if (str1.equals(str2)) {
					signal = true;
					break;
				}
			}
			if (signal){
				//System.out.println(oringinfilePath+str1);
			    deferlist.add(oringinfilePath+str1);
			}
		}
		return deferlist;
	}

	/**
	 * 移除光宇特有文件
	 * 
	 * @param oringinfilePath
	 * @param resourcefilePath2
	 */
	public static void romoveGyyx(String oringinfilePath, String resourcefilePath) {
		Vector<String> filelist = getDifferences(oringinfilePath,resourcefilePath);
		Iterator i = filelist.iterator();
		while (i.hasNext()) {
			String delName = (String) i.next();
			System.out.println(delName);
			String delFile = delName.substring(delName.lastIndexOf("\\"));
			if(!delFile.equals("\\AndroidManifest.xml"))
			{
			File f = new File(delName);
			f.delete();
			}
			
		}
	}

}
