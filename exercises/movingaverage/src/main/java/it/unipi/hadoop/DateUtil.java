package it.unipi.hadoop;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil
{
	private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat(DATE_FORMAT);

    /**
     *  Returns the Date from a given dateAsString
     */
	public static Date getDate(final String dateAsString)
    {
        try {
        	return SIMPLE_DATE_FORMAT.parse(dateAsString);
        } catch(Exception e) {
        	return null;
        }
	}

    public static String getDateAsString(long timestamp)
    {
        return SIMPLE_DATE_FORMAT.format(timestamp);
    }

    public static void main(String args[])
    {
        System.err.println(getDateAsString(1374184800000l));

    }
    /**
     *  Returns the number of milliseconds since January 1, 1970,
     *  00:00:00 GMT represented by this Date object.
     */
    /*
	public static long getDateAsMilliSeconds(Date date) throws Exception {
        return date.getTime();
	}
	*/

    /**
     *  Returns the number of milliseconds since January 1, 1970,
     *  00:00:00 GMT represented by this Date object.
     */
    /*
	public static long getDateAsMilliSeconds(String dateAsString) throws Exception {
		Date date = getDate(dateAsString);
        return date.getTime();
	}
    */

}