package com.yokke.usermanagement.transactions.fds_transactions_audit;


import com.yokke.usermanagement.transactions.fds_parameter.FdsParameterDto;
import com.yokke.usermanagement.transactions.fds_parameter.FdsParameterMapper;
import com.yokke.usermanagement.transactions.fds_parameter.FdsParameterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class FdsParameterService {
    private final FdsParameterRepository fdsParameterRepository;
    private final FdsParameterMapper fdsParameterMapper;

    public Page<FdsParameterDto> read(
            String category,
            Pageable pageable
    ) {
        return
                fdsParameterRepository.findByFdsParameterCategoryIgnoreCase(
                        category, pageable
                ).map(
                        fdsParameter -> fdsParameterMapper.mapToDto(
                                fdsParameter, new FdsParameterDto()
                        )
                );
    }
}
