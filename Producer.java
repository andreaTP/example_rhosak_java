///usr/bin/env jbang "$0" "$@" ; exit $?

//DEPS org.apache.kafka:kafka-streams:3.3.1
//DEPS org.slf4j:slf4j-api:2.0.4
//DEPS org.slf4j:slf4j-simple:2.0.4

//SOURCES KafkaConfig.java

//JAVA_OPTIONS -Dorg.slf4j.simpleLogger.defaultLogLevel=warn

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.KafkaProducer;

public class Producer {
  public static void main(String[] args) {
    var message = (args.length > 0) ? "Hello, " + args[0] + "!" : "Hello world!";

    try (KafkaProducer<String,String> producer = new KafkaProducer<String,String>(KafkaConfig.properties())) {
      System.out.println("Sending message: \"" + message + "\" on topic \"" + KafkaConfig.topic + "\"");
      producer.send(new ProducerRecord<>(KafkaConfig.topic, message));
      producer.flush();
    }
  }
}
