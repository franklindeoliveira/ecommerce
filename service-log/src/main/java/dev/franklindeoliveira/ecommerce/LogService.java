package dev.franklindeoliveira.ecommerce;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Map;
import java.util.Properties;
import java.util.UUID;
import java.util.regex.Pattern;

public class LogService {
  public static void main(String[] args) {
    try (var kafkaService = new KafkaService(LogService.class.getSimpleName(),
            Pattern.compile("ECOMMERCE_.*"),
            new ParserConsumeRecordLog(),
            String.class,
            Map.of(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName()))) {
      kafkaService.run();
    }
  }

  private static Properties properties() {
    var properties = new Properties();
    properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
    properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
    properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
    properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, LogService.class.getSimpleName());
    properties.setProperty(ConsumerConfig.CLIENT_ID_CONFIG, LogService.class.getSimpleName() + "-" + UUID.randomUUID().toString());
    properties.setProperty(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "1");
    return properties;
  }
}
