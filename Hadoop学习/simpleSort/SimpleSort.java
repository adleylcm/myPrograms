package lzl.simplesort;

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

public class SimpleSort 
{
	private static int count = 0;
	
	public static class MapSimpleSort
		extends Mapper<LongWritable, Text, IntWritable, IntWritable>
	{
		private int myKey = -1;
		private IntWritable myValue = new IntWritable(1);
		
		public void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException
		{
			myKey = -1;
			StringTokenizer itr = new StringTokenizer(value.toString());
			if(itr.hasMoreTokens())
			{
				myKey = Integer.parseInt(itr.nextToken());
				context.write(new IntWritable(myKey), myValue);
			}
			
		}
	}
	
	public static class ReduceSimpleSort
		extends Reducer<IntWritable, IntWritable, IntWritable, IntWritable>
	{
		
		
		public void reduce(IntWritable key, Iterable<IntWritable> values, Context context)
			throws IOException, InterruptedException
		{
			for(IntWritable val : values)
				context.write(new IntWritable(++count), key);
		}
	}
	
	public static void main(String[] args)
		throws Exception
	{
		Configuration conf = new Configuration();
		String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
		
		if(2 != otherArgs.length)
		{
			System.err.println("Usage: SimpleSort <in> <out>");
			System.exit(2);
		}
		
		Job job = new Job(conf, "simple sort");
		job.setJarByClass(SimpleSort.class);
		job.setMapperClass(MapSimpleSort.class);
		job.setReducerClass(ReduceSimpleSort.class);
		job.setOutputKeyClass(IntWritable.class);
		job.setOutputValueClass(IntWritable.class);
		FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
		FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));
		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
}
