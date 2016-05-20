package com.x.xjson;

import java.io.IOException;
import java.util.Properties;

public class PropUtil {
	
	public static String getValue(String propName){
		Properties p = null;
		try {
			p = initProperties("mpi.properties");
		} catch (IOException e) {
			System.out.println("初始化配置文件失败");
		}
		String value = p.getProperty(propName);
		return value;
	}

	/**
	 * 从给定的配置文件中查属性值
	 * @param propName
	 * 				待查属性key
	 * @param propFileName
	 * 				配置文件名
	 * @return
	 */
	public static String getValue(String propName,String propFileName){
		Properties p = null;
		try {
			p = initProperties(propFileName);
		} catch (IOException e) {
			System.out.println("初始化配置文件 "+propFileName+" 失败");
		}
		String value = p.getProperty(propName);
		return value;
	}
	
	public static Properties initProperties(String propFilePath) throws IOException{
		Properties p = new Properties();
		p.load(PropUtil.class.getClassLoader().getResourceAsStream(propFilePath));
		return p;
	}
}
