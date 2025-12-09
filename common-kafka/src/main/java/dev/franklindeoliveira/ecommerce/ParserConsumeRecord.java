package dev.franklindeoliveira.ecommerce;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public interface ParserConsumeRecord<T> {
  void parse(ConsumerRecord<String, T> record);
}
