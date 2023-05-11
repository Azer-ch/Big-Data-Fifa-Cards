package fifa.player.classifier;

import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.mapreduce.TableReducer;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class HadoopReducer extends Reducer<Text, Text, Text,Text> {
    @Override
    public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        for (Text value : values) {
            String[] attributes = value.toString().split(",");
            String name = attributes[0];
            int rating = Integer.parseInt(attributes[1]);
            int potential = Integer.parseInt(attributes[2]);
            int age = Integer.parseInt(attributes[3]);
            Long marketValue = Long.parseLong(attributes[4]);
            FifaCard fifaCard = new FifaCard(name, rating, potential, age, marketValue);
            fifaCard.computeTier();
            //Put put = new Put(Bytes.toBytes(fifaCard.playerID));
            //put.addColumn(Bytes.toBytes("player"), Bytes.toBytes("name"), Bytes.toBytes(fifaCard.name));
            //put.addColumn(Bytes.toBytes("player"), Bytes.toBytes("age"), Bytes.toBytes(fifaCard.age));
            //put.addColumn(Bytes.toBytes("attributes"), Bytes.toBytes("rating"), Bytes.toBytes(fifaCard.rating));
            //put.addColumn(Bytes.toBytes("attributes"), Bytes.toBytes("potential"), Bytes.toBytes(fifaCard.potential));
            //put.addColumn(Bytes.toBytes("attributes"), Bytes.toBytes("marketValue"), Bytes.toBytes(fifaCard.marketValue));
            //put.addColumn(Bytes.toBytes("attributes"), Bytes.toBytes("tier"), Bytes.toBytes(fifaCard.tier));
            context.write(key, fifaCard.toText());
        }
    }
}