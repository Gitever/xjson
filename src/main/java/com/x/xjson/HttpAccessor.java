/**
 * 
 * File Name: HttpAccessor.java 
 * Package Name: com.x.xijson
 * Date: 2016年5月20日 上午10:34:10
 */
package com.x.xjson;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentProducer;
import org.apache.http.entity.EntityTemplate;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * @author x
 *
 */
public class HttpAccessor {
	private CloseableHttpClient httpClient;
	private HttpPost httpPost;
	private HttpGet httpGet;
	final static int BUFFER_SIZE = 4096; 
	
	public HttpAccessor(){
		httpClient = HttpClients.createDefault();
	}
	
	public String getResponseByPost(String url, final String param, final String charset){
		ContentProducer cp = new ContentProducer() {
			public void writeTo(OutputStream arg0) throws IOException {
				arg0.write(param.getBytes(charset));
				arg0.flush();
				arg0.close();
			}
		};
        
        String result = "";
		CloseableHttpClient httpclient = HttpClients.createDefault();
		
		HttpPost httpPost = new HttpPost(url);
		httpPost.setEntity(new EntityTemplate(cp));
		try {
			HttpResponse response = httpclient.execute(httpPost);		
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity resEntity = response.getEntity();
				if (resEntity != null) {
					InputStream inputStream = resEntity.getContent();
					BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, charset));
					
					String readLine =null;
					while((readLine = br.readLine()) !=null){
						result = result + readLine;
					}
					inputStream.close();
					br.close();
					System.out.println("========="+result);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public String getResponseByGet(String url, String encode){
		String result = "";
	    httpGet = new HttpGet(url);
	    CloseableHttpResponse response = null;
	    try {
	        response = httpClient.execute(httpGet);
	        HttpEntity resEntity = response.getEntity();
	        if(resEntity != null){  
                result = EntityUtils.toString(resEntity, encode);  
            }
	        EntityUtils.consume(resEntity);
	        response.close();
	    }catch (Exception e){
	    	e.printStackTrace();
	    }
	    return result;
	}
	
	public String InputStreamTOString(InputStream in) throws Exception{
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] data = new byte[BUFFER_SIZE];
        int count = -1;
        while((count = in.read(data,0,BUFFER_SIZE)) != -1)  
            outStream.write(data, 0, count);  
        data = null;  
        return new String(outStream.toByteArray(),"UTF-8");  
    } 
}
