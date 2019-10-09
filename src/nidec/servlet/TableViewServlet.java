package nidec.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nidec.beans.Hub;
import nidec.beans.Machine;
import nidec.utils.DBUtils;
import nidec.utils.MyUtils;

@WebServlet(urlPatterns= {"/hub1TableView"})
public class TableViewServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	public TableViewServlet() {
		super();
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = MyUtils.getStoreConnection(request);
		String errorString = null;
		List<Hub> list = null;
		List<Machine> machineList = null;
		
		try {
			list = DBUtils.queryProductHub(conn);
			machineList = DBUtils.queryMachine(conn);
			
		} catch (SQLException e) {
			e.printStackTrace();
			errorString = e.getMessage();
		}
		request.setAttribute("errorString", errorString);
		request.setAttribute("hubProduct", list);
		request.setAttribute("machineList", machineList);
		
		RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/hubTableView.jsp");
		dispatcher.forward(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
