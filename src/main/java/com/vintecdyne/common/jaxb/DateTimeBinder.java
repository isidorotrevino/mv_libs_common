package com.vintecdyne.common.jaxb;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeBinder {

	public static LocalDate parseDate(String s){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		return LocalDate.parse(s, formatter);
	}
	public static String printDate(LocalDate dt){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		return dt!=null?dt.format(formatter):null;
	}

	public static LocalDateTime parseDateTime(String s){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
		return LocalDateTime.parse(s, formatter);
	}

	public static String printDateTime(LocalDateTime dt) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
		return dt!=null?dt.format(formatter):null;
	  }

}
