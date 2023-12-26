SELECT LEFT(date,7) MONTH,
COUNT(DISTINCT s.ScheduleId) FlightsAmount,
COUNT(f.ReservationId) TicketsAmount,
SUM(f.Payment) TicketsRevenue
FROM `schedule` s
LEFT JOIN flightreservation f on s.ScheduleId = f.ScheduleId
WHERE date >= "2019-08-01" AND date < DATE_ADD("2019-09-01",INTERVAL 1 MONTH)
and Status = 'Confirmed' 
GROUP BY YEAR(date),MONTH(date)