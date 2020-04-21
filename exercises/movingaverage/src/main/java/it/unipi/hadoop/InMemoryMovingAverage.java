package it.unipi.hadoop;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

public class InMemoryMovingAverage
{
    public static class MovingAverageMapper extends Mapper<LongWritable, Text, Text, TimeSeriesData>
    {
        // reuse Hadoop's Writable objects
        private final Text reducerKey = new Text();
        private final TimeSeriesData reducerValue = new TimeSeriesData();

        @Override
        public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException
        {
            String record = value.toString();
            if (record == null || record.length() == 0)
                return;

            String[] tokens = record.trim().split(",");

            if (tokens.length == 3) {
                Date date = DateUtil.getDate(tokens[1]);
                if (date == null) {
                    return;
                }
                reducerKey.set(tokens[0]); // set the name as key
                reducerValue.set(date.getTime(), Double.parseDouble(tokens[2]));
                context.write(reducerKey, reducerValue);
            }
        }
    }

    public static class MovingAverageReducer extends Reducer<Text, TimeSeriesData, Text, Text>
    {
        private int windowSize;

        public void setup(Context context) throws IOException, InterruptedException
        {
            this.windowSize = context.getConfiguration().getInt("moving.average.window.size", 5);
        }

        public void reduce(Text key, Iterable<TimeSeriesData> values, Context context) throws IOException, InterruptedException
        {
            // build the unsorted list of timeseries
            List<TimeSeriesData> timeseries = new ArrayList<TimeSeriesData>();
            for (TimeSeriesData tsData : values) {
                timeseries.add(TimeSeriesData.copy(tsData));
            }

            // sort the timeseries data in memory
            Collections.sort(timeseries);

            // apply moving average algorithm to sorted timeseries
            Text outputValue = new Text(); // reuse object

            double sum = 0.0d;
            for (int i = 0; i < windowSize - 1; i++)
                sum += timeseries.get(i).getValue();

            for (int i = windowSize - 1; i < timeseries.size(); i++) {
                sum += timeseries.get(i).getValue();

                double movingAverage = sum / windowSize;
                long timestamp = timeseries.get(i).getTimestamp();
                outputValue.set(DateUtil.getDateAsString(timestamp) + ", " + movingAverage);
                context.write(key, outputValue);

                sum -= timeseries.get(i - windowSize + 1).getValue();
            }
        }
    }

    public static void main(String[] args) throws Exception
    {
        Configuration conf = new Configuration();
        String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
        if (otherArgs.length != 3) {
           System.err.println("Usage: SortInMemory_MovingAverageDriver <window_size> <input> <output>");
           System.exit(1);
        }
        System.out.println("args[0]: <window_size>="+otherArgs[0]);
        System.out.println("args[1]: <input>="+otherArgs[1]);
        System.out.println("args[2]: <output>="+otherArgs[2]);

        Job job = Job.getInstance(conf, "InMemoryMovingAverage");
		job.setJarByClass(InMemoryMovingAverage.class);

        // set mapper/reducer
        job.setMapperClass(MovingAverageMapper.class);
        job.setReducerClass(MovingAverageReducer.class);

        // define mapper's output key-value
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(TimeSeriesData.class);

        // define reducer's output key-value
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        // set window size for moving average calculation
        int windowSize = Integer.parseInt(otherArgs[0]);
        job.getConfiguration().setInt("moving.average.window.size", windowSize);

        // define I/O
        FileInputFormat.addInputPath(job, new Path(otherArgs[1]));
        FileOutputFormat.setOutputPath(job, new Path(otherArgs[2]));

        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);

        System.exit(job.waitForCompletion(true) ? 0 : 1);
     }
}
