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

    public List<OptionResponse.FindByProductIdDto> findByProductId(Long id){
        List<Option> optionList = optionRepositry.findByProductId(id);
        List <OptionResponse.FindByProductIdDto> optionResponses =
                optionList.stream().map(OptionResponse.FindByProductIdDto::new)
                        .collect(Collectors.toList());
        return optionResponses;
    }

    public List<OptionResponse.FindAllDto> findAll() {
        List<Option> optionList = optionRepositry.findAll();
        List<OptionResponse.FindAllDto> findAllDtos =
                optionList.stream().map(OptionResponse.FindAllDto::new)
                        .collect(Collectors.toList());
        return findAllDtos;
    }

    @Transactional
    public OptionResponse.CreateDto save(OptionResponse.CreateDto createDto) {
        Product product = productRepository.findById(createDto.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("상품 id가 존재 하지 않습니다."));
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

    @Transactional
    public Option update(Long id,OptionResponse.UpdateDto updateDto){
        Optional<Option> optionOptional = optionRepositry.findById(id);

        if(optionRepositry.findById(id).isPresent()){
            Option option = optionOptional.get();
            Product product = productRepository.findById(updateDto.getProductId())
                    .orElseThrow(() -> new IllegalArgumentException("상품 id가 존재 하지 않습니다."));
            option.updateOption(updateDto.getOptionName(),
                    updateDto.getPrice(),
                    product,
                    updateDto.getQuantity());
            return optionRepositry.save(option);
        }else {
            throw new IllegalArgumentException("해당 옵션이 존재 하지 않습니다.");
        }
    }
}
