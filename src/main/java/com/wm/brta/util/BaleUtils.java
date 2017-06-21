package com.wm.brta.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.mockito.Mockito;

import net.iharder.Base64;

public class BaleUtils {

	
	private static  SessionFactory mockedSessionFactory;
	private static  Session mockedSession;
	private static Transaction mockedTransaction;
	
	
	public static Integer getWeekNumber(String date) throws ParseException {

		String format = "MM/dd/yyyy";

		SimpleDateFormat df = new SimpleDateFormat(format);
		Date dateFormat = df.parse(date);
		Calendar cal = Calendar.getInstance();
		cal.setTime(dateFormat);

		int weeknumber = cal.get(Calendar.WEEK_OF_MONTH);

		if (cal.get(Calendar.DAY_OF_WEEK) == 1)
			weeknumber = weeknumber - 1;

		return weeknumber;
	}

	public static Integer getDay(String date) throws ParseException {

		String format = "MM/dd/yyyy";

		SimpleDateFormat df = new SimpleDateFormat(format);
		Date dateFormat = df.parse(date);
		Calendar cal = Calendar.getInstance();
		cal.setTime(dateFormat);

		int day = cal.get(Calendar.DAY_OF_WEEK);
		day = day - 1;
		if (day == 0)
			day = 7;

		return day;
	}
	
	public static List<String> getLastOneWeekDates() {
		List<String> dateList=new ArrayList<String>();
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Calendar cal = Calendar.getInstance();
	
		cal.add(Calendar.DAY_OF_YEAR, -8);

		// loop adding one day in each iteration
		for(int i = 0; i< 7; i++){
		    cal.add(Calendar.DAY_OF_YEAR, 1);
		    dateList.add(sdf.format(cal.getTime()));
		}
		return dateList;
	}
	
	public  static void setUpMokito() {
	    mockedSessionFactory =  Mockito.mock(SessionFactory.class);
	    mockedSession = Mockito.mock(Session.class);
	    mockedTransaction = Mockito.mock(Transaction.class);
	}
	

	public static  String convertWeekNumberAndDayToDate1(int weekNumber, int day,
			Date startDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Calendar cal = Calendar.getInstance();
		if (day == 7) {
			day = 0;
			weekNumber = weekNumber + 1;
		}
		cal.set(Calendar.WEEK_OF_MONTH, weekNumber);
        boolean entryFlag = false;
		cal.set(Calendar.MONTH, cal.getTime().getMonth());
		cal.set(Calendar.DAY_OF_WEEK, day + 1);
		String dateToreturn = sdf.format(cal.getTime());
		Calendar startDateCalendar = Calendar.getInstance();
		Calendar todaysDatecalendar = Calendar.getInstance();
		Calendar dateToReturnCalendar = Calendar.getInstance();
		if (startDate.after(new Date())) {
			if (startDate.after(new Date(dateToreturn))) {
				cal.set(Calendar.WEEK_OF_MONTH, weekNumber);
				startDateCalendar.setTime(startDate);
				cal.set(Calendar.MONTH,
						startDateCalendar.getTime().getMonth() + 1);
				cal.set(Calendar.DAY_OF_WEEK, day + 1);
				dateToreturn = sdf.format(cal.getTime());

			}

		}
		
		

		if ( (startDate.before(new Date()) || startDate.equals(new Date()))) {
             
			if (new Date().getMonth() == (new Date(dateToreturn)).getMonth()
					&& new Date().getYear() == (new Date(dateToreturn))
							.getYear()
					&& new Date().getDate() == (new Date(dateToreturn)).getDate()) {
				
				cal.set(Calendar.WEEK_OF_MONTH, weekNumber);
				todaysDatecalendar.setTime(new Date());
				cal.set(Calendar.MONTH, todaysDatecalendar.getTime().getMonth());
				cal.set(Calendar.DAY_OF_WEEK, day + 1);
				dateToreturn = sdf.format(cal.getTime());
			}

			else if (new Date().after(new Date(dateToreturn))) {
				
				cal.set(Calendar.WEEK_OF_MONTH, weekNumber);
				todaysDatecalendar.setTime(new Date());
				cal.set(Calendar.MONTH,
						todaysDatecalendar.getTime().getMonth() + 1);
				cal.set(Calendar.DAY_OF_WEEK, day + 1);
				dateToreturn = sdf.format(cal.getTime());

			}
			
              
		}


		return dateToreturn;

	}
	
	public static String convertWeekNumberAndDayToDate(int weekNumber,int day){
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Calendar cal = Calendar.getInstance();
		Calendar cal1 = Calendar.getInstance();

		if(day == 7){
			day = 0;
			weekNumber = weekNumber + 1;
		}
		

		cal1.set(Calendar.WEEK_OF_MONTH, weekNumber);
		cal1.set(Calendar.MONTH, cal.getTime().getMonth());
		cal1.set(Calendar.DAY_OF_WEEK, day+1);
		String dateToreturn = sdf.format(cal1.getTime());
		
		return dateToreturn;
		
	}
	
