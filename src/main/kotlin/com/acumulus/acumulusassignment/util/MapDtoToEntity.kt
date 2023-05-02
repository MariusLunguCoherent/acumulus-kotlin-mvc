package com.acumulus.acumulusassignment.util

import com.acumulus.acumulusassignment.data.RegisterUsedAndToppingsDTO
import com.acumulus.acumulusassignment.data.UserAccountDto
import com.acumulus.acumulusassignment.entities.Toppings
import com.acumulus.acumulusassignment.entities.UserAccount

class MapDtoToEntity {

    companion object {
        fun mapDtoToEntity(userDto: RegisterUsedAndToppingsDTO): UserAccount {
            val userAccountEntity = UserAccount()
            userAccountEntity.email = userDto.email
            for(topping in userDto.toppingsPrefered)
                userAccountEntity.toppings.add(Toppings(topping))
            return userAccountEntity
        }

        fun updateDtoToppingsWithExistingOnes(userAccountEntity: UserAccount, toppings: MutableSet<Toppings>): UserAccount {

            for(t in toppings){
                userAccountEntity.addTopping(t)
            }
            return userAccountEntity
        }

        fun mapEntityToDto(userAccount: UserAccount): RegisterUsedAndToppingsDTO {
            val entityDto  = RegisterUsedAndToppingsDTO()
            entityDto.email = userAccount.email

            for(topping in userAccount.toppings)
                entityDto.toppingsPrefered.add(topping.topping)
            return entityDto
        }
        fun mapUserAccountEntityToUserAccountDto(userAccount: UserAccount): UserAccountDto{
            val userAccountDto = UserAccountDto()
            userAccountDto.accountEmail = userAccount.email
            userAccountDto.id  = userAccount.id
            return  userAccountDto
        }
    }

}