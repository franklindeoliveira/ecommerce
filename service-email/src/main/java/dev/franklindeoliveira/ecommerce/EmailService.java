package dev.franklindeoliveira.ecommerce;

import java.util.Map;

public class EmailService {
  public static void main(String[] args) {
    try (var kafkaService = new KafkaService(EmailService.class.getSimpleName(),
            "ECOMMERCE_SEND_EMAIL",
            new ParserConsumeRecordEmail(),
            String.class,
            Map.of())) {
      kafkaService.run();
    }
  }
}
