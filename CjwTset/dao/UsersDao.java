package CjwTset.dao;

import java.util.HashMap;
import java.util.List;

import CjwTset.helper.MySqlHelper;

public class UsersDao {
	
	public static List<HashMap<String, Object>> findByEmail(String email) {
		String sql = "select * from Users where Email=?";
		return MySqlHelper.executeQueryReturnMap(sql, new String[] { email });
	}
	
	public static HashMap<String, Object> findByEmailAndPassword(String email, String password) {
		String sql = "select * from Users where Email=? and Password=?";
		List<HashMap<String, Object>> list = MySqlHelper.executeQueryReturnMap(sql, new String[] { email, password });

		if (list != null && list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}
	
	public static List<HashMap<String, Object>> findUserListByPage(String name, int startPage, int pageSize) {
		String sql = "select * from Users where FirstName like ? or LastName like ? order by FirstName asc  limit ?,?";
		return MySqlHelper.executeQueryReturnMap(sql,
				new Object[] { "%" + name + "%", "%" + name + "%", (startPage-1) * pageSize, pageSize });
	}	

	public static int findUserCount(String name) {
		String sql = "select count(1) as Total from Users where FirstName like ? or LastName like ?";
		List<HashMap<String, Object>> list = MySqlHelper.executeQueryReturnMap(sql,
				new Object[] { "%" + name + "%", "%" + name + "%" });
		if (list != null && list.size() > 0) {
			return Integer.parseInt(list.get(0).get("Total").toString());
		} else {
			return 0;
		}
	}
	
	public static List<HashMap<String, Object>> findUserListByPageAndRoleId(String name, int roleId, int startPage,
			int pageSize) {
		String sql = "select * from Users where RoleId=? and (FirstName like ? or LastName like ?) order by FirstName asc  limit ?,?";
		return MySqlHelper.executeQueryReturnMap(sql,
				new Object[] { roleId, "%" + name + "%", "%" + name + "%", (startPage-1) * pageSize, pageSize });
	}

	public static int findUserCountAndRoleId(String name, int roleId) {
		String sql = "select count(1) as Total from Users where RoleId=? and (FirstName like ? or LastName like ?)";
		List<HashMap<String, Object>> list = MySqlHelper.executeQueryReturnMap(sql,
				new Object[] { roleId, "%" + name + "%", "%" + name + "%" });

		if (list != null && list.size() > 0) {
			return Integer.parseInt(list.get(0).get("Total").toString());
		} else {
			return 0;
		}
	}
	
	public static int addUser(HashMap<String, Object> map) {
		String sql = "insert into Users "
				+ "(Email,Password,FirstName,LastName,Gender,DateOfBirth,Phone,Photo,Address,RoleId) "
				+ "values(?,?,?,?,?,?,?,?,?,?)";
		return MySqlHelper.executeUpdate(sql,
				new Object[] { map.get("email"), map.get("password"), map.get("firstName"), map.get("lastName"),
						map.get("gender"), map.get("dateOfBirth"), map.get("phone"), map.get("photo"),
						map.get("address"), map.get("roleId") });
	}

	public static HashMap<String, Object> findByUserId(int userId) {
		String sql = "select * from Users where UserId=?";
		List<HashMap<String, Object>> list = MySqlHelper.executeQueryReturnMap(sql, new Object[] { userId });
		if (list != null && list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	public static List<HashMap<String, Object>> findByEmailAndUserId(String email, int userId) {
		String sql = "select * from Users where Email=? and UserId!=?";
		return MySqlHelper.executeQueryReturnMap(sql, new Object[] { email, userId });
	}

	public static int updateUser(HashMap<String, Object> map) {
		String sql = "update Users set "
				+ "Email=?,FirstName=?,LastName=?,Gender=?,DateOfBirth=?,Phone=?,Photo=?,Address=?,RoleId=? " 
				+ "where UserId=?";
		return MySqlHelper.executeUpdate(sql,
				new Object[] { map.get("email"), map.get("firstName"), map.get("lastName"),
						map.get("gender"), map.get("dateOfBirth"), map.get("phone"), map.get("photo"),
						map.get("address"), map.get("roleId"), map.get("userId") });
	}
	
}
