package CjwTset.dao;

import java.util.HashMap;
import java.util.List;

import CjwTset.helper.MySqlHelper;

public class CityDao {
	public static List<HashMap<String, Object>> getCityNames() {
		String sql = "select * from City";
		return MySqlHelper.executeQueryReturnMap(sql, null);
	}
}
