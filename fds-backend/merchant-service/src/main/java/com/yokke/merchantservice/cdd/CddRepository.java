package com.yokke.merchantservice.cdd;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CddRepository extends JpaRepository<Cdd, String> {
    @Query(value = """
                SELECT
                    *                        
                FROM
                    TBAZCMMNCDDTL_MST                        
                WHERE
                    CMMN_CD_ID = 'MCA0114'                            
                    AND DTL_CD_ID = :dtlCdId  FETCH FIRST 1 ROW ONLY
            """, nativeQuery = true)
    Optional<Cdd> findByDtlCdId(@Param("dtlCdId") String dtlCdId);

}