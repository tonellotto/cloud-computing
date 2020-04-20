package it.unipi.hadoop;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil
{
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat(DATE_FORMAT);

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
}