package CjwTset.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

import CjwTset.pojo.Result;
import CjwTset.service.ScheduleService;

/**
 * Servlet implementation class UpdateScheduleServlet
 */
@WebServlet("/updateSchedule")
public class UpdateScheduleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UpdateScheduleServlet() {
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
		int scheduleId = 0;
		try {
			scheduleId = Integer.parseInt(request.getParameter("scheduleId"));
		} catch (Exception e) {			
			scheduleId = 0;
		}
		String status = request.getParameter("status");			
		Result result = ScheduleService.updateSchedule(scheduleId, status);
		String msg = JSON.toJSONString(result);
		response.getWriter().append(msg);
	}
}
