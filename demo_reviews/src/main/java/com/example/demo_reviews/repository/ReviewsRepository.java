package com.example.demo_reviews.repository;

import java.util.List;

import com.example.demo_reviews.dao.ReviewsDAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ReviewsRepository extends JpaRepository<ReviewsDAO, Long> {
    // prodCode를 입력받아서 해당 제품의 review내용을 다 가져온다.
    @Query(nativeQuery = true, value = "select r.id, r.prod_code, r.reviews_id, r.contents, r.create_date, r.modi_date, p.prod_name from reviews r, product_code p where r.prod_code = p.prod_code and r.prod_code = ?1")
    List<ReviewsDAO> findByProdCode(String prodCode);

    // @Query(nativeQuery = true, value = "select * from reviews where prod_code = :#{#dao.prodCode}")
    // List<ReviewsDAO> findByProdCode(String prodCode);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value= "update reviews set contents = :#{#dao.contents}, modi_date = :#{#dao.modiDate} where reviews_id = :#{#dao.reviewsId}")
    Integer update(@Param("dao") ReviewsDAO dao);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value= "insert into reviews(prod_code, reviews_id, contents, create_date) values(:#{#dao.prodCode}, :#{#dao.reviewsId}, :#{#dao.contents}, :#{#dao.createDate})")
    Integer insert(@Param("dao") ReviewsDAO dao);


}
