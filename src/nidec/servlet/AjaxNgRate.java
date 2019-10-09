package nidec.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import nidec.utils.DBUtils;
import nidec.utils.MyUtils;

@WebServlet(urlPatterns= {"/ajaxNgRate"})
public class AjaxNgRate extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public AjaxNgRate() {
		super();
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = MyUtils.getStoreConnection(request);
		String fromDateRes = request.getParameter("fromDate");
		String toDateRes = request.getParameter("toDate");
		String[][] ngRate = null;
		LinkedHashMap<Long, Float> barData = new LinkedHashMap<Long, Float>();
		String errorString = null;
		try {			
			ngRate = DBUtils.getNgRate(conn, "pinion_shaft1", fromDateRes, toDateRes);		
		} catch (SQLException e) {			
			e.printStackTrace();
			errorString = e.getMessage();			
		}
		request.setAttribute("errorString", errorString);
		barData = dataFormatter(ngRate);
		GraphObject graphObj1 = new GraphObject();
		graphObj1.ngRateData = 	barData;
		String json = new Gson().toJson(graphObj1);
		
		response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");
	    response.getWriter().write(json);
	}
	
	public Long convertTimeStampToLong(String strDateTime) {
		SimpleDateFormat timeFortmatter = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date date = timeFortmatter.parse(strDateTime);
			long time = date.getTime() + 7*3600*1000; //time in milliseconds
			return time;
		} catch (Exception e) {
			return null;
		}
	}
	
	public LinkedHashMap<Long, Float> dataFormatter(String[][] arrayStrData) {
		
		LinkedHashMap<Long, Float> resultArr = new LinkedHashMap<Long, Float>();
		/*for(int i = 0; i < arrayStrData.length; ++i) {
			if(arrayStrData[i][1]==null) {
				resultArr = new Long[i][2];
			} else {
				resultArr = new Long[i+1][2];
			}
		}*/	
		for(int i = 0; i < arrayStrData.length; ++i) {
			resultArr.put(convertTimeStampToLong(arrayStrData[i][0]), Float.parseFloat(arrayStrData[i][1]));				
		}		
		return resultArr;
	}
	
	public class GraphObject {
		LinkedHashMap<Long, Float> ngRateData;
	}
}
