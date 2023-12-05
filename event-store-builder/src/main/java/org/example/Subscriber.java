package org.example;

public interface Subscriber {
    void start(FileStateEventBuilder listener,String topicName);
}
