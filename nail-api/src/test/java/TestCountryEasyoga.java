import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.testng.annotations.Test;

public class TestCountryEasyoga extends TestEasyoga {
	@Test
	public void info_post() {
		String url = "http://dev.shop.easyoga.com/apis/v2/country/info";
		
		String apiName = "country.info";
		
		String time = DateFormatUtils.format(new Date(), "yyyyMMddHHmmss");
		
		String checkCode = buildCheckCode(apiName, "", time);
		
		System.out.println(checkCode);
		
		Map<String, String> map = new HashMap<>();
		map.put("lang", "CH");
		map.put("time", time);
		map.put("check_code", checkCode);
		
		http_post(url, map);
	}
	
	@Test
	public void city_post() {
		String url = "http://dev.shop.easyoga.com/apis/v2/country/city";
		
		String apiName = "country.city";
		
		String time = DateFormatUtils.format(new Date(), "yyyyMMddHHmmss");
		
		String checkCode = buildCheckCode(apiName, "", time);
		
		System.out.println(checkCode);
		
		Map<String, String> map = new HashMap<>();
		map.put("lang", "CH");
		map.put("time", time);
		map.put("check_code", checkCode);
		
		http_post(url, map);
	}
}
