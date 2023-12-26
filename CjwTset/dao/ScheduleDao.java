package CjwTset.dao;

import java.util.List;

import CjwTset.helper.MySqlHelper;

import java.util.HashMap;

public class ScheduleDao {
	public static List<HashMap<String, Object>> findScheduleByDate(String startDate, String endDate, int startPage, int pageSize) {
		String sql = "select "+ 
			"`Schedule`.ScheduleId,"+ 
			"`Schedule`.Date,"+ 
			"`Schedule`.Time,"+ 
			"FlightStatus.ActualArrivalTime,"+ 
			"Route.DepartureAirportIATA,"+ 
			"DepartCity.CityName as DepartCityName,"+ 
			"Route.ArrivalAirportIATA,"+ 
			"ArriveCity.CityName as ArriveCityName,"+ 
			"`Schedule`.FlightNumber,"+ 
			"`Schedule`.Gate,"+ 
			"Route.FlightTime "+
			"from `Schedule` "+
			"left join FlightStatus on FlightStatus.ScheduleId = `Schedule`.ScheduleId "+
			"left join Route on Route.RouteId = `Schedule`.RouteId "+
			"left join Airport as DepartAirport on DepartAirport.IATACode = Route.DepartureAirportIATA "+
			"left join Airport as ArriveAirport on ArriveAirport.IATACode = Route.ArrivalAirportIATA "+
			"left join City as DepartCity on DepartCity.CityCode = DepartAirport.CityCode "+
			"left join City as ArriveCity on ArriveCity.CityCode = ArriveAirport.CityCode "+
			"where `Schedule`.Date between ? and ? "+
			"order by `Schedule`.Date,`Schedule`.FlightNumber limit ?,?";
		return MySqlHelper.executeQueryReturnMap(sql, new Object[] { startDate, endDate, (startPage-1) * pageSize, pageSize });
	}

	public static int findScheduleCountByDate(String startDate, String endDate) {
		String sql = "select count(1) as Total from `Schedule` where Date between ? and ?";
		List<HashMap<String, Object>> list = MySqlHelper.executeQueryReturnMap(sql,
				new Object[] { startDate, endDate });

		if (list != null && list.size() > 0) {
			return Integer.parseInt(list.get(0).get("Total").toString());
		}else {
			return 0;
		}
	}

	public static List<HashMap<String, Object>> findScheduleByCityAndDate(String fromCity, String toCity,
			String startDate, String endDate) {
		String sql = "select "+ 
				"`Schedule`.ScheduleId,"+ 
				"`Schedule`.Date,"+ 
				"`Schedule`.Time,"+ 
				"Route.DepartureAirportIATA,"+ 
				"DepartCity.CityName as DepartCityName,"+ 
				"Route.ArrivalAirportIATA,"+
				"ArriveCity.CityName as ArriveCityName,"+ 
				"Aircraft.`Name`,"+ 
				"`Schedule`.EconomyPrice,"+ 
				"`Schedule`.FlightNumber,"+ 
				"`Schedule`.Gate,"+ 
				"`Schedule`.`Status` "+ 
				"from `Schedule` "+ 
				"left join Aircraft on Aircraft.AircraftId = `Schedule`.AircraftId "+
				"left join Route on `Schedule`.RouteId = Route.RouteId "+
				"left join Airport as DepartAirport on Route.DepartureAirportIATA = DepartAirport.IATACode "+
				"left join City as DepartCity on DepartCity.Citycode = DepartAirport.Citycode "+
				"left join Airport as ArriveAirport on Route.ArrivalAirportIATA = ArriveAirport.IATACode "+
				"left join City as ArriveCity on ArriveCity.Citycode = ArriveAirport.Citycode "+
				"where DepartCity.CityName = ? and ArriveCity.CityName = ? and `Schedule`.Date between ? and ? "+
				"order by `Schedule`.Date";
		return MySqlHelper.executeQueryReturnMap(sql, new Object[] { fromCity, toCity, startDate, endDate });
	}

