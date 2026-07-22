package com.mardenluiz.harpa.api.domain.repository;

import com.mardenluiz.harpa.api.domain.model.Hymn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface HymnRepository extends JpaRepository<Hymn, UUID> {

    boolean existsByNumber(Integer number);
    Optional<Hymn> findByNumber(int number);

    @Query(value = """
    SELECT *
    FROM hymn
    WHERE lower(unaccent(title)) = lower(unaccent(:title))
    """, nativeQuery = true)
    Optional<Hymn> searchByTitle(@Param("title") String title);
}
