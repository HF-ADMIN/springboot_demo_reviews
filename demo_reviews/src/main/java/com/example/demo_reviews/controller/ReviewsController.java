package com.example.demo_reviews.controller;

import com.example.demo_reviews.dto.ReviewsDTO;
import com.example.demo_reviews.service.ReviewsService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// import io.opentracing.Tracer;

/**
 * @className ReviewsController
 * @method    getReviewsInfo, postReviewsInfo
 * @description 아래 예제는 Reviews Service 콘트롤러입니다. GET Method와 POST Method를 가지고
 *              있습니다.
 */
@CrossOrigin(origins="*")
@RestController
public class ReviewsController {

    Logger logger = LoggerFactory.getLogger(ReviewsController.class);

    @Autowired
    private ReviewsService service;

    // @Autowired
    // private Tracer tracer;

    /**
     * @methodName getReviewsInfo
     * @throws     Exception
     * @description GET Request를 받아서 전체 Productpage 정보를 조회하는 메소드
     */
    @RequestMapping(value="/reviewsInfo", method=RequestMethod.GET)
    public ResponseEntity<ReviewsDTO.Response> getReviewsInfo(@RequestHeader HttpHeaders requestHeader, @RequestParam String prodCode) throws Exception{
        ReviewsDTO.Response responseBody = null;
        try {
            responseBody = service.getReviewsInfo(requestHeader, prodCode);
        }catch(Exception e) {
            e.printStackTrace();
            throw e;
        }
        return ResponseEntity.ok().body(responseBody);
    }

    /**
     * @methodName postReviewsInfo
     * @throws     Exception
     * @description GET Request를 받아서 전체 해당 prodCode에 대한 Reviews 정보를 조회하는 메소드
     */
    @RequestMapping(value="/reviewsInfo", method=RequestMethod.POST)
    public ResponseEntity<ReviewsDTO.Response> postReviewsInfo(@RequestHeader HttpHeaders requestHeader, @RequestBody ReviewsDTO.Request request) throws Exception{
        ReviewsDTO.Response responseBody = null;
        try {
            responseBody = service.postReviewsInfo(requestHeader, request);
        }catch(Exception e) {
            e.printStackTrace();
            throw e;
        }
        return ResponseEntity.ok().body(responseBody);
    }
    
}
