package per.queal.main;

import java.io.File;
import java.util.List;

import per.queal.service.AmapPoiService;
import per.queal.xls.ExcelUtils;
import per.queal.xls.RowData;

public class Main {

	public static void main(String[] args) {

		// 郑州市  
		// 410100#110000
		//  
		// 许昌市  
		// 411000#110000
		//  
		// 成都市  
		// 510100#110000
		String filePath = "成都市-风景名胜.xlsx";
		String adCode = "510100";
		String poiCode = "110000";

		List<RowData> rowDataList = AmapPoiService
				.queryPoiData(adCode, poiCode);
		ExcelUtils.writeLine(new File(filePath), rowDataList);

	}
}
