package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface Listener {
    void consume(String message) throws JsonProcessingException;
}
