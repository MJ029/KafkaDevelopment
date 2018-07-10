package kafkaAPIexamples;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Arrays;
import java.util.Collections;
import java.util.Properties;

public class SimpleKafkaConsumer {
    // Topic Name Available at Producer level - Must be private
    private static final String topic = "SimpleKafkaProducerExample";

    // Run methods to trigger the Job
    private static void run(){
        Properties props = new Properties();
        //props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092,localhost:9093,localhost:9094");
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id","SimpleConsumers");
        props.put("enable.auto.commit","true");
        props.put("auto.commit.interval.ms","10");
        props.put("key.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList(topic));
        //consumer.subscribe(Collections.singletonList(topic));
        System.out.println("Subscription completed successfully");
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);
            //System.out.println("Consumer Record received " + records.count());
            for (ConsumerRecord<String, String> record: records)
                System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
        }
    }

    public static void main(String[] args) {
        SimpleKafkaConsumer consumer = new SimpleKafkaConsumer();
            consumer.run(); //calling the Run method to Execute the Code
    }
}
