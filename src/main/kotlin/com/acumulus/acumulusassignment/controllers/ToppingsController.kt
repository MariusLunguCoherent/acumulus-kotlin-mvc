package com.acumulus.acumulusassignment.controllers

import com.acumulus.acumulusassignment.data.RegisterUsedAndToppingsDTO
import com.acumulus.acumulusassignment.data.ToppingUpdateDto
import com.acumulus.acumulusassignment.exceptions.DuplicateUserException
import com.acumulus.acumulusassignment.exceptions.UserNotFoundException
import com.acumulus.acumulusassignment.services.ToppingsService
import com.acumulus.acumulusassignment.util.MapDtoToEntity
import org.jetbrains.annotations.NotNull
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class ToppingsController(val service: ToppingsService) {

    @Throws(UserNotFoundException::class)
    @GetMapping("/api/v1/{userId}/toppings")
    fun getToppings(@PathVariable("userId") userEmail: Long): ResponseEntity<String> {
        val userDTO = service.getUserTopping(userEmail)
        return ResponseEntity.status(HttpStatus.OK).body(userDTO.toppingsPrefered.toString())

    }

    @Throws(DuplicateUserException::class)
    @PostMapping("/api/v1/userAndToppings")
    fun postToppings(@RequestBody @NotNull toppings: RegisterUsedAndToppingsDTO): ResponseEntity<String> {
        println("Payload received : $toppings")
        val userAccount = service.registerUser(toppings)
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(MapDtoToEntity.mapUserAccountEntityToUserAccountDto(userAccount).mapDtoToJson())
    }

    @PostMapping("/api/v1/devFavoriteToppings")
    fun postDevFavoriteToppings(@RequestBody favoriteToppings: ToppingUpdateDto): ResponseEntity<String> {
        service.saveDevFavoriteToppings(favoriteToppings.toppingsPrefered)
        return ResponseEntity.status(HttpStatus.CREATED).body("")
    }

    @Throws(UserNotFoundException::class)
    @PutMapping("/api/v1/{userId}/all-toppings")
    fun changeToppings(
        @PathVariable("userId") userId: Long,
        @RequestBody @NotNull toppingsUpdate: ToppingUpdateDto

    ): ResponseEntity<String> {
        println(toppingsUpdate)
        service.updateToppings(userId, toppingsUpdate.toppingsPrefered)
        return ResponseEntity.status(HttpStatus.CREATED).body("")
    }

    @GetMapping("/api/v1/getDistictUsers")
    fun getUniqueUserNumberWithTopping(
        @RequestParam(
            name = "topping",
            required = true,
            defaultValue = "Mozzarella"
        ) topping: String
    ): ResponseEntity<Long> {
        val distinctUsers = service.countDistinctUsers(topping)
        return ResponseEntity.status(HttpStatus.valueOf(200)).body(distinctUsers)
    }

    @GetMapping("/api/v1/testDistinctUsers")
    fun testGetUniqueUsers(@RequestParam (name ="topping") topping: String): Long {
        return service.countDistinctUsers(topping)
    }

}