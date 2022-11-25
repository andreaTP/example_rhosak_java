
import java.util.*;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

class KafkaConfig {

  static public String topic= "prices";

  static Properties properties() {
    var properties= new Properties();

    var kafkaBootstrapServers = System.getenv("KAFKA_BOOTSTRAP_SERVERS");
    if (kafkaBootstrapServers == null) {
      throw new RuntimeException("Please provide the configuration in the env var 'KAFKA_BOOTSTRAP_SERVERS'");
    }

    properties.setProperty("bootstrap.servers", kafkaBootstrapServers);
    properties.setProperty("security.protocol", "SASL_SSL");
    properties.setProperty("sasl.mechanism", "OAUTHBEARER");

    properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
    properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
    properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "test_group");
    properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

    properties.put(ProducerConfig.ACKS_CONFIG, "all");
    properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
    properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
    

    var kafkaClientId = System.getenv("KAFKA_CLIENT_ID");
    var kafkaClientSecret = System.getenv("KAFKA_CLIENT_SECRET");
    if (kafkaClientId == null || kafkaClientSecret == null) {
      throw new RuntimeException("Please provide the configuration in the env vars 'KAFKA_CLIENT_ID' and 'KAFKA_CLIENT_SECRET'");
    }

    properties.setProperty("sasl.jaas.config", "org.apache.kafka.common.security.oauthbearer.OAuthBearerLoginModule required clientId=\"" + kafkaClientId + "\" clientSecret=\"" + kafkaClientSecret + "\" oauth.token.endpoint.uri=\"https://sso.redhat.com/auth/realms/redhat-external/protocol/openid-connect/token\";");

    properties.setProperty("sasl.login.callback.handler.class", "org.apache.kafka.common.security.oauthbearer.secured.OAuthBearerLoginCallbackHandler");
    properties.setProperty("sasl.oauthbearer.token.endpoint.url", "https://sso.redhat.com/auth/realms/redhat-external/protocol/openid-connect/token");
    properties.setProperty("sasl.oauthbearer.scope.claim.name", "api.iam.service_accounts");

    return properties;
  }

}