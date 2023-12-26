package CjwTset.helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

public class MySqlHelper {

	// 初始化
	private static Connection conn = null;
	private static PreparedStatement pstmt = null;
	private static ResultSet rs = null;
	//mysql8的连接字符串配置
	private static String url = "jdbc:mysql://localhost:3306/session1?serverTimezone=GMT%2B8&useOldAliasMetadataBehavior=true";
	private static String driver = "com.mysql.cj.jdbc.Driver";
	private static String userName = "root";
	private static String password = "123456";

	static {
		try {
			Class.forName(driver);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 打开连接
	public static Connection getConnection() {
		try {
			conn = DriverManager.getConnection(url, userName, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

	// 执行查询（返回HashMap）
	public static List<HashMap<String, Object>> executeQueryReturnMap(String sql, Object[] parameters) {
		List<HashMap<String, Object>> list = null;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			if (parameters != null) {
				for (int i = 0; i < parameters.length; i++) {
					String className = parameters[i].getClass().getName();
					if (className.contains("String")) {
						pstmt.setString(i + 1, parameters[i].toString());
					}
					if (className.contains("Integer")) {
						pstmt.setInt(i + 1, Integer.parseInt(parameters[i].toString()));
					}
				}
			}
			ResultSet rs = pstmt.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			list = new ArrayList<HashMap<String, Object>>();
			int columnNum = rsmd.getColumnCount();
			while (rs.next()) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				for (int i = 0; i < columnNum; i++) {
					String columnName = rsmd.getColumnName(i + 1);
					Object value = rs.getObject(i + 1);
					map.put(columnName, value);
				}
				list.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rs, pstmt, conn);
		}
		return list;
	}

	// 执行增删改
	public static int executeUpdate(String sql, Object[] parameters) {
		int result = 0;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			if (parameters != null) {
				for (int i = 0; i < parameters.length; i++) {
					String className = parameters[i].getClass().getName();
					if (className.contains("String")) {
						pstmt.setString(i + 1, parameters[i].toString());
					}
					if (className.contains("Integer")) {
						pstmt.setInt(i + 1, Integer.parseInt(parameters[i].toString()));
					}
				}

			}			
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rs, pstmt, conn);
		}
		return result;
	}

	// 关闭连接
	public static void close(ResultSet rs, PreparedStatement pstmt, Connection conn) {
		if (rs != null) {
			try {
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
