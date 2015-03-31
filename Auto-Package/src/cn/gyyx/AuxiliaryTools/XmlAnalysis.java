/*------------------------------------------------------------------------- 
 * 版权所有：北京光宇在线科技有限责任公司 
 * 作者：黄珂 
 * 联系方式：huangke@gyyx.cn 
 * 创建时间： 2014/11/18 09:30:16 
 * 版本号：v1.0 
 * 本类主要用途描述： 
 * XML操作类
-------------------------------------------------------------------------*/
package cn.gyyx.AuxiliaryTools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
 * 
 * @author HuangKe
 * 
 */

public class XmlAnalysis {

	public static void main(String args[]) {
//		String path = "C:\\Users\\Administrator\\Desktop\\新建文件夹\\TestCaseBaiDu3(10)\\AndroidManifest.xml";
//		String path2 = "C:\\Users\\Administrator\\Desktop\\Test\\resource\\uc\\res\\values\\strings.xml";
//		String path3 = "C:\\Users\\Administrator\\Desktop\\Test\\workEnvironment\\res\\values\\strings.xml";
//		//modifyApkName(path, path2, path3);
//		Document d =doAnalysis(path);
//		Element root = getRoot(d);
//		Vector<Element> eReceiver =resolve(root.element("application"),"activity");
//		for(int j =0;j<eReceiver.size();j++){
//			if(eReceiver.elementAt(j).getName().equals("intent-filter"))
//	    {   
//			Vector<Element> eAction = resolve(eReceiver.elementAt(5).element("intent-filter"),"action");
//			Vector<Element> eData = resolve(eReceiver.elementAt(5).element("intent-filter"),"data");
//			Vector<Element> eCategory = resolve(eReceiver.elementAt(5).element("intent-filter"),"category");
//			
//			for (int i = 0; i < eAction.size(); i++) {
//				System.out.println(eAction.elementAt(i).attributes());
//			}
//			System.out.println("\n\n");
//			for (int i = 0; i < eCategory.size(); i++) {
//				System.out.println(eCategory.elementAt(i).attributes());
//			}
//			System.out.println("\n\n");
//			for (int i = 0; i < eData.size(); i++) {
//				System.out.println(eData.elementAt(i).attributes());
//			}
//			System.out.println("\n\n");
//	}
//			else
//				System.out.println("不包含intent-filter结点！");
//		}
		
//		String path1 = "C:\\Users\\Administrator\\Desktop\\XML解析试验\\AndroidManifest.xml";
//		String path2 = "C:\\Users\\Administrator\\Desktop\\XML解析试验\\BDY\\AndroidManifest.xml";
//		String path3 = "C:\\Users\\Administrator\\Desktop\\XML解析试验\\gyyx\\AndroidManifest.xml";
//		String path4 = "C:\\Users\\Administrator\\Desktop\\XML解析试验\\AndroidManifest.xml";
//		modifyXML(path1,path2,path3,path4);
		
		String path1 = "C:\\Users\\Administrator\\Desktop\\XML解析试验\\AndroidManifest.xml";
		//String path2 = "C:\\Users\\Administrator\\Desktop\\XML解析试验\\BDY\\permission.xml";
		//modifyPermission(path1, path2, path1);
		modifyPlatformId(path1, "hk");

	}

