package configs;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.spark.SparkConf;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka010.ConsumerStrategies;
import org.apache.spark.streaming.kafka010.KafkaUtils;
import org.apache.spark.streaming.kafka010.LocationStrategies;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scala.Serializable;

import java.io.IOException;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class StreamingContext implements Serializable {

    private static final JavaStreamingContext javaStreamingContext = new JavaStreamingContext(new SparkConf().setMaster("local[2]").setAppName("SparkStreaming"), Durations.seconds(2));

    private static final Map<String, Object> kafkaParams = new HashMap<>();

    public static JavaInputDStream<ConsumerRecord<String, String>> javaInputDStream(){
        kafkaParams.put("bootstrap.servers", "localhost:9092");
        kafkaParams.put("key.deserializer", StringDeserializer.class);
        kafkaParams.put("value.deserializer", StringDeserializer.class);
        kafkaParams.put("group.id", "spark_consumer_group");
        kafkaParams.put("auto.offset.reset", "latest");
        kafkaParams.put("enable.auto.commit", false);
        return KafkaUtils.createDirectStream(
            javaStreamingContext,
                LocationStrategies.PreferConsistent(),
                ConsumerStrategies.Subscribe(Collections.singleton("wikimedia.recentchange"), kafkaParams)
        );
    }

    public static JavaStreamingContext getJavaStreamingContext() {
        return javaStreamingContext;
    }

    public static final Logger log = LoggerFactory.getLogger(StreamingContext.class.getSimpleName());

    public static boolean isBot(ConsumerRecord<String, String> recors) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(recors.value());
        return node.get("bot").asBoolean();
    }
    public static boolean isHuman(ConsumerRecord<String, String> record) throws IOException {
        return !isBot(record);
    }
}
