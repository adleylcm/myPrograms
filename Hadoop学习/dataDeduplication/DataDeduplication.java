package lzl.datadeduplication;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

public class DataDeduplication 
{
	public static class MapDataDeduplication
		extends Mapper<LongWritable, Text, Text, IntWritable>
	{
		private final static IntWritable myValue = new IntWritable(1);
		
		public void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException
		{
			System.out.println(key.toString());
			System.out.println(value.toString());
		
			//context.write(new Text(value.toString()), myValue);
			context.write(value, myValue);
		}
	}
	
	public static class ReduceDataDeduplication
		extends Reducer<Text, IntWritable, Text, NullWritable>
	{
		//private final static Text myValue = null;
		//private final static NullWritable nw;
		
		public void reduce(Text key, Iterable<IntWritable> values, Context context)
			throws IOException, InterruptedException
		{
			for(IntWritable val : values)
				;
			context.write(key, NullWritable.get());
		}
	}
	
	public static void main(String[] args)
		throws Exception
	{
		Configuration conf = new Configuration();
		String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
		
		if(2 != otherArgs.length)
		{
			System.err.println("Usage: DataDeduplication <in> <out>");
			System.exit(2);
		}
		
		Job job = new Job(conf, "data deduplication");
		job.setJarByClass(DataDeduplication.class);
		job.setMapperClass(MapDataDeduplication.class);
		job.setReducerClass(ReduceDataDeduplication.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(IntWritable.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(NullWritable.class);
		FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
		FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));
		System.exit(job.waitForCompletion(true) ? 0 : 1);
		
	}
}
