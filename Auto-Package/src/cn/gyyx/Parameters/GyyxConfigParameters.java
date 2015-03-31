package cn.gyyx.Parameters;

import java.io.Serializable;

public class GyyxConfigParameters implements Serializable{

	private String appId;
	private String appKey;
	private String gameName;
	private String oriention;
	private String platformName;
	private String platformIdMd5;
	private String extendId;
	private String clientId;
	private String clientKey;
	private String batchNo = 0+"";
	private String methodList ;
	private String manuFactoryId ;
	private String gameFlag;
	private String serverFlag;
	
	private String downloadPath;
	private String factoryPath;
	
	public String getDownloadPath() {
		return downloadPath;
	}
	public void setDownloadPath(String downloadPath) {
		this.downloadPath = downloadPath;
	}
	public String getFactoryPath() {
		return factoryPath;
	}
	public void setFactoryPath(String factoryPath) {
		this.factoryPath = factoryPath;
	}

	
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getAppKey() {
		return appKey;
	}
	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}
	public String getGameName() {
		return gameName;
	}
	public void setGameName(String gameName) {
		this.gameName = gameName;
	}
	public String getOriention() {
		return oriention;
	}
	public void setOriention(String oriention) {
		this.oriention = oriention;
	}
	public String getPlatformName() {
		return platformName;
	}
	public void setPlatformName(String platformName) {
		this.platformName = platformName;
	}
	public String getPlatformIdMd5() {
		return platformIdMd5;
	}
	public void setPlatformIdMd5(String platformIdMd5) {
		this.platformIdMd5 = platformIdMd5;
	}
	public String getExtendId() {
		return extendId;
	}
	public void setExtendId(String extendId) {
		this.extendId = extendId;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getClientKey() {
		return clientKey;
	}
	public void setClientKey(String clientKey) {
		this.clientKey = clientKey;
	}
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	public String getMethodList() {
		return methodList;
	}
	public void setMethodList(String methodList) {
		this.methodList = methodList;
	}
	public String getManuFactoryId() {
		return manuFactoryId;
	}
	public void setManuFactoryId(String manuFactoryId) {
		this.manuFactoryId = manuFactoryId;
	}
	public String getGameFlag() {
		return gameFlag;
	}
	public void setGameFlag(String gameFlag) {
		this.gameFlag = gameFlag;
	}
	public String getServerFlag() {
		return serverFlag;
	}
	public void setServerFlag(String serverFlag) {
		this.serverFlag = serverFlag;
	}
	
	
	
	

}
