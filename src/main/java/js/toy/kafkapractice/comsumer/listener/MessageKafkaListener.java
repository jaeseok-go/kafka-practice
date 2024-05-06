package js.toy.kafkapractice.comsumer.listener;

import js.toy.kafkapractice.storage.KafkaStorage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class MessageKafkaListener {

    private final KafkaStorage consumerStorage;

    private final static String TOPIC_NAME = "message";

    private final static String GROUP_NAME = "kafka-consumer-group-00";

    @KafkaListener(
            topics = TOPIC_NAME,
            groupId = GROUP_NAME,
            containerFactory = "recordStringContainerFactory"
    )
    public void recordListener(String record) {
        log.warn("{}: {}", TOPIC_NAME, record);

        consumerStorage.save(record);
    }
}
