/**
 * 
 * File Name: JsonUtil.java 
 * Package Name: com.x.xjson
 * Date: 2016年5月25日 上午9:51:39
 */
package com.x.xjson;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.text.SimpleDateFormat;

import com.fasterxml.jackson.annotation.JsonFormat.Feature;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

/**
 * @author x
 *
 */
public class JsonUtil {
	private static ObjectMapper mapper;
	private static XmlMapper xml;
	
	private JsonUtil(){ }
	
	public static synchronized ObjectMapper getInstance() {
        if(mapper == null){
        	mapper = new ObjectMapper();
        }
        return mapper;
    }
	
	public static synchronized XmlMapper getXmlMapper(){
		if (xml == null) {
			xml = new XmlMapper();
		}
		return xml;
	}
	
	/**
	 * 对象转json
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public static String beanToJson(Object obj){
		try {
			ObjectMapper objectMapper = getInstance();
			String json = objectMapper.writeValueAsString(obj);
			return json;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 对象转json，排除空属性
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public static String beanToJson_NOEMPTY(Object obj){
		try {
			ObjectMapper objectMapper = getInstance();
			objectMapper.setSerializationInclusion(Include.NON_EMPTY); // 配置mapper忽略空属性
			String json = objectMapper.writeValueAsString(obj);
			return json;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 对象转json，并格式化
	 * 生产中不建议使用，加大传输量
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public static String beanToJson_FORMAT(Object obj){
		try {
			ObjectMapper objectMapper = getInstance();
			mapper.configure(SerializationFeature.INDENT_OUTPUT, true); // FORMAT
			String json = objectMapper.writeValueAsString(obj);
			return json;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * json转对象
	 * @param json
	 * @param cls
	 * @return
	 * @throws Exception
	 */
	public static Object jsonToObject(String json, Class<?> cls){
		try {
			ObjectMapper objectMapper = getInstance();
			return objectMapper.readValue(json, cls);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 将对象转json，写入path路径
	 * @param obj
	 * @param path
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonGenerationException 
	 */
	public static void objToJsonPath(Object obj, String path){
		try {
			ObjectMapper mapper = getInstance();
			// 格式化
			mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
			// 配置mapper忽略空属性
	        mapper.setSerializationInclusion(Include.NON_EMPTY);
			mapper.writeValue(new File(path), obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 将对象转为xml
	 * @param obj
	 * @return
	 */
	public static String objToXml(Object obj){
		try {
			XmlMapper xml = getXmlMapper();
			return xml.writeValueAsString(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 将对象转xml，写入path路径
	 * xml处理时需要jackson-dataformat-xml、stax2-api
	 * @param obj
	 * @param path
	 */
	public static void objToXmlPath(Object obj, String path){
		try {
			XmlMapper xml = getXmlMapper();
			xml.writeValue(new File(path), obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*public static Object xmlToObject(String xl, Class<?> cls){
		try {
			XmlMapper xml = getXmlMapper();
			return xml.readValue(xl, cls);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/
}
