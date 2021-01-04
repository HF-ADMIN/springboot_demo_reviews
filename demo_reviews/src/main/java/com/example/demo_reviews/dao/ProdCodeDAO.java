package com.example.demo_reviews.dao;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
@Table(name="prod_code")
public class ProdCodeDAO {
    @Id 
    @Column(name="id", nullable=false)
    @GeneratedValue
    private Integer id;

    @Column(name="prod_code", nullable=false, length=20)
    private String prodCode;

    @Column(name="prod_name", nullable=true, length=20)
    private String prodName;

    @Column(name="create_date", nullable=true)
    private Date createDate;
}
