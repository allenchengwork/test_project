import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import org.testng.Assert;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.common.hash.Hashing;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TestEasyoga {
	public static final String TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJkZXYuc2hvcC5lYXN5b2dhLmNvbSIsImF1ZCI6ImRldi5zaG9wLmVhc3lvZ2EuY29tIiwiaWF0IjoxNDc5OTc2MzcwLCJleHAiOjE0ODA1ODExNzAsImp0aSI6IjU4MzZhNWIyOTU2NzgiLCJkYXRhIjp7ImlkX2N1c3RvbWVyIjoiMzQ2OSIsImVtYWlsIjoiZWFzeW9nYUBvdXRsb29rLmNvbSJ9fQ.VxGrNJrbIqmoYG67KsnddsfLSSAa2upTd-WwnSu3UIU";
	
	public static void http_post(String url, Map<String, String> map) {
		http_post(url, map, null);
	}
	
	public static void http_post(String url, Map<String, String> map, String auth) {
		OkHttpClient client = new OkHttpClient().newBuilder()
				.connectTimeout(30, TimeUnit.SECONDS) // connect timeout
				.readTimeout(30, TimeUnit.SECONDS)
				.build();
		
		FormBody.Builder formBody = new FormBody.Builder();
		
		for (Entry<String, String> entry : map.entrySet()) {
			formBody.add(entry.getKey(), entry.getValue());
		}
		if (auth != null) {
			formBody.add("token", auth);
		}
		RequestBody body = formBody.build();
		
		Request.Builder builder = new Request.Builder()
				.url(url)
				.post(body);
		
		/*if (auth != null) {
			builder = builder.header("Auth-user", "Bearer "+auth);
		}*/
		Request request = builder.build();

		try {
		    Response response = client.newCall(request).execute();
		    
		    String json = response.body().string();
		    System.out.println("out = "+json);
		    if (response.code() == 200) {
		    	ObjectMapper mapper = new ObjectMapper();
		    	Map<String, Object> obj = mapper.readValue(json, LinkedHashMap.class);
		    	
		    	ObjectWriter writer = mapper.writerWithDefaultPrettyPrinter();
			    
			    System.out.println(writer.writeValueAsString(obj));
		    }
		    
		    Assert.assertEquals(response.code(), 200);
		} catch (IOException e) {
		    e.printStackTrace();
		}
	}
	
	public static String buildCheckCode(String apiName, String data, String time) {
		String apiKey = "ebe84bb5e0ad85a1bd77506ab28753e8";
		
		return Hashing.md5().hashString(apiName+data+time+apiKey, StandardCharsets.UTF_8).toString();
	}
	
	public static void main(String[] args) {
		System.out.println(Hashing.md5().hashString("o9vbuqgptmfWToy6Z8cSqrTq0PYOcOzIMToCToO3imZqLV4wTv1Z3VYv"+"yoga168", StandardCharsets.UTF_8));
	}
}
