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

import nidec.beans.Machine;
import nidec.beans.PinionShaft;
import nidec.utils.DBUtils;
import nidec.utils.MyUtils;

@WebServlet(urlPatterns= {"/pinion_shaft1TableView"})
public class PinionShaftTableViewServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	public PinionShaftTableViewServlet() {
		super();
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = MyUtils.getStoreConnection(request);
		String errorString = null;
		List<PinionShaft> list = null;
		List<Machine> machineList = null;
		
		try {
			list = DBUtils.queryProductPinionShaft(conn);
			machineList = DBUtils.queryMachine(conn);
		} catch (SQLException e) {
			e.printStackTrace();
			errorString = e.getMessage();
		}
		request.setAttribute("errorString", errorString);
		request.setAttribute("pinionShaftProduct", list);
		request.setAttribute("machineList", machineList);
		
		RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/pinionShaftTableView.jsp");
		dispatcher.forward(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
