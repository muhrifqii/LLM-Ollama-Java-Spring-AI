package com.muhrifqii.springbed.services;

import com.muhrifqii.springbed.domains.Bed;
import com.muhrifqii.springbed.repositories.BedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class BedService {
    private final BedRepository bedRepository;

    @Transactional(readOnly = true)
    public Mono<Bed> findBedById(String id) {
        return bedRepository.findByIdentifier(id);
    }

    public Mono<Bed> addNewBed(Bed bed){
        return bedRepository.save(bed);
    }

    public Mono<Page<Bed>> getBed(PageRequest pageRequest) {
        return bedRepository.findAllBy(pageRequest)
                .collectList()
                .zipWith(bedRepository.count())
                .map(p -> new PageImpl<>(p.getT1(), pageRequest, p.getT2()));
    }
}
