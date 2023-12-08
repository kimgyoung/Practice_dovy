package com.example.product.product;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class ProductFileDto {
    private String filePath;

    private String fileName;

    private String fileType;

    private Long fileSize;

    private Long productId;

    private String uuid;

    public ProductFile toEntity(Product product){
        return ProductFile.builder()
                .filePath(filePath)
                .fileName(fileName)
                .fileType(fileType)
                .fileSize(fileSize)
                .uuid(uuid)
                .product(product)
                .build();
    }

}