	public static byte[] loadFile(File file) throws IOException {
	    InputStream is = new FileInputStream(file);

	    long length = file.length();
	    if (length > Integer.MAX_VALUE) {
	        // File is too large
	    }
	    byte[] bytes = new byte[(int)length];
	    
	    int offset = 0;
	    int numRead = 0;
	    while (offset < bytes.length
	           && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
	        offset += numRead;
	    }

	    if (offset < bytes.length) {
	        throw new IOException("Could not completely read file "+file.getName());
	    }

	    is.close();
	    return bytes;
	}
	
	public static String encodedBase64String(String filePath){
		String encodedString="";
		try {
			File file = new File(filePath.toString());
			if (file.length() > 0) {
				byte[] bytes = BaleUtils.loadFile(file);
				byte[] encoded = org.apache.tomcat.util.codec.binary.Base64.encodeBase64(bytes);
				encodedString = new String(encoded);
			}

		} catch (Exception e) {
			System.out.println("======exception while reading file ====" + e);
		}
		return encodedString;
	}
	
    public static String convertWeekNumberAndDayToDate(int weekNumber, int dayNumber, Date startDate) {
        
    	
    	//System.out.println("===weekNo="+weekNumber +" =====dayNumber="+dayNumber+ "======startDate="+startDate);
    	
        int _dayOfTheWeek = getDayOfTheWeek(dayNumber);
        Date _baseDate = new Date();
        if ((startDate != null) && startDate.after(_baseDate)) {
               
               _baseDate = startDate;
        }
        
        Calendar _cal = Calendar.getInstance();
        _cal.setFirstDayOfWeek(Calendar.MONDAY);
        _cal.setTime(_baseDate);

        Calendar _nextDateCal = Calendar.getInstance();
        _nextDateCal.setFirstDayOfWeek(Calendar.MONDAY);
        Calendar _calcDateCal = Calendar.getInstance();
        _calcDateCal.setFirstDayOfWeek(Calendar.MONDAY);

        int _monthNumber = _cal.get(Calendar.MONTH);
        boolean _checkMonth = true;
        
        do {

               _nextDateCal.set(Calendar.MONTH, _monthNumber);
               _nextDateCal.set(Calendar.WEEK_OF_MONTH, weekNumber);
               _nextDateCal.set(Calendar.DAY_OF_WEEK, _dayOfTheWeek);
               
               _monthNumber++;
               if (_monthNumber > 12) {
                     _monthNumber = 1;                 
                     _checkMonth = false;
               }
               
//             Following code is to ensure even if it is today's date, it is greater than _baseDate
               _calcDateCal.setTime(_nextDateCal.getTime());
               _calcDateCal.set(Calendar.HOUR_OF_DAY, 23);
               _calcDateCal.set(Calendar.MINUTE, 59);
               _calcDateCal.set(Calendar.SECOND, 59);
               _calcDateCal.set(Calendar.MILLISECOND, 59);

        } while (!((_calcDateCal.getTime().after(_baseDate))
                     && ((_calcDateCal.get(Calendar.MONTH) >= _cal.get(Calendar.MONTH)) || !_checkMonth)
                                   && (_calcDateCal.get(Calendar.WEEK_OF_MONTH) == weekNumber)
                                   && (_calcDateCal.get(Calendar.DAY_OF_WEEK) == _dayOfTheWeek)));
        
        
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		String dateString= sdf.format(_calcDateCal.getTime());
		
        return dateString;

 }

 /**
 * Returns Java day of the week
 * 
 * @param dayNumberStartingMonday
 * @return
 */
 private static int getDayOfTheWeek(int dayNumberStartingMonday) {
        
        int _dayOfTheWeek = Calendar.SUNDAY;
        switch (dayNumberStartingMonday) {
        case 1:
               _dayOfTheWeek = Calendar.MONDAY;
               
               break;

        case 2:
               _dayOfTheWeek = Calendar.TUESDAY;
               
               break;

        case 3:
               _dayOfTheWeek = Calendar.WEDNESDAY;
               
               break;

        case 4:
               _dayOfTheWeek = Calendar.THURSDAY;
               
               break;

        case 5:
               _dayOfTheWeek = Calendar.FRIDAY;
               
               break;

        case 6:
               _dayOfTheWeek = Calendar.SATURDAY;
               
               break;

        case 7:
               _dayOfTheWeek = Calendar.SUNDAY;
               
               break;
        default:
               break;
        }
        
        return _dayOfTheWeek;
 }



	
	public static boolean isValidEmailAddress(String email) {
		   boolean result = true;
		   try {
		      InternetAddress emailAddr = new InternetAddress(email);
		      emailAddr.validate();
		   } catch (AddressException ex) {
		      result = false;
		   }
		   return result;
		}
	

}
