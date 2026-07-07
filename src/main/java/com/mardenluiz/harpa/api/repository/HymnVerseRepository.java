package com.mardenluiz.harpa.api.repository;

import com.mardenluiz.harpa.api.domain.HymnVerse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface HymnVerseRepository extends JpaRepository<HymnVerse, UUID> {
}
