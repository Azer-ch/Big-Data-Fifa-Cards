package spark.consumer;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.WriteApi;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import io.github.cdimascio.dotenv.Dotenv;
import org.apache.spark.SparkConf;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.*;
import org.apache.spark.streaming.kafka.KafkaUtils;
import scala.Tuple2;

import java.util.*;

public class FootballStatsConsumer {
    public static void main(String[] args) throws InterruptedException {
        Dotenv dotenv = Dotenv.load();
        String influxUrl = "http://172.18.0.2:3005";
        String influxToken = dotenv.get("INFLUX_TOKEN");
        String influxOrg = "azer";
        String influxBucket = "fifa-cards";
        String measurement = "player_stats";
        SparkConf sparkConf = new SparkConf().setAppName("SparkKafkaWordCount");
        JavaStreamingContext jssc = new JavaStreamingContext(sparkConf,
                new Duration(2000));

        Map<String, Integer> topicMap = new HashMap<>();
        topicMap.put("football-stats", 1);

        JavaPairReceiverInputDStream<String, String> messages =
                KafkaUtils.createStream(jssc, "localhost:2182", "football-stats-group-id", topicMap);

        JavaPairDStream<String, PlayerStats> footballStats = messages.mapToPair(record -> {
            String[] fields = record._2.split(",");
            String playerName = fields[0];
            int playerAge = Integer.parseInt(fields[3]);
            int gamesPlayed = Integer.parseInt(fields[4]);
            int goals = Integer.parseInt(fields[7]);
            int assists = Integer.parseInt(fields[8]);
            double goalsPerGame = (double) goals / gamesPlayed;
            double assistsPerGame = (double) assists / gamesPlayed;
            PlayerStats playerStats = new PlayerStats(playerName, playerAge, gamesPlayed, goals, assists, goalsPerGame, assistsPerGame);
            System.out.println(playerStats);
            return new Tuple2<>(playerName, playerStats);
        });
        footballStats.foreachRDD(rdd -> {
            rdd.foreachPartition(partitionOfRecords -> {
                InfluxDBClient influxDBClient = InfluxDBClientFactory.create(influxUrl, influxToken.toCharArray(), influxOrg, influxBucket);

                partitionOfRecords.forEachRemaining(record -> {
                    try (WriteApi writeApi = influxDBClient.getWriteApi()) {
                        Point point = Point.measurement(measurement)
                                .addTag("play_name", record._2().playerName)
                                .addField("player_age", record._2().playerAge)
                                .addField("players_goals", record._2().goals)
                                .addField("players_assists", record._2().assists)
                                .addField("games_played", record._2().gamesPlayed)
                                .addField("goals_per_game", record._2().goalsPerGame)
                                .addField("assists_per_game", record._2().assistsPerGame)
                                .time(System.currentTimeMillis(), WritePrecision.MS);
                        writeApi.writePoint(point);
                    }
                });
                influxDBClient.close();
            });
        });
        jssc.start();
        jssc.awaitTermination();
    }
}
