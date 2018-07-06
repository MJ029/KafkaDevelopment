package dev.kafka;

import com.google.common.collect.Lists;
import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Client;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javautils.ConstantsUtils;

public class TwitterKafkaProducer {
    private static final String topic = "twitter-topic11";

    private static void run(String consumerKey, String consumerSecret,
                           String token, String secret) {

        Properties properties = new Properties();
        properties.put("metadata.broker.list", "localhost:9092");
        properties.put("serializer.class", "kafka.serializer.StringEncoder");
        ProducerConfig producerConfig = new ProducerConfig(properties);
        kafka.javaapi.producer.Producer<String, String> producer = new kafka.javaapi.producer.Producer<String, String>(
                producerConfig);

        BlockingQueue<String> queue = new LinkedBlockingQueue<>(100000);
        StatusesFilterEndpoint endpoint = new StatusesFilterEndpoint();
        // add some track terms
        endpoint.trackTerms(Lists.newArrayList("twitterapi",
                "#AAPSweep"));

        Authentication auth = new OAuth1(consumerKey, consumerSecret, token,
                secret);
        // Authentication auth = new BasicAuth(username, password);

        // Create a new BasicClient. By default gzip is enabled.
        Client client = new ClientBuilder().hosts(Constants.STREAM_HOST)
                .endpoint(endpoint).authentication(auth)
                .processor(new StringDelimitedProcessor(queue)).build();

        // Establish a connection
        client.connect();

        // Do whatever needs to be done with messages
        for (int msgRead = 0; msgRead < 1000; msgRead++) {
            KeyedMessage<String, String> message = null;
            try {
                message = new KeyedMessage<String, String>(topic, queue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            producer.send(message);
        }
        producer.close();
        client.stop();

    }

    public static void main(String[] args) {
        String consumerKey= ConstantsUtils.CONSUMER_KEY_KEY;
        String consumerSecret=ConstantsUtils.CONSUMER_SECRET_KEY;
        String accessToken=ConstantsUtils.ACCESS_TOKEN_KEY;
        String accessTokenSecret=ConstantsUtils.ACCESS_TOKEN_SECRET_KEY;

        try {
            TwitterKafkaProducer.run(consumerKey, consumerSecret, accessToken, accessTokenSecret);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
