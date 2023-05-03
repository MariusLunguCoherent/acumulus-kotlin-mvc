package com.acumulus.acumulusassignment.repositories

import com.acumulus.acumulusassignment.entities.Toppings
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface ToppingsRepository: JpaRepository<Toppings, Long> {
    fun findToppingsByToppingContaining(topping: String): List<Toppings>
    fun findAllByToppingContains(toppings: String): Toppings?
    fun findToppingsById(userId: Long): List<Toppings>

    @Query("SELECT t.id FROM Toppings t WHERE  t.topping = :toppingValue")
    fun  findByToppingValue(@Param("toppingValue") toppingsValues: String): Long

    @Query(value ="SELECT count(DISTINCT user_toppings.useraccount_id)from user_toppings where user_toppings.topping_id = :toppingId", nativeQuery = true)
    fun findUserDistinctById(@Param("toppingId") toppinhgId: Long): Long

    @Query(value ="SELECT * FROM Toppings as t  where t.topping IN (:toppings) ", nativeQuery = true)
    fun  findAllToppingsByValue(toppings: MutableSet<String>) : MutableSet<Toppings>

}