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
 * Servlet implementation class GetUserInfoServlet
 */
@WebServlet("/getUserInfo")
public class GetUserInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetUserInfoServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { 
		response.setContentType("text/html;charset=UTF-8");
		int  userId = 0;
		try {
			userId = Integer.parseInt(request.getParameter("userId"));
		}catch(Exception e) {			
			userId = 0;
		}		
		Result result = UsersService.findByUserId(userId);
		String msg = JSON.toJSONString(result);
		response.getWriter().append(msg);
	}
}
