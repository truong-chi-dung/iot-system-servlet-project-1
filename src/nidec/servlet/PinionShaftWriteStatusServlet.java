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

import nidec.utils.DBUtils;
import nidec.utils.MyUtils;

@WebServlet(urlPatterns = {"/pinionShaftWriteStatus"})
public class PinionShaftWriteStatusServlet extends HttpServlet  {
	
	private static final long serialVersionUID = 1L;
	
	public PinionShaftWriteStatusServlet() {
		super();
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Connection conn = MyUtils.getStoreConnection(request);		
		int machineStatus = Integer.parseInt(request.getParameter("mcsta"));
		String errorString = null;
		
		try {
			
			DBUtils.updatePinionShaftStatus(conn, machineStatus);
			
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
