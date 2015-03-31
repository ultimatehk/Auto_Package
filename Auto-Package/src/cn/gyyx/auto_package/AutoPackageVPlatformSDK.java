/*------------------------------------------------------------------------- 
 * 版权所有：北京光宇在线科技有限责任公司 
 * 作者：黄珂 
 * 联系方式：huangke@gyyx.cn 
 * 创建时间： 2014/10/27 09:30:16 
 * 版本号：v3.1 
 * 本类主要用途描述： 
 * 将打过光宇平台的游戏包解包并根据渠道信息文件修改配置文件、分配相应资源后打成若干渠道包
-------------------------------------------------------------------------*/
package cn.gyyx.auto_package;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import android.R.array;
import cn.gyyx.AuxiliaryTools.FileUtil;
import cn.gyyx.AuxiliaryTools.RemoveGyyxPlatform;
import cn.gyyx.AuxiliaryTools.RunCmd;
import cn.gyyx.AuxiliaryTools.XmlAnalysis;

public class AutoPackageVPlatformSDK {
	
	String currentPath;// 工作环境目录
	String productPath;// 批次产品目录
	String apkName;// apk绝对路径
	String workName;// 初次解压的apk目录名称(非路径）
	String disk;// 当前盘符
	String isSecret;//platform_id是否需要保密
	String iconName;//真.游戏图标名
	String keystoreAlias;//签名文件别名
	String keystorePwd;//签名文件密码
	String batchNo;//批次号
	String apkDownload;//下载目录
	String downloadPath;

