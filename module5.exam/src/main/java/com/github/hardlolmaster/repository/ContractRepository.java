package com.github.hardlolmaster.repository;

import com.github.hardlolmaster.domain.PropertyInsuranceContract;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreFilter;

import java.util.List;
import java.util.Optional;

public interface ContractRepository extends PagingAndSortingRepository<PropertyInsuranceContract, Long>
{
    Optional<PropertyInsuranceContract> findByNumber(String number);

    Boolean existsByNumber(String number);

    @Override
    @PostFilter("filterObject!=null ? hasPermission(filterObject, 'READ') : false")
    List<PropertyInsuranceContract> findAll();
}
