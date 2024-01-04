package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.ArrayList;

public interface Listener {
    void consume(String message, String topicName) throws JsonProcessingException;
}
