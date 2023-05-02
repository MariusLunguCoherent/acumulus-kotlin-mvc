package com.acumulus.acumulusassignment.repositories

import com.acumulus.acumulusassignment.entities.UserAccount
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserAccountRepository: JpaRepository<UserAccount, Long>{

  fun findUserEntityByEmail(email: String): UserAccount

  fun findUserAccountById(id: Long): UserAccount


}