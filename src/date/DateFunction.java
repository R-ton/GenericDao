package date;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Month;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;


public class DateFunction {
//localDate
	public static LocalDate stringToLocalDate(String date) {
		LocalDate localDate = LocalDate.parse(date);
		return localDate;
	}
	
	public static LocalDate projectionByDay(LocalDate date,int projection) {
			LocalDate lastWeek = date.plusDays(projection);
			return lastWeek;
	}
	
	public static LocalDate lastWeek(LocalDate date) {
		LocalDate lastWeek = date.minusDays(7);
		return lastWeek;
	}
	
	public static double dateToEpoch(LocalDate date) {
		double epoch = date.toEpochDay();
		return epoch;
	}
	
	public static double differenceDay(LocalDate last,LocalDate begin) {
		double lastEpoch = DateFunction.dateToEpoch(last);
		double beginEpoch = DateFunction.dateToEpoch(begin);
		
		return lastEpoch - beginEpoch;
	}
	
	
//date
	public static Date localDateToDate(LocalDate localDate) {
		Date date = Date.valueOf(localDate);
		return date;
	}
	
//hollidays (jours feries)
	
	
	public static HashMap hollidays(){
		HashMap<String,LocalDate> calendar = new HashMap();
		
		calendar.put("New Year",LocalDate.of(2019, Month.JANUARY, 1));
		calendar.put("Women's Day",LocalDate.of(2019, Month.MARCH, 8));
		calendar.put("Martyrs Day",LocalDate.of(2019, Month.MARCH, 29));
		calendar.put("Easter",LocalDate.of(2019, Month.APRIL, 21));
		calendar.put("Easter Monday",LocalDate.of(2019, Month.APRIL, 22));
		calendar.put("Labor Day",LocalDate.of(2019, Month.MAY, 1));
		calendar.put("Ascension Thursday",LocalDate.of(2019, Month.MAY, 30));
		calendar.put("Pentecost",LocalDate.of(2019, Month.JUNE, 9));
		calendar.put("Pentecost Monday",LocalDate.of(2019, Month.JUNE, 10));
		calendar.put("L'Aïd el Fitr",LocalDate.of(2019, Month.JUNE, 4));
		calendar.put("Independence Day",LocalDate.of(2019, Month.JUNE, 26));
		calendar.put("L'Aïd el kebir ou Aïd-al-adha",LocalDate.of(2019, Month.AUGUST, 11));
		calendar.put("Assumption of the Blessed Virgin",LocalDate.of(2019, Month.AUGUST, 15));
		calendar.put("All Saints",LocalDate.of(2019, Month.NOVEMBER, 1));
		calendar.put("Christmas",LocalDate.of(2019, Month.DECEMBER, 25));
			
		return calendar; 
	}
	
	public boolean isAHollidays(HashMap hollidays,LocalDate date){
		if(hollidays.containsValue(date)) {
			return true;
		}
		return false;
	}
}
