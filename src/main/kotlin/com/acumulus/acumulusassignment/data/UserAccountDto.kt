package com.acumulus.acumulusassignment.data

import com.fasterxml.jackson.databind.ObjectMapper

class UserAccountDto {

     var id: Long? = null
     lateinit var accountEmail: String;


     constructor(id: Long?, accountEmail: String) {
          this.id = id
          this.accountEmail = accountEmail
     }

     constructor()

     fun mapDtoToJson(): String{
          return ObjectMapper().writeValueAsString(this)
     }

}