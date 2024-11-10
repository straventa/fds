package com.yokke.usermanagement.transactions.fds_parameter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FdsParameterRepository extends JpaRepository<FdsParameter, String> {
    @Query("""
            select f from FdsParameter f 
            where (:fdsParameterCategory is null or f.fdsParameterCategory = :fdsParameterCategory)
            """)
    Page<FdsParameter> findByFdsParameterCategoryIgnoreCase(
            @Param("fdsParameterCategory") String fdsParameterCategory,
            Pageable pageable
    );
}