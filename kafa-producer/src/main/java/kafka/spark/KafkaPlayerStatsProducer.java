package kafka.spark;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.Normalizer;
import java.util.Properties;
import java.util.regex.Pattern;


public class KafkaPlayerStatsProducer {
    public static void main(String[] args) throws IOException {
        System.out.println("Starting KafkaPlayerStatsProducer");
        String kafkaServer = "localhost:9092";
        String topicName = "football-stats";
        Properties props = new Properties();
        props.put("bootstrap.servers", kafkaServer);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        KafkaProducer<String, String> producer = new KafkaProducer<>(props);
        try(ServerSocket serverSocket = new ServerSocket(9997)) {
            while (true) {
                try (Socket clientSocket = serverSocket.accept()) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    String line;
                    while ((line = in.readLine()) != null) {
                        // remove all special characters
                        String parsedLine = Normalizer.normalize(line, Normalizer.Form.NFD);
                        parsedLine = parsedLine.replaceAll("[^\\p{ASCII}]", "");
                        System.out.println("Received from client: " + parsedLine);
                        ProducerRecord<String, String> record = new ProducerRecord<>(topicName, parsedLine);
                        producer.send(record);
                    }
                }
            }
        }
    }
}