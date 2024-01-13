package com.example.fallenangels.repository.Database2;

import com.example.fallenangels.models.Database2.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(transactionManager = "db2TransactionManager")
public interface CountryRepository extends JpaRepository<Country,Integer> {

}
