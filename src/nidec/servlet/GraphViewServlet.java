package nidec.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nidec.beans.Machine;
import nidec.utils.DBUtils;
import nidec.utils.MyUtils;

@WebServlet(urlPatterns= {"/hub1GraphView"})
public class GraphViewServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	public GraphViewServlet() {
		super();
	}
	
	public String convertStrToTimeStamp(String strDateTime) {
		SimpleDateFormat timeFortmatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date date = timeFortmatter.parse(strDateTime);
			long time = date.getTime() + 9*3600*1000; //time in milliseconds
			return Long.toString(time);
		} catch (Exception e) {
			return null;
		}
	}
	
	public String dataFormatter(String[][] arrayStrData) {
		
		int arrayIndex = 0;
		String strData = "[";
		while(arrayStrData[arrayIndex][0] != null) {
			if(arrayIndex != 0) {
				strData = strData + ",";
			}
			strData = strData + "[" + convertStrToTimeStamp(arrayStrData[arrayIndex][0]);
			strData = strData + "," + arrayStrData[arrayIndex][1] + "]";
			arrayIndex++;
		}
		strData = strData + "]";
		
		return strData;
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = MyUtils.getStoreConnection(request);
		String errorString = null;
		List<Machine> list = null;
		
		int targetDay = 1;
		int sumOkDay = 0;
		double completePer = 0.0;
		
		Date date= new Date();
		String[][] okData = null;
		String[][] ngData = null;
		
		try {
			
			okData = DBUtils.queryOkGraphData(conn, "hub1", date);
			ngData = DBUtils.queryNgGraphData(conn, "hub1", date);
			targetDay = DBUtils.getTargetDay(conn, "hub1");
			sumOkDay = DBUtils.getSumOkDay(conn, "hub1", date);
			list = DBUtils.queryMachine(conn);
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			errorString = e.getMessage();
			
		}
		request.setAttribute("errorString", errorString);
		request.setAttribute("machineList", list);
		
		completePer = ((double) sumOkDay/ (double) targetDay)*100;
		
		System.out.println(dataFormatter(ngData));
		System.out.println(dataFormatter(okData));
		System.out.println(Integer.toString(targetDay));
		System.out.println(Integer.toString(sumOkDay));
		System.out.println(Double.toString(completePer));
		
		request.setAttribute("machineName", "HUB 1");
		request.setAttribute("okData", dataFormatter(okData));
		request.setAttribute("ngData", dataFormatter(ngData));
		request.setAttribute("targetDay", targetDay);
		request.setAttribute("sumOkDay", sumOkDay);
		request.setAttribute("completePer", (int) completePer);
				
		RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/hubGraphView.jsp");
		dispatcher.forward(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
