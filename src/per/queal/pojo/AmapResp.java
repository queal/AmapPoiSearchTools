package per.queal.pojo;

import java.util.List;

public class AmapResp {

	private String status;
	private String count;
	private String info;
	private String infocode;
	private List<PoiData> pois;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getInfocode() {
		return infocode;
	}

	public void setInfocode(String infocode) {
		this.infocode = infocode;
	}

	public List<PoiData> getPois() {
		return pois;
	}

	public void setPois(List<PoiData> pois) {
		this.pois = pois;
	}

}
