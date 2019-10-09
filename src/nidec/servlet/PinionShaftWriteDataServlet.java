package nidec.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
 
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nidec.beans.PinionShaft;
import nidec.utils.DBUtils;
import nidec.utils.MyUtils;

@WebServlet(urlPatterns = {"/pinionShaftWriteData"})
public class PinionShaftWriteDataServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public PinionShaftWriteDataServlet() {
		super();
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = MyUtils.getStoreConnection(request);
		
		int side = Integer.parseInt(request.getParameter("side"));
		String measureValue = request.getParameter("measure_value");
		String resultStr = request.getParameter("result");
		int result = Integer.parseInt(resultStr.substring(0,1));		
		PinionShaft pinionShaft = new PinionShaft(side, measureValue, result);		
		String errorString = null;
		try {			
			DBUtils.insertPinionShaftProduct(conn, pinionShaft);			
		} catch (SQLException e) {			
			e.printStackTrace();
			errorString = e.getMessage();			
		}
		
		if(errorString == null) {
			request.setAttribute("response", "OK");
		} else {
			request.setAttribute("errorString", errorString);
		}
		
		RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/pinionShaftWriteDataRes.jsp");
		dispatcher.forward(request, response);
	}
	
}