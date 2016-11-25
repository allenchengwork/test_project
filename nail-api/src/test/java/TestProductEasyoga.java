import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.testng.annotations.Test;

public class TestProductEasyoga extends TestEasyoga {
	@Test
	public void collection_post() {
		String url = "http://dev.shop.easyoga.com/apis/v2/product/collection";
		
		String apiName = "product.collection";
		String idCategory = "83";
		
		String time = "20160831142723";//DateFormatUtils.format(new Date(), "yyyyMMddHHmmss");
		
		String checkCode = buildCheckCode(apiName, idCategory, time);
		
		System.out.println(checkCode);
		
		Map<String, String> map = new HashMap<>();
		map.put("id_category", idCategory);
		map.put("page", "1");
		map.put("lang", "EN");
		map.put("time", time);
		map.put("check_code", checkCode);
		
		http_post(url, map);
	}
	
	@Test
	public void detail_post() {
		String url = "http://dev.shop.easyoga.com/apis/v2/product/detail";
		
		String apiName = "product.detail";
		String idProduct = "2397";
		
		String time = DateFormatUtils.format(new Date(), "yyyyMMddHHmmss");
		
		String checkCode = buildCheckCode(apiName, idProduct, time);
		
		System.out.println(checkCode);
		
		Map<String, String> map = new HashMap<>();
		map.put("id_product", idProduct);
		map.put("lang", "EN");
		map.put("time", time);
		map.put("check_code", checkCode);
		
		http_post(url, map, TOKEN);
	}
	
	@Test
	public void carousel_post() {
		String url = "https://shop.easyoga.com/apis/v2/product/carousel";
		
		String apiName = "product.carousel";
		
		String time = "20160831142723";//DateFormatUtils.format(new Date(), "yyyyMMddHHmmss");
		
		String checkCode = buildCheckCode(apiName, "", time);
		
		System.out.println(checkCode);
		
		Map<String, String> map = new HashMap<>();
		map.put("lang", "CH");
		map.put("time", time);
		map.put("check_code", checkCode);
		
		http_post(url, map);
	}
}
