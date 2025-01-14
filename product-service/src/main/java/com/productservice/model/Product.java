package com.productservice.model;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("product")
@Data
public class Product {
    @Id
    private Long id;
    @Column
    private String name;
    @Column
    private Long userId;
}
