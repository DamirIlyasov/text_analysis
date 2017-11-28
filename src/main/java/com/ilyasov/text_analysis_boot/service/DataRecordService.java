package com.ilyasov.text_analysis_boot.service;

import com.ilyasov.text_analysis_boot.entity.DataRecord;

import java.util.List;

public interface DataRecordService {
    void save(DataRecord dataRecord);
    List<DataRecord> getAll();
}
