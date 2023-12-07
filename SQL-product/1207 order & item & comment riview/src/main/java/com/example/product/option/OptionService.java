package com.example.product.option;

import com.example.product.product.Product;
import com.example.product.product.ProductRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class OptionService {
    private final OptionRepositry optionRepositry;
    private final ProductRepository productRepository;

    // 리스트 형태로 반환
    public List<OptionResponse.FindByProductIdDto> findByProductId(Long id){
        // id 값으로 제품 id 찾아서 옵션 리스트에 넣어 줌
        List<Option> optionList = optionRepositry.findByProductId(id);
        // optionList에 저장된 각 옵션을 Dto 형태로 변환 하여 optionResponses 리스트에 저장
        List <OptionResponse.FindByProductIdDto> optionResponses =
                optionList.stream().map(OptionResponse.FindByProductIdDto::new)
                        .collect(Collectors.toList());
        return optionResponses;
    }

    // 모든 옵션 찾아서 리스트로 반환
    public List<OptionResponse.FindAllDto> findAll() {
        List<Option> optionList = optionRepositry.findAll();
        List<OptionResponse.FindAllDto> findAllDtos =
                optionList.stream().map(OptionResponse.FindAllDto::new)
                        .collect(Collectors.toList());
        return findAllDtos;
    }

    // CreatDto에 새로운 옵션 값 저장
    @Transactional
    public OptionResponse.CreateDto save(OptionResponse.CreateDto createDto) {
        // Dto 제품 id 값으로 제품 찾기
        Product product = productRepository.findById(createDto.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("상품 id가 존재 하지 않습니다."));
        // createDto, product 로 새로운 Option 객체를 생성
        Option option = new Option(createDto,product);
        optionRepositry.save(option);
        return new OptionResponse.CreateDto(option);
    }

    @Transactional
    public void delete(Long id) {
        Optional<Option> option = optionRepositry.findById(id);
        if(optionRepositry.findById(id).isPresent()){
            Option optionDelete = option.get();
            optionRepositry.delete(optionDelete);
        }else {
            throw new IllegalArgumentException("해당 옵션이 존재 하지 않습니다.");
        }
    }

    // 옵션 업데이트
    @Transactional
    public Option update(Long id,OptionResponse.UpdateDto updateDto){
        // 옵션 id 찾아서 저장
        Optional<Option> optionOptional = optionRepositry.findById(id);

        // 찾은 id가 DB에 존재 한다면
        if(optionRepositry.findById(id).isPresent()){
            Option option = optionOptional.get();
            // updateDto에 저장된 제품 ID로 제품을 찾아 product에 저장
            Product product = productRepository.findById(updateDto.getProductId())
                    .orElseThrow(() -> new IllegalArgumentException("상품 id가 존재 하지 않습니다."));
           // 변경된 이름, 가격, 제품, 수량 저장
            option.updateOption(updateDto.getOptionName(),
                    updateDto.getPrice(),
                    product,
                    updateDto.getQuantity());
            // 변경된 옵션 값 저장 후 반환
            return optionRepositry.save(option);
        }else {
            throw new IllegalArgumentException("해당 옵션이 존재 하지 않습니다.");
        }
    }
}