	public static HashMap<String, Object> findByScheduleId(int scheduleId) {
		String sql = "select * from `Schedule` "+
				"left join Route on `Schedule`.RouteId = Route.RouteId "+
				"left join Aircraft on Aircraft.AircraftId = `Schedule`.AircraftId "+
				"where `Schedule`.ScheduleId = ?";
		List<HashMap<String, Object>> list = MySqlHelper.executeQueryReturnMap(sql, new Object[] { scheduleId });
		if (list != null && list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	public static List<HashMap<String, Object>> findTicketInfoList(int scheduleId) {
		String sql = "select FlightReservation.CabinTypeId,count(1) as SoldCounts,count(SeatLayoutId) as SelectedCounts "+
				"from FlightReservation "+
				"where FlightReservation.ScheduleId = ? "+
				"group by FlightReservation.CabinTypeId";
		return MySqlHelper.executeQueryReturnMap(sql, new Object[] { scheduleId });
	}

	public static List<HashMap<String, Object>> findSelectedSeatList(int scheduleId) {
		String sql = "select FlightReservation.CabinTypeId,SeatLayout.RowNumber,SeatLayout.ColumnName "+
				"from FlightReservation "+
				"left join SeatLayout on SeatLayout.Id = FlightReservation.SeatLayoutId "+
				"where FlightReservation.SeatLayoutId is not null and FlightReservation.ScheduleId = ?";
		return MySqlHelper.executeQueryReturnMap(sql, new Object[] { scheduleId });
	}

	public static List<HashMap<String, Object>> findSeatLayoutList(int aircraftId) {
		String sql = "select * from SeatLayout where AircraftId = ?";
		return MySqlHelper.executeQueryReturnMap(sql, new Object[] { aircraftId });
	}
	
	public static int updateSchedule(int scheduleId, String status) {
		String sql = "update `Schedule` set Status = ? where ScheduleId = ?";
		return MySqlHelper.executeUpdate(sql, new Object[] { status, scheduleId });		
	}
	
	public static List<HashMap<String, Object>> findNonStopScheduleList(String fromCity, String toCity,
			String startDate, String endDate) {
		String sql = "select "+ 
			"`Schedule`.ScheduleId, "+
			"`Schedule`.Date, "+
			"`Schedule`.Time, "+
			"DATE_ADD(`Schedule`.Date,INTERVAL Route.FlightTime MINUTE) as PreArrivalTime, "+
			"Route.DepartureAirportIATA, "+
			"DepartCity.CityName as DepartCityName, "+
			"Route.ArrivalAirportIATA, "+
			"ArriveCity.CityName as ArriveCityName, "+
			"Route.FlightTime, "+
			"`Schedule`.EconomyPrice, "+
			"`Schedule`.FlightNumber, "+
			"Aircraft.FirstSeatsAmount, "+
			"Aircraft.BusinessSeatsAmount, "+
			"Aircraft.EconomySeatsAmount, "+
			"FlightCount.AllCount, "+
			"FlightCount.DelayCount, "+
			"FlightCount.NotDelay "+
			"from `Schedule` "+
			"left join Aircraft on Aircraft.AircraftId = `Schedule`.AircraftId "+
			"left join Route on Route.RouteId = `Schedule`.RouteId "+
			"left join Airport as DepartAirport on Route.DepartureAirportIATA = DepartAirport.IATACode "+
			"left join Airport as ArriveAirport on Route.ArrivalAirportIATA = ArriveAirport.IATACode "+
			"left join City as DepartCity on DepartAirport.CityCode = DepartCity.CityCode "+
			"left join City as ArriveCity on ArriveAirport.CityCode = ArriveCity.CityCode "+
			"left join "+
				"( "+
					"select AllFlight.FlightNumber,AllFlight.AllCount,DelayFlight.DelayCount,(AllFlight.AllCount-DelayFlight.DelayCount) as NotDelay "+
						"from "+ 
							"("+
								"select `Schedule`.FlightNumber,count(1) as AllCount from `Schedule` "+
								"where ? between `Schedule`.Date and DATE_ADD(`Schedule`.Date,INTERVAL 30 DAY) "+
								"group by FlightNumber "+
							") as AllFlight "+
						"left join "+
							"("+
								"select `Schedule`.FlightNumber,count(1) as DelayCount from `Schedule` "+
								"left join FlightStatus on FlightStatus.ScheduleId = `Schedule`.ScheduleId "+
								"left join Route on Route.RouteId = `Schedule`.RouteId "+
								"where ? between `Schedule`.Date and DATE_ADD(`Schedule`.Date,INTERVAL 30 DAY) and (Status = 'Canceled' or TIMESTAMPDIFF(MINUTE,DATE_ADD(`Schedule`.Date,INTERVAL Route.FlightTime MINUTE),FlightStatus.ActualArrivalTime) >15) "+
								"group by `Schedule`.FlightNumber "+
							") as DelayFlight "+
						"on AllFlight.flightNumber = DelayFlight.FlightNumber "+
				") as FlightCount "+
			"on FlightCount.FlightNumber = `Schedule`.FlightNumber "+
			"where DepartCity.CityName = ? and ArriveCity.CityName = ? and `Schedule`.Date between ? and ? "+
			"order by `Schedule`.Date";
		return MySqlHelper.executeQueryReturnMap(sql, new Object[] { startDate, startDate, fromCity,
				toCity, startDate, endDate });
	}

	public static int findSoldTicketsCount(int scheduleId,int cabinTypeId) {
		String sql = "select count(1) as Counts from FlightReservation where ScheduleId = ? and CabinTypeId = ?";
		List<HashMap<String, Object>> list = MySqlHelper.executeQueryReturnMap(sql, new Object[] { scheduleId, cabinTypeId });
		if (list != null && list.size() > 0) {
			return Integer.parseInt(list.get(0).get("Counts").toString());
		} else {
			return 0;
		}
	}
	
	public static List<HashMap<String, Object>> findOneStopScheduleList(String fromCity, String toCity,
			String startDate, String endDate) {
		String sql = "select "+ 
				"S1.ScheduleId as S1ScheduleId, "+ 
				"Route.DepartureAirportIATA as S1DepartureAirportIATA, "+
				"DepartCity.CityName as S1DepartCityName, "+ 
				"Route.ArrivalAirportIATA as S1ArrivalAirportIATA, "+
				"ArriveCity.CityName as S1ArriveCityName, "+ 
				"S1.Date as S1Date, "+ 
				"S1.Time as S1Time, "+ 
				"DATE_ADD(S1.Date,INTERVAL Route.FlightTime MINUTE) as S1PreArrivalTime, "+ 
				"Route.FlightTime as S1FlightTime, "+ 
				"S1.EconomyPrice as S1EconomyPrice, "+ 
				"S1.FlightNumber as S1FlightNumber, "+ 
				"Aircraft.FirstSeatsAmount as S1FirstSeatsAmount, "+ 
				"Aircraft.BusinessSeatsAmount as S1BusinessSeatsAmount, "+ 
				"Aircraft.EconomySeatsAmount as S1EconomySeatsAmount, "+ 
				"S2.* "+ 
				"from `Schedule` as S1 "+ 
				"left join Aircraft on Aircraft.AircraftId=S1.AircraftId "+ 
				"left join Route on Route.RouteId=S1.RouteId "+ 
				"left join Airport as DepartAirport on Route.DepartureAirportIATA=DepartAirport.IATACode "+ 
				"left join Airport as ArriveAirport on Route.ArrivalAirportIATA=ArriveAirport.IATACode "+ 
				"left join City as DepartCity on DepartAirport.CityCode = DepartCity.CityCode "+ 
				"left join City as ArriveCity on ArriveAirport.CityCode = ArriveCity.CityCode "+ 
				"left join "+ 
				"("+ 
					"select "+ 
					"`Schedule`.ScheduleId as S2ScheduleId, "+ 
					"Route.DepartureAirportIATA as S2DepartureAirportIATA, "+ 
					"DepartCity.CityName as S2DepartCityName, "+ 
					"Route.ArrivalAirportIATA as S2ArrivalAirportIATA, "+
					"ArriveCity.CityName as S2ArriveCityName, "+ 
					"`Schedule`.Date as S2Date, "+ 
					"`Schedule`.Time as S2Time, "+ 
					"DATE_ADD(`Schedule`.Date,INTERVAL Route.FlightTime MINUTE) as S2PreArrivalTime, "+ 
					"Route.FlightTime as S2FlightTime, "+ 
					"`Schedule`.EconomyPrice as S2EconomyPrice, "+ 
					"`Schedule`.FlightNumber as S2FlightNumber, "+ 
					"Aircraft.FirstSeatsAmount as S2FirstSeatsAmount, "+ 
					"Aircraft.BusinessSeatsAmount as S2BusinessSeatsAmount, "+ 
					"Aircraft.EconomySeatsAmount as S2EconomySeatsAmount "+ 
					"from `Schedule` "+ 
					"left join Aircraft on Aircraft.AircraftId=`Schedule`.AircraftId "+ 
					"left join Route on Route.RouteId=`Schedule`.RouteId "+ 
					"left join Airport as DepartAirport on Route.DepartureAirportIATA=DepartAirport.IATACode "+ 
					"left join Airport as ArriveAirport on Route.ArrivalAirportIATA=ArriveAirport.IATACode "+ 
					"left join City as DepartCity on DepartAirport.CityCode = DepartCity.CityCode "+ 
					"left join City as ArriveCity on ArriveAirport.CityCode = ArriveCity.CityCode "+ 
				")"+
				"as S2 "+ 
				"on Route.ArrivalAirportIATA = S2.S2DepartureAirportIATA "+ 
				"where DepartCity.CityName = ? and S2.S2ArriveCityName = ? and S1.Date between ? and ? "+ 
				"and TIMESTAMPDIFF(HOUR,DATE_ADD(S1.Date,INTERVAL Route.FlightTime MINUTE),S2.S2Date) between 2 and 9 "+
				"order by S1.Date";
		return MySqlHelper.executeQueryReturnMap(sql,
				new Object[] { fromCity, toCity, startDate, endDate });
	}
	
	public static List<HashMap<String, Object>> findDelayInfoList(String startDate) {
		String sql = "select AllFlight.FlightNumber,AllFlight.AllCount, DelayFlight.DelayCount,(AllFlight.AllCount-DelayFlight.DelayCount) as NotDelay "+
				"from "+ 
					"("+
						"select `Schedule`.FlightNumber,count(1) as AllCount from `Schedule`  "+
						"where  ? between `Schedule`.Date and DATE_ADD(`Schedule`.Date,INTERVAL 30 DAY) "+
						"group by `Schedule`.FlightNumber  "+
					") as AllFlight "+
				"left join  "+
					"( "+
						"select `Schedule`.FlightNumber,count(1) as DelayCount from `Schedule` "+
						"left join FlightStatus on FlightStatus.ScheduleId = `Schedule`.ScheduleId "+
						"left join Route on Route.RouteId = `Schedule`.RouteId "+
						"where ? between `Schedule`.Date and DATE_ADD(`Schedule`.Date,INTERVAL 30 DAY) and (Status = 'Canceled' or TIMESTAMPDIFF(MINUTE,DATE_ADD(`Schedule`.Date,INTERVAL Route.FlightTime MINUTE),FlightStatus.ActualArrivalTime) >15) "+
						"group by `Schedule`.FlightNumber "+
					") as DelayFlight "+
				"on AllFlight.flightNumber = DelayFlight.FlightNumber";
		return MySqlHelper.executeQueryReturnMap(sql, new Object[] { startDate, startDate });
	}
}
