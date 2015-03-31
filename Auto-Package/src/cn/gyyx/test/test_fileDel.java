package cn.gyyx.test;

import cn.gyyx.AuxiliaryTools.FileUtil;

public class test_fileDel {
	public static void main(String[] args) throws Exception {
		 FileUtil.trace("警告：请按照您自己的要求修改程序，风险自负。"); 
		 FileUtil.delete("C:\\Users\\Administrator\\Desktop\\Test\\360_test\\res\\layout", "*mzw*.xml"); 
	 }
}
