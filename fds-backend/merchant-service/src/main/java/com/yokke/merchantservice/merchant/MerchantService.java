package com.yokke.merchantservice.merchant;

import com.yokke.base.exception.response_status.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
@EnableTransactionManagement
public class MerchantService {
    private final MerchantRepository merchantRepository;
    private final MerchantMapper merchantMapper;

    public Page<MerchantDto> read(Pageable pageable){
        return merchantRepository.findAll(pageable)
                .map(merchantMapper::mapToDto);
    }

    public MerchantDto read(String merchantId){
        return merchantRepository.findByMid(merchantId)
                .map(merchantMapper::mapToDto)
                .orElseThrow(() -> new NotFoundException("Merchant not found"));
    }
}
