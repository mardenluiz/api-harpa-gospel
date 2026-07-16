package com.mardenluiz.harpa.api.repository;

import com.mardenluiz.harpa.api.domain.Hymn;
import com.mardenluiz.harpa.api.dto.HymnDto;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface HymnRepository extends JpaRepository<Hymn, UUID> {

    boolean existsByNumber(Integer number);
    Optional<Hymn> findByNumber(int number);

  //  Optional<Hymn> findByTitle(String title);

    @Query(value = """
        SELECT *
        FROM hymn
        WHERE unaccent(title)
        ILIKE unaccent(CONCAT('%', :title, '%'))
    """, nativeQuery = true) Optional<Hymn> searchByTitle(String title);
}
