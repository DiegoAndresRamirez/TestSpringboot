package com.riwi.industry.domain.persistence;

import com.riwi.industry.domain.models.Pallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PalletRepository extends JpaRepository<Pallet, Long> {
}
