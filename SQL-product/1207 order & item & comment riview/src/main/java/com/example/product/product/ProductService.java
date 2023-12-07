package com.example.product.product;

import com.example.product.error.exception.Exception404;
import com.example.product.option.Option;
import com.example.product.option.OptionRepositry;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final OptionRepositry optionRepositry;

    // 전체 상품 검색
    public List<ProductResponse.FindAllDto> findAll(int page) {

        Pageable pageable = PageRequest.of(page,3);
        Page<Product> productPage = productRepository.findAll(pageable);
                                        //이 변수에 넣어줌
        List<ProductResponse.FindAllDto> productDtos =
                                                      //뭘 생성할 지       //비어있는 기본 생성자 생성
                productPage.getContent().stream().map(ProductResponse.FindAllDto::new)
                .collect(Collectors.toList());
        return productDtos;
    }

    // 개별 상품 검색
    // Dto 안에 List가 포함 되어 있기 때문에 (list가 아닌)dto로 바로 반환 하면 됨
    public ProductResponse.FindByIdDto findById(Long id) {
        // 상품을 id로 바로 찾을 수 있지만 상품이 없을 수도 있기 때문에 예외 처리
        Product product = productRepository.findById(id).orElseThrow(
                () -> new Exception404("해당 상품을 찾을 수 없습니다.: " + id));

        // 나는 하나만 가지고 들고 왔는데 실제 상품이 하나가 아닐 수도 있다?
        // 내가 상품 하나를 찾았는 데, 그 하나에 들어 있는 제품이 하나가 아닐 수 있어

        // 따라서 id를 product.getId()로 Option 상품 검색
        List<Option> optionList = optionRepositry.findByProductId(product.getId());

        // 검색이 완료된 제품 반환
        return new ProductResponse.FindByIdDto(product, optionList);
    }

    @Transactional
    public ProductResponse.CreateDto save(ProductResponse.CreateDto createDto) {
        Product product = new Product(createDto); // DTO에서 Product 객체 생성
        productRepository.save(product);
        return new ProductResponse.CreateDto(product); // 생성된 Product 객체를 이용해 CreateDto 객체 생성 및 반환
    }

    @Transactional
    public void delete(Long id){
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("상품 id가 존재하지 않습니다."));
        productRepository.delete(product);
    }

    @Transactional
    public Product update(Long id, ProductResponse.UpdateDto updateDto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("상품 id가 존재하지 않습니다."));
        product.updateProduct(updateDto.getProductName(),
                updateDto.getDescription(),
                updateDto.getImage(),
                updateDto.getPrice());

        return productRepository.save(product);
    }
}
