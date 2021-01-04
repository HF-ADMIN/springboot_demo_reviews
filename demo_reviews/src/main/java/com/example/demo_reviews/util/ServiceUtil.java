package com.example.demo_reviews.util;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class ServiceUtil {
    public static final String DETAILS_URI = "192.168.188.156:30413";
    public static final String DETAILS_SERVICE = "DetailsInfo";

    public static final String REVIEWS_URI = "192.168.188.150:30366";
    public static final String REVIEWS_SERVICE = "ReviewsInfo";

    public static final String RATINGS_URI = "192.168.188.150:30293";
    public static final String RATINGS_SERVICE = "RatingsInfo";

    private static Logger logger = LoggerFactory.getLogger(ServiceUtil.class);

    @Autowired
    private static RestTemplate restTemplate;

    // @Autowired
    // public ServiceUtil(RestTemplate restTemplate) {
    //     this.restTemplate = restTemplate;
    // }

    /**
     * @methodName  callRemoteService
     * @param       String baseURL, HttpEntity<?> requestEntity, String httpMethod
     * @return      JSONObject
     * @throws      Exception
     * @description baseURL에 CALL 하는 Mehtod입니다.
     */
    public static JSONObject callRemoteService(String baseURL, HttpEntity<?> requestEntity, String httpMethod) throws Exception {
        logger.info("=====================> [ServiceUtil / callRemoteService] baseURL : " + baseURL);
        JSONObject jsonObject = null;

        ResponseEntity<String> serviceResponse = null;

        if("GET".equals(httpMethod)) {
            serviceResponse = restTemplate.exchange(
                baseURL, HttpMethod.GET, requestEntity, String.class);  
        } else if("POST".equals(httpMethod)) {
            serviceResponse = restTemplate.postForEntity(
                baseURL, requestEntity, String.class);
        }

        if(serviceResponse != null && serviceResponse.getBody() != null) {
            logger.info(
                    "=====================> [ServiceUtil / callRemoteService] callResponse :" + serviceResponse.getBody());

            // From String to JSONOBject
            JSONParser jsonParser = new JSONParser();
            String jsonString = String.valueOf(serviceResponse.getBody());
            jsonObject = (JSONObject)jsonParser.parse(jsonString);
        }

        logger.info("=====================> [ServiceUtil / callRemoteService] Return jsonObject : " + jsonObject);
        return jsonObject;
    }

}
