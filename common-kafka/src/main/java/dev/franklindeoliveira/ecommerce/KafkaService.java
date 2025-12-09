package dev.franklindeoliveira.ecommerce;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.io.Closeable;
import java.time.Duration;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;
import java.util.regex.Pattern;

public class KafkaService<T> implements Closeable {
  private final KafkaConsumer<String, T> consumer;
  private final ParserConsumeRecord<T> parser;

  KafkaService(String groupId, String topic, ParserConsumeRecord parser, Class<T> type, Map<String,String> properties) {
    this(groupId, parser, type, properties);
    consumer.subscribe(Collections.singletonList(topic));
  }

  KafkaService(String groupId, Pattern compile, ParserConsumeRecordLog parser, Class<T> type, Map<String,String> properties) {
    this(groupId, parser, type, properties);
    consumer.subscribe(compile);
  }

  private KafkaService(String groupId, ParserConsumeRecord parser, Class<T> type, Map<String, String> properties) {
    this.consumer = new KafkaConsumer<>(getProperties(type, groupId, properties));
    this.parser = parser;
  }

  void run() {
    while(true) {
      var records = consumer.poll(Duration.ofMillis(100));
      if(!records.isEmpty()) {
        System.out.println("Encontrei " + records.count() + " registros");
        for(var record: records) {
          parser.parse(record);
        }
      }
    }
  }

  private Properties getProperties(Class<T> type, String groupId, Map<String, String> overrideProperties) {
    var properties = new Properties();
    properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
    properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
    properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, GsonDeserializer.class.getName());
    properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupId);
    properties.setProperty(ConsumerConfig.CLIENT_ID_CONFIG, UUID.randomUUID().toString());
    properties.setProperty(GsonDeserializer.TYPE_CONFIG, type.getName());
    properties.putAll(overrideProperties);
    return properties;
  }


  @Override
  public void close() {
    consumer.close();
  }
}
