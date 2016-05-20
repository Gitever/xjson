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
	
	public String getPost0(String url, final String param, final String charset) throws Exception{
		System.out.println(param);
		
		ContentProducer cp = new ContentProducer() {
			public void writeTo(OutputStream arg0) throws IOException {
				arg0.write(param.getBytes(charset));
				arg0.flush();
				arg0.close();
			}
		};
		
		/*ContentProducer cp = new ContentProducer(){
            // 二进制流
            public void writeTo(OutputStream outstream) throws IOException{
                ObjectOutputStream oos = new ObjectOutputStream(outstream);
                oos.writeObject(param);
                oos.flush();
                oos.close();
            }
        };*/
        return getPost1(url, new EntityTemplate(cp), charset);
	}
	
	private static OutputStream doPost(String url, HttpEntity entity){  
		CloseableHttpClient client = HttpClients.createDefault();
        HttpPost post = new HttpPost(url);
        OutputStream os = null;
        post.setEntity(entity);
        try{
            HttpResponse response = client.execute(post);
            HttpEntity resEntity = response.getEntity();
            InputStream inputStream = resEntity.getContent();
  
            if (resEntity != null){
                os = new ByteArrayOutputStream();
                int temp = 0;
                while ((temp = inputStream.read()) != -1){
                    os.write(temp);
                }
                os.flush();
                os.close();
                EntityUtils.consume(resEntity);
                return os;  
            }  
        }
        catch (Exception e){  
        }  
        finally{  
            try {
				client.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }  
        return null;  
    }
	
	public String getPost1(String url, HttpEntity entity, String charset) throws Exception{
		String result = "";
		CloseableHttpClient httpclient = HttpClients.createDefault();
		
		HttpPost httpPost = new HttpPost(url);
		httpPost.setEntity(entity);
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
		return result;
	}
	
	public String getResponseByPost(String url, String ContentType, String encode,String[] params)throws Exception{
		String result = null;
		httpPost = new HttpPost(url); 
		List<org.apache.http.NameValuePair> formParams = new ArrayList<org.apache.http.NameValuePair>();
		for(int i=0; i<params.length/2;i++){
			formParams.add(new BasicNameValuePair(params[i*2], params[i*2+1]));
		}
		
		HttpEntity entityForm = new UrlEncodedFormEntity(formParams, encode);
		httpPost.setHeader("Content-Type", ContentType);
		httpPost.setEntity(entityForm);
		CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
		try {
			if (httpResponse!=null) {
				HttpEntity resEntity = httpResponse.getEntity();
	            if(resEntity != null){
	                result = EntityUtils.toString(resEntity, encode);  
	            }
			}
		}finally {
			httpResponse.close();
		}
		
		return result;
	}
	
	public String getResponseByGet(String url, String encode)throws Exception{
		String result = "";
	    httpGet = new HttpGet(url);
	    CloseableHttpResponse response1 = httpClient.execute(httpGet);
	    // The underlying HTTP connection is still held by the response object
	    // to allow the response content to be streamed directly from the network socket.
	    // In order to ensure correct deallocation of system resources
	    // the user MUST either fully consume the response content  or abort request
	    // execution by calling CloseableHttpResponse#close().

	    try {
	        System.out.println(response1.getStatusLine());
	        HttpEntity resEntity = response1.getEntity();
	        if(resEntity != null){  
                result = EntityUtils.toString(resEntity, encode);  
            }
	        // do something useful with the response body
	        // and ensure it is fully consumed
	        EntityUtils.consume(resEntity);
	    } finally {
	        response1.close();
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
