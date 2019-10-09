package nidec.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedHashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import nidec.utils.DBUtils;
import nidec.utils.MyUtils;

@WebServlet(urlPatterns= {"/realtimeProduct"})
public class RealtimeProduct extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public RealtimeProduct() {
		super();
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = MyUtils.getStoreConnection(request);
		String errorString = null;
		LinkedHashMap<Integer, Float> realtimeLeftProduct = new LinkedHashMap<Integer, Float>();
		LinkedHashMap<Integer, Float> realtimeRightProduct = new LinkedHashMap<Integer, Float>();
		
		try {
			
			realtimeLeftProduct = DBUtils.realTimeLeftPinionShaft(conn);
			realtimeRightProduct = DBUtils.realTimeRightPinionShaft(conn);
			
		} catch (SQLException e) {
			e.printStackTrace();
			errorString = e.getMessage();
		}
		RealtimeGraphObject graphRealtimeObj1 = new RealtimeGraphObject();
		graphRealtimeObj1.leftRealTimeData = realtimeLeftProduct;
		graphRealtimeObj1.rightRealTimeData = realtimeRightProduct;
		request.setAttribute("errorString", errorString);
		
		String json = new Gson().toJson(graphRealtimeObj1);
		response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");
	    response.getWriter().write(json);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	public class RealtimeGraphObject {
		LinkedHashMap<Integer, Float> leftRealTimeData;
		LinkedHashMap<Integer, Float> rightRealTimeData;
	}
	
}
