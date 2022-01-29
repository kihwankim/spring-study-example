package com.example.springrediscache.application;

import com.example.springrediscache.infrastucture.domain.Zone;
import com.example.springrediscache.infrastucture.repository.ZoneRepository;
import com.example.springrediscache.presentation.dto.ZoneCreationRequest;
import com.example.springrediscache.presentation.dto.ZoneResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ZoneService {
    private final ZoneRepository zoneRepository;

    @Transactional
    public Long saveZone(ZoneCreationRequest request) {
        Zone zone = ZoneCreationRequest.from(request);
        Zone savedZone = zoneRepository.save(zone);
        return savedZone.getId();
    }

    public ZoneResponse readZone(Long id) {
        Zone findZone = zoneRepository.findById(id).orElseThrow();

        return ZoneResponse.from(findZone);
    }
}
