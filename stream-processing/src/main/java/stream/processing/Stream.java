package stream.processing;

import org.apache.spark.SparkConf;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import scala.Tuple2;

import java.util.Arrays;
import java.util.List;

public class Stream {
    public static void main(String[] args) throws InterruptedException {
        SparkConf conf = new SparkConf()
                .setAppName("Squad Price");
        JavaStreamingContext jssc =
                new JavaStreamingContext(conf, Durations.seconds(1));

        JavaReceiverInputDStream<String> lines =
                jssc.socketTextStream("192.168.100.159", 9999);

        JavaPairDStream<String, Long> pairs =
                lines.mapToPair(line -> {
                    List<String> columns = Arrays.asList(line.split(","));
                    return new Tuple2<>("Squad Price",Long.parseLong(columns.get(4)));
                });
        JavaPairDStream<String, Long> totalPriceCount =
                pairs.reduceByKey(Long::sum);

        totalPriceCount.print();
        jssc.start();
        jssc.awaitTermination();
    }
}