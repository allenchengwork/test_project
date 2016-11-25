import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.testng.annotations.Test;

public class TestMemberEasyoga extends TestEasyoga {
	@Test
	public void register_post() {
		String url = "http://dev.shop.easyoga.com/apis/v2/member/register";
		
		String apiName = "member.register";
		String email = "allen@arcrma.com";
		
		String time = DateFormatUtils.format(new Date(), "yyyyMMddHHmmss");
		
		String checkCode = buildCheckCode(apiName, email, time);
		
		System.out.println(checkCode);
		
		Map<String, String> map = new HashMap<>();
		map.put("email", email);
		map.put("name", "王小明");
		map.put("passwd", "12343545");
		map.put("id_country", "203");
		map.put("phone_mobile", "0989423580");
		map.put("lang", "CH");
		map.put("time", time);
		map.put("check_code", checkCode);
		
		http_post(url, map);
	}
	
	@Test
	public void valid_code_post() {
		String url = "http://dev.shop.easyoga.com/apis/v2/member/valid_code";
		
		String apiName = "member.valid_code";
		String email = "allen@arcrma.com";
		
		String time = DateFormatUtils.format(new Date(), "yyyyMMddHHmmss");
		
		String checkCode = buildCheckCode(apiName, email, time);
		
		System.out.println(checkCode);
		
		Map<String, String> map = new HashMap<>();
		map.put("email", "allen@arcrma.com");
		map.put("code", "50381");
		map.put("lang", "CH");
		map.put("time", time);
		map.put("check_code", checkCode);
		
		http_post(url, map);
	}
	
	@Test
	public void login_post() {
		String url = "https://shop.easyoga.com/apis/v2/member/login";
		
		String apiName = "member.login";
		String email = "easyoga@outlook.com";
		
		String time = DateFormatUtils.format(new Date(), "yyyyMMddHHmmss");
		
		String checkCode = buildCheckCode(apiName, email, time);
		
		System.out.println(checkCode);
		
		Map<String, String> map = new HashMap<>();
		map.put("email", email);
		map.put("passwd", "yoga168");
		map.put("lang", "CH");
		map.put("time", time);
		map.put("check_code", checkCode);
		
		http_post(url, map);
	}
	
	@Test
	public void resend_code_post() {
		String url = "http://dev.shop.easyoga.com/apis/v2/member/resend_code";
		
		String apiName = "member.resend_code";
		String email = "allen@arcrma.com";
		
		String time = DateFormatUtils.format(new Date(), "yyyyMMddHHmmss");
		
		String checkCode = buildCheckCode(apiName, email, time);
		
		System.out.println(checkCode);

		Map<String, String> map = new HashMap<>();
		map.put("email", email);
		map.put("id_country", "203");
		map.put("phone_mobile", "0989423580");
		map.put("lang", "CH");
		map.put("time", time);
		map.put("check_code", checkCode);
		
		http_post(url, map);
	}
	
	@Test
	public void resend_pwd_post() {
		String url = "http://dev.shop.easyoga.com/apis/v2/member/resend_pwd";
		
		String apiName = "member.resend_pwd";
		String email = "allen@arcrma.com";
		
		String time = DateFormatUtils.format(new Date(), "yyyyMMddHHmmss");
		
		String checkCode = buildCheckCode(apiName, email, time);
		
		System.out.println(checkCode);

		Map<String, String> map = new HashMap<>();
		map.put("email", email);
		map.put("id_country", "203");
		map.put("phone_mobile", "0989423580");
		map.put("lang", "CH");
		map.put("time", time);
		map.put("check_code", checkCode);
		
		http_post(url, map);
	}
	
	@Test
	public void info_post() {
		String url = "https://shop.easyoga.com/apis/v2/member/info";
		
		String apiName = "member.info";
		String email = "easyoga@outlook.com";
		
		String time = DateFormatUtils.format(new Date(), "yyyyMMddHHmmss");
		
		String checkCode = buildCheckCode(apiName, email, time);
		
		System.out.println(checkCode);
		
		Map<String, String> map = new HashMap<>();
		map.put("lang", "CH");
		map.put("time", time);
		map.put("check_code", checkCode);
		
		http_post(url, map, TOKEN);
	}
	
	@Test
	public void edit_info_post() {
		String url = "http://dev.shop.easyoga.com/apis/v2/member/edit_info";
		
		String apiName = "member.edit_info";
		String email = "allen@arcrma.com";
		
		String time = DateFormatUtils.format(new Date(), "yyyyMMddHHmmss");
		
		String checkCode = buildCheckCode(apiName, email, time);
		
		System.out.println(checkCode);

		Map<String, String> map = new HashMap<>();
		map.put("name", "王小天");
		map.put("id_gender", "1");
		map.put("id_country", "203");
		map.put("birthdate", "1973-09-09");
		map.put("newsletter", "0");
		map.put("optin", "0");
		map.put("address", "地址");
		map.put("phone", "0987654321");
		map.put("phone_mobile", "0987654321");
		map.put("city", "台北市");
		map.put("area", "104中山區");
		map.put("postcode", "104");
		map.put("lang", "CH");
		map.put("time", time);
		map.put("check_code", checkCode);
		
		http_post(url, map, TOKEN);
	}
	
	@Test
	public void edit_pwd_post() {
		String url = "http://dev.shop.easyoga.com/apis/v2/member/edit_pwd";
		
		String apiName = "member.edit_pwd";
		String email = "allen@arcrma.com";
		
		String time = DateFormatUtils.format(new Date(), "yyyyMMddHHmmss");
		
		String checkCode = buildCheckCode(apiName, email, time);
		
		System.out.println(checkCode);

		Map<String, String> map = new HashMap<>();
		map.put("old_passwd", "80624");
		map.put("passwd", "123456");
		map.put("lang", "CH");
		map.put("time", time);
		map.put("check_code", checkCode);
		
		http_post(url, map, TOKEN);
	}
	
	@Test
	public void show_my_favorite_post() {
		String url = "http://dev.shop.easyoga.com/apis/v2/member/show_my_favorite";
		
		String apiName = "member.show_my_favorite";
		String email = "allen@arcrma.com";
		
		String time = DateFormatUtils.format(new Date(), "yyyyMMddHHmmss");
		
		String checkCode = buildCheckCode(apiName, email, time);
		
		System.out.println(checkCode);
		
		Map<String, String> map = new HashMap<>();
		map.put("lang", "CH");
		map.put("time", time);
		map.put("check_code", checkCode);
		
		http_post(url, map, TOKEN);
	}
	
	@Test
	public void add_my_favorite_post() {
		String url = "http://dev.shop.easyoga.com/apis/v2/member/add_my_favorite";
		
		String apiName = "member.add_my_favorite";
		String email = "allen@arcrma.com";
		
		String time = DateFormatUtils.format(new Date(), "yyyyMMddHHmmss");
		
		String checkCode = buildCheckCode(apiName, email, time);
		
		System.out.println(checkCode);
		
		Map<String, String> map = new HashMap<>();
		map.put("id_product", "3384");
		map.put("color", "667");
		map.put("size", "151");
		map.put("lang", "CH");
		map.put("time", time);
		map.put("check_code", checkCode);
		
		http_post(url, map, TOKEN);
	}
}
