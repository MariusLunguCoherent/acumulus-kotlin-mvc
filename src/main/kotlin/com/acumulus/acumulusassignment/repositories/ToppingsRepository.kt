package com.acumulus.acumulusassignment.repositories

import com.acumulus.acumulusassignment.entities.Toppings
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface ToppingsRepository: JpaRepository<Toppings, Long> {
    fun findToppingsByToppingContaining(topping: String): List<Toppings>
    fun findAllByToppingContains(toppings: String): Toppings?
    fun findToppingsById(userId: Long): List<Toppings>

}