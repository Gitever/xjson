/**
 * 
 * File Name: XmlUtil.java 
 * Package Name: com.x.xjson
 * Date: 2016年5月31日 下午2:49:40
 */
package com.x.xjson;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import org.dom4j.Document;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
 * @author x
 *
 */
public class XmlUtil {
	public static String formatXML(String inputXML) throws Exception {
		SAXReader reader = new SAXReader();
		Document document = reader.read(new StringReader(inputXML));
		String requestXML = null;
		XMLWriter writer = null;
		if (document != null) {
			try {
				StringWriter stringWriter = new StringWriter();
				OutputFormat format = new OutputFormat("    ", true);
				writer = new XMLWriter(stringWriter, format);
				writer.write(document);
				writer.flush();
				requestXML = stringWriter.getBuffer().toString();
			} finally {
				if (writer != null) {
					try {
						writer.close();
					} catch (IOException e) {
						
					}
				}
			}
		}
		return requestXML;
	}

	public static void main(String[] args) {
		try {
			String s = "<Province xmlns=\"\"><name>Shanxi</name><population><a>37751200</a><a>37751201</a></population><time>2016/12/03 12:12:50</time></Province>";
			s = formatXML(s);
			System.out.println(s);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
