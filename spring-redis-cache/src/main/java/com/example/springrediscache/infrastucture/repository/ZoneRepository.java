package com.example.springrediscache.infrastucture.repository;

import com.example.springrediscache.infrastucture.domain.Zone;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ZoneRepository extends JpaRepository<Zone, Long> {
    @Cacheable(value = "zone", key="#p0", unless = "#result == null")
    Optional<Zone> findById(Long id);
    /**
     * value: key와 같이 사용되어 캐시의 키값으로 사용
     * key: value와 같이 사용되는 key 값
     * #p0: 첫번 째 파라미터 -> 파라미터가 객체라면 #p0.userId와 같이 사용됨
     * unless: 조건에 맞으면 캐시로 지정하지 않는다른 뜻입니다
     * Zone 엔티티에 반드시 implements Serializable 가 필요
     */
}
