package nidec.beans;

public class Machine {
	
	private String machineId;
	private String machineName;
	private String productId;
	private int status;
	
	public Machine() {
		
	}
	
	public Machine(String machineId, String machineName, String productId, int status) {
		this.machineId = machineId;
		this.machineName = machineName;
		this.productId = productId;
		this.status = status;
	}
	
	public String getMachineId() {
		return machineId;
	}
	
	public void setMachineId(String machineId) {
		this.machineId = machineId;
	}
	
	public String getMachineName() {
		return machineName;
	}
	
	public void setMachineName(String machineName) {
		this.machineName = machineName;
	}
	
	public String getProductId() {
		return productId;
	}
	
	public void setProductId(String productId) {
		this.productId = productId;
	}
	
	public int getStatus() {
		return status;
	}
	
	public void setStatus(int status) {
		this.status = status;
	}
}
