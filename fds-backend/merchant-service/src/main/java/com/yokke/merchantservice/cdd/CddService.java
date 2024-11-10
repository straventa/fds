package com.yokke.merchantservice.cdd;


import com.yokke.base.exception.response_status.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
@EnableTransactionManagement
public class CddService {
    private final CddRepository cddRepository;
    private final CddMapper cddMapper;

    public CddDto readByByDtlCdId(String cmmnCdId) {
        return cddRepository.findByDtlCdId(cmmnCdId)
                .map(CddMapper::mapToDto)
                .orElseThrow(() -> new NotFoundException("Cdd not found"));
    }
}
