package com.mardenluiz.harpa.api.repository;

import com.mardenluiz.harpa.api.domain.Hymn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface HymnRepository extends JpaRepository<Hymn, UUID> {

    boolean existsByNumber(Integer number);
    Optional<Hymn> findByNumber(int number);
}
