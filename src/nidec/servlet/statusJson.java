package nidec.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import nidec.beans.Machine;
import nidec.utils.DBUtils;
import nidec.utils.MyUtils;

@WebServlet(urlPatterns= {"/statusJson"})
public class statusJson extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public statusJson() {
		super();
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Connection conn = MyUtils.getStoreConnection(request);
		List<Machine> list = null;
				
		try {			
			list = DBUtils.queryMachine(conn);			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		String json = new Gson().toJson(list);
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");
	    response.getWriter().write(json);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}	
}
