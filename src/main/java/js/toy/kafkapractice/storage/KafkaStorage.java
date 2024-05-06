package js.toy.kafkapractice.storage;

public interface KafkaStorage {

    void initialize();

    void save(Object data);

    Object get(int offset);
}
