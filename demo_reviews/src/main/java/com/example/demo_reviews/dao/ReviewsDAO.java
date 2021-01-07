package com.example.demo_reviews.dao;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reviews")
public class ReviewsDAO {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue
    private Long id;

    @Column(name = "prod_code", nullable = false, length = 20)
    private String prodCode;

    @Column(name = "reviews_id", nullable = true, length = 32)
    private String reviewsId;

    @Column(name = "contents", nullable = true, length = 100)
    private String contents;

    @Column(name = "create_date", nullable = true)
    private Date createDate;

    @Column(name = "modi_date", nullable = true)
    private Date modiDate;

    @Column(name="prod_name", nullable=true, length=20)
    private String prodName;

    // @ManyToOne(cascade = { CascadeType.ALL })
    // @JoinColumn(name = "prod_code", insertable = false, updatable = false)
    // private ProdCodeDAO prodCodeDAO;

    // public ProdCodeDAO getProdCodeDAO() {
    //     return prodCodeDAO;
    // }

    // public void setProdCodeDAO(ProdCodeDAO prodCodeDAO) {
    //     this.prodCodeDAO = prodCodeDAO;
    // }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        // return String.valueOf("prod_code : " + this.prodCode + ", reviews_id : " + this.reviewsId + ", contents : " + this.contents + ", create_date : " + this.createDate);
        return String.valueOf("        DAO : " + this.prodCode + ", " + this.reviewsId + ", " + this.contents + ", " + this.createDate + ", " + this.prodName);
    }

    
}
