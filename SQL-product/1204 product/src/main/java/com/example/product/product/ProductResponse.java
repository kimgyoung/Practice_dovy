package com.example.product.product;

import com.example.product.option.Option;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.stream.Collectors;

//@NoArgsConstructor
@ToString
@Setter
@Getter
public class ProductResponse {

    @Setter
    @Getter
    public static class FindAllDto{

        private Long id;

        private String productName;

        private String description;

        private String image;

        private int price;

        //private int quantity; // 수량은 옵션에 들어갈 것 이므로 나중에 삭제 가능성

        public FindAllDto(Product product) {
            this.id = product.getId();
            this.productName = product.getProductName();
            this.description = product.getDescription();
            this.image = product.getImage();
            this.price = product.getPrice();
        }
    }

    @Setter
    @Getter
    public static class FindByIdDto{

        private Long id;

        private String productName;

        private String description;

        private String image;

        private int price;

        private List<OptionDto> optionList;

        //private int quantity; // 수량은 옵션에 들어갈 것 이므로 나중에 삭제 가능성

        public FindByIdDto(Product product, List<Option> optionList) {
            this.id = product.getId();
            this.productName = product.getProductName();
            this.description = product.getDescription();
            this.image = product.getImage();
            this.price = product.getPrice();
            this.optionList = optionList.stream().map(OptionDto::new)
                    .collect(Collectors.toList());
        }
    }

    // product 금액을 보고 왔지만 -> 실 결제 금액? 할인 전 금액? 옵션의 가격

    @Setter
    @Getter
    public static class OptionDto{
        private Long id;
        private String optionName;
        private Long price;
        private Long quantity;

        public OptionDto(Option option){
            this.id = option.getId();
            this.optionName = option.getOptionName();
            this.price = option.getPrice();
            this.quantity = option.getQuantity();
        }
    }

    @NoArgsConstructor
    @Setter
    @Getter
    public static class CreateDto{
        //private Long id;

        private String productName;

        private String description;

        private String image;

        private int price;

        public CreateDto(Product product){
            //this.id = product.getId();
            this.productName = product.getProductName();
            this.description = product.getDescription();
            this.image = product.getImage();
            this.price = product.getPrice();
        }
    }

    @NoArgsConstructor
    @Setter
    @Getter
    public static class UpdateDto{
        private Long id;

        private String productName;

        private String description;

        private String image;

        private int price;

        public UpdateDto(Product product){
            this.id = product.getId();
            this.productName = product.getProductName();
            this.description = product.getDescription();
            this.image = product.getImage();
            this.price = product.getPrice();
        }
    }
}



