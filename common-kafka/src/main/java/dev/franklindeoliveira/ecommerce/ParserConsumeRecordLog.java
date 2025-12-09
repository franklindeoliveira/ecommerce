package dev.franklindeoliveira.ecommerce;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public class ParserConsumeRecordLog<T> implements ParserConsumeRecord<T> {
  @Override
  public void parse(ConsumerRecord<String, T> record) {
    System.out.println("---------------------");
    System.out.println("LOG: " + record.topic());
    System.out.println("Message partition key: " + record.key());
    System.out.println("Message content: " + record.value());
    System.out.println("Partition topic: " + record.partition());
    System.out.println("Partition topic offset: " + record.offset());
  }
}
