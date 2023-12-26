package CjwTset.service;

import java.util.HashMap;
import java.util.List;

import CjwTset.dao.CityDao;
import CjwTset.pojo.Result;

public class CityService {
	public static Result getCityNames(){
		List<HashMap<String, Object>> list = CityDao.getCityNames();
		Result result = new Result("success", null, list);
		return result;
	}
}
