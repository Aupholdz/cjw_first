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
 * Servlet implementation class GetScheduleServlet
 */
@WebServlet("/getSchedule")
public class GetScheduleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetScheduleServlet() {
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
		String fromCity = request.getParameter("fromCity");
		String toCity =  request.getParameter("toCity");
		String startDate = request.getParameter("startDate") + " 00:00:00";
		String endDate = request.getParameter("endDate") + " 23:59:59";			
		Result result = ScheduleService.getSchedule(fromCity, toCity, startDate, endDate);
		String msg = JSON.toJSONString(result,SerializerFeature.WriteDateUseDateFormat);
		response.getWriter().append(msg);
	}

}
