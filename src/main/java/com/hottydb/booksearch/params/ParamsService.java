package com.hottydb.booksearch.params;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParamsService {
    @Autowired
    ParamsRepository paramsRepository;

    public long count() {
        return paramsRepository.count();
    }

    public ParamsEntity save(ParamsEntity paramsEntity) {
        return paramsRepository.save(paramsEntity);
    }

    public List<ParamsEntity> findAll() {
        return paramsRepository.findAll();
    }
}
