package com.yokke.merchantservice.owner;

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
public class OwnerService {
    private final OwnerRepository ownerRepository;
    private final OwnerMapper ownerMapper;

    public Page<OwnerDto> read(Pageable pageable){
        return ownerRepository.findAll(pageable)
                .map(ownerMapper::mapToDto);
    }


    public OwnerDto read(String ownerId){
        return ownerRepository.findByMid(ownerId)
                .map(ownerMapper::mapToDto)
                .orElseThrow(() -> new NotFoundException("Owner not found"));
    }
}
