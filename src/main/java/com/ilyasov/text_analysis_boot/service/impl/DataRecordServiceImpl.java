package com.ilyasov.text_analysis_boot.service.impl;

import com.ilyasov.text_analysis_boot.entity.DataRecord;
import com.ilyasov.text_analysis_boot.repository.DataRecordRepository;
import com.ilyasov.text_analysis_boot.service.DataRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataRecordServiceImpl implements DataRecordService {
    @Autowired
    private DataRecordRepository dataRecordRepository;

    @Override
    public void save(DataRecord dataRecord) {
        dataRecordRepository.save(dataRecord);
    }

    @Override
    public List<DataRecord> getAll() {
        return dataRecordRepository.findAll();
    }
}
