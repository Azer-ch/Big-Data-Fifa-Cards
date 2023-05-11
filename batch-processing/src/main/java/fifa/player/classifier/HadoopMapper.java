package fifa.player.classifier;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class HadoopMapper extends Mapper<Object, Text, Text, Text> {
    public void map(Object key, Text value, Mapper.Context context) throws IOException, InterruptedException {
        String line = value.toString();
        System.out.println(line);
        String[] attributes = line.split(",");
        FifaCard fifaCard = new FifaCard(attributes[1], Integer.parseInt(attributes[2]), Integer.parseInt(attributes[3]), Integer.parseInt(attributes[8]), Long.parseLong(attributes[4]));
        context.write(fifaCard.getPlayerID(), fifaCard.toText());
    }

}