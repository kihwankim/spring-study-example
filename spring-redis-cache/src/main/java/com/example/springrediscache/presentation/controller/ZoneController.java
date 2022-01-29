package com.example.springrediscache.presentation.controller;

import com.example.springrediscache.application.ZoneService;
import com.example.springrediscache.presentation.dto.ZoneCreationRequest;
import com.example.springrediscache.presentation.dto.ZoneResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@Transactional
@RequiredArgsConstructor
public class ZoneController {
    private final ZoneService zoneService;

    @PostMapping("/zones")
    public ResponseEntity<URI> createZone(@RequestBody ZoneCreationRequest request) {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(zoneService.saveZone(request))
                .toUri();

        return ResponseEntity.ok(uri);
    }

    @GetMapping("/zones/{id}")
    public ResponseEntity<ZoneResponse> findZone(@PathVariable("id") Long id) {
        return ResponseEntity.ok(zoneService.readZone(id));
    }

    @DeleteMapping("/zones/{id}")
    public ResponseEntity<Void> deleteZoneById(@PathVariable("id") Long id) {
        zoneService.deleteZone(id);

        return ResponseEntity.ok().build();
    }
}
