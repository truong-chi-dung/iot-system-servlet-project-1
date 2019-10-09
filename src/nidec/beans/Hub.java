package nidec.beans;

public class Hub {

	private int partId;
	private int side;
	private String time;
	private int result;
	private int front;
	private int top;
	private int boss;
	private int id;
	private int r;
	private int bottom;
	
	public Hub() {
		
	}
	
	public Hub(int partId, int side, String time, int result, int front, int top, int boss, int id, int r, int bottom) {
		this.partId = partId;
		this.side = side;
		this.time = time;
		this.result = result;
		this.front = front;
		this.top = top;
		this.boss = boss;
		this.id = id;
		this.r = r;
		this.bottom = bottom;
	}
	
	public Hub(int side, int result, int front, int top, int boss, int id, int r, int bottom) {
		this.side = side;
		this.result = result;
		this.front = front;
		this.top = top;
		this.boss = boss;
		this.id = id;
		this.r = r;
		this.bottom = bottom;
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
	
	public int getResult() {
		return result;
	}
	
	public void setResult(int result) {
		this.result = result;
	}

	public int getFront() {
		return front;
	}
	
	public void setFront(int front) {
		this.front = front;
	}
	
	public int getTop() {
		return top;
	}
	
	public void setTop(int top) {
		this.top = top;
	}

	public int getBoss() {
		return boss;
	}
	
	public void setBoss(int boss) {
		this.boss = boss;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public int getR() {
		return r;
	}
	
	public void setR(int r) {
		this.r = r;
	}
	
	public int getBottom() {
		return bottom;
	}
	
	public void setBottom(int bottom) {
		this.bottom = bottom;
	}

}
