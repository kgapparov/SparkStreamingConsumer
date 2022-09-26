package configs;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaInputDStream;

import scala.Serializable;

public class App implements Serializable {


    public static void main(String[] args) throws InterruptedException {

        JavaInputDStream<ConsumerRecord<String, String>> javaInputDStream = StreamingContext.javaInputDStream();
        JavaDStream<ConsumerRecord<String,String>> human = javaInputDStream.filter(StreamingContext::isHuman);
        javaInputDStream.foreachRDD(rdd -> {
                    //rdd.saveAsTextFile("human_updates");
                    rdd.saveAsTextFile("hdfs://quickstart.cloudera:8020/user/cloudera/BDTProject/human_updated");
                    });
        StreamingContext.getJavaStreamingContext().start();
        StreamingContext.getJavaStreamingContext().awaitTermination();

    }
}
