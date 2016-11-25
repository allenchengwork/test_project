import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class ExampleAPI {
	public static final String API_KEY = "ebe84bb5e0ad85a1bd77506ab28753e8";
	public static void main(String[] args) throws MalformedURLException, IOException {
		String url = "http://dev.shop.easyoga.com/apis/v2/product/collection";

		String apiName = "product.collection";
		String idCategory = "58";
		String time = time();

		String checkCode = buildCheckCode(apiName, idCategory, time);

		Map<String, String> map = new HashMap<>();
		map.put("id_category", idCategory);
		map.put("lang", "CH");
		map.put("time", time);
		map.put("check_code", checkCode);
		
		String postData = queryString(map);
		
		HttpURLConnection httpURLConnection = null;
		DataOutputStream dataOutputStream = null;
		BufferedReader bufferedReader = null;
		try {
			httpURLConnection = (HttpURLConnection) new URL(url).openConnection();
			httpURLConnection.setRequestMethod("POST");
			httpURLConnection.setDoInput(true);
			httpURLConnection.setDoOutput(true);
			httpURLConnection.setUseCaches(false);

			dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());
			dataOutputStream.write(postData.getBytes("UTF-8"));
			dataOutputStream.flush();
			
			int responseCode = httpURLConnection.getResponseCode();
			
			if (responseCode == 200) {
				StringBuffer responseString = new StringBuffer();
				bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
				String line = null;
				while ((line = bufferedReader.readLine()) != null) {
					responseString.append(line);
				}
				System.out.println(responseString);
			}
		} finally {
			if (dataOutputStream != null) {
				dataOutputStream.close();
			}
			if (bufferedReader != null) {
				bufferedReader.close();
			}
		}
	}

	public static String buildCheckCode(String apiName, String data, String time) {
		return md5(apiName + data + time + API_KEY);
	}
	
	public static String queryString(Map<String, String> map) {
		String query = "";
		for (Entry<String, String> entry : map.entrySet()) {
			if (query.isEmpty() == false) {
				query += "&";
			}
			query += entry.getKey() + "=" + entry.getValue();
		}
		return query;
	}
	
	public static String time() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		return dateFormat.format(new Date());
	}

	public static String md5(String md5) {
		try {
			java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
			byte[] array = md.digest(md5.getBytes());
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < array.length; ++i) {
				sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
			}
			return sb.toString();
		} catch (java.security.NoSuchAlgorithmException e) {
		}
		return null;
	}
}
