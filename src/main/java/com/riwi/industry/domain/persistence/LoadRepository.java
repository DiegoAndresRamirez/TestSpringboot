package com.riwi.industry.domain.persistence;

import com.riwi.industry.domain.models.Load;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoadRepository extends JpaRepository<Load, Long> {
    Load findByCode(String code);

}
