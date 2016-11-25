import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.testng.annotations.Test;

public class TestHtmlEasyoga extends TestEasyoga {
	@Test
	public void content_post() {
		String url = "http://dev.shop.easyoga.com/apis/v2/html/content";
		String code = "Use_of_Term";
		
		String apiName = "html.content";
		
		String time = DateFormatUtils.format(new Date(), "yyyyMMddHHmmss");
		
		String checkCode = buildCheckCode(apiName, "", time);
		
		System.out.println(checkCode);
		
		Map<String, String> map = new HashMap<>();
		map.put("code", code);
		map.put("lang", "CH");
		map.put("time", time);
		map.put("check_code", checkCode);
		
		http_post(url, map);
	}
}
