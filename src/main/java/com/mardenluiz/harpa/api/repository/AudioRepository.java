package com.mardenluiz.harpa.api.repository;

import com.mardenluiz.harpa.api.domain.Audio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AudioRepository extends JpaRepository<Audio, UUID> {

    Optional<Audio> findByHymn_Number(int number);
    boolean existsByHymn_Number(int number);
}
