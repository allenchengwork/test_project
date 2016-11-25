import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.springframework.util.DigestUtils;
import org.testng.annotations.Test;

import com.google.common.hash.Hashing;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TestCase {
	public static void main2(String[] args) throws IOException {
		String secureHash = "BAB817EFFA9286ED51CC37D69446B027";
		
		Map<String, String> map = new TreeMap<>();
		map.put("Title", "easyoga");
		map.put("vpc_Command", "pay");
		map.put("vpc_Locale", "en");
		map.put("vpc_Merchant", "TEST177726801USD");
		map.put("vpc_AccessCode", "787BBF8F");
		map.put("vpc_Version", "1");
		map.put("vpc_Amount", "200");
		map.put("vpc_OrderInfo", "");
		map.put("vpc_MerchTxnRef", "9809039788:::0:");
		map.put("vpc_ReturnURL", "http://dev.shop.easyoga.com/test_trans/vpc_php_authenticate_and_pay_merchanthost_dr.php");
		map.put("vpc_gateway", "ssl");
		map.put("vpc_card", "VISA");
		map.put("vpc_CardNum", "4005550000000001");
		map.put("vpc_CardExp", "1705");
		map.put("vpc_CardSecurityCode", "100");
		
		String md5HashData = secureHash;
		for (String value : map.values()) {
			if (value.length() > 0) {
				md5HashData += value;
			}
		}
		String hashedValue = DigestUtils.md5DigestAsHex(md5HashData.getBytes()).toUpperCase();
		map.put("vpc_SecureHash", hashedValue);
		
		for (Entry<String, String> entry : map.entrySet()) {
			System.out.println(entry.getKey()+"="+entry.getValue());
		}
		
		
		/*OkHttpClient client = new OkHttpClient();
		FormEncodingBuilder formBody = new FormEncodingBuilder();
		for (Entry<String, String> entry : map.entrySet()) {
			formBody.add(entry.getKey(), entry.getValue());
		}
		RequestBody requestBody = formBody.build();
		
		Request request = new Request.Builder()
		        .url("https://migs.mastercard.com.au/vpcpay")
		        .post(requestBody)
		        .build();
		
		Response response = client.newCall(request).execute();
		System.out.println("code = "+response.code());
		
		FileUtils.write(new File("D:/trans.html"), response.body().string(), Charsets.UTF_8);*/
	}
	
	@Test
	public void query() {
		OkHttpClient client = new OkHttpClient();

		RequestBody formBody = new FormBody.Builder()
		        .add("Title", "easyoga")
		        .add("vpc_AccessCode", "787BBF8F")
		        .add("vpc_MerchTxnRef", "9809039788:::0:")
		        .add("vpc_Merchant", "TEST177726801USD")
		        .add("vpc_Version", "1")
		        .add("vpc_Command", "queryDR")
		        .add("vpc_User", "13011")
		        .add("vpc_Password", "greenarrow2016")
		        .build();
		Request request = new Request.Builder()
		        .url("https://migs.mastercard.com.au/vpcdps")
		        .post(formBody)
		        .build();

		try {
		    Response response = client.newCall(request).execute();

		    System.out.println("body = "+response.body().string());
		} catch (IOException e) {
		    e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		String hashed = Hashing.sha256()
		        .hashString("123456"+".maplebox", StandardCharsets.UTF_8)
		        .toString();
		System.out.println(hashed);
	}
}
