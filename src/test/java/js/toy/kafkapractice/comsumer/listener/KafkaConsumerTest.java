package js.toy.kafkapractice.comsumer.listener;

import js.toy.kafkapractice.producer.KafkaProducer;
import js.toy.kafkapractice.storage.ConsumerStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class KafkaConsumerTest {

    @Autowired
    private KafkaProducer messageKafkaProducer;

    @Autowired
    private ConsumerStorage consumerStorage;

    @BeforeEach
    void initialize() {
        consumerStorage.initialize();
    }

    @Test
    void 메세지를_발행하면_구독해서_처리() throws InterruptedException {
        // given
        String message = "test message for pub/sub";

        // when
        messageKafkaProducer.produce(message);
        Thread.sleep(1000L);

        // then
        assertEquals(consumerStorage.get(0).toString(), message);
    }
}