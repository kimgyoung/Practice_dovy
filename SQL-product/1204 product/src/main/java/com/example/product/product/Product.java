package com.example.product.product;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class Product {

    // PK
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 상품명, 입력 값 필수
    @Column(length = 100, nullable = false)
    private String productName;

    // 상품 설명, 입력 값 필수
    @Column(length = 500, nullable = false)
    private String description;

    // 이미지 정보
    @Column(length = 100)
    private String image;

    // 가격
    private int price;

    // ProductRequest.CreateDto를 인자로 받는 생성자
    public Product(ProductResponse.CreateDto createDto) {
        this.productName = createDto.getProductName();
        this.description = createDto.getDescription();
        this.image = createDto.getImage();
        this.price = createDto.getPrice();
        // this.quantity = createDto.getQuantity();
    }

    public void updateProduct(String productName, String description, String image, int price) {
        this.productName = productName;
        this.description = description;
        this.image = image;
        this.price = price;
    }

    // 수량
    //private int quantity;

}

