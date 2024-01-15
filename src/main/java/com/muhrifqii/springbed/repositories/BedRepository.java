package com.muhrifqii.springbed.repositories;

import com.muhrifqii.springbed.domains.Bed;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface BedRepository extends ReactiveCrudRepository<Bed, Long> {
    Flux<Bed> findAllBy(Pageable pageable);

    Flux<Bed> findAllByCategory(Bed.Category category, Pageable pageable);

    Mono<Bed> findByIdentifier(String identifier);

    int countByCategory(Bed.Category category);
}
