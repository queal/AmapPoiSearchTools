package per.queal.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.lang.StringUtils;

import per.queal.pojo.AmapResp;
import per.queal.pojo.PoiData;
import per.queal.utils.HttpClientUtils;
import per.queal.xls.RowData;

import com.alibaba.fastjson.JSON;

public class AmapPoiService {
	// 8fc934421e0a94b2629dc98fb03a1164
	private static String API_URL = "http://restapi.amap.com/v3/place/text?city=${adCode}&types=${poiCode}&output=json&offset=20&page=${page}&key=8fc934421e0a94b2629dc98fb03a1164&extensions=all";

	public static List<RowData> queryPoiData(String adCode, String poiCode) {
		List<RowData> rowDataList = new ArrayList<>();

		int pageNum = 0;
		int index = 0;

		while (true) {
			String url = API_URL;
			url = url.replace("${adCode}", adCode);
			url = url.replace("${poiCode}", poiCode);
			url = url.replace("${page}", pageNum++ + "");

			HttpClient httpClient = HttpClientUtils.getHttpClient();
			String ret = HttpClientUtils.doGet(httpClient, url);
			System.out.println("正在爬去第 " + pageNum + " 页数据, 数据为: " + ret);

			AmapResp amapResp = JSON.parseObject(ret, AmapResp.class);
			if (StringUtils.equals(amapResp.getStatus(), "1")
					&& amapResp.getPois() != null
					&& amapResp.getPois().size() > 0) {

				List<PoiData> poiDataList = amapResp.getPois();
				for (PoiData poiData : poiDataList) {

					RowData rowData = new RowData();

					Map<Integer, String> rowDataUnit = new HashMap<>();

					rowDataUnit.put(0, poiData.getName());
					rowDataUnit.put(1, poiData.getType());
					rowDataUnit.put(2, poiData.getPname());
					rowDataUnit.put(3, poiData.getCityname());
					rowDataUnit.put(4, poiData.getAdname());
					rowDataUnit.put(5, poiData.getAddress());
					rowDataUnit.put(6, poiData.getTel());
					rowDataUnit.put(7, poiData.getLocation());

					rowData.setIndex(index++);
					rowData.setRowDataUnit(rowDataUnit);

					rowDataList.add(rowData);
				}

			} else {
				break;
			}
		}

		return rowDataList;
	}

	public static void main(String[] args) {
		// 郑州市  
		// 410100#110000
		//  
		// 许昌市  
		// 411000#110000
		//  
		// 成都市  
		// 510100#110000
		queryPoiData("410100", "110000");

	}
}
