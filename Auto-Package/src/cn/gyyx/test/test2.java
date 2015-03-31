package cn.gyyx.test;

import cn.gyyx.AuxiliaryTools.RemoveGyyxPlatform;

public class test2 {
	public static void main(String[] args){
    String path1 = "C:\\Users\\Administrator\\Desktop\\Test\\proj.android.guangyu_tongyi11101";
    String path2 = "C:\\Users\\Administrator\\Desktop\\Test\\resource\\gy";
    RemoveGyyxPlatform.getDifferences(path1, path2);
    RemoveGyyxPlatform.romoveGyyx(path1, path2);

	}
}
