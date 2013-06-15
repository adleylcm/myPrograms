package lzl.simpletablejoin;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

public class SimpleTableJoin 
{
	public static int time = 0; 
	public static class TableJoinWritable
		implements WritableComparable<TableJoinWritable>
	{
		private Text people;
		private IntWritable type;
		
		public TableJoinWritable()
		{
			set(new Text(), new IntWritable());
		}
		public TableJoinWritable(String people, int type)
		{
			set(new Text(people), new IntWritable(type));
		}
		public TableJoinWritable(Text people, IntWritable type)
		{
			set(people, type);
		}
		
		public void set(Text people, IntWritable type)
		{
			this.people = people;
			this.type = type;
		}
		public Text getPeople()
		{
			return people;
		}
		public String toString()
		{
			return people.toString();
		}
		public int getType()
		{
			return Integer.parseInt(type.toString());
		}
		public void write(DataOutput out)
			throws IOException
		{
			people.write(out);
			type.write(out);
		}
		public void readFields(DataInput in)
			throws IOException
		{
			people.readFields(in);
			type.readFields(in);
		}

		public int compareTo(TableJoinWritable tj) 
		{
			
			return people.compareTo(tj.people);
		}
	}
	
	public static class MapSimpleTableJoin
		extends Mapper<LongWritable, Text, TableJoinWritable, TableJoinWritable>
	{
		private String people1;
		private String people2;
		
		public void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException
		{
			if(0 != Integer.parseInt(key.toString()))
			{
				int count = 0;
				StringTokenizer itr = new StringTokenizer(value.toString());
				if(itr.hasMoreTokens())
				{
					people1 = itr.nextToken();
					count++;
				}
				if(itr.hasMoreTokens())
				{
					people2 = itr.nextToken();
					count++;
				}
				if(2 == count)
				{
					context.write(new TableJoinWritable(people1, 0), new TableJoinWritable(people2, 1));
					context.write(new TableJoinWritable(people2, 1), new TableJoinWritable(people1, 0));
				}
			}
		}		
	}
	
	public static class ReduceSimpleTableJoin
		extends Reducer<TableJoinWritable, TableJoinWritable, Text, Text>
	{		
		public void reduce(TableJoinWritable key, Iterable<TableJoinWritable> values, Context context)
			throws IOException, InterruptedException
		{
			List<String> child = new ArrayList<String>();
			List<String> parent = new ArrayList<String>();
			
			
			for(TableJoinWritable tjw : values)
			{
				if(0 == tjw.getType())
				{
					child.add(tjw.getPeople().toString());
				}
				else
				{
					parent.add(tjw.getPeople().toString());
				}
			}
			
			if(0 != child.size() && 0 != parent.size())
			{
				for(int i = 0; i < child.size(); i++)
					for(int j = 0; j < parent.size(); j++)
					{
						context.write(new Text(child.get(i)), new Text(parent.get(j)));
					}
			}
		}
		
	}
	
	public static class MapSortResult
	extends Mapper<LongWritable, Text, Text, Text>
	{
		public void map(LongWritable key, Text value, Context context)
				throws IOException, InterruptedException
			{
				String grandchild = null;
				String grandparent = null;
				StringTokenizer itr = new StringTokenizer(value.toString());
				if(itr.hasMoreTokens()) 
				{
					grandchild = itr.nextToken();
				}
				if(itr.hasMoreTokens())
				{
					grandparent = itr.nextToken();
					context.write(new Text(grandchild), new Text(grandparent));
				}
			}
	}
	
	public static class ReduceSortResult
	extends Reducer<Text, Text, Text, Text>
	{		
		public void reduce(Text key, Iterable<Text> values, Context context)
			throws IOException, InterruptedException
		{
			if(0 == time++)
				context.write(new Text("grandchild"), new Text("grandparent"));
			for(Text val : values)
				context.write(key, val);
		}
	}
	
	public static void main(String[] args)
		throws Exception
	{
		Configuration conf = new Configuration();
		String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
		
		if(3 != otherArgs.length)
		{
			System.err.println("Usage: SimpleTableJoin <in> <out> <out>");
			System.exit(2);
		}
		
		Job job = new Job(conf, "simple table join");
		job.setJarByClass(SimpleTableJoin.class);
		job.setMapperClass(MapSimpleTableJoin.class);
		job.setReducerClass(ReduceSimpleTableJoin.class);
		job.setMapOutputKeyClass(TableJoinWritable.class);
		job.setMapOutputValueClass(TableJoinWritable.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
		FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));
		
		job.waitForCompletion(true);
		
		Configuration conf1 = new Configuration();
		Job job1 = new Job(conf1, "simple table join sort result");
		job1.setJarByClass(SimpleTableJoin.class);
		job1.setMapperClass(MapSortResult.class);
		job1.setReducerClass(ReduceSortResult.class);
		job1.setMapOutputKeyClass(Text.class);
		job1.setMapOutputValueClass(Text.class);
		job1.setOutputKeyClass(Text.class);
		job1.setOutputValueClass(Text.class);
		FileInputFormat.addInputPath(job1, new Path(otherArgs[1]));
		FileOutputFormat.setOutputPath(job1, new Path(otherArgs[2]));
		
		System.exit(job1.waitForCompletion(true) ? 0 : 1);
	}
}
