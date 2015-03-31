package cn.gyyx.AuxiliaryTools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileUtil {
	/**
	 * * 删除指定目录下符合指定<param>pattern</param>的文件或目录
	 * 
	 * @param path
	 *            指定的目录
	 * @param pattern
	 *            要删除的目标，可以含通配符(*) 如：x*d\*.bak，为指定目录下以x开头d结尾的目录下的所有bak文件
	 * @return * true, 没有错误；false， 发生了错误
	 */

	public static boolean delete(String path, String pattern) throws Exception {
		if (path == null || pattern == null)
			throw new IllegalArgumentException("参数为空");
		boolean result = true;

		File root = new File(path);
		if (!root.isDirectory() || !root.exists())
			throw new IllegalArgumentException("不是目录或者目录不存在");

		List<File> folderBuffer = new ArrayList<File>();
		folderBuffer.add(root);

		String[] ps = pattern.split("\\" + File.separator);
		for (int i = 0; i < ps.length; i++) {
			List<File> tmpList = new ArrayList<File>();
			while (folderBuffer.size() > 0) {
				File folder = folderBuffer.remove(0);
				List<File> fileMatched = applyPattern(folder, ps[i]);
				tmpList.addAll(fileMatched);
			}
			folderBuffer.addAll(tmpList);
			if (folderBuffer.size() == 0)
				return true;
		}
		for (int i = 0; i < folderBuffer.size(); i++) {
			boolean r = deleteFile(folderBuffer.get(i));
			if (!r)
				return r;
		}

		return result;
	}

	public static boolean deleteFile(File f) {
		trace("DELETE: " + f.getPath());
		if (f.isFile())
			return f.delete();
		// 目录也有可能满足通配符！
		// 是否执行删除带有文件的目录？自己判断，这里直接删，不管成功失败
		 return f.delete(); 
	 } 
//	 /**  
//	   * 将含通配符（*）的字符串转变成正则表达式 
//	   * @param pattern  
//	   * @return 
//	   * @remark  
//	   * 这里做得比较简单，只是替换了"."和"*"，实际情况可能还有其他字符需要替换  
//	  */
//	 private static Pattern createPattern(String pattern) { 
//		 String _pattern = pattern.replaceAll("\\.", "\\\\.").replaceAll("\\*", ".*");
//		 Pattern p = Pattern.compile(_pattern, Pattern.CASE_INSENSITIVE); 
//		 return p;
//	 }
	 
		
		 /**文件copy
		  * param src,des
		  * return ture 成功。false 失败
		  */
		 public static boolean fileCopy(String src,String des){
			 File srcFile=new File(src);
			 File desFile=new File(des);
			 byte[]b=new byte[1024];
			 try {
				 FileInputStream fis=new FileInputStream(srcFile);
				 FileOutputStream fos=new FileOutputStream(desFile,false);
				 while(true){
					 int i=fis.read(b);
					 if(i==-1)break;
					 fos.write(b,0,i);
					 //cn.gyyx.auto_package.Main.LOGGER.debug("        copying "+src+"........");
				 }
				 fos.close();
				 fis.close();
				 return true;
			 }catch (Exception e){ 
				 e.printStackTrace();
			 }
			 return false;
	    }
		 
		 /**
		  * 文件夹copy
		  * @param src
		  * @param des
		  * @return
		  */
		 public static boolean folderCopy(String src,String des){
			 File srcFile=new File(src);
			 File desFile=new File(des);
			 File []files=srcFile.listFiles();
			 boolean flag = false;
			 if(!desFile.exists())desFile.mkdir();
			 for(int i=0;i<files.length;i++){
				 String path=files[i].getAbsolutePath(); 
				 if(files[i].isDirectory()){
					 File newFile=new File("path.replace(src,des)");
					 if(!newFile.exists())newFile.mkdir();//不存在新建文件夹
					 folderCopy(path,path.replace(src,des));
				 }
				 else
					 flag=fileCopy(path,path.replace(src,des));//文件复制函数
			 }
			 return flag;
		 }
	 
		  
		 /*
		  * 通过递归得到某一路径下所有的目录及其文件
		  */
		 public static ArrayList<String> getFiles(String filePath){
			ArrayList<String> filelist = new ArrayList<String>();
		    File root = new File(filePath);
		    File[] files = root.listFiles();
		    
		    for(File file:files){     
		     if(file.isDirectory()){
	          /*
		       * 递归调用
		       */
		      getFiles(file.getAbsolutePath());
		      filelist.add(file.getAbsolutePath());
		     // System.out.println("显示"+filePath+"下所有子目录及其文件"+file.getAbsolutePath());
		     }else{
		     // System.out.println("显示"+filePath+"下所有子目录"+file.getAbsolutePath());
		     }     
		    }
            
		    return filelist;
		 }
		 
	 private static List<File> applyPattern(File folder, String pattern) {
		 List<File> list = new ArrayList<File>();
		 if( folder == null || folder.isFile() || pattern == null )
			 return list; // 空List
		 
		 Pattern p = createPattern(pattern);  
		 File[] files = folder.listFiles();
		 for(int i = 0; i < files.length; i ++) {
			 File file = files[i];
			 String name = file.getName();
			 Matcher m = p.matcher(name);
			 if( m.matches() ) {
				 list.add(file);
				 trace("[ M] Folder: " + folder + ", Pattern: " + pattern + ", File: " + name + "             <-----Matched----"); 
				 
			 }
			 else{
				 trace("[NM] Folder: " + folder + ", Pattern: " + pattern +        ", File: " + name);
			 } 
		 }
			return list; 
	 }
	 
//	 public static void trace(String message) {
//		 System.out.println(message); 
//	 }
	 
	 public static void main(String[] args) throws Exception {
		 trace("警告：请按照您自己的要求修改程序，风险自负。"); 
		 FileUtil.delete("F:\\temp", "*x1\\*.exe"); 
	 }
//	return f.delete();
//	}

	/**
	 * 将含通配符（*）的字符串转变成正则表达式
	 * 
	 * @param pattern
	 * @return
	 * @remark 这里做得比较简单，只是替换了"."和"*"，实际情况可能还有其他字符需要替换
	 */
	private static Pattern createPattern(String pattern) {
		String _pattern = pattern.replaceAll("\\.", "\\\\.").replaceAll("\\*",
				".*");
		Pattern p = Pattern.compile(_pattern, Pattern.CASE_INSENSITIVE);
		return p;
	}
//
//	private static List<File> applyPattern(File folder, String pattern) {
//		List<File> list = new ArrayList<File>();
//		if (folder == null || folder.isFile() || pattern == null)
//			return list; // 空List
//
//		Pattern p = createPattern(pattern);
//		File[] files = folder.listFiles();
//		for (int i = 0; i < files.length; i++) {
//			File file = files[i];
//			String name = file.getName();
//			Matcher m = p.matcher(name);
//			if (m.matches()) {
//				list.add(file);
//				trace("[ M] Folder: " + folder + ", Pattern: " + pattern
//						+ ", File: " + name + "             <-----Matched----");
//
//			} else {
//				trace("[NM] Folder: " + folder + ", Pattern: " + pattern
//						+ ", File: " + name);
//			}
//		}
//		return list;
//	}

	public static void trace(String message) {
		System.out.println(message);
	}

//	public static void main(String[] args) throws Exception {
//		trace("警告：请按照您自己的要求修改程序，风险自负。");
//		FileUtil.delete("F:\\temp", "*x1\\*.exe");
//	}

}