package org.example.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.models.User;

public class Json {
    public static void main(String[] args) throws JsonProcessingException {
        User user = new User();
        user.setUsername("jmc");
        user.setPassword("0524");
        user.setSex("男");
        ObjectMapper om = new ObjectMapper();
        //json序列化
        String json = om.writeValueAsString(user);
        System.out.println(json);
        //json反序列化
        User user1  = om.readValue(json, new TypeReference<User>(){});
        System.out.println(user1);
    }
}
