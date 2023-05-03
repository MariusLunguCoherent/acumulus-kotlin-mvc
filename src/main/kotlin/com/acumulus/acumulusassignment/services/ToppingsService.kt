package com.acumulus.acumulusassignment.services

import com.acumulus.acumulusassignment.data.RegisterUsedAndToppingsDTO
import com.acumulus.acumulusassignment.data.UserAccountDto
import com.acumulus.acumulusassignment.entities.Toppings
import com.acumulus.acumulusassignment.entities.UserAccount
import com.acumulus.acumulusassignment.exceptions.UserNotFoundException
import com.acumulus.acumulusassignment.repositories.ToppingsRepository
import com.acumulus.acumulusassignment.repositories.UserAccountRepository
import com.acumulus.acumulusassignment.util.MapDtoToEntity
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ToppingsService(
    private val userAccountRepository: UserAccountRepository,
    private val toppingsRepository: ToppingsRepository
) {


    @Transactional
    fun registerUser(newUserAndToppings: RegisterUsedAndToppingsDTO): UserAccount {
        var toppingsInDb = toppingsRepository.findAllToppingsByValue(newUserAndToppings.toppingsPrefered)

        toppingsInDb.stream().forEach { topping -> newUserAndToppings.toppingsPrefered.remove(topping.topping) }

        //   newUserAndToppings.toppingsPrefered.removeAll(toppingsInDb as Collection<*>)
        val userDataEntity = MapDtoToEntity.mapDtoToEntity(userDto = newUserAndToppings)
        MapDtoToEntity.updateDtoToppingsWithExistingOnes(userDataEntity, toppingsInDb)
        return userAccountRepository.save(userDataEntity)
    }

    fun getDistinctUsers(topping: String): MutableSet<UserAccountDto> {
        val toppingsForUniqueUsers = toppingsRepository.findToppingsByToppingContaining(topping)

        val userIds: MutableSet<Long?> = mutableSetOf()

        for (topping in toppingsForUniqueUsers)
            userIds.add(topping.id)

        val listOfUniqueUsers = userAccountRepository.findAllById(userIds);

        val uniqueUsers: MutableSet<UserAccountDto> = mutableSetOf()

        for (userAccount in listOfUniqueUsers)
            uniqueUsers.add(MapDtoToEntity.mapUserAccountEntityToUserAccountDto(userAccount))
        return uniqueUsers
    }


    fun countDistinctUsers(topping: String): Long {
        val idTopping = toppingsRepository.findByToppingValue(topping)
        println("idTopping found : $idTopping")
        val idUser = toppingsRepository.findUserDistinctById(idTopping)
        print("idUser : $idUser")
        return idUser

    }

    @Transactional
    fun updateToppings(id: Long, toppings: MutableSet<String>) {
        val userAccount = userAccountRepository.findUserAccountById(id)
        userAccount.removeAllToppings()
        for (top in toppings)
            userAccount.addTopping(Toppings(top))

        userAccountRepository.saveAndFlush(userAccount)
    }


    @Transactional
    fun saveDevFavoriteToppings(toppings: MutableSet<String>) {
        val devAccount = UserAccount()
        devAccount.email = "dev@gmail.com"
        for (top in toppings)
            devAccount.addTopping(Toppings(top))

        userAccountRepository.saveAndFlush(devAccount)
    }

    @Throws(UserNotFoundException::class)
    fun getUserTopping(userId: Long): RegisterUsedAndToppingsDTO {
        // val userAccount = toppingsRepository.findUserEntityByEmail(userId)
        lateinit var userAccount: UserAccount
        try {
            userAccount = userAccountRepository.findUserAccountById(userId)
        } catch (e: EmptyResultDataAccessException) {
            throw UserNotFoundException("User is not in database")
        }

        return MapDtoToEntity.mapEntityToDto(userAccount)
    }
}