package dev.kafka;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;

public class TwitterKafkaConsumer {

    private static final Logger LOGGER = Logger.getLogger(TwitterKafkaConsumer.class.getName());
    private ConsumerConnector consumerConnector = null;
    private final String topic = "twitter-topic11";

    public void initialize() {
        LOGGER.info("Initialization started");
        Properties props = new Properties();
        props.put("zookeeper.connect", "localhost:2181");
        props.put("group.id", "testgroup");
        props.put("zookeeper.session.timeout.ms", "400");
        props.put("zookeeper.sync.time.ms", "10");
        props.put("auto.commit.interval.ms", "100");
        ConsumerConfig conConfig = new ConsumerConfig(props);
        consumerConnector = Consumer.createJavaConsumerConnector(conConfig);
    }

    public void consume(){
        LOGGER.info("Consumption started");
        //Key = topic name, Value = No. of threads for topic
        Map<String, Integer> topicCount = new HashMap<String, Integer>();
        topicCount.put(topic, new Integer(1));

        //ConsumerConnector creates the message stream for each topic
        Map<String, List<KafkaStream<byte[], byte[]>>> consumerStreams =
                consumerConnector.createMessageStreams(topicCount);

        // Get Kafka stream for topic 'mytopic'
        List<KafkaStream<byte[], byte[]>> kStreamList =
                consumerStreams.get(topic);

        // Iterate stream using ConsumerIterator
        for (final KafkaStream<byte[], byte[]> kStreams : kStreamList) {
            ConsumerIterator<byte[], byte[]> consumerIte = kStreams.iterator();
            while (consumerIte.hasNext())
                System.out.println("Message consumed from topic[" + topic + "] : "       +
                        new String(consumerIte.next().message()));
        }

        //Shutdown the consumer connector
        if (consumerConnector != null)   consumerConnector.shutdown();

    }

    public static void main(String[] args) throws InterruptedException {
        TwitterKafkaConsumer kafkaConsumer = new TwitterKafkaConsumer();

        // Configure Kafka consumer
        kafkaConsumer.initialize();

        // Start consumption
        kafkaConsumer.consume();

    }

}
