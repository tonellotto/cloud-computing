package it.unipi.hadoop;

import java.io.IOException;
import java.util.Map;
import java.util.HashMap;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

public class MatrixMultiplication
{
    public static class MatrixMapper extends Mapper<LongWritable, Text, Text, Text> 
    {
        private final Text outputKey   = new Text();
        private final Text outputValue = new Text();

        @Override
        public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException 
        {
            Configuration conf = context.getConfiguration();
            final int NUM_COLS_N = Integer.parseInt(conf.get("num_cols_n"));
            final int NUM_ROWS_M = Integer.parseInt(conf.get("num_rows_m"));
            
            String line = value.toString();
            // (M, i, j, Mij);
            String[] indicesAndValue = line.split(",");
            if (indicesAndValue[0].equals("M")) {
                for (int k = 0; k < NUM_COLS_N; k++) {
                    outputKey.set(indicesAndValue[1] + "," + k);
                    outputValue.set("M" + "," + indicesAndValue[2] + "," + indicesAndValue[3]);
                    context.write(outputKey, outputValue);
                }
            } else {
                // (N, j, k, Njk);
                for (int i = 0; i < NUM_ROWS_M; i++) {
                    outputKey.set(i + "," + indicesAndValue[2]);
                    outputValue.set("N," + indicesAndValue[1] + "," + indicesAndValue[3]);
                    context.write(outputKey, outputValue);
                }
            }
        }
    }

    public static class MatrixReducer extends Reducer<Text, Text, NullWritable, Text>
    {
        @Override
        public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException 
        {
            // key=(i,k),
            // Values = [(M/N,j,V/W),..]
            Map<Integer, Float> hashA = new HashMap<Integer, Float>();
            Map<Integer, Float> hashB = new HashMap<Integer, Float>();
            String[] value;
            for (Text val : values) {
                value = val.toString().split(",");
                if (value[0].equals("M"))
                    hashA.put(Integer.parseInt(value[1]), Float.parseFloat(value[2]));
                else
                    hashB.put(Integer.parseInt(value[1]), Float.parseFloat(value[2]));
            }
            int NUM_COLS_M = Integer.parseInt(context.getConfiguration().get("num_cols_m"));
            float result = 0.0f;
            float m_ij;
            float n_jk;
            for (int j = 0; j < NUM_COLS_M; j++) {
                m_ij = hashA.containsKey(j) ? hashA.get(j) : 0.0f;
                n_jk = hashB.containsKey(j) ? hashB.get(j) : 0.0f;
                result += m_ij * n_jk;
            }
            if (result != 0.0f)
                context.write(null, new Text(key.toString() + "," + Float.toString(result)));
        }
    }

    public static void main(String[] args) throws Exception
    {
        Configuration conf = new Configuration();
        String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
        if (otherArgs.length != 2) {
           System.err.println("Usage: MatrixMultiplication <input> <output>");
           System.exit(1);
        }
        System.out.println("args[0]: <input>="  + otherArgs[0]);
        System.out.println("args[1]: <output>=" + otherArgs[1]);

        Job job = Job.getInstance(conf, "MatrixMultiplication");
        job.getConfiguration().set("num_rows_m", "100");
        job.getConfiguration().set("num_cols_m", "1000");
        job.getConfiguration().set("num_rows_n", "1000");
        job.getConfiguration().set("num_cols_n", "100");

        job.setJarByClass(MatrixMultiplication.class);
        job.setMapperClass(MatrixMapper.class);
        job.setReducerClass(MatrixReducer.class);

        job.setNumReduceTasks(3);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
        FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));

        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);

        System.exit(job.waitForCompletion(true) ? 0 : 1);
     }
}
