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
    // public static final String DETAILS_URI = "192.168.188.156:30413";
    public static final String DETAILS_URI = "http://springboot-details-service.demo-tinfo.svc.cluster.local:8082";
    public static final String DETAILS_SERVICE = "detailsInfo";

    // public static final String REVIEWS_URI = "192.168.188.150:30366";
    public static final String REVIEWS_URI = "http://springboot-reviews-service.demo-tinfo.svc.cluster.local:8083";
    public static final String REVIEWS_SERVICE = "reviewsInfo";

    // public static final String RATINGS_URI = "http://tinfo-demo-rating.demo-tinfo.svc.cluster.local:5000";
    public static final String RATINGS_URI = "http://192.168.188.156:30486";
    public static final String RATINGS_SERVICE = "ratingInfo";

    private static Logger logger = LoggerFactory.getLogger(ServiceUtil.class);

    // @Autowired
    // private static RestTemplate restTemplate;

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
    public static JSONObject callRemoteService(RestTemplate restTemplate, String baseURL, HttpEntity<?> requestEntity, String httpMethod) throws Exception {
        logger.info("=====================> [ServiceUtil / callRemoteService] baseURL : " + baseURL);
        JSONObject jsonObject = null;

        ResponseEntity<String> serviceResponse = null;

        try {
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
        }catch(Exception e) {
            e.printStackTrace();
            throw e;
        }

        logger.info("=====================> [ServiceUtil / callRemoteService] Return jsonObject : " + jsonObject);
        return jsonObject;
    }

}
