package dev.franklindeoliveira.ecommerce;

import java.util.Map;

public class FraudDetectorService {
  public static void main(String[] args) {
    try (var kafkaService = new KafkaService(FraudDetectorService.class.getSimpleName(),
            "ECOMMERCE_NEW_ORDER",
            new ParserConsumeRecordFraudDetector(),
            Order.class,
            Map.of())) {
      kafkaService.run();
    }
  }
}
