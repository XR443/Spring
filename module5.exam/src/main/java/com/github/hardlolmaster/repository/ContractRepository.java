package com.github.hardlolmaster.repository;

import com.github.hardlolmaster.domain.PropertyInsuranceContract;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface ContractRepository extends PagingAndSortingRepository<PropertyInsuranceContract, Long> {
    Optional<PropertyInsuranceContract> findByNumber(String number);

    @Override
    List<PropertyInsuranceContract> findAll();
}
