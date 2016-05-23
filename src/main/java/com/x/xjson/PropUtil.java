package com.x.xjson;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

public class PropUtil {
	
	private static String filePath = "mpi.properties";
	private static Properties props = new Properties();
	
	private PropUtil() { 
		
	}

	static{
		try {
			props.load(new FileInputStream(filePath));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String getValue(String key) {
        return props.getProperty(key);
    }
	
	public static void updateProperties(String key,String value) {   
        try {
            // 调用 Hashtable 的方法 put，使用 getProperty 方法提供并行性。
            // 强制要求为属性的键和值使用字符串。返回值是 Hashtable 调用 put 的结果。   
            OutputStream fos = new FileOutputStream(filePath);
            String old = getValue(key);
            if (old == null) {
				old = "";
			}else{
				old = old.equals("null") ? "" : old;
			}
            
            props.setProperty(key, old + value);
            // 以适合使用 load 方法加载到 Properties 表中的格式，
            // 将此 Properties 表中的属性列表（键和元素对）写入输出流
            props.store(fos, "Update '" + key + "' value");
            fos.flush();
            fos.close();
        } catch (IOException e) {   
            System.err.println("属性文件更新错误");   
        }
    }
}
