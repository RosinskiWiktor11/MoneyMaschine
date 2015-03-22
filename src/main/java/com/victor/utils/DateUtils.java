package com.victor.utils;

import java.util.GregorianCalendar;

import org.joda.time.DateTime;
import org.joda.time.Days;

/**
 * @author Wiktor formatowanie i operacje na datach
 */
public class DateUtils {

	/**
	 * @param date
	 *            data do konwersji
	 * @param format
	 *            format daty
	 * @return
	 * @see {@link com.victor.utils.DateFormat}
	 */
	public static String convertToStringFormat(GregorianCalendar date,
			DateFormat format) {
		StringBuffer buffer = new StringBuffer();
		switch (format) {
		case YYYY_MM_DD: {
			int i;

			buffer.append(getYear(date)).append("-");
			i = getMonth(date);
			if (i < 10)
				buffer.append("0");
			buffer.append(i).append("-");
			i = getDayOfMonth(date);
			if (i < 10)
				buffer.append("0");
			buffer.append(i);

			return buffer.toString();
		}
		
		case DD_MM_YYYY:{
		    buffer=new StringBuffer();
		    int i;
		    i=date.get(GregorianCalendar.DAY_OF_MONTH);
		    if(i<10)
		    	buffer.append("0");
		    buffer.append(i).append(".");
		    i=date.get(GregorianCalendar.MONTH)+1;
		   	if(i<10)
		   		buffer.append("0");
		   	buffer.append(i).append(".");
		   	i=date.get(GregorianCalendar.YEAR);
		   	buffer.append(i);
		  
		   	return buffer.toString();
		}

		default:
			return null;
		}
	}
	
	
	/**
	 * @param stringDate
	 *            data w String do konwersji w odp. formacie
	 * @param format
	 *            format daty
	 * @return
	 * @see {@link com.victor.utils.DateFormat}
	 */
	public static GregorianCalendar convertToDateFormat(String stringDate,
			DateFormat format) {
		switch(format){
		case YYYY_MM_DD: {
			int year;
			int month;
			int day;
			if (stringDate.indexOf("-") > 0 || stringDate.indexOf(".") > 0) {
				String num;
				num = stringDate.substring(0, 4);
				year = Integer.parseInt(num);
				num = stringDate.substring(5, 7);
				month = Integer.parseInt(num);
				num = stringDate.substring(8, 10);
				day = Integer.parseInt(num);
				return new GregorianCalendar(year, month - 1, day);
			}			
		}
		case DD_MM_YYYY:{
			int year;
			int month;
			int day;
			if (stringDate.indexOf("-") > 0 || stringDate.indexOf(".") > 0) {
				String num;
				num = stringDate.substring(0, 2);
				day=Integer.parseInt(num);
				num=stringDate.substring(3,5);
				month=Integer.parseInt(num)-1;
				num=stringDate.substring(6,10);
				year=Integer.parseInt(num);
				return new GregorianCalendar(year,month,day);
			}	
		}
		}

		return null;
	}
	
	public static String convertBetweenDateFormat(String stringDate, DateFormat oldFormat, DateFormat newFormat){
		String[] values;
		switch(oldFormat){
			case DD_MM_YYYY:{
				values=getValues(stringDate, oldFormat);
				switch(newFormat){
				case DD_MM_YYYY:
					return stringDate;
				case YYYY_MM_DD:
					return convertToString(DateFormat.YYYY_MM_DD, values[2], values[1], values[0]);
				}
			}
			case YYYY_MM_DD:{
				values=getValues(stringDate, oldFormat);
				switch(newFormat){
				case DD_MM_YYYY:
					return convertToString(DateFormat.DD_MM_YYYY, values[2], values[1], values[0]);
				case YYYY_MM_DD:
					return stringDate;
				}
				
			}
		}
		return stringDate;
	}
	
	private static String[] getValues(String stringDate, DateFormat format){
		String[] values;
		switch(format){
		case DD_MM_YYYY:
			values=new String[3];
			values[0]=stringDate.substring(0, 2);
			values[1]=stringDate.substring(3,5);
			values[2]=stringDate.substring(6,10);
			return values;
		case YYYY_MM_DD:
			values=new String[3];
			values[0]=stringDate.substring(0, 4);
			values[1]=stringDate.substring(5, 7);
			values[2]=stringDate.substring(8, 10);
			return values;
		}
		return null;
	}
	
	private static String convertToString(DateFormat dateFormat, String...values){
		StringBuffer buffer=new StringBuffer();
		switch(dateFormat){
		case DD_MM_YYYY:
			buffer.append(values[0]).append('.');
			buffer.append(values[1]).append('.');
			buffer.append(values[2]);
			return buffer.toString();
		case YYYY_MM_DD:
			buffer.append(values[0]).append('-');
			buffer.append(values[1]).append('-');
			buffer.append(values[2]);
			return buffer.toString();
		}
		
		return values[0];
	}
	

	/** format: YYYY-MM-DD */
	public static GregorianCalendar convertToDate(String stringDate) {
		int year;
		int month;
		int day;
		if (stringDate.indexOf("-") > 0 || stringDate.indexOf(".") > 0) {
			String num;
			num = stringDate.substring(0, 4);
			year = Integer.parseInt(num);
			num = stringDate.substring(5, 7);
			month = Integer.parseInt(num);
			num = stringDate.substring(8, 10);
			day = Integer.parseInt(num);
			return new GregorianCalendar(year, month - 1, day);
		}
		return null;
	}
	
	

	public static int getYear(GregorianCalendar date) {
		return date.get(GregorianCalendar.YEAR);
	}

	public static int getMonth(GregorianCalendar date) {
		return date.get(GregorianCalendar.MONTH) + 1;
	}

	public static int getDayOfMonth(GregorianCalendar date) {
		return date.get(GregorianCalendar.DAY_OF_MONTH);
	}

	public static int numberDaysBetween(GregorianCalendar firstDate,
			GregorianCalendar secondDate) {
		int result = firstDate.compareTo(secondDate);
		DateTime firstJodaDate;
		DateTime secondJodaDate;
		if (result <= 0) {
			firstJodaDate = new DateTime(firstDate.getTimeInMillis());
			secondJodaDate = new DateTime(secondDate.getTimeInMillis());
		} else {
			firstJodaDate = new DateTime(secondDate.getTimeInMillis());
			secondJodaDate = new DateTime(firstDate.getTimeInMillis());
		}

		int numberDaysBetween = Days.daysBetween(firstJodaDate, secondJodaDate)
				.getDays();
		return numberDaysBetween;
	}
	
	public static void setToStartDate(GregorianCalendar calendar){
		calendar.set(GregorianCalendar.HOUR, 0);
		calendar.set(GregorianCalendar.HOUR_OF_DAY, 0);
		calendar.set(GregorianCalendar.MINUTE, 0);
		calendar.set(GregorianCalendar.SECOND, 0);
		calendar.set(GregorianCalendar.MILLISECOND, 0);
	}

	public static boolean isWeekendDay(GregorianCalendar date) {
		int dateOfWeek = date.get(GregorianCalendar.DAY_OF_WEEK);
		switch (dateOfWeek) {
		case GregorianCalendar.SUNDAY:
			return true;
		case GregorianCalendar.SATURDAY:
			return true;
		default:
			return false;
		}
	}
}
