package com.example.orderapi.outbox.repository;

import com.example.orderapi.outbox.entity.OrderOutBoxEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderOutBoxRepository extends JpaRepository<OrderOutBoxEntity, Long> {

}