package com.example.springrediscache.application;

import com.example.springrediscache.infrastucture.domain.Zone;
import com.example.springrediscache.infrastucture.repository.ZoneRepository;
import com.example.springrediscache.presentation.dto.ZoneCreationRequest;
import com.example.springrediscache.presentation.dto.ZoneResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
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

    @Cacheable(value = "zone", key = "#p0", unless = "#result == null")
    public ZoneResponse readZone(Long id) {
        Zone findZone = zoneRepository.findById(id).orElseThrow();

        return ZoneResponse.from(findZone);
    }

    @Transactional
    @CachePut(value = "zone", key = "#p0", unless = "#result == null")
    public ZoneResponse updateZoneName(Long id, String newName) {
        Zone findZone = zoneRepository.findById(id).orElseThrow();
        findZone.updateName(newName);

        return ZoneResponse.from(findZone);
    }

    @Transactional
    @CacheEvict(value = "zone", key = "#p0", allEntries = true)
    public void deleteZone(Long id) {
        Zone findZone = zoneRepository.findById(id).orElseThrow();
        zoneRepository.delete(findZone);
    }
    /**
     * value: key와 같이 사용되어 캐시의 키값으로 사용
     * key: value와 같이 사용되는 key 값
     * #p0: 첫번 째 파라미터 -> 파라미터가 객체라면 #p0.userId와 같이 사용됨
     * unless: 조건에 맞으면 캐시로 지정하지 않는다른 뜻입니다
     * Zone 엔티티에 반드시 implements Serializable 가 필요
     */
}
