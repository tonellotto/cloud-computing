package it.unipi.hadoop;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import org.apache.hadoop.io.Writable;

public class TimeSeriesData implements Writable, Comparable<TimeSeriesData>
{
	private long timestamp;
	private double value;

	public static TimeSeriesData copy(final TimeSeriesData tsd)
	{
		return new TimeSeriesData(tsd.timestamp, tsd.value);
	}

	public TimeSeriesData()
	{
	}

	public TimeSeriesData(final long timestamp, final double value)
	{
		this.set(timestamp, value);
	}


	public void set(final long timestamp, final double value)
	{
		this.timestamp = timestamp;
		this.value = value;
	}

	public long getTimestamp()
	{
		return this.timestamp;
	}

	public double getValue()
	{
		return this.value;
	}

	/**
	 * Deserializes the point from the underlying data.
	 * @param in a DataInput object to read the point from.
	 */
	public void readFields(DataInput in) throws IOException
	{
		this.timestamp  = in.readLong();
		this.value  = in.readDouble();
	}

	@Override
	public void write(DataOutput out) throws IOException
	{
		out.writeLong(this.timestamp );
		out.writeDouble(this.value );
	}

	/**
	 * Convert a binary data into TimeSeriesData
	 *
	 * @param in A DataInput object to read from.
	 * @return A TimeSeriesData object
	 * @throws IOException
	 */
	/*
	public static TimeSeriesData read(DataInput in) throws IOException {
		TimeSeriesData tsData = new TimeSeriesData();
		tsData.readFields(in);
		return tsData;
	}
	*/

	/*
	public String getDate()
	{
		return DateUtil.getDateAsString(this.timestamp);
	}
	*/

   /**
    * Creates a clone of this object
    */
   /*
    public TimeSeriesData clone() {
       return new TimeSeriesData(timestamp, value);
    }
    */

	@Override
	public int compareTo(TimeSeriesData that)
	{
		if (this == that)
			return 0;
		if (this.timestamp  < that.timestamp )
			return -1;
		if (this.timestamp  > that.timestamp )
			return 1;
	    return 0;
	}

	@Override
	public String toString()
	{
       return "(" + timestamp + ", " + value + ")";
    }
}