	/**
	 * 解析xml文件
	 * 
	 * @param url
	 * @return
	 */
	public static Document doAnalysis(String url) {
		File file = new File(url);
		Document document = null;
		try {
			SAXReader saxReader = new SAXReader();
			document = saxReader.read(file); // 读取XML文件,获得document对象
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return document;
	}

	/**
	 * 获得根节点
	 * 
	 * @param document
	 * @return
	 */
	public static Element getRoot(Document document) {
		Element root = document.getRootElement();
		return root;
	}

	/**
	 * 分解各节点并返回节点数组(无参)
	 * 
	 * @param root
	 * @return
	 */
	public static Vector resolve(Element root) {
		Vector<Element> elementVector = new Vector<Element>();
		for (Iterator i = root.elementIterator(); i.hasNext();) {
			Element elt = (Element) i.next();
			elementVector.add(elt);
		}
		return elementVector;
	}
	
	/**
	 * 分解某节点下特定名称的节点数组
	 * @param root
	 * @param name
	 * @return
	 */
	public static Vector resolve(Element root,String name) {
		Vector<Element> elementVector = new Vector<Element>();
		for (Iterator i = root.elementIterator(); i.hasNext();) {
			Element elt = (Element) i.next();
			if(elt.getName().equals(name))
			elementVector.add(elt);
		}
		return elementVector;
	}

	/**
	 * 写XML文件
	 * 
	 * @param document
	 * @param outPath
	 */
	public static void writeXML(Document document, String outPath) {
		XMLWriter writer = null;
		try {
			writer = new XMLWriter(new FileWriter(outPath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			writer.write(document);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 获取apk图标名称
	 * 
	 * @param document
	 * @return
	 */
	public static String getIconName(String XMLurl) {
		Document doc = doAnalysis(XMLurl);
		Element root = getRoot(doc);
		Element target = root.element("application");
		String originName = target.attribute("icon").toString();
		String iconName = originName.substring(originName.lastIndexOf("/") + 1,
				originName.length() - 2);
		return iconName;
	}
	
	/**
	 * 修改图标名称
	 * @param XMLurl
	 * @param iconName
	 */
	public static void modifyIconName(String XMLurl,String iconName){
		Document doc = doAnalysis(XMLurl);
		Element root = getRoot(doc);
		Element target = root.element("application");
		target.attribute("icon").setText(iconName);
		writeXML(doc, XMLurl);
	}

	/**
	 * 修正apk名称
	 * 
	 * @param stringUrl
	 */
	public static void modifyApkName(String originalStringUrl,
			String resouceStringUrl, String outputXML) {
		String apkName = null;
		String falseName = null;
		try {
			Document doc = doAnalysis(originalStringUrl);
			Element root = getRoot(doc);
			List<Element> list = root.elements();
			for (int i = 0; i < list.size(); i++) {
				Element ele = list.get(i);
				String value = ele.attribute("name").toString();
				String trueValue = value.substring(value.lastIndexOf("["));
				if (trueValue
						.equals("[Attribute: name name value \"app_name\"]")) {
					apkName = ele.getTextTrim();
					System.out.println(apkName);
				}
			}
			Document doc2 = doAnalysis(resouceStringUrl);
			Element root2 = getRoot(doc2);
			List<Element> list2 = root2.elements();
			for (int i = 0; i < list2.size(); i++) {
				Element ele = list2.get(i);
				String value = ele.attribute("name").toString();
				String trueValue = value.substring(value.lastIndexOf("["));
				if (trueValue
						.equals("[Attribute: name name value \"app_name\"]")) {
					falseName = ele.getTextTrim();
					System.out.println(falseName);
					ele.setText(apkName);
					;
					System.out.println(ele.getText());
				}
			}

			// 注意解决中文乱码问题
			File file = new File(outputXML);
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("UTF-8");
			FileOutputStream fos = null;
			fos = new FileOutputStream(file);
			XMLWriter writer = new XMLWriter(fos, format);
			writer.write(doc2);
			writer.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 修改包名与提供商信息
	 * 
	 * @param originalStringUrl
	 * @param outputXML
	 * @param platformId
	 */
	public static void modifyPackageName(String packNamePath,String originalStringUrl,String outputXML){		
	String oringinName = null;
	String targetName = null;
	String oringinProvider = null;
	String targetProvider = null;
	File f = new File(packNamePath);
    BufferedReader br = null;
    try {
    	br = new BufferedReader(new FileReader(f));
    	String line = null;
    	while ((line = br.readLine()) != null) {
    		 targetName = line;
    		 targetProvider = "downloads."+targetName;
    	}
    }catch(Exception e){
    	e.printStackTrace();
    }
    try{
	Document doc = cn.gyyx.AuxiliaryTools.XmlAnalysis.doAnalysis(originalStringUrl);
	Element root = cn.gyyx.AuxiliaryTools.XmlAnalysis.getRoot(doc);
	//修改包名
	String tempName = root.attribute("package").toString();
	oringinName =tempName.substring(tempName.lastIndexOf("value")).replace("\"", "").replace("]", "").replace("value ","");
	System.out.println(oringinName);
	//targetName = oringinName + "." + platformId;
	System.out.println(targetName);
	root.attribute("package").setText(targetName);
	System.out.println(root.attribute("package").toString());
	//修改提供商
	String tempProvider = root.element("application").element("provider").attribute("authorities").toString();
	oringinProvider = tempProvider.substring(tempProvider.lastIndexOf("value")).replace("\"", "").replace("]", "").replace("value ","");
	System.out.println(oringinProvider);
	//targetProvider = oringinProvider + "." + platformId;
	System.out.println(targetProvider);
	root.element("application").element("provider").attribute("authorities").setText(targetProvider);
	System.out.println(root.element("application").element("provider").attribute("authorities").toString());
	
	File file = new File(outputXML);
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

	/**
	 * 插入节点
	 * 
	 * @param root
	 * @param position
	 * @param name
	 * @return
	 */
	public static Document insertNodes(Element root, String position,
			Vector<Element> elementVector) {
		Element insertElement = root.element(position);
		for (int i = 0; i < elementVector.size(); i++) {
			insertElement.addElement(elementVector.elementAt(i).getName());
		}
		Document doc = insertElement.getDocument();
		return doc;
	}

	/**
	 * 删除光宇相应配置信息
	 * 
	 * @param targetXML
	 * @param sourceXML
	 * @param outputXML
	 */
	public static Document delGyyxPlatform(String targetXML, String sourceXML) {
		// try{
		Document doc = doAnalysis(targetXML);
		Element rootTarget = doc.getRootElement();
		Element rootSource = doAnalysis(sourceXML).getRootElement();
		Vector<Element> targetVector = resolve(rootTarget);
		Vector<Element> sourcetVector = resolve(rootSource);
		List<Element> list2 = rootTarget.element("application").elements();
		List<Element> list = rootSource.elements();
		for (int i = 0; i < list.size(); i++) {
			Element ele = list.get(i);
			String value1 = ele.attribute("name").toString();
			String trueValue1 = value1.substring(value1.lastIndexOf("["));
			for (int j = 0; j < list2.size(); j++) {
				Element ele2 = list2.get(j);
				String value2 = ele2.attribute("name").toString();
				String trueValue2 = value2.substring(value2.lastIndexOf("["));
				if (trueValue1.equals(trueValue2))
					// System.out.println(value1);
					list2.remove(ele2);
			}

		}

		// OutputFormat format = OutputFormat.createPrettyPrint();
		// format.setEncoding("UTF-8");
		// XMLWriter writer = new XMLWriter(new FileWriter(outputXML),format);
		// writer.write(doc);
		// writer.close();
		//
		// }catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		return doc;
	}
	
	/**
	 * 修改platform_id
	 * @param targetXML
	 * @param platformId
	 */
	public static void modifyPlatformId(String targetXML, String platformId){
		Document doc = doAnalysis(targetXML);
		Element rootTarget = doc.getRootElement();
		Vector<Element> eMetaDta = resolve(rootTarget.element("application"),"meta-data");
		for(int i=0;i<eMetaDta.size();i++){
			Element e = eMetaDta.elementAt(i);
			String value = e.attributes().toString();
			if(value.contains("platform_id")){
				eMetaDta.elementAt(i).attribute("value").setValue(platformId);
			}
		}
		writeXML(doc, targetXML);
	}

	/**
	 * 将一个XML解析后插入到目标XML中，并输出XML文件
	 * 
	 * @param targetXML 需要修改的XML
	 * @param fragmentXML 资源库平台XML
	 * @param sourceXML 光宇XML
	 * @param outputXML 需要写入的XML
	 */
	public static void modifyXML(String targetXML, String fragmentXML,
			String sourceXML, String outputXML) {
		try {
			Document doc = delGyyxPlatform(targetXML, sourceXML);
			Element rootTarget = doc.getRootElement();
			Element rootFrag = doAnalysis(fragmentXML).getRootElement();
			Vector<Element> elementVector = new Vector<Element>();
			elementVector = resolve(rootFrag);
			List<Element> childsLayer1 = rootTarget.element("application").elements();
			for (int i = 0; i < elementVector.size(); i++) // 第一层循环,子节点
			{
				System.out.println("第一层检索开始！");
				Element elem = DocumentHelper.createElement(elementVector
						.elementAt(i).getName());
				elem.setAttributes(elementVector.elementAt(i).attributes());
				childsLayer1.add(0, elem);
				List<Element> childsLayer2 = elem.elements();
				
				//试验是否需要建立后两层循环
				Vector<Element> eTest =resolve(elementVector.elementAt(i));
				if(eTest.size()>0){
				for (int j = 0; j < eTest.size(); j++)// 第二层循环，intent-filter
				{   
					Vector<Element> eAction = resolve(elementVector.elementAt(i).element("intent-filter"),"action");
					Vector<Element> eData = resolve(elementVector.elementAt(i).element("intent-filter"),"data");
					Vector<Element> eCategory = resolve(elementVector.elementAt(i).element("intent-filter"),"category");
						
					System.out.println("第二层检索开始");
					Element elem2 = DocumentHelper.createElement(elementVector
							.elementAt(i).element("intent-filter").getName());
					elem2.setAttributes(elementVector.elementAt(i)
							.element("intent-filter").attributes());
					childsLayer2.add(0, elem2);
					List<Element> childsLayer3 = elem2.elements();

					for (int k = 0; k < eAction.size(); k++)// 第三层循环，action
					{
						System.out.println("第三层检索action开始");					
//						Element elem3 = DocumentHelper
//								.createElement(elementVector.elementAt(i)
//										.element("intent-filter")
//										.element("action").getName());
						Element elem3 = DocumentHelper
								.createElement(eAction.elementAt(k).getName());
						elem3.setAttributes(eAction.elementAt(k).attributes());
						childsLayer3.add(0, elem3);
					}
					for (int k = 0; k < eData.size(); k++)// 第三层循环，data
					{
						System.out.println("第三层检索data开始");					
						Element elem3 = DocumentHelper
								.createElement(eData.elementAt(k).getName());
						elem3.setAttributes(eData.elementAt(k).attributes());
						childsLayer3.add(0, elem3);
					}
					for (int k = 0; k < eCategory.size(); k++)// 第三层循环，category
					{
						System.out.println("第三层检索category开始");					
						Element elem3 = DocumentHelper
								.createElement(eCategory.elementAt(k).getName());
						elem3.setAttributes(eCategory.elementAt(k).attributes());
						childsLayer3.add(0, elem3);
					}
				}
				}
					else{
						//Log.e("STATUS", "------->无需进行后两层循环");
						System.out.println("无需进行后两层循环！");
					}
					
			}
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("UTF-8");
			XMLWriter writer = new XMLWriter(new FileWriter(outputXML), format);
			writer.write(doc);
			writer.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
     /**
      * 修改权限配置
      * @param targetXML
      * @param fragmentXML
      * @param outputXML
      */
	public static void modifyPermission(String targetXML, String fragmentXML,String outputXML) {
		try {
			Document doc  = doAnalysis(targetXML);
			Element rootTarget = doc.getRootElement();
			Element rootFrag = doAnalysis(fragmentXML).getRootElement();
			
			Vector<Element> eFragPermission =  resolve(rootFrag,"uses-permission");
			Vector<Element> eFragPermission2 =  resolve(rootFrag,"permission");
			Vector<Element> eFragApplication =  resolve(rootFrag,"application");
			
			Vector<Element> eTarPermission  = resolve(rootTarget,"uses-permission");
			Vector<Element> eTarPermission2  = resolve(rootTarget,"permission");
			
			List<Element> childsLayer = rootTarget.elements();
			
			System.out.println("application属性修改开始！");
			for (int i = 0; i < eFragApplication.size(); i++) 
			{
				Element elem = rootTarget.element("application");
				elem.setAttributes(eFragApplication.elementAt(i).attributes());
			}
			
			System.out.println("用户权限修改开始！");
			for (int i = 0; i < eFragPermission.size(); i++) 
			{
				Element elem = DocumentHelper.createElement(eFragPermission
						.elementAt(i).getName());
				elem.setAttributes(eFragPermission.elementAt(i).attributes());
				if(!checkSameName(eTarPermission,elem))
				childsLayer.add(0, elem);
				else
					System.out.println("已经存在此权限");
			}
			
			System.out.println("用户权限修改开始！");
			for (int i = 0; i < eFragPermission2.size(); i++) 
			{
				Element elem = DocumentHelper.createElement(eFragPermission2
						.elementAt(i).getName());
				elem.setAttributes(eFragPermission2.elementAt(i).attributes());
				if(!checkSameName(eTarPermission2,elem))
				childsLayer.add(0, elem);
				else
					System.out.println("已经存在此权限");
			}
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("UTF-8");
			XMLWriter writer = new XMLWriter(new FileWriter(outputXML), format);
			writer.write(doc);
			writer.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	/**
	 * 判断是否有重复结点
	 * @param vec
	 * @param e
	 * @return
	 */
	public static boolean checkSameName(Vector<Element> vec,Element e){
		boolean isExist = false;
		for(int i=0;i<vec.size();i++){
			if(vec.elementAt(i).attributes().equals(e.attributes())){
				isExist = true;
		        break;	
			}
		}
		return isExist;
	}
	
	/**
	 * 获得权限和application属性
	 * @param targetXML
	 * @param outputXML
	 */
	public static void getPemissionAndApplication(String targetXML,String outputXML){
		try {
			//新建一个Document
			Document docOut = DocumentHelper.createDocument();
			Document doc  = doAnalysis(targetXML);
			Element rootTarget = doc.getRootElement();
			//设置根节点
			Element manifest = DocumentHelper.createElement("manifest");    
			docOut.setRootElement(manifest);
			Element rootOut = docOut.getRootElement();
			rootOut.setAttributes(rootTarget.attributes());
			
			Vector<Element> eFragPermission =  resolve(rootTarget,"uses-permission");
			Vector<Element> eFragPermission2 =  resolve(rootTarget,"permission");
			Vector<Element> eFragApplication =  resolve(rootTarget,"application");
			
			List<Element> childsLayer = rootOut.elements();
			
			System.out.println("application属性修改开始！");
			for (int i = 0; i < eFragApplication.size(); i++) 
			{   
				Element elem = DocumentHelper.createElement(eFragApplication
						.elementAt(i).getName());
				childsLayer.add(elem);
				elem.setAttributes(eFragApplication.elementAt(i).attributes());
			}
			System.out.println("用户权限修改开始！");
			for (int i = 0; i < eFragPermission.size(); i++) 
			{
				Element elem = DocumentHelper.createElement(eFragPermission
						.elementAt(i).getName());
				childsLayer.add(elem);
				elem.setAttributes(eFragPermission.elementAt(i).attributes());
			}
			
			System.out.println("用户权限修改开始！");
			for (int i = 0; i < eFragPermission2.size(); i++) 
			{
				Element elem = DocumentHelper.createElement(eFragPermission2
						.elementAt(i).getName());
				childsLayer.add(elem);
				elem.setAttributes(eFragPermission2.elementAt(i).attributes());
			}
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("UTF-8");
			XMLWriter writer = new XMLWriter(new FileWriter(outputXML), format);
			writer.write(docOut);
			writer.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
