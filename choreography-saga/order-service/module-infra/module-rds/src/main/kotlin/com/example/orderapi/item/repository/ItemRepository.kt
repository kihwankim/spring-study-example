package com.example.orderapi.item.repository

import com.example.orderapi.item.entity.ItemEntity
import org.springframework.data.jpa.repository.JpaRepository

internal interface ItemRepository : JpaRepository<ItemEntity, Long>