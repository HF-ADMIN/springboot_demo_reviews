package com.example.demo_reviews.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.demo_reviews.dao.ReviewsDAO;
import com.example.demo_reviews.dto.ReviewsDTO;
import com.example.demo_reviews.repository.ReviewsRepository;
import com.example.demo_reviews.util.CommUtil;
import com.example.demo_reviews.util.ServiceUtil;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

/**
 * @className ReviewsService
 * @mehtod getReviewsInfo, postReviewsInfo
 * @description 아래 예제는 Employee 정보를 관리하는 서비스 Service 입니다.
 */
@Service
public class ReviewsService {
    
    Logger logger = LoggerFactory.getLogger(ReviewsService.class);

    @Autowired
    ReviewsRepository repository;

    /**
     * @methodName  getReviewsInfo
     * @return      ReviewsDTO.Response
     * @throws      Exception
     * @description ReviewsInfo 정보를 가져오는 Mehtod 입니다.
     */
    public ReviewsDTO.Response getReviewsInfo(HttpHeaders requestHeader, String prodCode) throws Exception {
        ReviewsDTO.Response response = new ReviewsDTO.Response();

        try {
            // Reviews Table에서 해당 prodCode의 정보를 모두 가져와서 Ratings Service에서 가져온 rating 정보와 함께 뿌려준다.
            List<ReviewsDTO.Reviews> reviewsList = new ArrayList<>();
            for(ReviewsDAO dao : repository.findByProdCode(prodCode)) {
                ReviewsDTO.Reviews reviews = new ReviewsDTO.Reviews();
                System.out.println("                                      dao : " + dao.toString());

                // Rating Service를 Call
                String baseURL = ServiceUtil.RATINGS_URI + "/" + ServiceUtil.RATINGS_SERVICE + "?reviewsId=" + dao.getReviewsId();
                final HttpEntity<String> requestEntity = new HttpEntity<String>(requestHeader);
                String httpMethod = "GET";

                // 테스트를 위해 잠시 막아놓음
                // JSONObject ratingResponse = ServiceUtil.callRemoteService(baseURL, requestEntity, httpMethod);

                

                reviews.setReviewsId(dao.getReviewsId());
                reviews.setContents(dao.getContents());
                // 테스트를 위해 잠시 막아놓음
                // reviews.setRating((Integer)ratingResponse.get("rating"));
                reviews.setRating(5);
                System.out.println("                                      reviews : " + reviews);

                reviewsList.add(reviews);
            }

            response.setProdCode(prodCode);
            response.setReviewsList(reviewsList);
            response.setResultCode(200);
        }catch(Exception e) {
            e.printStackTrace();
            throw e;
        }

        return response;
    } 

    /**
     * @methodName  postReviewsInfo
     * @return      ReviewsDTO.Response
     * @throws      Exception
     * @description ReviewsInfo를 저장하는 Mehtod 입니다.
     */
    public ReviewsDTO.Response postReviewsInfo(HttpHeaders requestHeader, ReviewsDTO.Request request) throws Exception {
        ReviewsDTO.Response response = new ReviewsDTO.Response();
        System.out.println("                               request.prodCode : " + request.getProdCode());

        try {
            // request를 입력받아서 reviews Table에 데이터를 저장한다.
            if("C".equals(request.getCudFlag())) {
                request.setReviewsId(CommUtil.genUUID());
                ReviewsDAO entity = new ReviewsDAO();
                entity.setProdCode(request.getProdCode());
                entity.setReviewsId(request.getReviewsId());
                entity.setContents(request.getContents());
                entity.setCreateDate(CommUtil.getCurrentDate());

                System.out.println("                               entity : " + entity.toString());
                Integer result = repository.insert(entity);
                logger.info("=====================> [ReviewsService / postDetailsInfo] Insert Result : " + result);
            } else if("U".equals(request.getCudFlag())) { 
                ReviewsDAO entity = new ReviewsDAO();
                entity.setReviewsId(request.getReviewsId());
                entity.setContents(request.getContents());
                entity.setModiDate(CommUtil.getCurrentDate());

                Integer result = repository.update(entity);
                logger.info("=====================> [ReviewsService / postDetailsInfo] Update Result : " + result);
            }

            // 테스트를 위해 잠시 막아놓음
            // request를 입력받아서 Rating Service를 POST 호출한다.
            // String baseURL = ServiceUtil.RATINGS_URI + "/" + ServiceUtil.RATINGS_SERVICE;

            // Map<String, Object> req_payload = new HashMap<>();
            // req_payload.put("cudFlag", request.getCudFlag());
            // req_payload.put("reviewsId", request.getReviewsId());
            // req_payload.put("rating", request.getRating());
            // final HttpEntity<?> httpEntity = new HttpEntity<>(req_payload, requestHeader);
            // String httpMethod = "POST";

            // JSONObject callResponse = ServiceUtil.callRemoteService(baseURL, httpEntity, httpMethod);

            // response.setResultCode((Integer)callResponse.get("resultCode"));

        }catch(Exception e) {
            e.printStackTrace();
            throw e;
        }

        // test
        response.setResultCode(200);
        return response;
    } 

}
