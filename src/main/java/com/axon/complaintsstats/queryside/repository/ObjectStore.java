package com.axon.complaintsstats.queryside.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;

import java.util.HashMap;
import java.util.Map;

public class ObjectStore {

    private static final Map<String,Object> ObjectStores = new HashMap<>();


    public static Map<String, Object> getObjectStores() {
        return ObjectStores;
    }

    public static void save(String key,Object value){
        getObjectStores().put(key,value);
    }

    public static Object getValue(String key){
        return getObjectStores().getOrDefault(key,null);
    }
}
