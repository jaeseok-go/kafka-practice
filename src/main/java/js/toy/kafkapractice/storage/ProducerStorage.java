package js.toy.kafkapractice.storage;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ProducerStorage implements KafkaStorage {
    private Map<Integer, Object> storeMap = new HashMap<>();

    private int offset = 0;

    @Override
    public void initialize() {
        storeMap = new HashMap<>();
        offset = 0;
    }

    @Override
    public synchronized void save(Object data) {
        storeMap.put(offset++, data);
    }

    @Override
    public Object get(int offset) {
        return storeMap.get(offset);
    }
}
