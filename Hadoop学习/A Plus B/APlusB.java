package lzl.aplusb;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

public class APlusB
{
	public static class MapAPlusB
		extends Mapper<LongWritable, Text, IntWritable, IntWritable>
	{
		private IntWritable myKey = null;
		private int myValue = 0;
		
		public void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException
		{
			myValue = 0;
			StringTokenizer itr = new StringTokenizer(value.toString());
			if(itr.hasMoreTokens())
				myKey = new IntWritable(
							Integer.parseInt(
									itr.nextToken()));
			while(itr.hasMoreTokens())
			{
				myValue = Integer.parseInt(
							itr.nextToken());
				context.write(myKey, new IntWritable(myValue));
			}
		}
	}
	
	public static class ReducerAPlusB
		extends Reducer<IntWritable, IntWritable, IntWritable, IntWritable>
	{
		private IntWritable result = new IntWritable();
		
		public void reduce(IntWritable key, Iterable<IntWritable> values, Context context)
			throws IOException, InterruptedException
		{
			int sum = 0;
			
			for(IntWritable val : values)
				sum += val.get();
			result.set(sum);
			context.write(key, result);
		}
	}
	
	public static void main(String[] args)
		throws Exception
	{
		Configuration conf = new Configuration();
		String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
		
		if(2 != otherArgs.length)
		{
			System.err.println("Usage: APlusB <in> <out>");
			System.exit(2);
		}
		
		Job job = new Job(conf, "a plus b");
		job.setJarByClass(APlusB.class);
		job.setMapperClass(MapAPlusB.class);
		job.setReducerClass(ReducerAPlusB.class);
		job.setOutputKeyClass(IntWritable.class);
		job.setOutputValueClass(IntWritable.class);
		FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
		FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));
		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
}