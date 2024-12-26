package utilities;

import java.io.IOException;

import org.testng.annotations.DataProvider;

public class DataProviders {
	@DataProvider(name = "LoginData")
	public String[][] getData() throws IOException {
		String path = ".\\testData\\login_test_data.xlsx";
		ExcelUtility xlUtility = new ExcelUtility(path);

		int tRows = xlUtility.getRowCount("Sheet1");
		int tCols = xlUtility.getCellCount("Sheet1", 1);

		String loginData[][] = new String[tRows][tCols];
		for (int i = 1; i <= tRows; i++) {
			for (int j = 0; j < tCols; j++) {
				loginData[i - 1][j] = xlUtility.getCellData("Sheet1", i, j);
			}
		}
		return loginData;
	}
}
