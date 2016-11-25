import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.io.FileUtils;

public class TestImg {
	static List<String> shopImgs = new CopyOnWriteArrayList<>();
	static List<String> devImgs = new CopyOnWriteArrayList<>();
	static List<String> loseImgs = new CopyOnWriteArrayList<>();
	static List<String> sqlImgs = new CopyOnWriteArrayList<>();
	
	public static void main2(String[] args) throws IOException, SQLException {
		File shopFile = new File("C:/Users/tony/Downloads/px");
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Connecting to database...");
			
			conn = DriverManager.getConnection("jdbc:mysql://localhost/easyogap", "root", "123asd");
			stmt = conn.createStatement();
			String sql;
			
			sql = "SELECT b.id_product id_product, d.id_image id_image FROM ps_product_attribute b "
					+ "LEFT JOIN ps_product_lang c ON b.id_product = c.id_product AND c.id_lang = '6' "
					+ "LEFT JOIN ("
					+ "SELECT x.id_image, x.id_product_attribute FROM ps_product_attribute_image x "
					+ "LEFT JOIN ps_image y ON x.id_image = y.id_image "
					+ "ORDER BY position) d "
					+ "ON b.id_product_attribute = d.id_product_attribute";
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				Integer id_product  = rs.getInt("id_product");
				Integer id_image = rs.getInt("id_image");
		         
				String img = id_product+"-"+id_image+".jpg";
				if (sqlImgs.contains(img) == false) {
					sqlImgs.add(img);
				}
			}
		} catch(SQLException se) {
		      se.printStackTrace();
		} catch(Exception e) {
		      e.printStackTrace();
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		
		stackFile(shopFile.listFiles(), shopImgs, "px");
		
		for (String path : sqlImgs) {
			if (shopImgs.contains(path) == false) {
				loseImgs.add(path);
			}
		}
		
		for (String path : loseImgs) {
			System.out.println(path);
		}
		System.out.println("size = "+loseImgs.size());
	}
	
	public static void main(String[] args) throws IOException {
		String rootPath = "C:/Users/tony/Downloads";
		String shopName = "new_p";
		String devName = "old_p";
		String loseName = "plose";
		
		File shopFile = new File(rootPath, shopName);
		File devFile = new File(rootPath, devName);
		File loseFile = new File(rootPath, loseName);
		
		stackFile(shopFile.listFiles(), shopImgs, shopName);
		stackFile(devFile.listFiles(), devImgs, devName);
		
		for (String path : devImgs) {
			if (shopImgs.contains(path) == false) {
				loseImgs.add(path);
			}
		}
		
		if (loseFile.exists() == false) {
			loseFile.mkdirs();
		}
		
		for (String path : loseImgs) {
			File srcFile = new File(devFile, path);
			File destFile = new File(loseFile, path);
			File parentFile = destFile.getParentFile();
			if (parentFile.exists() == false) {
				parentFile.mkdirs();
			}
			FileUtils.copyFile(srcFile, destFile);
		}
		System.out.println("count = "+loseImgs.size());
	}
	
	public static void stackFile(File[] files, List<String> list, String prefix) {
		for (File file : files) {
			if (file.isDirectory()) {
				stackFile(file.listFiles(), list, prefix);
			} else {
				String path = file.getAbsolutePath();
				String subName = path.replace("C:\\Users\\tony\\Downloads\\"+prefix+"\\", "");
				
				list.add(subName);
			}
		}
	}
}