	public AutoPackageVPlatformSDK(String apkName, String factory,String keystoreAlias,String keystorePwd,String isSecret,String downloadPath,String productPath) {
		
		this.apkName = apkName;
		this.currentPath = factory;
		String[] apkFilePath = apkName.split("\\\\");
		String shortApkName = apkFilePath[apkFilePath.length - 1];
		// 取得真.文件名
		this.workName = shortApkName.split(".apk")[0];
		this.keystoreAlias = keystoreAlias;
		this.keystorePwd = keystorePwd;
		this.isSecret = isSecret;
		this.batchNo = null;
		this.downloadPath = downloadPath;
		
		System.out.println(workName);
		Main.LOGGER.debug("workName: {}", workName);
		
		//this.productPath = apkName.substring(0, apkName.lastIndexOf("\\"));
		this.productPath = productPath;
		disk = factory.substring(0, 2);
		Main.LOGGER.debug("productPath: {}", productPath);
		Main.LOGGER.debug("DISK: {}", disk);
		System.out.println("productPath is:" + productPath);
		
		System.out.println(currentPath);
		Main.LOGGER.debug("currentPath: {}", currentPath);
		
		// 拷贝必要资源
		Main.LOGGER.debug("copying supplement resources.......");
		String cmdCpRes = String.format(
				"cmd.exe /C %s && cd %s && xcopy /E /Y %s %s", disk,
				productPath, productPath + "\\resource", currentPath
						+ "\\resource");
		RunCmd.runCmd(cmdCpRes);
		// 补完目录结构
		String apksPath = productPath + "\\apk";
		File f = new File(apksPath);
		if (!f.exists()) {
			f.mkdir();
		}
		String logPath = productPath + "\\log.txt";
		File log1 = new File(logPath);
		if (!log1.exists()) {
			try {
				log1.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void packageForPlatforms() {
		System.out.println("*********现在打出的是渠道SDK包哟~~~*********");
		Main.LOGGER.info("Package for platforms...");
		HashMap<String, String[]> configs = getChannelInfo();// 获取渠道信息
		doPackage(configs);// 依据渠道信息进行打包
	}

	/**
	 * 获取渠道信息
	 */
	private HashMap<String, String[]> getChannelInfo() {
		HashMap<String, String[]> configs = new HashMap<String, String[]>();// 存储渠道信息

		File f = new File(productPath + "\\channel.txt"); // 三栏分别是
															// platform_id、extend_id
															// 和
		// batch_no
		System.out.println(productPath + "\\channel.txt");
		System.out.println("*******STEP 1:读取配置文件中。。。*******");

		Main.LOGGER.debug("productPath: {}", productPath + "\\channel.txt");
		Main.LOGGER.info("  STEP 1: Read channel.txt");
		
		BufferedReader br = null;
		try {

			br = new BufferedReader(new FileReader(f));
			String line = null;
			while ((line = br.readLine()) != null) {
				String[] array = line.split("\t");// 注意用tab分割
				for (int i = 0; i < 3; i++) {
					System.out.print(array[i] + ',');
				}
				System.out.println();
				configs.put(array[0].trim(), array);// 存入hashmap
				batchNo = array[2];
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null) {
					br.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		System.out.println("******* 完成. 准备打出 " + configs.size()
				+ " 个渠道包...*******");
		Main.LOGGER.debug("  finished: ready for {} platforms.", configs.size());
		
		
		return configs;
	}

	/**
	 * 启动自动打包流程
	 * 
	 */
	private void doPackage(HashMap<String, String[]> configs) {

		// 1. 解包，decodePath为解包后的文件夹名
		System.out.println("*******STEP 2 解压游戏母包 *******");
		Main.LOGGER.info("  STEP 2: Unpackage.");
		
		String decodePath = workName;
		String cmdUnpack = String.format(
				"cmd.exe /C %s && cd %s && java -jar apktool.jar d %s", disk,
				currentPath, apkName);
		System.out.println(cmdUnpack);
		Main.LOGGER.debug("cmdUnpack: {}", cmdUnpack);
		RunCmd.runCmd(cmdUnpack);
		
		iconName = XmlAnalysis.getIconName(currentPath + "\\" + workName +"\\AndroidManifest.xml");		
		
		// 2.剔除光宇平台文件
		System.out.println("*******STEP 3 开始清理光宇平台文件  *******");
		Main.LOGGER.info("  STEP 3: Remove gyyx file.");
		
		RemoveGyyxPlatform.romoveGyyx(currentPath + "\\" + workName,
				currentPath + "\\resource\\gyyx");
		String cmdRemoveGyyx = String.format(
				"cmd.exe /C  %s && cd %s && rd /s/q %s", disk, currentPath,
				currentPath + "\\" + workName + "\\smali\\cn\\gyyx\\platform");
		RunCmd.runCmd(cmdRemoveGyyx);

		// 2.1清理多余文件
		String cmdClean = String.format(
				"cmd.exe /C  %s && cd %s && rd /s/q %s", disk, currentPath,
				currentPath + "\\" + workName + "\\res\\values\\public.xml");
		RunCmd.runCmd(cmdClean);
		String cmdClean2 = String.format(
				"cmd.exe /C  %s && cd %s && rd /s/q %s", disk, currentPath,
				currentPath + "\\" + workName + "\\smali_assets");
		RunCmd.runCmd(cmdClean2);

		// 3.备份工作环境
		System.out.println("*******STEP 4 备份无平台游戏包  *******");
		Main.LOGGER.info("  STEP 4: Backup.");
		
		String backupPath = currentPath + "\\backup"+batchNo;
		File f = new File(backupPath);
		if (!f.exists()) {
			f.mkdir();
		}
		String cmdCpBackup = String.format(
				"cmd.exe /C %s && cd %s && xcopy /E /Y %s %s", disk,
				currentPath, currentPath + "\\" + workName, backupPath);
		RunCmd.runCmd(cmdCpBackup);

		// 4. 循环打包
		System.out.println("*******STEP 5 开始循环打包  *******");
		Main.LOGGER.info("  STEP 5: Start loop.");

		String unsignApkName = null;
		for (final Map.Entry<String, String[]> entry : configs.entrySet()) {
			try {
				String platform_id = entry.getKey();
				String[] values = entry.getValue();
				
				System.out.println("针对 " + platform_id + "进行配置...");
				Main.LOGGER.info("    package {}", platform_id);

				// 准备工作环境
				Main.LOGGER.info("    Establishing the workEnvironment.......");
				establish(currentPath + "\\backup"+batchNo, currentPath,batchNo);

				// 替换渠道平台资源文件
				Main.LOGGER.info("    Replacing files.......");
				replacePlatformFile(platform_id, batchNo);

				// 修改manifest文件
				Main.LOGGER.info("    Create manifest.......");
				File createXML = new File(currentPath + "\\workEnvironment"+batchNo,
						"\\AndroidManifest.xml");
				if (!createXML.exists()) {
					createXML.createNewFile();
				}
				// 插入渠道SDK信息
				Main.LOGGER.info("    Modifying manifest.......");
				XmlAnalysis.modifyXML(currentPath
						+ "\\backup"+batchNo+"\\AndroidManifest.xml", currentPath
						+ "\\resource\\" + platform_id
						+ "\\AndroidManifest.xml", currentPath
						+ "\\resource\\gyyx" + "\\AndroidManifest.xml",
						currentPath + "\\workEnvironment"+batchNo+"\\AndroidManifest.xml");
				// 修改个推信息
				Main.LOGGER.info("    Modifying geitui information.......");
				XmlAnalysis.modifyXML(currentPath
						+ "\\workEnvironment"+batchNo+"\\AndroidManifest.xml", currentPath
						+ "\\resource\\" + platform_id + "\\manifest.xml",
						currentPath + "\\resource\\gyyx"
								+ "\\AndroidManifest.xml", currentPath
								+ "\\workEnvironment"+batchNo+"\\AndroidManifest.xml");
				// 修改包名与提供商信息
				Main.LOGGER.info("    Modifying packagName&provider.......");
				XmlAnalysis.modifyPackageName(currentPath + "\\resource\\"
						+ platform_id + "\\packageName.txt", currentPath
						+ "\\workEnvironment"+batchNo+"\\AndroidManifest.xml", 
						currentPath+"\\workEnvironment"+batchNo+"\\AndroidManifest.xml");
				// 修改权限配置
				Main.LOGGER.info("    Modifying permission.......");
				XmlAnalysis.modifyPermission(currentPath + "\\workEnvironment"+batchNo+"\\AndroidManifest.xml", 
						                     currentPath + "\\resource\\" + platform_id + "\\permission.xml",
						                     currentPath + "\\workEnvironment"+batchNo+"\\AndroidManifest.xml");
				//修改图标名称
				Main.LOGGER.info("    Modifying iconName.......");
				XmlAnalysis.modifyIconName(currentPath + "\\workEnvironment"+batchNo+"\\AndroidManifest.xml", "@drawable/"+iconName);
				
				//根据情况看是否需要修改platform_id
				if(!isSecret.equals("secret"))
					XmlAnalysis.modifyPlatformId(currentPath + "\\workEnvironment"+batchNo+"\\AndroidManifest.xml",
												 platform_id);
				// 修正apk名称
				System.out.println("正在修正apk名称");
				Main.LOGGER.debug("    edit apk name........");
				
				XmlAnalysis.modifyApkName(currentPath
						+ "\\backup"+batchNo+"\\res\\values\\strings.xml", currentPath
						+ "\\resource\\" + platform_id
						+ "\\res\\values\\strings.xml", currentPath
						+ "\\workEnvironment"+batchNo+"\\res\\values\\strings.xml");

				// 删除未知文件
				System.out.println("删除未知文件~");
				Main.LOGGER.debug("    delete unknown.......");
				
				String cmdKey = String
						.format("cmd.exe /C  %s && cd %s && rd /s/q %s", disk,
								currentPath, currentPath
										+ "\\workEnvironment"+batchNo+"\\unknown");
				RunCmd.runCmd(cmdKey);

				// 打包
				Main.LOGGER.debug("    package th apk........");
				unsignApkName = decodePath + "_" + platform_id
						+ "_unsigned.apk";
				String cmdPack = String
						.format("cmd.exe /C  %s && cd %s && java -jar apktool.jar b %s %s",
								disk, currentPath, currentPath
										+ "\\workEnvironment"+batchNo, unsignApkName);
				RunCmd.runCmd(cmdPack);

				// 移动出apk文件
				File file = new File(currentPath + "\\workEnvironment"+batchNo+"\\dist\\"
						+ workName + ".apk");
				if (file.exists()) {
					file.renameTo(new File(currentPath
							+ "\\workEnvironment"+batchNo+"\\dist\\" + platform_id + "_"
							+ workName + "_unsigned.apk"));
				}
				String move_source = currentPath + "\\workEnvironment"+batchNo+"\\dist\\"
						+ "*.apk";
				String move_destiny = productPath + "\\apk";
				String cmdMove = String.format(
						"cmd.exe /C  %s && cd %s && move %s %s", disk,
						currentPath, move_source, move_destiny);
				RunCmd.runCmd(cmdMove);
				
				//自动签名
				Main.LOGGER.debug("start Auto_sign.......");
				autoSign(keystoreAlias, keystorePwd,platform_id + "_" + workName + "_unsigned.apk");
				String cmdDelApk = String.format("cmd.exe /C  %s && cd %s && del /s/q %s",
						disk, productPath+"\\apk", productPath+"\\apk\\"+platform_id + "_" + workName + "_unsigned.apk");
				RunCmd.runCmd(cmdDelApk);
				Main.LOGGER.debug("sign complete.......");
				
				//移动到下载目录
				Main.LOGGER.debug("move to download.......");
				System.out.println("请到下载目录下完成下载~");
				apkDownload = downloadPath + "\\" + batchNo;
				System.out.println(apkDownload);
				File fdown = new File(apkDownload);
				if (!fdown.exists()) {
					fdown.mkdirs();
				}
				String move_s = productPath +"\\apk\\" + "*.apk";
				String move_d = apkDownload;
				String cmdMoveToDld = String.format("cmd.exe /C  %s  && move %s %s", disk, move_s, move_d);
				RunCmd.runCmd(cmdMoveToDld);

				// 写日志
				PrintWriter pw = new PrintWriter(new FileOutputStream(
						productPath + "\\log.txt", true));
				pw.write(values[2] + "," + values[0] + "," + values[1] + ","
						+ 1 + "\r\n");
				pw.flush();
				pw.close();
			} catch (Exception e) {
				System.out.println("ERROR at " + entry.getKey() + ":");
				e.printStackTrace();
				Main.LOGGER.error("Exception on {}:{}", entry.getKey(), e);
			}

			// 结束，清除当前工作环境
			System.out.print("清除当前工作环境");
			Main.LOGGER.debug("    clean...");
			
			String cmdKey = String.format(
					"cmd.exe /C %s && cd %s && rd /s/q %s", disk, currentPath,
					currentPath + "\\workEnvironment"+batchNo);
			RunCmd.runCmd(cmdKey);
			
			System.out.println("当前渠道包已打出！");
			Main.LOGGER.info("    {} finished.", unsignApkName);
			
		}

		//批量签名
//				System.out.println("启动批量签名.......");
//				Main.LOGGER.debug("  start batch AutoSign...。。。。");
//				String cmdBatchAutoSign = String.format("cmd.exe /C %s && cd %s && setlocal EnableDelayedExpansion && "
//						    + "for %%i in (*.apk) do (set name=%%i && set modify=!name:_unsigned=!"
//						   // + "&& echo !modify!"
//						   // + "&& echo !name!"
//						   // + "&&echo %%i"
//						    + "&&jarsigner  -keystore autopackage.keystore -storepass %s  -signedjar %%i -digestalg SHA1 -sigalg MD5withRSA %%i %s"
//						    + ")",
//						    disk,apkDownload,keystorePwd,keystoreAlias
//						    );
//		        RunCmd.runCmd(cmdBatchAutoSign);
//				Main.LOGGER.debug("  AutoSign complete...。。。。");
				
		// 循环打包结束，清理多余文件
		System.out.println("全部渠道包已经打出！正在清理多余文件");
		Main.LOGGER.debug("  All finished & clean...");
		
		String cmdDel = String.format("cmd.exe /C  %s && cd %s && rd /s/q %s",
				disk, currentPath, currentPath + "\\backup"+batchNo);
		RunCmd.runCmd(cmdDel);
		String cmdDel2 = String.format("cmd.exe /C  %s && cd %s && rd /s/q %s",
				disk, currentPath, currentPath + "\\" + workName);
		RunCmd.runCmd(cmdDel2);
		
		System.out.println("清理完成~");
		Main.LOGGER.info("Done.");
		
		
	}

	/**
	 * 替换文件
	 * 
	 * @param platform_id
	 * @param gameName
	 * @return
	 */
	private void replacePlatformFile(String platform_id,String batchNo) {
		System.out.println("Replaceing files...");

		String copy_source = currentPath + "\\resource\\" + platform_id;
		String copy_destiny = currentPath + "\\workEnvironment"+batchNo;
		 FileUtil.folderCopy(copy_source, copy_destiny);
		//String cmdCopy = String.format("cmd.exe /C  %s && cd %s && xcopy /E /Y %s %s", disk,currentPath, copy_source, copy_destiny);
		//RunCmd.runCmd(cmdCopy);
	}

	/**
	 * 建立工作环境
	 * 
	 * @param sourcePath
	 * @param targetPath
	 */
	public static void establish(String backupPath, String currentPath,String batchNo) {
		String trueTargetPath = currentPath + "\\workEnvironment"+batchNo;
		File f = new File(trueTargetPath);
		if (!f.exists()) {
			f.mkdir();
		}
		FileUtil.folderCopy(backupPath, trueTargetPath);
		try {
			FileUtil.delete(trueTargetPath, "\\res\\raw\\config.txt");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 使用 apktool解包apk
	 * 
	 * @param apkName
	 * @return 文件路径
	 */
	private String apkToolDecode(String apkName) {
		System.out.println("正在解包apk文件...");

		String cmdUnpack = String.format(
				"cmd.exe /C %s && cd %s && java -jar apktool.jar d %s", disk,
				currentPath, apkName);
		RunCmd.runCmd(cmdUnpack);

		String[] apkFilePath = apkName.split("\\\\");
		String shortApkName = apkFilePath[apkFilePath.length - 1];
		return shortApkName.split(".apk")[0];
	}
	
	/**
	 * 自动签名
	 * @param keystoreAlias
	 * @param keystorePwd
	 */
	private void autoSign(String keystoreAlias,String keystorePwd,String apk){
		String cmdSign = String.format("cmd.exe /C %s && cd %s && jarsigner  -keystore autopackage.keystore -storepass %s  -signedjar %s  -digestalg SHA1 -sigalg MD5withRSA %s %s",
	            disk,
	            productPath+"\\apk",
	            keystorePwd,
	            apk.substring(0, apk.lastIndexOf("unsigned")-1)+".apk",
	            apk,
	            keystoreAlias);
		RunCmd.runCmd(cmdSign);
	}

	public static byte[] readInputStreamToByte(InputStream in)
			throws IOException {
		int length = 0;
		byte buffer[] = new byte[1024];
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		while ((length = in.read(buffer)) != -1) {
			out.write(buffer, 0, length);
		}

		return out.toByteArray();
	}

}