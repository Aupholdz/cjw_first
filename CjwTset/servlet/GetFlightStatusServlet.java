package CjwTset.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import CjwTset.pojo.Result;
import CjwTset.service.ScheduleService;

/**
 * Servlet implementation class GetFlightStatusServlet
 */
@WebServlet("/getFlightStatus")
public class GetFlightStatusServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetFlightStatusServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		int startPage = 1;
		int pageSize = 10;
		String departureDate = request.getParameter("departureDate");
		String startDate = departureDate + " 00:00:00";
		String endDate = departureDate + " 23:59:59";
		try {
			startPage = Integer.parseInt(request.getParameter("startPage"));
		} catch (Exception e) {
			startPage = 1;
		}
		try {
			pageSize = Integer.parseInt(request.getParameter("pageSize"));
		} catch (Exception e) {
			pageSize = 10;
		}

		Result result = ScheduleService.getFlightStatus(startDate, endDate, startPage, pageSize);
		String msg = JSON.toJSONString(result, SerializerFeature.WriteDateUseDateFormat);
		response.getWriter().append(msg);
	}

}
