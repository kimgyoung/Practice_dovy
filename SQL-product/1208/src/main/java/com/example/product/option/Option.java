package com.example.product.option;

import com.example.product.product.Product;
import com.example.product.product.ProductResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "option_tb",
        indexes = {
            @Index(name = "option_product_id_index", columnList = "product_id")
        })
public class Option {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    // 옵션 상품 이름
    @Column(length = 100, nullable = false)
    private String optionName;

    // 옵션 상품 가격
    private Long price;

    // 옵션 상품 수량
    private Long quantity;

    public Option (OptionResponse.CreateDto createDto, Product product){
        this.product = product;
        this.optionName = createDto.getOptionName();
        this.price = createDto.getPrice();
        this.quantity = createDto.getQuantity();
    }

    public void updateOption(String optionName, Long price, Product product, Long quantity){
        this.optionName = optionName;
        this.price = price;
        this.product = product;
        this.quantity = quantity;
    }

}
