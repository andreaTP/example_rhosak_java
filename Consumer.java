///usr/bin/env jbang "$0" "$@" ; exit $?

//DEPS org.apache.kafka:kafka-streams:3.3.1
//DEPS org.slf4j:slf4j-api:2.0.4
//DEPS org.slf4j:slf4j-simple:2.0.4

//SOURCES KafkaConfig.java

//JAVA_OPTIONS -Dorg.slf4j.simpleLogger.defaultLogLevel=warn

import java.util.Arrays;
import java.time.Duration;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;

public class Consumer {
    public static void main(String[] args) {
        KafkaConsumer<String,String> consumer = new KafkaConsumer<String,String>(KafkaConfig.properties());
        consumer.subscribe(Arrays.asList(KafkaConfig.topic));
        while (true) {
            for(ConsumerRecord<String,String> record: consumer.poll(Duration.ofMillis(200))) {
                System.out.println("Message received:");
                System.out.println("    Key: "+ record.key() + ", Value: \"" +record.value() + "\"");
                System.out.println("    Partition: " + record.partition()+", Offset: "+record.offset());
            }
        }
    }
}
