package com.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang3.StringUtils;

public class App {

    public static String greet(String name) {
        if (StringUtils.isBlank(name)) {
            return "Hello, World!";
        }
        return "Hello, " + StringUtils.capitalize(name.trim()) + "!";
    }

    public static String toJson(String name) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(ImmutableMap.of("message", greet(name)));
    }

    public static void main(String[] args) throws JsonProcessingException {
        String name = args.length > 0 ? args[0] : "";
        System.out.println(toJson(name));
    }
}
