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
 * Servlet implementation class GetSearchFlightServlet
 */
@WebServlet("/getSearchFlight")
public class GetSearchFlightServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetSearchFlightServlet() {
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
		String fromCity = request.getParameter("fromCity");
		String toCity = request.getParameter("toCity");
		String flightType = request.getParameter("flightType");
		String departureDate = request.getParameter("departureDate");
		String startDate = departureDate + " 00:00:00";
		String endDate = departureDate + " 23:59:59";
		int cabinTypeId = 0;
		try {
			cabinTypeId = Integer.parseInt(request.getParameter("cabinTypeId"));
		} catch (Exception e) {
			cabinTypeId = 0;
		}

		Result result = ScheduleService.getSearchFlight(fromCity, toCity, startDate, endDate,cabinTypeId, flightType);
		String msg = JSON.toJSONString(result, SerializerFeature.WriteDateUseDateFormat);
		response.getWriter().append(msg);
	}

}
