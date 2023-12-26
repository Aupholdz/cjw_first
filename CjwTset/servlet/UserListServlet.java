package CjwTset.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

import CjwTset.pojo.Result;
import CjwTset.service.UsersService;

/**
 * Servlet implementation class UserListServlet
 */
@WebServlet("/userList")
public class UserListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserListServlet() {
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
		String name = request.getParameter("name");
		int roleId = 0;
		int startPage = 1;
		int pageSize = 10;		
		try {
			roleId = Integer.parseInt(request.getParameter("roleId"));
		} catch (Exception e) {
			roleId = 0;
		}
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
		Result result = UsersService.userList(name, roleId, startPage, pageSize);
		String msg = JSON.toJSONString(result);
		response.getWriter().append(msg);
	}
}
