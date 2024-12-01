package com.manualtasks.jobchecklist.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import org.springframework.stereotype.Component;

@Component
public class TimingsValidatorService {

	private TimeZone easternTimeZone = TimeZone.getTimeZone("America/New_York");

	private SimpleDateFormat dateFormatter2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	private SimpleDateFormat dateFormatter3 = new SimpleDateFormat("yyyyMMddHHmm");

	public boolean isLogCretdInGivnShift(String logTimeStamp, String shiftStartTime, String shiftEndTime,
			String logNameWithTimeStamp) throws ParseException {

		dateFormatter2.setTimeZone(easternTimeZone);

		dateFormatter3.setTimeZone(easternTimeZone);

		String gvnLogCrtdTime = dateFormatter2
				.format(dateFormatter3.parse(logTimeStamp.substring(0, logTimeStamp.length() - 2)));
//		System.out.print("Are all the below statements correct :- (" + logNameWithTimeStamp + ")-");
//		System.err.print(gvnLogCrtdTime + "\n");
//		System.out.println("The time " + gvnLogCrtdTime + " is after " + shiftStartTime);
//		System.out.println("The time " + gvnLogCrtdTime + " is before " + shiftEndTime);
		if (dateFormatter2.parse(gvnLogCrtdTime).after(dateFormatter2.parse(shiftStartTime))
				&& dateFormatter2.parse(gvnLogCrtdTime).before(dateFormatter2.parse(shiftEndTime))) {

//			System.out.println("Yes");
			return true;
		} else {
//			System.out.println("No");
			return false;
		}
	}
}
