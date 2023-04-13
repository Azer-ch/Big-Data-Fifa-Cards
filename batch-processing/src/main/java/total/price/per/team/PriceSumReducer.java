package total.price.per.team;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class PriceSumReducer extends Reducer<Text, LongWritable, Text, LongWritable> {

    private LongWritable totalPrice = new LongWritable();

    public void reduce(Text key, Iterable<LongWritable> values, Context context) throws IOException, InterruptedException {
        long priceSum = 0;
        for (LongWritable val : values) {
            priceSum += val.get();
        }
        totalPrice.set(priceSum);
        context.write(key, totalPrice);
    }
}