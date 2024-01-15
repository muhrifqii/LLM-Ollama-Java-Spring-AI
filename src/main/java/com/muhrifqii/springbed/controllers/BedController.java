package com.muhrifqii.springbed.controllers;

import com.muhrifqii.springbed.domains.Bed;
import com.muhrifqii.springbed.dto.CreateBedRequest;
import com.muhrifqii.springbed.services.BedService;
import com.muhrifqii.springbed.services.mappers.BedMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/bed")
@RequiredArgsConstructor
@Slf4j(topic = "BedController")
public class BedController {

    private final BedService bedService;
    private final BedMapper bedMapper;

    @GetMapping
    public Mono<Page<Bed>> getBed(@RequestParam int page, @RequestParam int size) {
        return bedService.getBed(PageRequest.of(page, size));
    }

    @PostMapping
    public Mono<ResponseEntity<Void>> addNewBed(
            @RequestBody CreateBedRequest request,
            UriComponentsBuilder uriComponentsBuilder
    ) {
        return bedService.addNewBed(bedMapper.map(request))
                .map(item -> {
                    final var createdLocation = uriComponentsBuilder.pathSegment("bed", "{id}")
                            .buildAndExpand(item.identifier())
                            .toUri();
                    return ResponseEntity.created(createdLocation).build();
                });
    }

    @GetMapping("/{id}")
    public Mono<Bed> getBedById(@PathVariable String id) {
        return bedService.findBedById(id);
    }
}
