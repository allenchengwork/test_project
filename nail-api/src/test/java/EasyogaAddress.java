import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.testng.annotations.Test;

public class EasyogaAddress {
	@Test
	public void address() throws FileNotFoundException, IOException, EncryptedDocumentException, InvalidFormatException {
		File file = new File("D:\\temp\\en_address.xlsx");
		try (
			FileInputStream fin = new FileInputStream(file);
			Workbook wb = WorkbookFactory.create(fin);
		) {
			Sheet st = wb.getSheetAt(0);
			String json = "[\n";
			for (int i = 0; i < st.getPhysicalNumberOfRows(); i+=2) {
				Row row = st.getRow(i);
				if (i > 1) {
					json += ",\n";
				} else {
					json += "\n";
				}
				json += "\t[";
				for (int j = 0; j < row.getPhysicalNumberOfCells(); j++) {
					Cell cell = row.getCell(j);
					String zip = "";
					if (j > 0) {
						Cell zipCell = st.getRow(i).getCell(j);
						zip = zipCell.getStringCellValue().substring(0, 3)+" ";
					}
					String addr = cell.getStringCellValue().trim();
					if (j > 0) {
						json += ", ";
						addr = addr.substring(3).trim();
					}
					//String addr = cell.getStringCellValue().trim().replace("District", "Dist.");
					json += String.format("\"%s%s\"", zip, addr);
				}
				json += "]";
			}
			json += "\n];";
			
			System.out.println(json);
		}
	}
	
	@Test
	public void map_address() throws FileNotFoundException, IOException, EncryptedDocumentException, InvalidFormatException {
		File file = new File("D:\\temp\\en_address.xlsx");
		try (
			FileInputStream fin = new FileInputStream(file);
			Workbook wb = WorkbookFactory.create(fin);
		) {
			Sheet st = wb.getSheetAt(0);
			String json = "{\n";
			for (int i = 1; i < st.getPhysicalNumberOfRows(); i+=2) {
				Row row = st.getRow(i);
				for (int j = 0; j < row.getPhysicalNumberOfCells(); j++) {
					Cell cell = row.getCell(j);
					Cell chCell = st.getRow(i-1).getCell(j);
					String zip = "";
					String chAddr = chCell.getStringCellValue().trim();
					String enAddr = cell.getStringCellValue().trim().replace("District", "Dist.");
					if (j > 0) {
						zip = chCell.getStringCellValue().substring(0, 3)+" ";
						chAddr = chAddr.substring(3);
					}
					if (j > 0 || i > 1) {
						json += ", \n";
					}
					String ch = String.format("\"%s%s\"", zip, chAddr);
					String en = String.format("\"%s%s\"", zip, enAddr);
					
					json += "\t"+ch+": "+en;
				}
			}
			json += "\n};";
			
			System.out.println(json);
		}
	}
}
