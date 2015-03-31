package cn.gyyx.test;

import java.io.File;
import java.io.FileOutputStream;
import java.security.acl.LastOwnerException;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import cn.gyyx.AuxiliaryTools.XmlAnalysis;

public class test_modifyPackageName {
	public static void main(String[] args){
	String urlOrigin = "C:\\Users\\Administrator\\Desktop\\Test\\最新Demo\\AdapterDemo_Gy\\AndroidManifest.xml" ;
	String urlTarget = "C:\\Users\\Administrator\\Desktop\\Test\\最新Demo\\AdapterDemo_Gy\\AndroidManifest.xml";	
		
	String oringinName = null;
	String targetName = null;
	String oringinProvider = null;
	String targetProvider = null;
	String platformId = "uc";
 
	try{
	Document doc = cn.gyyx.AuxiliaryTools.XmlAnalysis.doAnalysis(urlOrigin);
	Element root = cn.gyyx.AuxiliaryTools.XmlAnalysis.getRoot(doc);
	//修改包名
	String tempName = root.attribute("package").toString();
	oringinName =tempName.substring(tempName.lastIndexOf("value")).replace("\"", "").replace("]", "").replace("value ","");
	System.out.println(oringinName);
	targetName = oringinName + "." + platformId;
	System.out.println(targetName);
	root.attribute("package").setText(targetName);
	System.out.println(root.attribute("package").toString());
	//修改提供商
	String tempProvider = root.element("application").element("provider").attribute("authorities").toString();
	oringinProvider = tempProvider.substring(tempProvider.lastIndexOf("value")).replace("\"", "").replace("]", "").replace("value ","");
	System.out.println(oringinProvider);
	targetProvider = oringinProvider + "." + platformId;
	System.out.println(targetProvider);
	root.element("application").element("provider").attribute("authorities").setText(targetProvider);
	System.out.println(root.element("application").element("provider").attribute("authorities").toString());
	
	File file = new File(urlTarget);
	OutputFormat format = OutputFormat.createPrettyPrint();
	format.setEncoding("UTF-8");
	FileOutputStream fos = null;
	fos = new FileOutputStream(file);
	XMLWriter writer = new XMLWriter(fos,format);
	writer.write(doc);
	writer.close();
	
	}catch(Exception e){
    	e.printStackTrace();
    }
}
}
