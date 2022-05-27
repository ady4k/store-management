package helper;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public final class DateFormatterHelper {
    // constructor is private as there is no point in instantiating this class
    private DateFormatterHelper() {
    }

    // method that formats the date given to a specific format
    public static String dateFormat(Date date) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        return formatter.format(date);
    }

    // method that returns the month name given a month number
    // don't know why it's implemented, there is no usage
    public static String getMonth(int month) {
        return new DateFormatSymbols().getMonths()[month - 1];
    }

    // method used to add a number of days to a date given
    // used when calculating the estimated time of arrival date based on the launch date and the number of days
    // given by the transport method method
    public static Date addDays(Date date, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, days);
        return calendar.getTime();
    }
}
