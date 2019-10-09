package nidec.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import nidec.utils.DBUtils;
import nidec.utils.MyUtils;

@WebServlet(urlPatterns= {"/ajaxGraphView"})
public class AjaxGraphView extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public AjaxGraphView() {
		super();
	}
	
	public Long convertTimeStampToLong(String strDateTime) {
		SimpleDateFormat timeFortmatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date date = timeFortmatter.parse(strDateTime);
			long time = date.getTime() + 7*3600*1000; //time in milliseconds
			return time;
		} catch (Exception e) {
			return null;
		}
	}
	
	public Long[][] dataFormatter(String[][] arrayStrData) {
		
		Long[][] resultArr = null;
		for(int i = 0; i < arrayStrData.length; ++i) {
			if(arrayStrData[i][1]==null) {
				resultArr = new Long[i][2];
			} else {
				resultArr = new Long[i+1][2];
			}
		}		
		for(int i = 0; i < arrayStrData.length; ++i) {
			if(arrayStrData[i][0]!=null) {
				resultArr[i][0]=convertTimeStampToLong(arrayStrData[i][0]);
				resultArr[i][1]=Long.parseLong(arrayStrData[i][1]);
			} else {
				break;
			}			
		}		
		return resultArr;
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = MyUtils.getStoreConnection(request);
		String errorString = null;
		int pinionShaftStatus = 0;
		int targetDay = 1;
		int sumOkDay = 0;
		double completePer = 0.0;
		
		Date date= new Date();
		String[][] okData = null;
		String[][] ngData = null;
		try {
			
			okData = DBUtils.queryOkGraphDataMinute(conn, "pinion_shaft1", date);
			ngData = DBUtils.queryNgGraphDataMinute(conn, "pinion_shaft1", date);

			//okData = DBUtils.queryOkGraphData(conn, "pinion_shaft1", date);
			//ngData = DBUtils.queryNgGraphData(conn, "pinion_shaft1", date);
			
			targetDay = DBUtils.getTargetDay(conn, "pinion_shaft1");
			sumOkDay = DBUtils.getSumOkDay(conn, "pinion_shaft1", date);
			pinionShaftStatus = DBUtils.getPinionShaftStatus(conn);
			
		} catch (SQLException e) {
			e.printStackTrace();
			errorString = e.getMessage();
		}		
		completePer = ((double) sumOkDay/ (double) targetDay)*100;
		
		request.setAttribute("errorString", errorString);
		
		GraphObject graphObj1 = new GraphObject();
		graphObj1.okData = 	dataFormatter(okData);
		graphObj1.ngData = dataFormatter(ngData);
		graphObj1.targetDay = targetDay;
		graphObj1.sumOkDay = sumOkDay;
		graphObj1.completePer = completePer;
		graphObj1.mcStatus = pinionShaftStatus;
		String json = new Gson().toJson(graphObj1);
		
		
		response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");
	    response.getWriter().write(json);
	    
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	public class GraphObject {
		Long[][] okData;
		Long[][] ngData;
		int targetDay;
		int sumOkDay;
		double completePer;
		int mcStatus;
	}
}
