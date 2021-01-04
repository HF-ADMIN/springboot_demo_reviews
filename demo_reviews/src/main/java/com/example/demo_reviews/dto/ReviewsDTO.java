package com.example.demo_reviews.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class ReviewsDTO {
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Request implements TInfoDTO {
        private String cudFlag;
        private String prodCode;
        private String reviewsId;
        private String contents;
        private Integer rating;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Reviews implements TInfoDTO {
        private String reviewsId;
        private String contents;
        private Integer rating;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response implements TInfoDTO {
        private String prodCode;
        private Integer resultCode;
        private List<Reviews> reviewsList;
    }
}
