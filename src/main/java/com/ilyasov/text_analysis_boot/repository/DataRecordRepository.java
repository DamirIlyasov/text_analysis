package com.ilyasov.text_analysis_boot.repository;

import com.ilyasov.text_analysis_boot.entity.DataRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataRecordRepository extends JpaRepository<DataRecord,Long> {
}
