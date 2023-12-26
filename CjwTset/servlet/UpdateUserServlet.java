package CjwTset.servlet;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

import CjwTset.pojo.Result;
import CjwTset.service.UsersService;

/**
 * Servlet implementation class UpdateUserServlet
 */
@WebServlet("/updateUser")
public class UpdateUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UpdateUserServlet() {
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
		
		String email = request.getParameter("email");
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String gender = request.getParameter("gender");
		String dateOfBirth = request.getParameter("dateOfBirth");
		String phone = request.getParameter("phone");
		String photo = request.getParameter("photo");
		String address = request.getParameter("address");
		
		int userId = 0;
		try {
			userId = Integer.parseInt(request.getParameter("userId"));
		} catch (Exception e) {
			userId = 0;
		}
		int roleId = 0;
		try {
			roleId = Integer.parseInt(request.getParameter("roleId"));
		} catch (Exception e) {
			roleId = 0;
		}		

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("email", email);
		map.put("firstName", firstName);
		map.put("lastName", lastName);
		map.put("dateOfBirth", dateOfBirth);
		map.put("address", address);
		map.put("gender", gender);
		map.put("phone", phone);
		map.put("photo", photo);
		map.put("roleId", roleId);

		Result result = UsersService.updateUser(map);
		String msg = JSON.toJSONString(result);
		response.getWriter().append(msg);
	}

}
