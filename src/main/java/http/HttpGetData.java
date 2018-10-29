package http;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**http请求工具，post,get 只支持Content-Type=application/json
 * @author zhangshancheng
 *
 */
public class HttpGetData {

    /**
     * @param url：连接地址
     * @param param:请求参数，可以定义空的声明
     * @return
     * @throws ParseException
     * @throws UnsupportedEncodingException
     * @throws IOException
     */
    public static String httpClientGet(String url,Map<String, String> param) throws ParseException, UnsupportedEncodingException, IOException{
        CloseableHttpClient client = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        if(param!=null){
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            for(Map.Entry<String, String> entry:param.entrySet()){
                list.add(new BasicNameValuePair(entry.getKey(),entry.getValue()));
            }
            url = url+"?"+ EntityUtils.toString(new UrlEncodedFormEntity(list), "UTF-8");
        }
        HttpGet get = new HttpGet(url);
        get.setHeader("Content-Type","application/json; charset=utf-8");
        response = client.execute(get);
        HttpEntity entity = response.getEntity();

        String string = EntityUtils.toString(entity,"UTF-8");
        response.close();
        client.close();
        return string;
    }
    
    public static String httpClientPost(String url,Map<String, String> param) throws ClientProtocolException, IOException{
    	CloseableHttpClient client = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		URIBuilder builder = new URIBuilder();
		URI uri = null;
		//uri = builder.setScheme("http").setHost(host).setPort(port).setPath(path).build();
		HttpPost post = new HttpPost(url);
		post.setHeader("Content-Type", "application/json; charset=utf-8");
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		if(param!=null){
			for (Map.Entry<String, String> entry:param.entrySet()) {
				params.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}
		}
		UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(params,"UTF-8");
		post.setEntity(urlEncodedFormEntity);
		
		response = client.execute(post);
		HttpEntity entity = response.getEntity();
		String res = EntityUtils.toString(entity);
		response.close();
		client.close();
		return res;
    }
    

    public static <T> List<T> aliStrToList(Class<T> c,String liststr){
		List<T> parseArray = JSON.parseArray(liststr, c);
		return parseArray;
	}
    public static <T> T aliJsonToObject(Class<T> c,String jsonstr){
		JSONObject parse = JSON.parseObject(jsonstr);
		T javaObject = JSONObject.toJavaObject(parse, c);
		return javaObject;
	}
    

}
