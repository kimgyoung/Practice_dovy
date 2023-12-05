package com.example.product.option;

import com.example.product.product.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class OptionResponse {

    @NoArgsConstructor
    @Setter
    @Getter
    public static class FindByProductIdDto{
        private Long id;
        private Long productId;
        private String optionName;
        private Long price;
        private Long quantity;

        public FindByProductIdDto(Option option){
            this.id = option.getId();
            this.productId = option.getProduct().getId();
            this.optionName = option.getOptionName();
            this.price = option.getPrice();
            this.quantity = option.getQuantity();
        }
    }

    @NoArgsConstructor
    @Setter
    @Getter
    public static class FindAllDto{
        private Long id;
        private Long productId;
        private String optionName;
        private Long price;
        private Long quantity;

        public FindAllDto(Option option){
            this.id = option.getId();
            this.productId = option.getProduct().getId();
            this.optionName = option.getOptionName();
            this.price = option.getPrice();
            this.quantity = option.getQuantity();
        }
    }

    @NoArgsConstructor
    @Setter
    @Getter
    public static class CreateDto{
        private Long productId;
        private String optionName;
        private Long price;
        private Long quantity;

        public CreateDto(Option option){
            this.productId = option.getProduct().getId();
            this.optionName = option.getOptionName();
            this.price = option.getPrice();
            this.quantity = option.getQuantity();
        }
    }
    @NoArgsConstructor
    @Setter
    @Getter
    public static class UpdateDto{
        private Long id;
        private Long productId;
        private String optionName;
        private Long price;
        private Long quantity;

        public UpdateDto(Option option){
            this.id = option.getId();
            this.productId = option.getProduct().getId();
            this.optionName = option.getOptionName();
            this.price = option.getPrice();
            this.quantity = option.getQuantity();
        }
    }
}
