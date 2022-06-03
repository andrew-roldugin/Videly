package ru.vsu.csf.group7.http.request;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public interface Mappable {
    default Map<String, Object> toMap(){
        Map<String, Object> map = new HashMap<>();

        Arrays.stream(this.getClass().getDeclaredFields()).forEach(field -> {
            field.setAccessible(true);
            try {
                map.put(field.getName(), field.get(this));
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });
        return map;
    }
}
