package nidec.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import nidec.beans.Product;
import nidec.beans.Machine;
import nidec.beans.UserAccount;
import nidec.beans.Hub;
import nidec.beans.PinionShaft;

public class DBUtils {

	public static UserAccount findUser(Connection conn, String userName, String password) throws SQLException {
		
		String sql = "Select a.User_Name, a.Password, a.Gender from User_Account a where a.User_Name = ? and a.password = ?";
		
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, userName);
		pstm.setString(2, password);
		ResultSet rs = pstm.executeQuery();
		
		if(rs.next()) {
			String gender = rs.getString("Gender");
			UserAccount user = new UserAccount();
			user.setUserName(userName);
			user.setPassword(password);
			user.setGender(gender);
			return user;
		}
		return null;
	}
	
	public static UserAccount findUser(Connection conn, String userName) throws SQLException {
		String sql = "Select a.User_Name, a.Password, a.Gender from User_Account a where a.User_Name = ?";
		
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, userName);
		
		ResultSet rs = pstm.executeQuery();
		
		if (rs.next()) {
			String password = rs.getString("password");
			String gender = rs.getString("Gender");
			UserAccount user = new UserAccount();
			user.setUserName(userName);
			user.setPassword(password);
			user.setGender(gender);
			return user;
		}
		return null;
	}
	
	public static List<Product> queryProduct(Connection conn) throws SQLException {
		String sql = "Select * from nidec1.product";
		
		PreparedStatement pstm = conn.prepareStatement(sql);
		
		ResultSet rs = pstm.executeQuery();
		List<Product> list = new ArrayList<Product>();
		while(rs.next()) {
			String productId = rs.getString("product_id");
			String productName = rs.getString("product_name");
			int targetDay = rs.getInt("target_day");
			int targetWeek = rs.getInt("target_week");
			int targetMonth = rs.getInt("target_month");
			Product product = new Product();
			product.setProductId(productId);
			product.setProductName(productName);
			product.setTargetDay(targetDay);
			product.setTargetWeek(targetWeek);
			product.setTargetMonth(targetMonth);
			list.add(product);
		}
		return list;
	}
	
	public static Product findProduct(Connection conn, String productId) throws SQLException {
		String sql = "Select a.product_id, a.product_name, a.target_day, a.target_week, a.target_month from nidec1.product a where a.product_id=?";
		
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, productId);
		
		ResultSet rs = pstm.executeQuery();
		
		while (rs.next()) {
			String productName = rs.getString("product_name");
			int targetDay = rs.getInt("target_day");
			int targetWeek = rs.getInt("target_week");
			int targetMonth = rs.getInt("target_month");
			Product product = new Product(productId, productName, targetDay, targetWeek, targetMonth);
			return product;
		}
		return null;
	}
	
	public static void updateProduct(Connection conn, Product product) throws SQLException {
		String sql = "Update Product set product_name=?, target_day=?, target_week=?, target_month=? where product_id=?";
		
		PreparedStatement pstm = conn.prepareStatement(sql);
		
		pstm.setString(1, product.getProductName());
		pstm.setInt(2, product.getTargetDay());
		pstm.setInt(3, product.getTargetWeek());
		pstm.setInt(4, product.getTargetMonth());
		pstm.setString(5, product.getProductId());
		pstm.executeUpdate();
	}
	
	public static void insertProduct(Connection conn, Product product) throws SQLException {
		String sql = "Insert into Product(product_id, product_name, target_day, target_week, target_month) values (?,?,?,?,?)";
		
		PreparedStatement pstm = conn.prepareStatement(sql);
		
		pstm.setString(1, product.getProductId());
		pstm.setString(2, product.getProductName());
		pstm.setInt(3, product.getTargetDay());
		pstm.setInt(4, product.getTargetWeek());
		pstm.setInt(5, product.getTargetMonth());
		pstm.executeUpdate();
		
	}
	
	public static void deleteProduct(Connection conn, String productId) throws SQLException {
		String sql = "Delete From Product where product_id=?";
		
		PreparedStatement pstm = conn.prepareStatement(sql);
		
		pstm.setString(1, productId);
		pstm.executeUpdate();
	}
	
	public static String[][] queryOkGraphData(Connection conn, String machineId, Date dateTime) throws SQLException {
				
		SimpleDateFormat hourFormatter = new SimpleDateFormat("HH");
		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
		
		int hour = Integer.parseInt(hourFormatter.format(dateTime));
		String date = dateFormatter.format(dateTime);
		
		//int hour = 15;
		//String date = "2019-01-30";
		
		int i = 0;
		int stepHour = 8 + i;
		String[][] okData = new String[stepHour+1][2];
		String sql = "Select count(*) from " + machineId + " where result=1 and time>=(?) and time<(?)";		

		int sumOk = 0;
		while (stepHour <= hour) {
			
			String dateMin = date + " " + Integer.toString(stepHour) + ":00:00";
			String dateMax = date + " " + Integer.toString(stepHour) + ":59:00";
			
			PreparedStatement pstm = conn.prepareStatement(sql);
			
			pstm.setString(1, dateMin);
			pstm.setString(2, dateMax);
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				okData[i][0] = date + " " + Integer.toString(stepHour) + ":00:00";
				sumOk = sumOk + rs.getInt("count(*)");
				okData[i][1] = Integer.toString(sumOk);
			}
			i++;
			stepHour = 8 + i;
		}
		return okData;		
	}
	
	public static String[][] queryNgGraphData(Connection conn, String machineId, Date dateTime) throws SQLException {
		
		SimpleDateFormat hourFormatter = new SimpleDateFormat("HH");
		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
		
		int hour = Integer.parseInt(hourFormatter.format(dateTime));
		String date = dateFormatter.format(dateTime);
		//int hour = 15;
		//String date = "2019-01-30";
		
		int i = 0;
		int stepHour = 8 + i;
		String[][] ngData = new String[stepHour+1][2];
		String sql = "Select count(*) from " + machineId + " where result=0 and time>=(?) and time<(?)";		

		int sumNg = 0;
		while (stepHour <= hour) {
			
			String dateMin = date + " " + Integer.toString(stepHour) + ":00:00";
			String dateMax = date + " " + Integer.toString(stepHour) + ":59:00";
			
			PreparedStatement pstm = conn.prepareStatement(sql);
			
			pstm.setString(1, dateMin);
			pstm.setString(2, dateMax);
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				ngData[i][0] = date + " " + Integer.toString(stepHour) + ":00:00";
				sumNg = sumNg + rs.getInt("count(*)");
				ngData[i][1] = Integer.toString(sumNg);
			}
			i++;
			stepHour = 8 + i;
		}
		return ngData;		
	}
	
	public static String[][] queryOkGraphDataMinute(Connection conn, String machineId, Date dateTime) throws SQLException {
		
		SimpleDateFormat hourFormatter = new SimpleDateFormat("HH");
		SimpleDateFormat minuteFormatter = new SimpleDateFormat("mm");
		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
		
		int hour = Integer.parseInt(hourFormatter.format(dateTime));
		int minute = Integer.parseInt(minuteFormatter.format(dateTime));
		String date = dateFormatter.format(dateTime);
		
		//int hour = 9;
		//int minute = 00;
		//String date = "2019-04-18";

		//System.out.println(hour);
		//System.out.println(minute);
		//System.out.println(date);
		
		int i = 0;
		int stepHour = 8;
		int stepMinute = minute;
		String[][] okData = new String[(hour-stepHour+1)*60][2];
		String sql = "Select count(*) from " + machineId + " where result=1 and time>=(?) and time<(?)";		

		int sumOk = 0;
		while (stepHour <= hour) {
			
			String dateMin = date + " " + Integer.toString(stepHour) + ":" + Integer.toString(stepMinute) + ":00";
			String dateMax = date + " " + Integer.toString(stepHour) + ":" + Integer.toString(stepMinute) + ":59";
			
			
			PreparedStatement pstm = conn.prepareStatement(sql);
			
			pstm.setString(1, dateMin);
			pstm.setString(2, dateMax);
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				okData[i][0] = date + " " + Integer.toString(stepHour) + ":" + Integer.toString(stepMinute) + ":00";
				sumOk = sumOk + rs.getInt("count(*)");
				okData[i][1] = Integer.toString(sumOk);
			}
			i++;
			if(stepMinute == 59) {
				stepHour = stepHour + 1;
				stepMinute = 0;
			} else {
				stepMinute = stepMinute + 1;
			}
			
		}
		return okData;		
	}
	
	public static String[][] queryNgGraphDataMinute(Connection conn, String machineId, Date dateTime) throws SQLException {
		
		SimpleDateFormat hourFormatter = new SimpleDateFormat("HH");
		SimpleDateFormat minuteFormatter = new SimpleDateFormat("mm");
		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
		
		int hour = Integer.parseInt(hourFormatter.format(dateTime));
		int minute = Integer.parseInt(minuteFormatter.format(dateTime));
		String date = dateFormatter.format(dateTime);
		
		//int hour = 9;
		//int minute = 0;
		//String date = "2019-04-18";
		
		int i = 0;
		int stepHour = 8;
		int stepMinute = minute;	
		String[][] ngData = new String[(hour-stepHour+1)*60][2];
		String sql = "Select count(*) from " + machineId + " where result=0 and time>=(?) and time<(?)";		

		int sumNg = 0;
		while (stepHour <= hour) {
			
			String dateMin = date + " " + Integer.toString(stepHour) + ":" + Integer.toString(stepMinute) + ":00";
			String dateMax = date + " " + Integer.toString(stepHour) + ":" + Integer.toString(stepMinute) + ":59";
			
			PreparedStatement pstm = conn.prepareStatement(sql);
			
			pstm.setString(1, dateMin);
			pstm.setString(2, dateMax);
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				ngData[i][0] = date + " " + Integer.toString(stepHour) + ":" + Integer.toString(stepMinute) + ":00";
				sumNg = sumNg + rs.getInt("count(*)");
				ngData[i][1] = Integer.toString(sumNg);
			}
			i++;
			if(stepMinute == 59) {
				stepHour = stepHour + 1;
				stepMinute = 0;
			} else {
				stepMinute = stepMinute + 1;
			}
		}
		return ngData;		
	}
	
	public static int getTargetDay(Connection conn, String machineId) throws SQLException {
		String sql = "Select product.target_day from machine inner join product on machine.product_id = product.product_id where machine.machine_id = ?";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, machineId);
		
		ResultSet rs = pstm.executeQuery();
		
		while (rs.next()) {
			return rs.getInt("target_day");
		}
		return 1;
	}
	
	public static int getSumOkDay(Connection conn, String machineId, Date dateTime) throws SQLException {
		
		SimpleDateFormat hourFormatter = new SimpleDateFormat("HH");
		SimpleDateFormat dateFormatter = new SimpleDateFormat("YYYY-MM-dd");
		
		int hour = Integer.parseInt(hourFormatter.format(dateTime));
		String date = dateFormatter.format(dateTime);
		
		//int hour = 15;
		//String date = "2018-09-16";
		
		int i = 0;
		int stepHour = 8 + i;
		String sql = "Select count(*) from " + machineId + " where result=1 and time>=(?) and time<(?)";		

		int sumOk = 0;
		while (stepHour <= hour) {
			
			String dateMin = date + " " + Integer.toString(stepHour) + ":00:00";
			String dateMax = date + " " + Integer.toString(stepHour) + ":59:00";
			
			PreparedStatement pstm = conn.prepareStatement(sql);
			
			pstm.setString(1, dateMin);
			pstm.setString(2, dateMax);
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				sumOk = sumOk + rs.getInt("count(*)");
			}
			i++;
			stepHour = 8 + i;
		}
		return sumOk;
	}
	
	public static List<Hub> queryProductHub(Connection conn) throws SQLException {
		String sql = "Select * from nidec1.hub1";
		
		PreparedStatement pstm = conn.prepareStatement(sql);
		
		ResultSet rs = pstm.executeQuery();
		List<Hub> list = new ArrayList<Hub>();
		
		while(rs.next()) {
			int partId = rs.getInt("part_id");
			int side = rs.getInt("side");
			String time = rs.getString("time");
			int result = rs.getInt("result");
			int front = rs.getInt("front");
			int top = rs.getInt("top");
			int boss = rs.getInt("boss");
			int id = rs.getInt("id");
			int r = rs.getInt("r");
			int bottom = rs.getInt("bottom");
			
			Hub hub = new Hub();
			hub.setPartId(partId);
			hub.setSide(side);
			hub.setTime(time);
			hub.setResult(result);
			hub.setFront(front);
			hub.setTop(top);
			hub.setBoss(boss);
			hub.setId(id);
			hub.setR(r);
			hub.setBottom(bottom);
			
			list.add(hub);
		}
		return list;
	}
	
	public static List<PinionShaft> queryProductPinionShaft(Connection conn) throws SQLException {
		String sql = "Select * from nidec1.pinion_shaft1";
		
		PreparedStatement pstm = conn.prepareStatement(sql);
		
		ResultSet rs = pstm.executeQuery();
		List<PinionShaft> list = new ArrayList<PinionShaft>();
		
		while(rs.next()) {
			int partId = rs.getInt("part_id");
			int side = rs.getInt("side");
			String time = rs.getString("time");
			String measureValue = rs.getString("measure_value");
			int result = rs.getInt("result");
			
			PinionShaft pinionShaft = new PinionShaft();
			pinionShaft.setPartId(partId);
			pinionShaft.setSide(side);
			pinionShaft.setTime(time);
			pinionShaft.setMeasureValue(measureValue);
			pinionShaft.setResult(result);
			
			list.add(pinionShaft);
		}
		return list;
	}
	
	public static List<Machine> queryMachine(Connection conn) throws SQLException {
		String sql = "Select * from nidec1.machine";
		
		PreparedStatement pstm = conn.prepareStatement(sql);
		
		ResultSet rs = pstm.executeQuery();
		List<Machine> list = new ArrayList<Machine>();
		while(rs.next()) {
			String machineId = rs.getString("machine_id");
			String machineName = rs.getString("machine_name");
			String productId = rs.getString("product_id");
			int status = rs.getInt("status");
			
			Machine machine = new Machine();
			machine.setProductId(productId);
			machine.setMachineId(machineId);
			machine.setMachineName(machineName);
			machine.setStatus(status);
			
			list.add(machine);
		}
		return list;
	}
	
	public static void insertHubProduct(Connection conn, Hub hub) throws SQLException {
		String sql = "Insert into hub1(side, time, result, front, top, boss, id, r, bottom) values (?,?,?,?,?,?,?,?,?)";
		
		PreparedStatement pstm = conn.prepareStatement(sql);
		
		Date date= new Date();
		SimpleDateFormat dateTimeFormatter = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
		String dateTime = dateTimeFormatter.format(date);
		
		
		pstm.setInt(1, hub.getSide());
		pstm.setString(2, dateTime);
		pstm.setInt(3, hub.getResult());
		pstm.setInt(4, hub.getFront());
		pstm.setInt(5, hub.getTop());
		pstm.setInt(6, hub.getBoss());
		pstm.setInt(7, hub.getId());
		pstm.setInt(8, hub.getR());
		pstm.setInt(9, hub.getBottom());
		pstm.executeUpdate();
		
	}
	
	public static void insertPinionShaftProduct(Connection conn, PinionShaft pinionShaft) throws SQLException {
		String sql = "Insert into pinion_shaft1(side, time, measure_value, result) values (?,?,?,?)";
		
		PreparedStatement pstm = conn.prepareStatement(sql);
		
		Date date= new Date();
		SimpleDateFormat dateTimeFormatter = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
		String dateTime = dateTimeFormatter.format(date);
		
		
		pstm.setInt(1, pinionShaft.getSide());
		pstm.setString(2, dateTime);
		pstm.setString(3, pinionShaft.getMeasureValue());
		pstm.setInt(4, pinionShaft.getResult());
		pstm.executeUpdate();
		
	}
	
	public static LinkedHashMap<Integer, Float> realTimeLeftPinionShaft(Connection conn) throws SQLException {
		LinkedHashMap<Integer, Float> realtimeProductMap = new LinkedHashMap<Integer, Float>();
		String sql = "SELECT * FROM nidec1.pinion_shaft1 WHERE side = 0 ORDER BY part_id DESC LIMIT 200;";
		
		PreparedStatement pstm = conn.prepareStatement(sql);		
		ResultSet rs = pstm.executeQuery();
		while (rs.next()) {
			realtimeProductMap.put(rs.getInt("part_id"), rs.getFloat("measure_value"));
		}
		return realtimeProductMap;
		
	}
	
	public static LinkedHashMap<Integer, Float> realTimeRightPinionShaft(Connection conn) throws SQLException {
		LinkedHashMap<Integer, Float> realtimeProductMap = new LinkedHashMap<Integer, Float>();
		String sql = "SELECT * FROM nidec1.pinion_shaft1 WHERE side = 1 ORDER BY part_id DESC LIMIT 200;";
		
		PreparedStatement pstm = conn.prepareStatement(sql);		
		ResultSet rs = pstm.executeQuery();
		while (rs.next()) {
			realtimeProductMap.put(rs.getInt("part_id"), rs.getFloat("measure_value"));
		}
		return realtimeProductMap;
		
	}
	
	public static void updatePinionShaftStatus(Connection conn, int status) throws SQLException {
		String sql = "UPDATE nidec1.machine SET status = ? WHERE machine_id = 'pinion_shaft1'";		
		PreparedStatement pstm = conn.prepareStatement(sql);				
		pstm.setInt(1, status);
		pstm.executeUpdate();		
	}
	
	public static int getPinionShaftStatus(Connection conn) throws SQLException {
		int status=0;
		String sql = "select status from nidec1.machine where machine_id='pinion_shaft1'";		
		PreparedStatement pstm = conn.prepareStatement(sql);		
		ResultSet rs = pstm.executeQuery();
		while (rs.next()) {
			status = rs.getInt("status");
		}
		return status;
	}
	
	public static String[][] getNgRate(Connection conn, String machineId, String startDate, String endDate) throws SQLException {
		
		String[] splitStartDate = startDate.split("-");
		int dayStart = Integer.parseInt(splitStartDate[2]);
		String monthYearStart = splitStartDate[0] + "-" + splitStartDate[1];
		
		String[] splitEndDate = endDate.split("-");
		int dayEnd = Integer.parseInt(splitEndDate[2]);
		//String monthYearEnd = splitEndDate[0] + "-" + splitEndDate[1];
		
		String[][] ngRate = new String[dayEnd-dayStart+1][2];
		Integer[] totalDay = new Integer[dayEnd-dayStart+1];
		Integer[] ngDay = new Integer[dayEnd-dayStart+1];
		
		String startHour = "00:00:00";
		String endHour = "23:59:59";
		
		String totalSql = "Select count(*) from " + machineId + " where time>=(?) and time<(?)";
		String ngSql = "Select count(*) from " + machineId + " where result=0 and time>=(?) and time<(?)";
		int i=0;
		int stepDay=dayStart;
		while(i<=(dayEnd-dayStart)) {
			String dateMin = monthYearStart + "-" + Integer.toString(stepDay) + " " + startHour;
			String dateMax = monthYearStart + "-" + Integer.toString(stepDay) + " " + endHour;
			PreparedStatement pstmTotal = conn.prepareStatement(totalSql);
			pstmTotal.setString(1, dateMin);
			pstmTotal.setString(2, dateMax);
			ResultSet rsTotal = pstmTotal.executeQuery();
			//total[i][0] = monthYearStart+ "-" + Integer.toString(stepDay);
			while (rsTotal.next()) {
				totalDay[i] = rsTotal.getInt("count(*)");
			}
			
			PreparedStatement pstmNg = conn.prepareStatement(ngSql);
			pstmNg.setString(1, dateMin);
			pstmNg.setString(2, dateMax);
			ResultSet rsNg = pstmNg.executeQuery();
			while (rsNg.next()) {
				ngDay[i] = rsNg.getInt("count(*)");
			}
			
			if(Integer.toString(stepDay).length()>1) {
				ngRate[i][0] = monthYearStart+ "-" + Integer.toString(stepDay);
			} else {
				ngRate[i][0] = monthYearStart+ "-" + "0" + Integer.toString(stepDay);
			}
			if(totalDay[i] == 0) {
				ngRate[i][1] = Float.toString((float) 0.0);
			} else {
				ngRate[i][1] = Float.toString(((float)ngDay[i]/(float)totalDay[i])*100);
			}			
			stepDay++;
			i++;
		}			
		return ngRate;
	}
	
	public static int getTotalNgDay(Connection conn, String machineId, Date dateTime) throws SQLException {
		
		SimpleDateFormat dateFormatter = new SimpleDateFormat("YYYY-MM-dd");
		String date = dateFormatter.format(dateTime);
		
		int total = 0;
				
		String startHour = "00:00:00";
		String endHour = "23:59:59";
		
		String sql = "Select count(*) from " + machineId + " where result=0 and time>=(?) and time<(?)";
			
		String dateMin = date + " " + startHour;
		String dateMax = date + " " + endHour;
		
		PreparedStatement pstm = conn.prepareStatement(sql);
		
		pstm.setString(1, dateMin);
		pstm.setString(2, dateMax);
		
		ResultSet rs = pstm.executeQuery();			
		while (rs.next()) {
			total = rs.getInt("count(*)");
		}			
		return total;
	}
}
