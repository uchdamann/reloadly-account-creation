package com.reloadly.devops.utilities;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonBuilder {
	public static <T> T toClass(String json, Class<T> clazz) {
		log.info("---->>>> Start parsing JSON to object");
		
		T t = null;
		
		ObjectMapper objectMapper = new ObjectMapper();
		
		try {
			t = objectMapper.readValue(json, clazz);
		} catch (JsonProcessingException e) {
			log.info("---->>>> Error: JSON parsing failure");
		}
		
		return t;
	}
	
	public static <T> T toClassTypeReference(String json, TypeReference<T> type) {
        T target = null;
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            target = objectMapper.readValue(json, type);
        } catch (IOException e) {
        	log.info("---->>>> Error: JSON parsing failure");
        }
        return target;
    }
}