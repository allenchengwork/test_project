import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.core.io.ClassPathResource;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Charsets;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Ipie2Case {
	@Test
	public void replaceImageName() throws JsonParseException, JsonMappingException, IOException {
		String dirPath = "C:\\Users\\tony\\Desktop\\元樂\\photo";
		File dir = new File(dirPath);
		
		String json = FileUtils.readFileToString(new ClassPathResource("ipie2/replace-image-name.json").getFile(), Charsets.UTF_8);
		ObjectMapper mapper = new ObjectMapper();
    	Map<String, String> map = mapper.readValue(json, LinkedHashMap.class);
    	for (File file : dir.listFiles()) {
    		if (map.containsKey(file.getName())) {
    			File newFile = new File(dirPath, map.get(file.getName()));
    			FileUtils.moveFile(file, newFile);
    		}
    	}
	}
	
	@Test
	public void getHTML() throws MalformedURLException, IOException {
		String dir = "D:\\IIS_WebSite\\ipie2\\news\\babyshower_new\\data\\彌月卡片";
		String url = "http://www.ipie2.com/news/babyshower_new/babyshower/index_1.html";
		String fileName = "皇家客製.json";
		String imgPath = "babyshower/images";
		Document doc = Jsoup.connect(url).get();
		Elements elms = doc.select("table img[width=\"216\"][height=\"144\"]");
		List<String> imgSet = elms.stream()
				.map(e -> StringUtils.replaceEach(e.attr("src"), 
						new String[] {"images/", ".jpg"}, new String[] {"", ""}))
				.distinct()
				//.sorted(String::compareTo)
				.collect(Collectors.toList());
		
		List<Map<String, String>> cardList = new ArrayList<>();
		System.out.println("size = "+imgSet.size());
		String result = "";
		for (String value : imgSet) {
			String val = value.trim();
			String id = val.replace("_s", "");
			String img = id+".jpg";
			String img_s = val+".jpg";
			
			Map<String, String> attr = new HashMap<>();
			attr.put("id", id);
			attr.put("img", img);
			attr.put("img_s", img_s);
			
			cardList.add(attr);
			
			result += "\t\t{\n";
			result += "\t\t\t\"id\": \""+id.toUpperCase()+"\",\n";
			result += "\t\t\t\"img\": \""+imgPath+"/"+img+"\",\n";
			result += "\t\t\t\"img_s\": \""+imgPath+"/"+img_s+"\"\n";
			result += "\t\t},\n";
		}
		result = result.substring(0, result.length()-2)+"\n";
		result = "{\n\t\"list\": [\n"+result+"\t]\n}";
		System.out.print(result);
		FileUtils.writeStringToFile(new File(dir, fileName), result, Charsets.UTF_8);
		System.out.println();
		System.out.println();
		for (int i = 0; i < imgSet.size(); i++) {
			System.out.printf("%-10s", imgSet.get(i));
			
			if (i%4 == 3) {
				System.out.println();
			}
		}
	}
	
	@Test
	public void encode() {
		String MerchantID = "8220130008035";
		String TerminalID = "99801153";
		String lidm = "2016100409370001";
		String purchAmt = "123";
		String txType = "0";
		String Option = "1";
		String Key = "U9uUPx6pKCHXK4TD6PRGVF92";
	}
	
	@Test
	public void testURL() throws JsonProcessingException, IOException {
		String root = "D:\\IIS_WebSite\\ipie2\\news\\babyshower_new";
		String dir = "D:\\IIS_WebSite\\ipie2\\news\\babyshower_new\\data";
		String settings = "settings.json";
		
		File file = new File(dir, settings);
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode node = mapper.readTree(file);
		
		JsonNode rootMenuList = node.findValue("root_menu");
		for (JsonNode rootMenu : rootMenuList) {
			String rootTitle = rootMenu.findValue("title").asText();
			JsonNode subMenuList = rootMenu.findValue("sub_menu");
			
			if (subMenuList == null) {
				continue;
			}
			
			File typeDir = new File(dir, rootTitle);
			for (JsonNode subMenu : subMenuList) {
				String subTitle = subMenu.findValue("title").asText();
				
				File styleFile = new File(typeDir, subTitle+".json");
				
				String styleText = updateFile(styleFile);
				
				FileUtils.writeStringToFile(styleFile, styleText, Charsets.UTF_8);
			}
		}
	}
	
	@Test
	public void testUrlFile() throws IOException {
		File typeDir = new File("D:\\IIS_WebSite\\ipie2\\news\\babyshower_new\\data", "彌月卡片");
		String subTitle = "皇家客製";
		File styleFile = new File(typeDir, subTitle+".json");
		String styleText = updateFile(styleFile);
		
		//FileUtils.writeStringToFile(styleFile, styleText, Charsets.UTF_8);
	}
	
	public String updateFile(File styleFile) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		
		OkHttpClient client = new OkHttpClient().newBuilder()
				.connectTimeout(30, TimeUnit.SECONDS) // connect timeout
				.readTimeout(30, TimeUnit.SECONDS)
				.build();
		
		String styleText = FileUtils.readFileToString(styleFile, Charsets.UTF_8);
		JsonNode styleNode = mapper.readTree(styleFile);
		JsonNode list = styleNode.findValue("list");
		if (list != null) {
			for (JsonNode item : list) {
				String id = item.findValue("id").asText();
				String img = item.findValue("img").asText();
				
				if ("".equals(img)) {
					continue;
				}
				String imgUrl = "http://www.ipie2.com/news/babyshower_new/"+img;
				
				Request.Builder builder = new Request.Builder()
						.url(imgUrl)
						.get();
				Request request = builder.build();

				try (Response response = client.newCall(request).execute()) {
				    if (response.code() == 404) {
				    	String[] splitImg = img.split("/");
				    	String oldImg = splitImg[splitImg.length-1];
				    	String[] oldImgArr = oldImg.split("\\.");
				    	String oldImgName = oldImgArr[0];
				    	String extName = oldImgArr[1];
				    	String uprImg = oldImgName.toUpperCase()+"."+extName;
				    	String newImg = "";
				    	for (int i = 0; i < splitImg.length-1; i++) {
				    		newImg += splitImg[i]+"/";
				    	}
				    	newImg += uprImg;
				    	System.out.println(newImg);
				    	
				    	styleText = styleText.replace(img, newImg);
				    	System.out.println("file = "+styleFile.getName());
				    	System.out.println("id = "+id);
				    }
				} catch (IOException e) {
				    e.printStackTrace();
				}
			}
		} else {
			JsonNode groupList = styleNode.findValue("group_list");
			
			for (JsonNode item : groupList) {
				JsonNode myList = item.findValue("list");
				
				for (JsonNode it : myList) {
					String id = it.findValue("id").asText();
					String img = it.findValue("img").asText();
					
					if ("".equals(img)) {
						continue;
					}
					String imgUrl = "http://www.ipie2.com/news/mark_new/"+img;
					
					Request.Builder builder = new Request.Builder()
							.url(imgUrl)
							.get();
					Request request = builder.build();

					try (Response response = client.newCall(request).execute()) {
					    if (response.code() == 404) {
					    	String[] splitImg = img.split("/");
					    	String oldImg = splitImg[splitImg.length-1];
					    	String[] oldImgArr = oldImg.split("\\.");
					    	String oldImgName = oldImgArr[0];
					    	String extName = oldImgArr[1];
					    	String uprImg = oldImgName.toUpperCase()+"."+extName;
					    	String newImg = "";
					    	for (int i = 0; i < splitImg.length-1; i++) {
					    		newImg += splitImg[i]+"/";
					    	}
					    	newImg += uprImg;
					    	System.out.println(newImg);
					    	
					    	styleText = styleText.replace(img, newImg);
					    	System.out.println("file = "+styleFile.getName());
					    	System.out.println("id = "+id);
					    }
					} catch (IOException e) {
					    e.printStackTrace();
					}
				}
			}
		}
		
		return styleText;
	}
}
