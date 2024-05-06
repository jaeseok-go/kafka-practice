package js.toy.kafkapractice.producer;

import js.toy.kafkapractice.storage.KafkaStorage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
class MessageKafkaProducerTest {

    @Autowired
    private KafkaProducer messageKafkaProducer;

    @Autowired
    private KafkaStorage producerStorage;

    @BeforeEach
    void initializeStorage() {
        producerStorage.initialize();;
    }

    @Test
    void 카프카에_메세지를_전송한다() throws InterruptedException {
        // given
        String testMessage = "test message";

        // when
        messageKafkaProducer.produce(testMessage);
        Thread.sleep(1000L);

        // then
        Object data = producerStorage.get(0);
        Assertions.assertEquals(testMessage, (String) data);
    }

    @Test
    void 멀티스레드_환경에서_메세지를_전송한다() throws InterruptedException {
        // given
        String testMessagePrefix = "test message";

        int requestCount = 100;
        int activeThreadCount = 30;
        ExecutorService executorService = Executors.newFixedThreadPool(activeThreadCount);

        // when
        for (int i = 0; i < requestCount; i++) {
            String message = testMessagePrefix + i;
            executorService.submit(() -> {
                messageKafkaProducer.produce(message);
            });
        }
        Thread.sleep(1000);

        // then
        for (int offset = 0; offset < requestCount; offset++) {
            Object data = producerStorage.get(offset);
            Assertions.assertTrue(((String) data).startsWith(testMessagePrefix));
        }
    }
}