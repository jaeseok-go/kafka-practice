package js.toy.kafkapractice.producer;

import js.toy.kafkapractice.storage.KafkaStorage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;


@Slf4j
@RequiredArgsConstructor
@Component
public class MessageKafkaProducer implements KafkaProducer {

    private final KafkaTemplate<String, String> messageKafkaTemplate;

    private final KafkaStorage producerStorage;

    private static final String TOPIC_NAME = "message";

    @Override
    public void produce(String message) {
        messageKafkaTemplate
                .send(TOPIC_NAME, message)
                .whenComplete((result, exception) -> {
                    log.info("TOPIC={} {}: result={}",
                            TOPIC_NAME,
                            ObjectUtils.isEmpty(exception) ? "SUCCESS" : "FAILURE",
                            ObjectUtils.isEmpty(exception) ? result : exception.getMessage()
                    );

                    producerStorage.save(message);
                });
    }
}
