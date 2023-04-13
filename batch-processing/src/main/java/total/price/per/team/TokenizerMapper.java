package total.price.per.team;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.mortbay.log.Log;

import java.io.IOException;
import java.util.logging.Logger;

public class TokenizerMapper extends Mapper<Object, Text, Text, LongWritable> {

    private Text team = new Text();
    private LongWritable price;

    public void map(Object key, Text value, Mapper.Context context) throws IOException, InterruptedException {
        String line = value.toString();
        String[] columns = line.split(",");
        team.set(columns[13]);
        price = new LongWritable(Long.parseLong(columns[4]));
        context.write(team,price);
    }
}