package nidec.beans;

public class PinionShaft {

	private int partId;
	private int side;
	private String time;
	private String measureValue;
	private int result;
	
	public PinionShaft() {
		
	}
	
	public PinionShaft(int partId, int side, String time, String measureValue, int result) {
		this.partId = partId;
		this.side = side;
		this.time = time;
		this.measureValue = measureValue;
		this.result = result;
	}
	
	public PinionShaft(int side, String measureValue, int result) {
		this.side = side;
		this.measureValue = measureValue;
		this.result = result;
	}
	public int getPartId() {
		return partId;
	}
	
	public void setPartId(int partId) {
		this.partId = partId;
	}

	public int getSide() {
		return side;
	}
	
	public void setSide(int side) {
		this.side = side;
	}

	public String getTime() {
		return time;
	}
	
	public void setTime(String time) {
		this.time = time;
	}
	
	public String getMeasureValue() {
		return measureValue;
	}
	
	public void setMeasureValue(String measureValue) {
		this.measureValue = measureValue;
	}

	public int getResult() {
		return result;
	}
	
	public void setResult(int result) {
		this.result = result;
	}
	
}
