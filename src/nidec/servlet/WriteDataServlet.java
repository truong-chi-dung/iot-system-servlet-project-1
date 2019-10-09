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
 
import nidec.beans.Hub;
import nidec.utils.DBUtils;
import nidec.utils.MyUtils;

@WebServlet(urlPatterns = {"/writeData"})
public class WriteDataServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public WriteDataServlet() {
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
		int result = Integer.parseInt(request.getParameter("result"));
		int front = Integer.parseInt(request.getParameter("front"));
		int top = Integer.parseInt(request.getParameter("top"));
		int boss = Integer.parseInt(request.getParameter("boss"));
		int id = Integer.parseInt(request.getParameter("id"));
		int r = Integer.parseInt(request.getParameter("r"));
		int bottom = Integer.parseInt(request.getParameter("bottom"));		
		
		Hub hub = new Hub(side, result, front, top, boss, id, r, bottom);
		
		String errorString = null;
		try {
			
			DBUtils.insertHubProduct(conn, hub);
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			errorString = e.getMessage();
			
		}
		
		if(errorString == null) {
			request.setAttribute("response", "OK");
		} else {
			request.setAttribute("errorString", errorString);
		}
		
		RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/writeDataRes.jsp");
		dispatcher.forward(request, response);
	}
	
}

