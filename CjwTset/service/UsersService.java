package CjwTset.service;

import java.util.HashMap;
import java.util.List;

import CjwTset.dao.UsersDao;
import CjwTset.pojo.Page;
import CjwTset.pojo.Result;

public class UsersService {
	/*
	 * 判断邮箱
	 */
	public static Boolean findByEmail(String email) {
		List<HashMap<String, Object>> list = UsersDao.findByEmail(email);
		if (list != null && list.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	/*
	 * 登录
	 */
	public static Result login(String email, String password) {
		Result result = new Result("fail", null, null);

		HashMap<String, Object> user = UsersDao.findByEmailAndPassword(email, password);

		if (user != null) {
			result.setFlag("success");
			HashMap<String, Object> loginInfo = new HashMap<String, Object>();
			loginInfo.put("Email", user.get("Email"));
			loginInfo.put("RoleId", user.get("RoleId"));
			//返回登录关键信息（邮箱、角色），过滤敏感信息
			result.setData(loginInfo);
		} else {
			Boolean isEmail = UsersService.findByEmail(email);
			if (isEmail) {
				result.setData("密码错误");
			} else {
				result.setData("邮箱不存在");
			}
		}
		return result;
	}
	
	/*
	 * 查询用户
	 */
	public static Result userList(String name, int roleId, int startPage, int pageSize) {
		List<HashMap<String, Object>> list = null;
		int total = 0;
		if (roleId == 0) {
			list = UsersDao.findUserListByPage(name, startPage, pageSize);
			total = UsersDao.findUserCount(name);
		} else {
			list = UsersDao.findUserListByPageAndRoleId(name, roleId, startPage, pageSize);
			total = UsersDao.findUserCountAndRoleId(name, roleId);
		}
		Page page = new Page(total, startPage, pageSize);
		Result result = new Result("success", page, list);
		return result;
	}

	/*
	 * 添加用户
	 */
	public static Result addUser(HashMap<String, Object> map) {
		Result result = new Result("fail", null, null);

		Boolean isEmail = UsersService.findByEmail(map.get("email").toString());

		if (isEmail) {
			result.setData("邮箱重复");
			return result;
		}

		int addResult = UsersDao.addUser(map);
		if (addResult > 0) {
			result.setFlag("success");
		}
		return result;
	}

	/*
	 * 根据用户id获取用户信息
	 */
	public static Result findByUserId(int userId) {
		Result result = new Result("fail", null, null);
		HashMap<String, Object> user = UsersDao.findByUserId(userId);

		if (user != null) {
			result.setFlag("success");
			result.setData(user);
		} else {
			result.setData("用户信息不存在");
		}
		return result;
	}

	/*
	 * 判断邮箱，根据邮箱和用户id
	 */
	public static Boolean findByEmailAndUserId(String email, int userId) {
		List<HashMap<String, Object>> list = UsersDao.findByEmailAndUserId(email, userId);
		if (list != null && list.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	/*
	 * 更新用户
	 */
	public static Result updateUser(HashMap<String, Object> map) {
		Result result = new Result("fail", null, null);
		//判断用户信息是否存在
		int userId = Integer.parseInt(map.get("userId").toString());
		HashMap<String, Object> userInfo = UsersDao.findByUserId(userId);
		if(userInfo == null) {
			result.setData("用户信息不存在");
			return result;
		}
		Boolean isEmail = UsersService.findByEmailAndUserId(map.get("email").toString(),userId);
		if (isEmail) {
			result.setData("邮箱重复");
			return result;
		}
		int updateResult = UsersDao.updateUser(map);
		if (updateResult > 0) {
			result.setFlag("success");
		}
		return result;
	}

	
}
