package com.example.demo_reviews.test;

import java.util.HashMap;
import java.util.Map;

import com.example.demo_reviews.dto.TInfoDTO;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

public class TestCode01 {
    // @Autowired
    // public TInfoDTO getDto(String testData) {
    //     ProductpageDTO.Request request = new ProductpageDTO.Request();
    //     return request;
    // }


    public static void main(String[] args) {
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        Map<Object, Object> tempMap = new HashMap<Object, Object>();
        
        JSONObject jsonObject1 = new JSONObject();
        // jsonObject1.put("key1-1", "value1-1");
        // jsonObject1.put("key2", "value2");
        tempMap.put("key1-1", "value1-1");
        tempMap.put("key1-2", "value1-2");
        jsonObject1.putAll(tempMap);

        JSONObject jsonObject2 = new JSONObject();
        tempMap.clear();
        tempMap.put("key2-1", "value2-1");
        tempMap.put("key2-2", "value2-2");
        jsonObject2.putAll(tempMap);

        jsonArray.add(jsonObject1);
        jsonArray.add(jsonObject2);

        jsonObject.put("detailList", jsonArray);
        jsonObject.put("id", "1");

        System.out.println("                        " + jsonObject);
	}
}
